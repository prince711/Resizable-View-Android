package com.example.linchpin.demo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class SimpleActivity extends AppCompatActivity {

    private static final float IMG_PADDING = 0;
    private float screenWidth;
    private float screenHeight;
    private int move_distance = 20;
    private static final int MOVE_LEFT_TOP_2 = 1;
    private static final int MOVE_TOP_RIGHT_2 = 2;
    private static final int MOVE_RIGHT_BOTTOM_2 = 3;
    private static final int MOVE_BOTTOM_LEFT_2 = 4;
    private PointF DownPT_repos = null;
    private PointF StartPT_repos = null;
    private CustomRelativeLayout relativeLayout;
    //    private float Position_X2;
//    private float Position_Y2;
    private int X;
    private int Y;
    private PointF DownPT = null;
    private PointF StartPT = null;
    private float rowX2;
    private float rowY2;
    private int movmentType2;
    public final float BOARD_WIDTH = 854;
    public final float BOARD_HEIGHT = 480;
    public final int VIEW_WIDTH = 249;
    public final int VIEW_HEIGHT = 140;
    private RelativeLayout.LayoutParams layoutParams1;
    //    private float currentX;
//    private float previousX;
    private float width;
    private float height;
    private int actionBarHeight;
    private int REPOSITION_2 = 5;
    private float screenRatio;

    public void getScreenSize() {
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y - actionBarHeight;
        screenRatio = screenWidth / screenHeight;
        Log.d("Screen Dimension", "Screen Width  : " + screenWidth + "Screen Height :  " + screenHeight);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        relativeLayout = (CustomRelativeLayout) findViewById(R.id.rl_image1);
//        relativeLayout.getLayoutParams().height = 350;
//        relativeLayout.getLayoutParams().width = 500;
//        relativeLayout.requestLayout();

        getScreenSize();
        width = (int) ((VIEW_WIDTH * screenWidth) / BOARD_WIDTH);
        height = (int) ((VIEW_HEIGHT * screenHeight) / BOARD_HEIGHT);
        layoutParams1 = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams1.width = (int) width;
        layoutParams1.height = (int) height;
        relativeLayout.setX(screenWidth / 2);
        relativeLayout.setY(screenHeight / 5);
        relativeLayout.setLayoutParams(layoutParams1);
        relativeLayout.requestLayout();
        DownPT_repos = new PointF();
        StartPT_repos = new PointF();
        DownPT = new PointF();
        StartPT = new PointF();
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
                                              @Override
                                              public boolean onTouch(View view, MotionEvent event) {

                                                  float x = event.getX();
                                                  float y = event.getY();

                                                  switch (event.getAction() & MotionEvent.ACTION_MASK) {

                                                      case MotionEvent.ACTION_DOWN:
                                                          DownPT_repos.x = event.getRawX();
                                                          DownPT_repos.y = event.getRawY();
                                                          StartPT_repos = new PointF(relativeLayout.getX(), relativeLayout.getY());
                                                          Log.e("get points : ", relativeLayout.getX() + "" + relativeLayout.getY());

                                                          DownPT.x = event.getX();
                                                          DownPT.y = event.getY();
                                                          StartPT = new PointF(relativeLayout.getX(), relativeLayout.getY());
                                                          rowX2 = event.getX();
                                                          rowY2 = event.getY();
                                                          RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                                                          movmentType2 = -1;
                                                          if (rowX2 < (layoutParams.width / 4)) {
                                                              if (rowY2 < (layoutParams.height / 4)) {
                                                                  movmentType2 = MOVE_LEFT_TOP_2;
                                                              } else {
                                                                  if (rowY2 > ((layoutParams.height * 3) / 4)) {

                                                                      movmentType2 = MOVE_BOTTOM_LEFT_2;
                                                                  }
                                                              }
                                                          } else if (rowX2 > ((layoutParams.width * 3) / 4)) {
                                                              if (rowY2 < (layoutParams.height / 4)) {
                                                                  movmentType2 = MOVE_TOP_RIGHT_2;
                                                              } else {

                                                                  if (rowY2 > ((layoutParams.height * 3) / 4)) {

                                                                      movmentType2 = MOVE_RIGHT_BOTTOM_2;

                                                                  }
                                                              }
                                                          } else {
                                                              movmentType2 = REPOSITION_2;
                                                          }

                                                          break;
                                                      case MotionEvent.ACTION_UP:
                                                          break;
                                                      case MotionEvent.ACTION_MOVE:
                                                          PointF mv_repos = new PointF(event.getRawX() - DownPT_repos.x, event.getRawY()
                                                                  - DownPT_repos.y);

                                                          if (movmentType2 == MOVE_LEFT_TOP_2) {
                                                              if (((layoutParams1.width - (int) mv_repos.x) > width) &&
                                                                      (StartPT_repos.y + ((int) (mv_repos.x / (screenRatio)))) >= 0 &&
                                                                      layoutParams1.height - ((int) (mv_repos.x / (screenRatio))) > height &&
                                                                      (StartPT_repos.x + ((int) (mv_repos.x))) > 0) {
                                                                  relativeLayout.setX(StartPT_repos.x + (int) mv_repos.x);
                                                                  layoutParams1.width = (layoutParams1.width - (int) mv_repos.x);
                                                                  Log.d("end direction 1", "" + mv_repos.x);
                                                                  relativeLayout.setY(StartPT_repos.y + ((int) (mv_repos.x / (screenRatio))));
                                                                  layoutParams1.height = layoutParams1.height - ((int) (mv_repos.x / (screenRatio)));
                                                                  relativeLayout.setLayoutParams(layoutParams1);
                                                                  relativeLayout.requestLayout();
                                                              } else if (layoutParams1.height - ((int) (mv_repos.x / (screenRatio))) < height ||
                                                                      ((layoutParams1.width - (int) mv_repos.x) < width)) {
                                                                  Log.d("change dim", "" + (width - layoutParams1.width) + "   : " + (height - layoutParams1.height));
                                                                  relativeLayout.setX((float) (StartPT_repos.x - (width - layoutParams1.width)));
                                                                  relativeLayout.setY((float) (StartPT_repos.y - (height - layoutParams1.height)));
                                                                  layoutParams1.height = (int) (height);
                                                                  layoutParams1.width = (int) (width);
                                                                  relativeLayout.setLayoutParams(layoutParams1);
                                                                  relativeLayout.requestLayout();

                                                              }


                                                          } else if (movmentType2 == MOVE_TOP_RIGHT_2) {
                                                              if (((layoutParams1.width + (int) mv_repos.x) >= width) &&
                                                                      (StartPT_repos.y + ((int) (-mv_repos.x / ((screenRatio))))) >= 0 &&
                                                                      layoutParams1.height - ((int) (-mv_repos.x / (screenRatio))) > height &&
                                                                      StartPT_repos.x + layoutParams1.width + ((int) mv_repos.x) <= screenWidth) {
                                                                  layoutParams1.width = layoutParams1.width + ((int) mv_repos.x);

                                                                  relativeLayout.setY(StartPT_repos.y + ((int) (-mv_repos.x / (screenRatio))));
                                                                  layoutParams1.height = layoutParams1.height - ((int) (-mv_repos.x / (screenRatio)));
                                                                  relativeLayout.setLayoutParams(layoutParams1);
                                                                  relativeLayout.requestLayout();

                                                              }
                                                          } else if (movmentType2 == MOVE_BOTTOM_LEFT_2) {


                                                              if (((layoutParams1.width - (int) mv_repos.x) >= width) &&
                                                                      (StartPT_repos.y + layoutParams1.height + ((int) (-mv_repos.x / (screenRatio)))) <= screenHeight &&
                                                                      layoutParams1.height + ((int) (-mv_repos.x / (screenRatio))) > height &&
                                                                      (StartPT_repos.x + (int) mv_repos.x) >= 0) {
                                                                  relativeLayout.setX(StartPT_repos.x + (int) mv_repos.x);
                                                                  layoutParams1.width = layoutParams1.width - ((int) mv_repos.x);
                                                                  layoutParams1.height = layoutParams1.height + ((int) (-mv_repos.x / (screenRatio)));
                                                                  Log.d("Movement BOTTOM LEFT", "" + layoutParams1.width);
                                                                  Log.d("View Dimension", " Width : " + layoutParams1.width +
                                                                          "    height : " + layoutParams1.height + "  left margin : " + layoutParams1.leftMargin +
                                                                          "  top margin : " + layoutParams1.topMargin);
                                                                  relativeLayout.setLayoutParams(layoutParams1);
                                                                  relativeLayout.requestLayout();

                                                              }


                                                          } else if (movmentType2 == MOVE_RIGHT_BOTTOM_2) {

                                                              if (((layoutParams1.width + (int) mv_repos.x) >= width) &&
                                                                      (StartPT_repos.y + layoutParams1.height + ((int) (mv_repos.x / (screenRatio)))) <= screenHeight &&
                                                                      (StartPT_repos.x + layoutParams1.width + mv_repos.x) <= screenWidth &&
                                                                      layoutParams1.height + ((int) (mv_repos.x / (screenRatio))) > height) {

                                                                  layoutParams1.width = layoutParams1.width + ((int) mv_repos.x);
                                                                  layoutParams1.height = layoutParams1.height + ((int) (mv_repos.x / (screenRatio)));
                                                                  Log.d("Movement BOTTOM LEFT", "" + layoutParams1.width);
                                                                  Log.d("View Dimension", " Width : " + layoutParams1.width +
                                                                          "    height : " + layoutParams1.height + "  left margin : " + layoutParams1.leftMargin +
                                                                          "  top margin : " + layoutParams1.topMargin);
                                                                  relativeLayout.setLayoutParams(layoutParams1);
                                                                  relativeLayout.requestLayout();
                                                              }


                                                          } else if (movmentType2 == REPOSITION_2) {

                                                              PointF mv = new PointF(event.getX() - DownPT.x, event.getY()
                                                                      - DownPT.y);
                                                              int imgWidth = (int) (relativeLayout.getWidth() + px2dp(getResources(), IMG_PADDING));
                                                              int imgHeight = (int) (relativeLayout.getHeight() + (px2dp(getResources(), IMG_PADDING)));
                                                              if (StartPT.x >= 0 && StartPT.x + imgWidth <= screenWidth) {
                                                                  if ((StartPT.x + mv.x / 5) < 0) {
                                                                      relativeLayout.setX((int) (0));
                                                                  } else if ((StartPT.x + imgWidth + mv.x / 5) > screenWidth) {
                                                                      relativeLayout.setX((int) (screenWidth - imgWidth));
                                                                  } else
                                                                      relativeLayout.setX((int) (StartPT.x + mv.x / 5));
                                                              }
                                                              if (StartPT.y >= 0 && StartPT.y + imgHeight <= screenHeight) {
                                                                  relativeLayout.setY((int) (StartPT.y + mv.y / 5));
                                                                  if ((StartPT.y + mv.y / 5) < 0) {
                                                                      relativeLayout.setY((int) (0));
                                                                  } else if ((StartPT.y + imgHeight + mv.y / 5) > screenHeight) {
                                                                      relativeLayout.setY((int) (screenHeight - imgHeight));
                                                                  } else
                                                                      relativeLayout.setY((int) (StartPT.y + mv.y / 5));
                                                              }
                                                              if (StartPT.x < 0) {
                                                                  StartPT.x = 0;
                                                                  relativeLayout.setX((int) (StartPT.x));

                                                              } else {
                                                                  StartPT.x = relativeLayout.getX();
                                                              }
                                                              if (StartPT.y < 0) {
                                                                  StartPT.y = 0;
                                                                  relativeLayout.setY((int) (StartPT.y));
                                                              } else {
                                                                  StartPT.y = relativeLayout.getY();
                                                              }
                                                              if (relativeLayout.getX() + imgWidth > screenWidth) {
                                                                  StartPT.x = screenWidth - imgWidth;
                                                                  relativeLayout.setX((int) StartPT.x);
//                                                                  AndroidAppUtils.showLog(TAG, "Screen width");

                                                              } else {
                                                                  StartPT.x = relativeLayout.getX();
                                                              }
                                                              if (relativeLayout.getY() + imgHeight > screenHeight) {

                                                                  StartPT.y = screenHeight - imgHeight;
                                                                  relativeLayout.setY((int) (StartPT.y));
//                                                                  AndroidAppUtils.showLog(TAG, "Screen height");

                                                              } else {
                                                                  StartPT.y = relativeLayout.getY();
                                                              }
//                                                              AndroidAppUtils.showLog(TAG, "MOVE VIEW2 " + leftMargin + "  top margin : " + topMargin);

                                                          }


                                                          StartPT_repos = new PointF(relativeLayout.getX(), relativeLayout.getY());
                                                          DownPT_repos.x = event.getRawX();
                                                          DownPT_repos.y = event.getRawY();
                                                          break;
                                                  }
                                                  return true;
                                              }
                                          }

        );
    }

    /**
     * TO convert pixel to dp
     *
     * @param resource
     * @param px
     * @return
     */
    public static float px2dp(Resources resource, float px) {
        return (float) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, resource.getDisplayMetrics());
    }
}
