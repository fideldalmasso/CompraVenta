package com.example.compraventa;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.compraventa.model.Categoria;

import java.util.List;

public class CatRecyclerAdapter extends RecyclerView.Adapter<CatRecyclerAdapter.ViewHolder> {

    private List<Categoria> dataset_categorias;

    public CatRecyclerAdapter(List<Categoria> dataset_categorias) {
        this.dataset_categorias = dataset_categorias;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagen_cat;
        private final TextView nombre_cat;
        private CardView card_cat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen_cat = itemView.findViewById(R.id.imagen_cat);
            nombre_cat = itemView.findViewById(R.id.nombre_cat);
            card_cat = itemView.findViewById(R.id.card);
        }

        public ImageView getImagen_cat() {
            return imagen_cat;
        }

        public TextView getNombre_cat() {
            return nombre_cat;
        }
    }

    @NonNull
    @Override
    public CatRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fila_cat,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        Categoria cat = dataset_categorias.get(posicion);
        viewHolder.imagen_cat.setImageResource(android.R.drawable.star_on);
        viewHolder.nombre_cat.setText(cat.getNombre());
        viewHolder.card_cat.setCardBackgroundColor(Color.parseColor(cat.getColor()));
    }

    @Override
    public int getItemCount() {
        return dataset_categorias.size();
    }


}
