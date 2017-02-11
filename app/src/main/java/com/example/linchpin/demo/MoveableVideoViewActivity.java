package com.example.linchpin.demo;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.VideoView;

/**
 * Created by anuj on 19/12/16.
 */

public class MoveableVideoViewActivity extends AppCompatActivity {

    private static final int MOVE_LEFT_TOP = 1;
    private static final int MOVE_TOP_RIGHT = 2;
    private static final int MOVE_RIGHT_BOTTOM = 3;
    private static final int MOVE_BOTTOM_LEFT = 4;
    private static final int MOVE_LEFT_TOP_2 = 1;
    private static final int MOVE_TOP_RIGHT_2 = 2;
    private static final int MOVE_RIGHT_BOTTOM_2 = 3;
    private static final int MOVE_BOTTOM_LEFT_2 = 4;
    private boolean isDraggingStart = true;

    // Gesture
    private GestureDetector mGestureDetector;
    private VideoView videoView1;
    private VideoView videoView2;

    // Pinch Gesture
    //private ImageView imageView;
    private float scale = 1f;
    private ScaleGestureDetector detector;

    //Resize
    private ViewGroup rootLayout;
    private int Position_X;
    private int Position_Y;
    private ViewGroup RootLayout2;
    private int Position_X2;
    private int Position_Y2;
    private float rowX, rowY;
    private int movmentType;
    private float rowX2, rowY2;
    private int movmentType2;
    private RelativeLayout.LayoutParams layoutParams;
    private RelativeLayout.LayoutParams layoutParams2;

    private String msg = MoveableVideoViewActivity.class.getSimpleName();
    private float dX;
    private float dY;
    private int clickcount;
    private float dX2;
    private float dY2;
    private int screenWidth;
    private int screenHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movable_video_view);
        getScreenSize();
        initViews();
        setListener();

        // Gesture
        //gestureDecetor();
    }

    private void initViews() {

        // Gesture
        videoView1 = (VideoView) findViewById(R.id.video_view1);
        videoView2 = (VideoView) findViewById(R.id.video_view2);

        //Pinch Gesture
        //imageView=(ImageView)findViewById(R.id.imageView);
        //detector = new ScaleGestureDetector(this,new ScaleListener());

        //Scalling
        rootLayout = (RelativeLayout) findViewById(R.id.root_layout);

    }

    private void setListener() {

        videoView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

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
                            dX2 = view.getX() - event.getRawX();
                            dY2 = view.getY() - event.getRawY();
                            break;
                        } else {

                            rowX2 = event.getX();
                            rowY2 = event.getY();

                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                            Position_X2 = X - layoutParams.leftMargin;
                            Position_Y2 = Y - layoutParams.topMargin;

                            if (rowX2 < (layoutParams.width / 2)) {
                                if (rowY2 < (layoutParams.height / 2)) {
                                    movmentType2 = MOVE_LEFT_TOP_2;
                                } else {
                                    movmentType2 = MOVE_BOTTOM_LEFT_2;
                                }
                            } else {
                                if (rowY2 < (layoutParams.height / 2)) {
                                    movmentType2 = MOVE_TOP_RIGHT_2;
                                } else {
                                    movmentType2 = MOVE_RIGHT_BOTTOM_2;
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

//                        if (event.getRawX()>)
                        if (isDraggingStart) {
                            int x = (int) event.getRawX();
                            int y = (int) event.getRawY();
                            if (x > 0 && y > 0 && x < screenWidth && y < screenHeight)
                                view.animate()
                                        .x(event.getRawX() + dX2)
                                        .y(event.getRawY() + dY2)
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
//rootLayout
//                        if (pointerCount == 2){

                        else {

                            float diffX = event.getX() - rowX2;
                            float diffY = event.getY() - rowY2;


                            if (diffX >= 1 || diffX <= 1 || diffY >= 1 || diffY <= 1) {

                                rowX2 = rowX2 + diffX;
                                rowY2 = rowY2 + diffY;

                                RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                            layoutParams1.leftMargin = X - Position_X;
//                            layoutParams1.topMargin = Y - Position_Y;

//                            layoutParams1.width = layoutParams1.width + ((int)event.getX() - Position_X);
//                            layoutParams1.height = layoutParams1.height + ((int)event.getY() - Position_Y);

                                if (movmentType2 == MOVE_LEFT_TOP_2) {

                                    layoutParams1.leftMargin = layoutParams1.leftMargin + (int) diffX;
                                    layoutParams1.width = layoutParams1.width + (-1 * (int) diffX);
                                    layoutParams1.topMargin = layoutParams1.topMargin + (int) diffX;
                                    layoutParams1.height = layoutParams1.height + (-1 * (int) diffX);
                                    Log.d("Movement LEFT Top", "" + layoutParams1.width);

                                } else if (movmentType2 == MOVE_TOP_RIGHT_2) {

                                    layoutParams1.rightMargin = layoutParams1.rightMargin + (-1 * (int) diffX);
                                    layoutParams1.height = layoutParams1.height + (1 * (int) diffX);
                                    layoutParams1.width = layoutParams1.width + (int) diffX;
                                    Log.d("Movement RIGHT Top", "" + layoutParams1.width);


                                } else if (movmentType2 == MOVE_RIGHT_BOTTOM_2) {
                                    layoutParams1.rightMargin = layoutParams1.rightMargin + (-1 * (int) diffX);
                                    layoutParams1.bottomMargin = layoutParams1.bottomMargin + (-1 * (int) diffX);
                                    layoutParams1.width = layoutParams1.width + (int) diffX;
                                    layoutParams1.height = layoutParams1.height + (int) diffX;
                                    Log.d("Movement RIGHT BOTTOM", "" + layoutParams1.width);

                                } else {

                                    layoutParams1.height = layoutParams1.height + (-1 * (int) diffX);
//                                    layoutParams1.leftMargin = layoutParams1.leftMargin + (int) diffX;
                                    layoutParams1.leftMargin = layoutParams1.leftMargin + (1 * (int) diffX);
                                    layoutParams1.bottomMargin = layoutParams1.bottomMargin + (1 * (int) diffX);
                                    layoutParams1.width = layoutParams1.width + (-1 * (int) diffX);
                                    Log.d("Movement LEFT BOTTOM", "" + layoutParams1.width);

                                }

//                        layoutParams1.width = layoutParams1.width + (int) diffX;
//                        layoutParams1.height = layoutParams1.height + (int) diffY;

                                view.setLayoutParams(layoutParams1);
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
        });

        videoView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {


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
                            dX = view.getX() - event.getRawX();
                            dY = view.getY() - event.getRawY();
                            break;
                        } else {

                            rowX = event.getX();
                            rowY = event.getY();


                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
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
                            view.animate()
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
//rootLayout
//                        if (pointerCount == 2){

                        else {

                            float diffX = event.getX() - rowX;
                            float diffY = event.getY() - rowY;


                            if (diffX >= 1 || diffX <= 1 || diffY >= 1 || diffY <= 1) {

                                rowX = rowX + diffX;
                                rowY = rowY + diffY;

                                RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) view.getLayoutParams();
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

                                view.setLayoutParams(layoutParams1);
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
        });
    }


    public void getScreenSize() {

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        Log.d("Screen Dimension", "Screen Width  : " + screenWidth + "Screen Height :  " + screenHeight);

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        //Gestue
////        mGestureDetector.onTouchEvent(event);
////        return super.onTouchEvent(event);
//
//        //Pinch Gesture
//        //  re-route the Touch Events to the ScaleListener class
//        detector.onTouchEvent(event);
//        return super.onTouchEvent(event);
//    }

//    private void gestureDecetor(){
//
//        // Create an object of the Android_Gesture_Detector  Class
//        AndroidGestureDetector androidGestureDetector  =  new AndroidGestureDetector();
//// Create a GestureDetector
//        mGestureDetector = new GestureDetector(this, androidGestureDetector);
//    }


//    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
//
//
//        float onScaleBegin = 0;
//        float onScaleEnd = 0;
//
//        @Override
//        public boolean onScale(ScaleGestureDetector detector) {
//            scale *= detector.getScaleFactor();
//            videoView1.setScaleX(scale);
//            videoView1.setScaleY(scale);
//            return true;
//        }
//
//        @Override
//        public boolean onScaleBegin(ScaleGestureDetector detector) {
//
//            Toast.makeText(getApplicationContext(), "Scale Begin", Toast.LENGTH_SHORT).show();
//            onScaleBegin = scale;
//
//            return true;
//        }
//
//        @Override
//        public void onScaleEnd(ScaleGestureDetector detector) {
//
//            Toast.makeText(getApplicationContext(),"Scale Ended",Toast.LENGTH_SHORT).show();
//            onScaleEnd = scale;
//
//            if (onScaleEnd > onScaleBegin){
//                Toast.makeText(getApplicationContext(),"Scaled Up by a factor of  " + String.valueOf( onScaleEnd / onScaleBegin ), Toast.LENGTH_SHORT  ).show();
//            }
//
//            if (onScaleEnd < onScaleBegin){
//                Toast.makeText(getApplicationContext(),"Scaled Down by a factor of  " + String.valueOf( onScaleBegin / onScaleEnd ), Toast.LENGTH_SHORT  ).show();
//            }
//
//            super.onScaleEnd(detector);
//        }
//    }

}
