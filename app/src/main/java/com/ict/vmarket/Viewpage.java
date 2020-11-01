package com.ict.vmarket;


import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Viewpage extends AppCompatActivity {

    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpage);

        btn = findViewById(R.id.btndem);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Expo.class);
                startActivity(intent);
            }
        });

        models = new ArrayList<>();
        models.add(new Model(R.drawable.image19, "Produit", "Retrouvez lesproduits que vous désirez acheter. Consultez le catalogue des produits en promotion pour faciliter vos achats."));
        models.add(new Model(R.drawable.image13, "Annonces", "Annonceurs, faites vos annonces via la plateforme VMarket de vente pour cibler le maximum d'utilisateur. Vous êtes particulier et vous désirez vendre un article sur la plateforme."));
        models.add(new Model(R.drawable.image3, "Exposition", "Exposez vos produits pour une meilleure visibilité. Faites connaître vos produits ainsi que leurs prix. Promouvoir votre marque."));
        models.add(new Model(R.drawable.image11, "Notifications", "Soyez informé en temps réels des offres de promotions et de l'arrivage de noveaux produits dans les locaux de nos sociétés partenaires."));
       // models.add(new Model(R.drawable.image17, "Message", " Les utilisateurs peuvent communiquer avec nos partenaires  sur les articles misent en ventes sur le marché virtuel."));
        models.add(new Model(R.drawable.image5, "Itinéraire", "Suivez un itinéraire pour vous rendre chez nos différents partenaires par rapport à votre position géographique"));

        adapter = new Adapter(models, this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(100,0,100,0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4),
                getResources().getColor(R.color.color5),
                getResources().getColor(R.color.color6)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position< (adapter.getCount() -1) && position < (colors.length -1)){
                    viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position +1]));
                }else {
                    viewPager.setBackgroundColor(colors[colors.length -1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
