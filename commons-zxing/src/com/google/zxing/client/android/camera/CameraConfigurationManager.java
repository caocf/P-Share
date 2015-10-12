/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.client.android.camera;

import java.util.Collection;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * A class which deals with reading, parsing, and setting the camera parameters which are used to
 * configure the camera hardware.
 */
final class CameraConfigurationManager {

  private static final String TAG = "CameraConfiguration";

  // This is bigger than the size of a small screen, which is still supported. The routine
  // below will still select the default (presumably 320x240) size for these. This prevents
  // accidental selection of very low resolution on some devices.
  private static final int MIN_PREVIEW_PIXELS = 470 * 320; // normal screen
  private static final int MAX_PREVIEW_PIXELS = 1280 * 720;


  private final Context context;
  private Point screenResolution;
  private Point cameraResolution;

  CameraConfigurationManager(Context context) {
    this.context = context;
  }

  /**
   * Reads, one time, values from the camera that are needed by the app.
   */
  void initFromCameraParameters(Camera camera, int titlebarHeight, boolean bPortrait) {
    Camera.Parameters parameters = camera.getParameters();
    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = manager.getDefaultDisplay();
    int width = display.getWidth();
    int height = display.getHeight();
    // We're landscape-only, and have apparently seen issues with display thinking it's portrait 
    // when waking from sleep. If it's not landscape, assume it's mistaken and reverse them:
    if ( (!bPortrait) &&(width < height)) {
      Log.i(TAG, "Display reports portrait orientation; assuming this is incorrect");
      int temp = width;
      width = height;
      height = temp;
    }
	
	// Update height with titlebar height.
	height -= titlebarHeight;
    screenResolution = new Point(width, height);
    Log.i(TAG, "Screen resolution: " + screenResolution);
    cameraResolution = findBestPreviewSizeValue(parameters, screenResolution,false,bPortrait);
    Log.i(TAG, "Camera resolution: " + cameraResolution);
  }

  @TargetApi(8)
void setDesiredCameraParameters(Camera camera, boolean safeMode, boolean bPortrait) {
    Camera.Parameters parameters = camera.getParameters();

    if (parameters == null) {
      Log.w(TAG, "Device error: no camera parameters are available. Proceeding without configuration.");
      return;
    }

    Log.i(TAG, "Initial camera parameters: " + parameters.flatten());

    if (safeMode) {
      Log.w(TAG, "In camera config safe mode -- most settings will not be honored");
    }
    
    initializeTorch(parameters);

    String focusMode = null;
      if (safeMode) {
        focusMode = findSettableValue(parameters.getSupportedFocusModes(),
                                      Camera.Parameters.FOCUS_MODE_AUTO);
      } else {
        focusMode = findSettableValue(parameters.getSupportedFocusModes(),
                                      "continuous-picture", // Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE in 4.0+
                                      "continuous-video",   // Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO in 4.0+
                                      Camera.Parameters.FOCUS_MODE_AUTO);
      }
    // Maybe selected auto-focus but not available, so fall through here:
    if (!safeMode && focusMode == null) {
      focusMode = findSettableValue(parameters.getSupportedFocusModes(),
                                    Camera.Parameters.FOCUS_MODE_MACRO,
                                    "edof"); // Camera.Parameters.FOCUS_MODE_EDOF in 2.2+
    }
    if (focusMode != null) {
      parameters.setFocusMode(focusMode);
    }

    parameters.setPreviewSize(cameraResolution.x, cameraResolution.y);
   // Attention! Different SDK versions have different way to set orientation.
    final int nVerCodeFroyo = 8; // Build.VERSION_CODES.FROYO
    if(bPortrait){
   	 if( Build.VERSION.SDK_INT >= nVerCodeFroyo ) {
   		  camera.setDisplayOrientation(90);
   	 }
   }
//    if( Build.VERSION.SDK_INT >= nVerCodeFroyo ) {
//	 Method downPolymorphic;
//     try {
//      downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[] { int.class });
//      if (downPolymorphic != null)
//    	  downPolymorphic.invoke(camera, new Object[] { 90 });
//      } catch (Exception aException){
//    	  aException.printStackTrace();
//      }
//    }
    camera.setParameters(parameters);
  }

  Point getCameraResolution() {
    return cameraResolution;
  }

  Point getScreenResolution() {
    return screenResolution;
  }

  boolean getTorchState(Camera camera) {
    if (camera != null) {
      Camera.Parameters parameters = camera.getParameters();
      if (parameters != null) {
        String flashMode = camera.getParameters().getFlashMode();
        return flashMode != null &&
            (Camera.Parameters.FLASH_MODE_ON.equals(flashMode) ||
             Camera.Parameters.FLASH_MODE_TORCH.equals(flashMode));
      }
    }
    return false;
  }

  void setTorch(Camera camera, boolean newSetting) {
    Camera.Parameters parameters = camera.getParameters();
    doSetTorch(parameters, newSetting);
    camera.setParameters(parameters);
  }

  private void initializeTorch(Camera.Parameters parameters) {
    boolean currentSetting =  false;
    doSetTorch(parameters, currentSetting);
  }

  private void doSetTorch(Camera.Parameters parameters, boolean newSetting) {
    String flashMode;
    if (newSetting) {
      flashMode = findSettableValue(parameters.getSupportedFlashModes(),
                                    Camera.Parameters.FLASH_MODE_TORCH,
                                    Camera.Parameters.FLASH_MODE_ON);
    } else {
      flashMode = findSettableValue(parameters.getSupportedFlashModes(),
                                    Camera.Parameters.FLASH_MODE_OFF);
    }
    if (flashMode != null) {
      parameters.setFlashMode(flashMode);
    }
  }

  private Point findBestPreviewSizeValue(Camera.Parameters parameters,
   Point screenResolution,
   boolean portrait,
   boolean flag) {



    Point bestSize = null;
    int diff = Integer.MAX_VALUE;
 	 for (Camera.Size supportedPreviewSize : parameters.getSupportedPreviewSizes()) {
      int pixels = supportedPreviewSize.height * supportedPreviewSize.width;
      if (pixels < MIN_PREVIEW_PIXELS || pixels > MAX_PREVIEW_PIXELS) {
        continue;
      }
      int supportedWidth = portrait ? supportedPreviewSize.height : supportedPreviewSize.width;
      int supportedHeight = portrait ? supportedPreviewSize.width : supportedPreviewSize.height;
      int newDiff = 0;
      if( flag )
    	  newDiff = Math.abs(screenResolution.y * supportedHeight - supportedWidth * screenResolution.x);
      else
    	  newDiff = Math.abs(screenResolution.x * supportedHeight - supportedWidth * screenResolution.y);
      
      if (newDiff == 0) {
        bestSize = new Point(supportedWidth, supportedHeight);
        break;
      }
      if (newDiff < diff) {
       bestSize = new Point(supportedWidth, supportedHeight);
        diff = newDiff;
      }
    }

    if (bestSize == null) {
      Camera.Size defaultSize = parameters.getPreviewSize();
      bestSize = new Point(defaultSize.width, defaultSize.height);
      Log.i(TAG, "No suitable preview sizes, using default: " + bestSize);
    }

    Log.i(TAG, "Found best approximate preview size: " + bestSize);
    return bestSize;
  }

  private static String findSettableValue(Collection<String> supportedValues,
                                          String... desiredValues) {
    Log.i(TAG, "Supported values: " + supportedValues);
    String result = null;
    if (supportedValues != null) {
      for (String desiredValue : desiredValues) {
        if (supportedValues.contains(desiredValue)) {
          result = desiredValue;
          break;
        }
      }
    }
    Log.i(TAG, "Settable value: " + result);
    return result;
  }

}
