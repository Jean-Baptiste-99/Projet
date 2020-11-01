package com.ict.vmarket;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Rediriger vers la page principale
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                /**
                 * démarrer page
                 */
                Intent intent = new Intent(getApplicationContext(), Viewpage.class);
                startActivity(intent);
                finish();

            }
        };
        //handler
        new Handler().postDelayed(runnable, 5000);
    }
}
