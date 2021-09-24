package com.example.compraventa.model;

import android.content.Context;
import android.graphics.Color;
import android.renderscript.ScriptGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private static String FILENAME = "categorias.json";
    private String id;
    private String nombre;
    private String color;

    public Categoria(){}

    public Categoria(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getColor() {
        return color;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "[" + id + "] '" + nombre;
    }

    public JSONObject toJson(){
        JSONObject cat = new JSONObject();
        try{
            cat.put("id",this.getId());
            cat.put("name",this.getNombre());
            cat.put("color",this.getColor());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cat;
    }

    public void loadFromJson(JSONObject cat){
        try {
            this.setId(cat.getString("id"));
            this.setNombre(cat.getString("name"));
            this.setColor(cat.getString("color"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static List<Categoria> leerCategoria(Context ctx){
        List<Categoria> lista_categorias = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        InputStreamReader is=null;
        try{
            is = new InputStreamReader(ctx.getAssets().open("categorias.json"),"UTF-8");

            BufferedReader buff = new BufferedReader(is);
            String linea;
            while((linea = buff.readLine())!=null){
                sb.append(linea);
            }
            is.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String contenido = sb.toString();


        try{
            JSONArray datos = (JSONArray) new JSONTokener(contenido).nextValue();
            for (int i = 0; i < datos.length(); i++) {
                JSONObject fila = datos.getJSONObject(i);
                Categoria cat = new Categoria();
                cat.loadFromJson(fila);
                lista_categorias.add(cat);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista_categorias;
    }
}
