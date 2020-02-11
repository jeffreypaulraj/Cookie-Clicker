package com.example.cookieclicker;

import androidx.appcompat.app.AppCompatActivity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainTesla = findViewById(R.id.id_mainTesla);
        teslaValuationText = findViewById(R.id.id_teslaValuationText);
        valuation = new AtomicInteger(0);
        final ScaleAnimation mainTeslaAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mainTeslaAnimation.setDuration(250);

        mainTesla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(mainTeslaAnimation);
                valuation.addAndGet(1);
                teslaValuationText.setText("Tesla's Valuation: $ " + valuation);
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
