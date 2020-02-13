package com.example.cookieclicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    ImageView mainTesla;
    TextView teslaValuationText;
    static AtomicInteger valuation;
    static AtomicInteger passiveIncome;
    TextView incrementIndicator;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainTesla = findViewById(R.id.id_mainTesla);
        teslaValuationText = findViewById(R.id.id_teslaValuationText);
        valuation = new AtomicInteger(0);
        incrementIndicator = new TextView(this);
        incrementIndicator.setId(View.generateViewId());
        incrementIndicator.setText("$");
        constraintLayout = findViewById(R.id.layout);

        final ObjectAnimator incrementAnimation = ObjectAnimator.ofFloat(incrementIndicator,"translationY", 0f,-500);
        incrementAnimation.setDuration(1000);

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        incrementIndicator.setLayoutParams(params);
        constraintLayout.addView(incrementIndicator);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(incrementIndicator.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
        constraintSet.connect(incrementIndicator.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(incrementIndicator.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
        constraintSet.connect(incrementIndicator.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);

        constraintSet.setHorizontalBias(incrementIndicator.getId(), 1f);

        final ScaleAnimation mainTeslaAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mainTeslaAnimation.setDuration(250);

        mainTesla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(mainTeslaAnimation);
                valuation.addAndGet(1);
                teslaValuationText.setText("Tesla's Valuation: $ " + valuation);
                incrementAnimation.start();
            }
        });
    }

    public static class SuperChargerUpgradeThread extends Thread{
        public void run(){
            try{
                Thread.sleep(2);
            }catch(Exception e){}
            valuation.getAndAdd(1);
        }
    }
}
