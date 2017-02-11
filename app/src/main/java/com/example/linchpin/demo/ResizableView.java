package com.example.linchpin.demo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by linchpin on 14/12/16.
 */
public class ResizableView extends TextView implements View.OnTouchListener {
    private float mLastTouchX;
    private float mLastTouchY;
    private float mPosX;
    private float mPosY;
    private int INVALID_POINTER_ID;
    int marginLeft;
    int marginRight;
    int marginTop;
    int marginBottom;
    private int screenWidth;
    private int screenHeight;
    private boolean isfirstCorner;
    private boolean isSecondCorner;
    private boolean isFourthCorner;
    private boolean isThirdCorner;
    private boolean isResizeOn;

    public ResizableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getScreenSize();
        setOnDragListener(new OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                int action = dragEvent.getAction();
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // do nothing
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
//                        v.setBackgroundDrawable(enterShape);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
//                        v.setBackgroundDrawable(normalShape);
                        break;
                    case DragEvent.ACTION_DROP:
                        // Dropped, reassign View to ViewGroup
//                        View view = (View) event.getLocalState();
                        ViewGroup owner = (ViewGroup) getParent();
                        owner.removeView(view);
                        RelativeLayout container = (RelativeLayout) view;
                        container.addView(view);
                        view.setVisibility(View.VISIBLE);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
//                        v.setBackgroundDrawable(normalShape);
                    default:
                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }


    public void getScreenSize() {

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        Log.d("Screen Dimension", "Screen Width  : " + screenWidth + "Screen Height :  " + screenHeight);

    }


    private int mActivePointerId = INVALID_POINTER_ID;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final float x = MotionEventCompat.getX(ev, pointerIndex);
                final float y = MotionEventCompat.getY(ev, pointerIndex);
                // Remember where we started (for dragging)
                mLastTouchX = x;
                mLastTouchY = y;
                Log.d("X         : ", "" + mLastTouchX);
                Log.d("y         : ", "" + mLastTouchY);

                getTouchListnerOnAllCorners(mLastTouchX, mLastTouchY);

                // Save the ID of this pointer (for dragging)
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                // Find the index of the active pointer and fetch its position
                final int pointerIndex =
                        MotionEventCompat.findPointerIndex(ev, mActivePointerId);

                final float x = MotionEventCompat.getX(ev, pointerIndex);
                final float y = MotionEventCompat.getY(ev, pointerIndex);
                int diffX = (int) (x - mLastTouchX);

                // Calculate the distance moved
                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;
                int width = this.getWidth();
                int height = this.getHeight();




                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                int marginLeft = params.leftMargin;
                int marginTop = params.topMargin;
                int marginRight = (screenWidth - (getWidth() + marginLeft));
                int marginBottom = (screenHeight - (getHeight() + marginTop));

                Log.d("margins : ", "" + marginLeft + " : " + marginRight + " : " + marginBottom + " : " + marginTop);


                if (isfirstCorner) {

                    params.rightMargin = marginRight;
                    params.bottomMargin = marginBottom;
                    params.leftMargin = 0;
                    params.topMargin = 0;


                } else if (isSecondCorner) {

                    params.leftMargin = marginLeft;
                    params.bottomMargin = marginBottom;
                    params.topMargin = 0;
                    params.rightMargin = 0;

                } else if (isThirdCorner) {


                    params.topMargin = marginTop;
                    params.rightMargin = marginRight;
                    params.leftMargin = 0;
                    params.bottomMargin = 0;

                } else if (isFourthCorner) {


                    params.leftMargin = marginLeft;
                    params.topMargin = marginTop;
                    params.rightMargin = 0;
                    params.bottomMargin = 0;

                }

//                Log.d("")

//                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(30, 40);
//                params.leftMargin = marginLeft;
//                params.topMargin = marginTop;
                params.height = params.height + diffX;
                params.width = params.width + diffX;
                setLayoutParams(params);
                mLastTouchX = x;


//                mPosX += dx;
//                mPosY += dy;
//                onSizeChanged(width + (int) dx, height + (int) dy, width, height);
//                invalidate();

                // Remember this touch position for the next move event
//                mLastTouchX = x;
//                mLastTouchY = y;

                break;
            }

            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {

                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);

                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = MotionEventCompat.getX(ev, newPointerIndex);
                    mLastTouchY = MotionEventCompat.getY(ev, newPointerIndex);
                    mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                }
                break;
            }
        }
        return true;
    }

    private void getTouchListnerOnAllCorners(float mLastTouchX, float mLastTouchY) {

        if (mLastTouchX < 100 && mLastTouchY < 100) {

            isfirstCorner = true;
            isSecondCorner = false;
            isThirdCorner = false;
            isFourthCorner = false;

        } else if (mLastTouchX > getWidth() - 100 && mLastTouchY < 100) {

            isfirstCorner = false;
            isSecondCorner = true;
            isThirdCorner = false;
            isFourthCorner = false;


        } else if (mLastTouchX < 100 && mLastTouchY > getHeight() - 100) {

            isfirstCorner = false;
            isSecondCorner = false;
            isThirdCorner = true;
            isFourthCorner = false;


        } else if (mLastTouchX > getWidth() - 100 && mLastTouchY > getHeight() - 100) {

            isfirstCorner = false;
            isSecondCorner = false;
            isThirdCorner = false;
            isFourthCorner = true;


        }


    }

   /* @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        // ... take into account the parent's size as needed ...
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;

    }
}
