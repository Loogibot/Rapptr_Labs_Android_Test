package com.datechnologies.androidtest.animation;

import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;

/**
 * Screen that displays the D & A Technologies logo.
 * The icon can be moved around on the screen as well as animated.
 */

public class AnimationActivity extends AppCompatActivity {

    ImageView d_a_techImage;
    float x, y, dx, dy;

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context) {
        Intent starter = new Intent(context, AnimationActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.animation_title);
        setContentView(R.layout.activity_animation);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // done
        // TODO: Add a ripple effect when the buttons are clicked
        // done
        // TODO: When the fade button is clicked, you must animate the D & A Technologies logo.

        d_a_techImage = findViewById(R.id.d_and_a_technologies);
        Button fadeButton = findViewById(R.id.fade_in);

        fadeButton.setOnClickListener(v -> {
            AlphaAnimation alphaTo0 = new AlphaAnimation(1.0f, 0.0f);
            alphaTo0.setDuration(1000);
            alphaTo0.setRepeatCount(1);
            alphaTo0.setRepeatMode(Animation.REVERSE);
            d_a_techImage.startAnimation(alphaTo0);
        });

        // TODO: It should fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha
        // done

        // TODO: The user should be able to touch and drag the D & A Technologies logo around the screen.
        // done

        // TODO: Add a bonus to make yourself stick out. Music, color, fireworks, explosions!!!
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            dx = event.getX() - x;
            dy = event.getY() - y;

            d_a_techImage.setX(d_a_techImage.getX() + dx);
            d_a_techImage.setY(d_a_techImage.getY() + dy);

            x = event.getX();
            y = event.getY();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
