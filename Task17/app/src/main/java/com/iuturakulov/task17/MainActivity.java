package com.iuturakulov.task17;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity implements OnGestureListener, OnDoubleTapListener {

    private GestureDetectorCompat detector;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detector = new GestureDetectorCompat(this, this);
        detector.setOnDoubleTapListener(this);
        info = findViewById(R.id.gesture);
        info.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.detector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d("Action", "Single tap: " + e.toString());
        info.setText("Single tap");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d("Action","Double tap: " + e.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d("Action", "Double tap event: " + e.toString());
        info.setText("Double tap event");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("Action","On down: " + e.toString());
        info.setText("On down");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("Action","on show press: " + e.toString());
        info.setText("on show press");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("Action","Single tap up: " + e.toString());
        info.setText("Single tap up");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("Action","On scroll: " + e1.toString() + ", " + e2.toString());
        info.setText("On scroll");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("Action", "onLongPress: " + e.toString());
        info.setText("onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("Action", "On fling: " + e1.toString() + ", " + e2.toString());
        info.setText("On fling");
        return true;
    }
}