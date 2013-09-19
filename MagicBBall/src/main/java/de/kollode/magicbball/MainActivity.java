package de.kollode.magicbball;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        final TextView mSwitcher = (TextView) findViewById(R.id.answerTextView);

        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(1500);

        final Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(1500);

        out.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {
                Log.d("MagicBBall", "Animation: Out, onStart, start");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d("MagicBBall", "Animation: Out, onEnd, start");
                int min = 1;
                int max = 10;

                Random r = new Random();
                int i1 = r.nextInt(max - min + 1) + min;

                if(i1 % 3 == 0) {
                    mSwitcher.setText(R.string.yes);
                }else {
                    mSwitcher.setText(R.string.no);
                }

                Log.d("MagicBBall", "Animation: Out, onEnd, animate in");
                mSwitcher.startAnimation(in);
            }
        });

        // ShakeDetector initialization
        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        ShakeDetector mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                Log.d("MagicBBall", "Shake!");
                mSwitcher.startAnimation(out);
            }
        });

        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }
}
