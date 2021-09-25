package com.example.compraventa;

import android.app.Activity;
import android.content.Intent;
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

    private static List<Categoria> dataset_categorias;
    private static CategoriaActivity activity;
    public CatRecyclerAdapter(List<Categoria> dataset_categorias, CategoriaActivity act) {
        this.dataset_categorias = dataset_categorias;
        this.activity = act;
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
            this.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Intent i = new Intent(activity,MainActivity.class);
                    Integer posicion = getAdapterPosition();
                    i.putExtra("NOMBRE",nombre_cat.getText());
                    i.putExtra("COLOR",dataset_categorias.get(posicion).getColor());
                    activity.setResult(Activity.RESULT_OK,i);
                    activity.finish();

                }
            });
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
