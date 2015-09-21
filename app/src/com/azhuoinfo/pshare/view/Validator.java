/** 
 * Copyright (c) 2013 xuewu.wei.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.azhuoinfo.pshare.view;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description Validator.java
 * @author xuewu.wei
 * @date 2013-7-14
 */
public class Validator {
	public static boolean validateNull(TextView view) {
		if (TextUtils.isEmpty(view.getText())) {
			return false;
		}
		return true;
	}

	public static boolean validateContent(TextView view) {
		String text = view.getText().toString().trim();
		if (text.length() >= 10 && text.length() <= 500) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean validateComment(TextView view) {
		String text = view.getText().toString().trim();
		if (text.length() >= 5 && text.length() <= 140) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean validateSamePassword(TextView view1,TextView view2) {
		String str1=view1.getText().toString().trim();
		String str2=view2.getText().toString().trim();
		if(str1.equals(str2)){
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean validatePassword(TextView view) {
		// ^[a-zA-Z0-9_]{6,14}$
		// [^\\s]{6,14}
		Pattern p = Pattern.compile("^[a-zA-Z0-9_]{6,14}$");
		Matcher m = p.matcher(view.getText().toString().trim());
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validateNickname(TextView view) {
		String rx = "[\\w]";
		String rx2 = "[\u4e00-\u9fa5]";
		final int MAXCOUNT = 20;
		int num = 0;
		boolean flag = true;
		String str = view.getText().toString();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (Pattern.compile(rx).matcher(c + "").matches()) {
				num += 1;
			} else if (Pattern.compile(rx2).matcher(c + "").matches()) {
				num += 2;
			} else {
				flag = false;
				break;
			}
		}

		if (num > MAXCOUNT)
			flag = false;

		if (!flag) {
			// view.setError(notnullStr);
			// view.requestFocus();
		}

		return flag;
	}

	public static boolean validatePhone(TextView view) {
		Pattern p = Patterns.PHONE;
		Matcher m = p.matcher(view.getText().toString().trim());
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean validateUsername(TextView view) {
        return validateMobile(view)||validateEmail(view);
	}
	
	public static boolean validateMobile(TextView view) {
        Pattern p  = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号  
        Matcher m = p.matcher(view.getText().toString().trim());  
        if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validateEmail(TextView view) {
		Pattern p = Patterns.EMAIL_ADDRESS;
		Matcher m = p.matcher(view.getText().toString().trim());
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validateURL(TextView view) {
		Pattern p = Patterns.WEB_URL;
		Matcher m = p.matcher(view.getText().toString().trim());
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validateIP(TextView view) {
		Pattern p = Patterns.IP_ADDRESS;
		Matcher m = p.matcher(view.getText().toString().trim());
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
}
