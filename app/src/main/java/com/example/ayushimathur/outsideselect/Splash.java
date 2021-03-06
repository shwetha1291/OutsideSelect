package com.example.ayushimathur.outsideselect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ayushimathur on 7/24/16.
 */
public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        TextView welcome = (TextView) findViewById(R.id.outsideLands);
        ImageView heart = (ImageView) findViewById(R.id.heart) ;
        welcome.setTextColor(Color.RED);

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash.this, SelectArtist.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public class OnSwipeTouchListener implements View.OnTouchListener {
        private final GestureDetector gestureDetector;
        private Context context;

        /* (non-Javadoc)
         * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
         */
        public boolean onTouch(final View view, final MotionEvent motionEvent) {
            return gestureDetector.onTouchEvent(motionEvent);
        }

        /**
         * Gets the gesture detector.
         *
         * @return the gesture detector
         */
        public GestureDetector getGestureDetector(){
            return  gestureDetector;
        }

        /**
         * Instantiates a new on swipe touch listener.
         *
         * @param context
         *            the context
         */
        public OnSwipeTouchListener(Context context) {
            super();
            this.context = context;
            gestureDetector = new GestureDetector(context, new GestureListener());
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            /* (non-Javadoc)
             * @see android.view.GestureDetector.SimpleOnGestureListener#onDown(android.view.MotionEvent)
             */
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

    /* (non-Javadoc)
     * @see android.view.GestureDetector.SimpleOnGestureListener#onFling(android.view.MotionEvent, android.view.MotionEvent, float, float)
     */

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getRawY() - e1.getRawY();
                    float diffX = e2.getRawX() - e1.getRawX();
                    if ((Math.abs(diffX) - Math.abs(diffY)) > SWIPE_THRESHOLD) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                    } else {
                        if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffY > 0) {
                                onSwipeBottom();
                            } else {
                                onSwipeTop();
                            }
                        }
                    }
                } catch (Exception e) {

                }
                return result;
            }
        }

        /**
         * On swipe right.
         */
        public void onSwipeRight() {
        }

        /**
         * On swipe left.
         */
        public void onSwipeLeft() {
        }

        /**
         * On swipe top.
         */
        public void onSwipeTop() {
        }

        /**
         * On swipe bottom.
         */
        public void onSwipeBottom() {
        }
    }
}
