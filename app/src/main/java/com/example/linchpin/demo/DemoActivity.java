package com.example.linchpin.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class DemoActivity extends AppCompatActivity {

    View videoView2;

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
    private static final int MOVE_LEFT_TOP = 1;
    private static final int MOVE_TOP_RIGHT = 2;
    private static final int MOVE_RIGHT_BOTTOM = 3;
    private static final int MOVE_BOTTOM_LEFT = 4;
    private static final int MOVE_LEFT_TOP_2 = 1;
    private static final int MOVE_TOP_RIGHT_2 = 2;
    private static final int MOVE_RIGHT_BOTTOM_2 = 3;
    private static final int MOVE_BOTTOM_LEFT_2 = 4;
    private boolean isDraggingStart = true;
    private int x;
    private int y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        videoView2 = findViewById(R.id.view);
        onTouchOnView();
    }

    private void onTouchOnView() {


        videoView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

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

//                                    layoutParams1.leftMargin = layoutParams1.leftMargin + (int) diffX;
                                    layoutParams1.width = layoutParams1.width + ((int) diffX);
//                                    layoutParams1.topMargin = layoutParams1.topMargin + (int) diffX;
                                    layoutParams1.height = layoutParams1.height + ((int) diffX);

                                    view.animate()
                                            .x(event.getRawX() - diffX)
                                            .y(event.getRawY() - diffY)
                                            .setDuration(0)
                                            .start();

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
//                rootLayout.invalidate();
                return true;

            }
        });

    }
}
