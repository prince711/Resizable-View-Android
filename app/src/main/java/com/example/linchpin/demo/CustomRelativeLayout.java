package com.example.linchpin.demo;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by linchpin on 16/1/17.
 */

public class CustomRelativeLayout extends LinearLayout implements View.OnTouchListener {

    private int screenWidth;
    private int screenHeight;
    private PointF DownPT = null;
    private PointF StartPT = null;
    private boolean isMoveEnabled;

    public boolean isMoveEnabled() {
        return isMoveEnabled;
    }

    public void setMoveEnabled(boolean moveEnabled) {
        isMoveEnabled = moveEnabled;
    }


    public CustomRelativeLayout(Context context) {
        super(context);
        this.getScreenSize();
        this.setOnTouchListener(this);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.getScreenSize();
        this.setOnTouchListener(this);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.getScreenSize();
        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                DownPT.x = event.getX();
                DownPT.y = event.getY();
                StartPT = new PointF(this.getX(), this.getY());
                Log.e("get points : ", this.getX() + "" + this.getY());
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                PointF mv = new PointF(event.getX() - DownPT.x, event.getY()
                        - DownPT.y);
                int imgWidth = this.getWidth();
                int imgHeight = this.getHeight();

               /* if (StartPT.x + mv.x / 15 > 0 && StartPT.x + imgWidth + mv.x / 15 < screenWidth) {
                    this.setX((int) (StartPT.x + mv.x / 10));
                    Log.d("Move X", "X :" + StartPT.x + "  Y :" + StartPT.y + "  mv.x  : " + mv.x + " mv.y" + mv.y);
                }
                if (StartPT.y + mv.y / 15 > 0 && StartPT.y + imgHeight + mv.y / 15 < screenHeight - 50) {
                    this.setY((int) (StartPT.y + mv.y / 10));
                    Log.d("Move Y", "X :" + StartPT.x + "  Y :" + StartPT.y + "  mv.x  : " + mv.x + " mv.y" + mv.y);
                }*/
                StartPT = new PointF(this.getX(), this.getY());

                break;
        }
        return true;
    }


    public void getScreenSize() {

        DownPT = new PointF();
        StartPT = new PointF();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        Log.d("Screen Dimension", "Screen Width  : " + screenWidth + "Screen Height :  " + screenHeight);

    }
}
