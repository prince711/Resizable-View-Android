package com.example.linchpin.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaRouter;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by linchpin on 14/12/16.
 */
public class ResizableVideoView extends TextView implements View.OnTouchListener {
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
    private static final int MOVE_LEFT_TOP = 1;
    private static final int MOVE_TOP_RIGHT = 2;
    private static final int MOVE_RIGHT_BOTTOM = 3;
    private static final int MOVE_BOTTOM_LEFT = 4;
    private boolean isDraggingStart = true;
    private float rowX, rowY;
    private int movmentType;
    private float dX;
    private float dY;
    private int clickcount;
    private ViewGroup rootLayout;


    public ResizableVideoView(Context context, AttributeSet attrs) {
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
    public boolean onTouchEvent(MotionEvent event) {
        // Let the ScaleGestureDetector inspect all events.

                int X = (int) event.getRawX();
                int Y = (int) event.getRawY();

                int pointerCount = event.getPointerCount();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:

                        clickcount++;
                        if (clickcount % 2 == 0) {

                            if (isDraggingStart)
                                isDraggingStart = false;
                            else isDraggingStart = true;

                        }

                        if (isDraggingStart) {
                            dX = getX() - event.getRawX();
                            dY = getY() - event.getRawY();
                            break;
                        } else {

                            rowX = event.getX();
                            rowY = event.getY();


                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
//                            Position_X = X - layoutParams.leftMargin;
//                            Position_Y = Y - layoutParams.topMargin;

                            if (rowX < (layoutParams.width / 2)) {
                                if (rowY < (layoutParams.height / 2)) {
                                    movmentType = MOVE_LEFT_TOP;
                                } else {
                                    movmentType = MOVE_BOTTOM_LEFT;
                                }
                            } else {
                                if (rowY < (layoutParams.height / 2)) {
                                    movmentType = MOVE_TOP_RIGHT;
                                } else {
                                    movmentType = MOVE_RIGHT_BOTTOM;
                                }
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:

                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        break;

                    case MotionEvent.ACTION_MOVE:
//                        clickcount=0;

                        if (isDraggingStart) {
                            animate()
                                    .x(event.getRawX() + dX)
                                    .y(event.getRawY() + dY)
                                    .setDuration(0)
                                    .start();
                        }

//                        if (pointerCount == 1){
//                            RelativeLayout.LayoutParams Params = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                            Params.leftMargin = X - Position_X;
//                            Params.topMargin = Y - Position_Y;
//                            Params.rightMargin = -500;
//                            Params.bottomMargin = -500;
//                            view.setLayoutParams(Params);
//                        }
//RootLayout
//                        if (pointerCount == 2){

                        else {

                            float diffX = event.getX() - rowX;
                            float diffY = event.getY() - rowY;


                            if (diffX >= 1 || diffX <= 1 || diffY >= 1 || diffY <= 1) {

                                rowX = rowX + diffX;
                                rowY = rowY + diffY;

                                RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) getLayoutParams();
//                            layoutParams1.leftMargin = X - Position_X;
//                            layoutParams1.topMargin = Y - Position_Y;

//                            layoutParams1.width = layoutParams1.width + ((int)event.getX() - Position_X);
//                            layoutParams1.height = layoutParams1.height + ((int)event.getY() - Position_Y);

                                if (movmentType == MOVE_LEFT_TOP) {

                                    layoutParams1.leftMargin = layoutParams1.leftMargin + (int) diffX;
                                    layoutParams1.width = layoutParams1.width + (-1 * (int) diffX);
                                    layoutParams1.topMargin = layoutParams1.topMargin + (int) diffX;
                                    layoutParams1.height = layoutParams1.height + (-1 * (int) diffX);
                                    Log.d("Movement LEFT Top", "" + layoutParams1.width);

                                } else if (movmentType == MOVE_TOP_RIGHT) {

                                    layoutParams1.topMargin = layoutParams1.topMargin + (-1 * (int) diffX);
                                    layoutParams1.height = layoutParams1.height + (1 * (int) diffX);
                                    layoutParams1.width = layoutParams1.width + (int) diffX;
                                    Log.d("Movement RIGHT Top", "" + layoutParams1.width);


                                } else if (movmentType == MOVE_RIGHT_BOTTOM) {
                                    layoutParams1.width = layoutParams1.width + (int) diffX;
                                    layoutParams1.height = layoutParams1.height + (int) diffX;
                                    Log.d("Movement RIGHT BOTTOM", "" + layoutParams1.width);

                                } else {

                                    layoutParams1.height = layoutParams1.height + (-1 * (int) diffX);
                                    layoutParams1.leftMargin = layoutParams1.leftMargin + (int) diffX;
                                    layoutParams1.width = layoutParams1.width + (-1 * (int) diffX);
                                    Log.d("Movement LEFT BOTTOM", "" + layoutParams1.width);

                                }

//                        layoutParams1.width = layoutParams1.width + (int) diffX;
//                        layoutParams1.height = layoutParams1.height + (int) diffY;

                                setLayoutParams(layoutParams1);
                            }
//                        }
//
//                        //Rotation
//                        if (pointerCount == 3){
//                            //Rotate the ImageView
//                            view.setRotation(view.getRotation() + 10.0f);
//                        }
                        }
                        break;
                }

// Schedules a repaint for the root Layout.
                rootLayout.invalidate();
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
