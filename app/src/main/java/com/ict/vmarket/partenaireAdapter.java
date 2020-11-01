package com.ict.vmarket;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class partenaireAdapter extends RecyclerView.Adapter<partenaireAdapter.partenaireViewHolder> implements Filterable {

    private Context mcontext;
    private List<partenaireitem> mpartnairelist;
    private List<partenaireitem> mpartnairelistAll;
    RequestOptions option;

    public partenaireAdapter(Context context, List <partenaireitem> partenairelist ){
        mcontext = context;
        mpartnairelist = partenairelist;
        mpartnairelistAll = new ArrayList<>(partenairelist);
        option = new RequestOptions().centerCrop().placeholder(R.drawable.bags).error(R.drawable.bags);
    }

    @NonNull
    @Override
    public partenaireViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.itempartenaire, parent, false);

        final partenaireViewHolder viewHolder = new  partenaireViewHolder(v);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mcontext, PartenaireDetail.class);

                i.putExtra("NomEntreprise", mpartnairelist.get(viewHolder.getAdapterPosition()).getmName());
                i.putExtra("Email", mpartnairelist.get(viewHolder.getAdapterPosition()).getmEmail());
                i.putExtra("Description", mpartnairelist.get(viewHolder.getAdapterPosition()).getmDesc());
                i.putExtra("Tel", mpartnairelist.get(viewHolder.getAdapterPosition()).getmTel());
                i.putExtra("Pays", mpartnairelist.get(viewHolder.getAdapterPosition()).getmPays());
                i.putExtra("Ville", mpartnairelist.get(viewHolder.getAdapterPosition()).getmVille());
                i.putExtra("ImageEntreprise", mpartnairelist.get(viewHolder.getAdapterPosition()).getmImageUrl());
                i.putExtra("Adresse", mpartnairelist.get(viewHolder.getAdapterPosition()).getmAdresse());

                mcontext.startActivity(i);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull partenaireViewHolder holder, int position) {


        holder.t1.setText(mpartnairelist.get(position).getmName());
        holder.t2.setText(mpartnairelist.get(position).getmEmail());
        //Picasso.get().load(mpartnairelist.get(position).getmImageUrl()).fit().centerCrop().into(holder.imageView);
        Glide.with(mcontext).load(mpartnairelist.get(position).getmImageUrl()).apply(option).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return mpartnairelist.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<partenaireitem> filteredlist = new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filteredlist.addAll(mpartnairelistAll);

            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (partenaireitem item: mpartnairelistAll){

                    if (item.getmName().toLowerCase().contains(filterPattern)) {
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
            mpartnairelist.clear();
            mpartnairelist.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    public class partenaireViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView imageView;
        public TextView t1, t2;
        public LinearLayout view_container;

        public partenaireViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.entreprise_image_cir);
            t1 = itemView.findViewById(R.id.entreprise_name);
            t2 = itemView.findViewById(R.id.entreprise_email);
            view_container = itemView.findViewById(R.id.pcontainer);
        }
    }
}
