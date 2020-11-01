package com.ict.vmarket;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ProduitQuantite extends AppCompatActivity {

    private TextView t1, t2, t3, t4, t5;
    private ImageView img;
    private Button b1;
    private ImageButton i1, i2;
    private int clicks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_quantite);


        t1 = findViewById(R.id.detail_quantite);
       /* t2 = findViewById(R.id.detail_pname_q);
        t3 = findViewById(R.id.detail_prix_q);
        t4 = findViewById(R.id.detail_desc_q);
        t5 = findViewById(R.id.detail_cat_q);
        img = findViewById(R.id.image_detail_q);*/

        b1 = findViewById(R.id.add_cart_q);

        i1 = findViewById(R.id.plus);
        i2 = findViewById(R.id.moin);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks++;
                t1.setText(""+clicks);

            }
        });

        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              clicks--;
               t1.setText(""+clicks);

            }
        });

    }
}
