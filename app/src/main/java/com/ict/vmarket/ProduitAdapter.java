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
import android.widget.Filter;
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

import java.util.ArrayList;
import java.util.List;

public class ProduitAdapter extends RecyclerView.Adapter<ProduitAdapter.produitViewHolder> {

    private Context mcontext;
    private List<ProduitItem> mproduitlist;
    private List<ProduitItem> mproduitlistAll;
    RequestOptions option;


    public ProduitAdapter (Context context, List <ProduitItem> produitlist ){
        mcontext = context;
        mproduitlist = produitlist;
        mproduitlistAll = new ArrayList<>(produitlist);
        option = new RequestOptions().centerCrop().placeholder(R.drawable.bags).error(R.drawable.bags);
    }

    @NonNull
    @Override
    public produitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.itemproduit, parent, false);

        final produitViewHolder viewHolder = new produitViewHolder(v);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mcontext, ProduiDetail.class);

                i.putExtra("LibelleProduit", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_libelle());
                i.putExtra("CategorieProduit", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_cat());
                i.putExtra("Description", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_desc());
                i.putExtra("NomEntreprise", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_nom_ent());
                i.putExtra("PrixNormal", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_prix_n());
                i.putExtra("PrixPromo", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_prix_p());
                i.putExtra("ImageProduit", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_image());
               /* i.putExtra("DateDebut", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_date_deb());
                i.putExtra("DateFin", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_date_fin());*/

                mcontext.startActivity(i);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull produitViewHolder holder, int position) {


        holder.t1.setText(mproduitlist.get(position).getProduct_libelle());
        holder.t2.setText(mproduitlist.get(position).getProduct_prix_n());
        //Picasso.get().load(mproduitlist.get(position).getProduct_image()).fit().centerCrop().into(holder.imageView);
        Glide.with(mcontext).load(mproduitlist.get(position).getProduct_image()).apply(option).into(holder.imageView);


    }


    @Override
    public int getItemCount() {
        return mproduitlist.size();
    }

    public void filterList(ArrayList<ProduitItem> filteredList) {
        mproduitlist = filteredList;
        notifyDataSetChanged();
    }

    public Filter getFilter(){
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<ProduitItem> filteredlist = new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filteredlist.addAll(mproduitlistAll);

            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ProduitItem item: mproduitlistAll){

                    if (item.getProduct_libelle().toLowerCase().contains(filterPattern)) {
                        filteredlist.add(item);
                    }

                }

            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredlist;


            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mproduitlist.clear();
            mproduitlist.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


    public class produitViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView t1, t2, t3;
        public LinearLayout view_container;
        public ImageButton favorite;
        public ImageButton unfavorite;
        private int clicks = 0;
        public String check = "unfavorite";


        public produitViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image_pop);
            t1 = itemView.findViewById(R.id.product_name_pop);
            t2 = itemView.findViewById(R.id.prix_pop);
            t3 = itemView.findViewById(R.id.textView14_likes);
            view_container = itemView.findViewById(R.id.container_pop);
            favorite = itemView.findViewById(R.id.favorite);
            unfavorite = itemView.findViewById(R.id.unfavorite);

            favorite.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (check.equals("favorite")){
                        dislikeheart(unfavorite);
                        favorite.setVisibility(v.GONE);
                        unfavorite.setVisibility(v.VISIBLE);
                        clicks--;
                        t3.setText(""+clicks);
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
                        t3.setText(""+clicks);
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
