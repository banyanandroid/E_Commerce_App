package banyan.com.e_commerce_app.Activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import banyan.com.e_commerce_app.R;

public class Demo_Activity extends Activity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animated);

        //wheel animation
        ImageView wheel = (ImageView)findViewById(R.id.wheel);
        //load the wheel animation
        AnimatorSet wheelSet = (AnimatorSet)
                AnimatorInflater.loadAnimator(this, R.animator.wheel_spin);
        //set the view as target
        wheelSet.setTarget(wheel);
        //start the animation
        wheelSet.start();

        //get the sun view
        ImageView sun = (ImageView)findViewById(R.id.sun);
        //load the sun movement animation
        AnimatorSet sunSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.sun_swing);
        //set the view as target
        sunSet.setTarget(sun);
        //start the animation
        sunSet.start();

        //darken sky
        ValueAnimator skyAnim = ObjectAnimator.ofInt
                (findViewById(R.id.car_layout), "backgroundColor",
                        Color.rgb(0x66, 0xcc, 0xff), Color.rgb(0x00, 0x66, 0x99));
        skyAnim.setDuration(3000);
        skyAnim.setRepeatCount(ValueAnimator.INFINITE);
        skyAnim.setRepeatMode(ValueAnimator.REVERSE);
        skyAnim.setEvaluator(new ArgbEvaluator());
        skyAnim.start();

        //move clouds
        ObjectAnimator cloudAnim =
                ObjectAnimator.ofFloat(findViewById(R.id.cloud1), "x", -350);
        cloudAnim.setDuration(3000);
        cloudAnim.setRepeatCount(ValueAnimator.INFINITE);
        cloudAnim.setRepeatMode(ValueAnimator.REVERSE);
        cloudAnim.start();
        //other cloud
        ObjectAnimator cloudAnim2 = ObjectAnimator.ofFloat(findViewById(R.id.cloud2), "x", -300);
        cloudAnim2.setDuration(3000);
        cloudAnim2.setRepeatCount(ValueAnimator.INFINITE);
        cloudAnim2.setRepeatMode(ValueAnimator.REVERSE);
        cloudAnim2.start();
    }

}

