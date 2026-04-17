package com.examcraft.portal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashActivity extends Activity {

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView    logo    = findViewById(R.id.splash_logo);
        TextView     tagline = findViewById(R.id.splash_tagline);
        LinearLayout dots    = findViewById(R.id.splash_dots);
        View         dot1    = findViewById(R.id.dot1);
        View         dot2    = findViewById(R.id.dot2);
        View         dot3    = findViewById(R.id.dot3);

        // 1. Logo — fade + scale in
        Animation logoAnim = AnimationUtils.loadAnimation(this, R.anim.splash_logo_in);
        logo.startAnimation(logoAnim);

        // 2. Tagline — slide up + fade after logo
        handler.postDelayed(() -> {
            tagline.setVisibility(View.VISIBLE);
            Animation textAnim = AnimationUtils.loadAnimation(this, R.anim.splash_text_in);
            tagline.startAnimation(textAnim);
        }, 900);

        // 3. Bouncing dots loading indicator
        handler.postDelayed(() -> {
            dots.setVisibility(View.VISIBLE);
            bounceDot(dot1, 0);
            bounceDot(dot2, 220);
            bounceDot(dot3, 440);
        }, 1600);

        // 4. Launch main screen
        handler.postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, 3400);
    }

    private void bounceDot(View dot, long delay) {
        handler.postDelayed(() -> {
            ScaleAnimation anim = new ScaleAnimation(
                    1.0f, 1.7f, 1.0f, 1.7f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(320);
            anim.setRepeatCount(Animation.INFINITE);
            anim.setRepeatMode(Animation.REVERSE);
            dot.startAnimation(anim);
        }, delay);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
