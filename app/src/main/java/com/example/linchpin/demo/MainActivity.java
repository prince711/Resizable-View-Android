package com.example.linchpin.demo;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    int prevX;
    int prevY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = findViewById(R.id.view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

       /* view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        prevX = (int) motionEvent.getX();
                        prevY = (int) motionEvent.getY();

                    }

                    case MotionEvent.ACTION_MOVE:
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        int diffX = x - prevX;
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(30, 40);
                        params.leftMargin = 50;
                        params.topMargin = 60;
                        params.height = params.height + diffX;
                        params.width = params.width + diffX;
                        view.setLayoutParams(params);
                        prevX = x;

                }

                return false;
            }

        });*/

        ViewGroup.LayoutParams vlp = view.getLayoutParams();
        int marginBottom = ((RelativeLayout.LayoutParams) vlp).bottomMargin;

//        ImageView my_image = (ImageView) findViewById(R.id.my_imageView);

        Rect rectf = new Rect();
        view.getLocalVisibleRect(rectf);
        int[] layout = new int[2];
        view.getLocationOnScreen(layout);

        Log.d("WIDTH        :",String.valueOf(rectf.width()));
        Log.d("HEIGHT       :",String.valueOf(rectf.height()));
        Log.d("left         :",String.valueOf(view.getLeft()));
        Log.d("right        :",String.valueOf(rectf.right));
        Log.d("top          :",String.valueOf(rectf.top));
        Log.d("bottom       :",String.valueOf(rectf.bottom));
        Log.d("bottom       :",String.valueOf(marginBottom));
        Log.d("X       :",String.valueOf(layout[0]));
        Log.d("Y       :",String.valueOf(layout[1]));
        Log.d("Widht       :",String.valueOf(view.getWidth()));
        Log.d("Height       :",String.valueOf(view.getHeight()));

        view.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
//                dragEvent.get
                return false;
            }
        });

        View rootLayout = view.getRootView().findViewById(android.R.id.content);

        int[] viewLocation = new int[2];
        view.getLocationInWindow(viewLocation);

        int[] rootLocation = new int[2];
        rootLayout.getLocationInWindow(rootLocation);

        int relativeLeft = viewLocation[0] - rootLocation[0];
        int relativeTop  = viewLocation[1] - rootLocation[1];
        Log.d("view cordinates       :",""+relativeLeft+""+relativeTop);


    }

    private int getRelativeLeft(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getLeft();
        else
            return myView.getLeft() + getRelativeLeft((View) myView.getParent());
    }

    private int getRelativeTop(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getTop();
        else
            return myView.getTop() + getRelativeTop((View) myView.getParent());



    }


}
