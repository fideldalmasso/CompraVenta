package com.example.compraventa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.compraventa.model.Categoria;

public class CategoriaActivity extends AppCompatActivity {

    protected RecyclerView lista_recycler;
    protected RecyclerView.LayoutManager layout_manager;
    protected CatRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_categoria);




        lista_recycler = findViewById(R.id.lista_recycler);
        lista_recycler.setHasFixedSize(true);
        layout_manager = new LinearLayoutManager(this);
        lista_recycler.setLayoutManager(layout_manager);

        adapter = new CatRecyclerAdapter(Categoria.leerCategoria(getApplicationContext()),this);
        lista_recycler.setAdapter(adapter);

    }
}