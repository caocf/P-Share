
package com.azhuoinfo.pshare.view;
import com.azhuoinfo.pshare.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
/**
* @Description: 
* @version $Revision: 1.0 $ 
* @author xuewu.wei
*/
public class SearchEditText extends EditText {
	private OnClickSearchListener onClickSearchListener;
    final Drawable search= getResources().getDrawable(R.drawable.search_black);
    public SearchEditText(Context context) {
        super(context);
        init();
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }


    void init()  {
        // Set bounds of our X button
        search.setBounds(0, 0, search.getIntrinsicWidth(), search.getIntrinsicHeight());      

        // There may be initial text in the field, so we may need to display the button
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                SearchEditText et = SearchEditText.this;

                // Is there an X showing?
                if (et.getCompoundDrawables()[2] == null) return false;
                // Only do this for up touches
                if (event.getAction() != MotionEvent.ACTION_UP) return false;
                // Is touch on our clear button?
                if (event.getX() > et.getWidth() - et.getPaddingRight() - search.getIntrinsicWidth()) {
                		if(onClickSearchListener!=null)
                			onClickSearchListener.onClick(v);
                		return true;
                }
                return false;
            }
        });

        addSearchButton();
    }

    void addSearchButton() {
        this.setCompoundDrawables(this.getCompoundDrawables()[0], 
                this.getCompoundDrawables()[1],
                search,
                this.getCompoundDrawables()[3]);
    }
    public void setOnClickSearchListener(OnClickSearchListener onClickSearchListener){
    	this.onClickSearchListener=onClickSearchListener;
    }
    public interface OnClickSearchListener {
    	void onClick(View v);
    }
}
