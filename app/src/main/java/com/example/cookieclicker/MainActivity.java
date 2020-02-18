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
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    ImageView mainTesla;
    TextView teslaValuationText;
    static AtomicInteger valuation;
    static AtomicInteger passiveIncome;
    TextView incrementIndicator;
    ConstraintLayout constraintLayout;
    TextView storeText;
    ImageView upgradeImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainTesla = findViewById(R.id.id_mainTesla);
        teslaValuationText = findViewById(R.id.id_teslaValuationText);
        valuation = new AtomicInteger(0);
        passiveIncome = new AtomicInteger(0);
        incrementIndicator = new TextView(this);
        incrementIndicator.setId(View.generateViewId());
        incrementIndicator.setText("$100");
        constraintLayout = findViewById(R.id.layout);
        storeText = findViewById(R.id.id_storeText);
        upgradeImage = new ImageView(this);
        upgradeImage.setId(View.generateViewId());
        upgradeImage.setImageResource(R.drawable.supercharger);
        upgradeImage.setVisibility(View.INVISIBLE);
        final ObjectAnimator incrementAnimation = ObjectAnimator.ofFloat(incrementIndicator,"translationY", 0f,-500);
        incrementAnimation.setDuration(1000);


        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        incrementIndicator.setLayoutParams(params);
        upgradeImage.setLayoutParams(params);
        constraintLayout.addView(incrementIndicator);
        constraintLayout.addView(upgradeImage);


        final ScaleAnimation mainTeslaAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mainTeslaAnimation.setDuration(250);

        mainTesla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(mainTeslaAnimation);
                valuation.addAndGet(100);
                teslaValuationText.setText("Tesla's Valuation: $ " + valuation);
                float randHorizontalBias = (float) (Math.random() * 0.5 + 0.25f);
                ConstraintSet incrementConstraintSet = new ConstraintSet();
                incrementConstraintSet.clone(constraintLayout);
                incrementConstraintSet.connect(incrementIndicator.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
                incrementConstraintSet.connect(incrementIndicator.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
                incrementConstraintSet.connect(incrementIndicator.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
                incrementConstraintSet.connect(incrementIndicator.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);

                incrementConstraintSet.setHorizontalBias(incrementIndicator.getId(), randHorizontalBias);
                incrementConstraintSet.setVerticalBias(incrementIndicator.getId(), 0.5f);

                incrementConstraintSet.applyTo(constraintLayout);
                incrementAnimation.start();
/*
                if(valuation.intValue() >= 1000){
                    upgradeImage.setVisibility(View.VISIBLE);
                    ConstraintSet upgradeConstraintSet = new ConstraintSet ();
                    upgradeConstraintSet.clone(constraintLayout);
                    upgradeConstraintSet.connect(upgradeImage.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
                    upgradeConstraintSet.connect(upgradeImage.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
                    upgradeConstraintSet.connect(upgradeImage.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
                    upgradeConstraintSet.connect(upgradeImage.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);

                    upgradeConstraintSet.setHorizontalBias(upgradeImage.getId(), 0);
                    upgradeConstraintSet.setVerticalBias(upgradeImage.getId(), 0.75f);

                    upgradeConstraintSet.applyTo(constraintLayout);
                }

            */
            }
        });

        upgradeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(mainTeslaAnimation);
                if(valuation.intValue()<1000){
                    Toast insufficientToast = Toast.makeText(MainActivity.this, "You have insufficient funds to buy a supercharger!", Toast.LENGTH_SHORT);
                    insufficientToast.show();
                }
                else{
                    valuation.addAndGet(-1000);
                    passiveIncome.addAndGet(500);
                }
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
