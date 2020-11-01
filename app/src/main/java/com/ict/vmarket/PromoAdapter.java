package com.ict.vmarket;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PromoAdapter  extends RecyclerView.Adapter<PromoAdapter.produitViewHolder> {

    private Context mcontext;
    private List<ProduitItem> mproduitlist;
    RequestOptions option;


    public PromoAdapter (Context context, List <ProduitItem> produitlist ){
        mcontext = context;
        mproduitlist = produitlist;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.bags).error(R.drawable.bags);
    }

    @NonNull
    @Override
    public produitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.itempromotion, parent, false);

        final produitViewHolder viewHolder = new produitViewHolder(v);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mcontext, PromoDetail.class);

                i.putExtra("LibelleProduit", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_libelle());
                i.putExtra("CategorieProduit", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_cat());
                i.putExtra("Description", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_desc());
                i.putExtra("NomEntreprise", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_nom_ent());
                i.putExtra("PrixNormal", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_prix_n());
                i.putExtra("PrixPromo", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_prix_p());
                i.putExtra("ImageProduit", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_image());
                i.putExtra("DateDebut", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_date_deb());
                i.putExtra("DateFin", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_date_fin());

                mcontext.startActivity(i);
            }
        });

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull produitViewHolder holder, int position) {


        holder.t1.setText(mproduitlist.get(position).getProduct_libelle());
        holder.t2.setText(mproduitlist.get(position).getProduct_prix_n());
        holder.t3.setText(mproduitlist.get(position).getProduct_prix_p());
        holder.t4.setText(mproduitlist.get(position).getProduct_date_deb());
        holder.t5.setText(mproduitlist.get(position).getProduct_date_fin());
        //Picasso.get().load(mproduitlist.get(position).getProduct_image()).fit().centerCrop().into(holder.imageView);
        Glide.with(mcontext).load(mproduitlist.get(position).getProduct_image()).apply(option).into(holder.imageView);


    }


    @Override
    public int getItemCount() {
        return mproduitlist.size();
    }

    public class produitViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView t1, t2, t3, t4, t5, t6;
        public LinearLayout view_container;
        public ImageButton favorite;
        private int clicks = 0;
        public ImageButton unfavorite;
        public String check = "unfavorite";


        public produitViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image_pr);
            t1 = itemView.findViewById(R.id.product_name_pr);
            t2 = itemView.findViewById(R.id.prix_pr);
            t3 = itemView.findViewById(R.id.prix_promo_pr);
            t4 = itemView.findViewById(R.id.prix_dat_deb);
            t5 = itemView.findViewById(R.id.prix_dat_fin);
            t6 = itemView.findViewById(R.id.textView15_likes);
            view_container = itemView.findViewById(R.id.container_pro);
            favorite = itemView.findViewById(R.id.favorite_pr);
            unfavorite = itemView.findViewById(R.id.unfavorite_pr);

            favorite.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (check.equals("favorite")){
                        dislikeheart(unfavorite);
                        favorite.setVisibility(v.GONE);
                        unfavorite.setVisibility(v.VISIBLE);
                        clicks--;
                        t6.setText(""+clicks);
                        Snackbar.make(v, "Dislike it", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                    }

                    check="unfavorite";

                }
            });

            unfavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (check.equals("unfavorite")){
                        animateheart(favorite);
                        favorite.setVisibility(v.VISIBLE);
                        unfavorite.setVisibility(v.GONE);
                        clicks++;
                        t6.setText(""+clicks);
                        Snackbar.make(v, "Like", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                    }

                    check="favorite";


                }
            });
        }



        private Animation prepareAnimationn(Animation animation){
            animation.setRepeatCount(0);
            animation.setRepeatMode(Animation.REVERSE);

            return animation;
        }

        private void dislikeheart(final ImageButton view){
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            prepareAnimationn(scaleAnimation);

            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            prepareAnimationn(alphaAnimation);
            AnimationSet animationSet  = new AnimationSet(true);
            animationSet.addAnimation(alphaAnimation);
            animationSet.addAnimation(scaleAnimation);
            animationSet.setDuration(700);
            view.startAnimation(animationSet);
        }

        private void animateheart(final ImageButton view){
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            prepareAnimation(scaleAnimation);

            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            prepareAnimation(alphaAnimation);
            AnimationSet animationSet  = new AnimationSet(true);
            animationSet.addAnimation(alphaAnimation);
            animationSet.addAnimation(scaleAnimation);
            animationSet.setDuration(500);
            view.startAnimation(animationSet);
        }

        private Animation prepareAnimation(Animation animation){
            animation.setRepeatCount(2);
            animation.setRepeatMode(Animation.REVERSE);

            return animation;
        }


    }




}
