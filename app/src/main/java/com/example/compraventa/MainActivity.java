package com.example.compraventa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText et_titulo;
    EditText et_descripcion;
    EditText et_correo;
    EditText et_precio;
    Spinner sp_categoria;
    Switch sw_descuento;
    SeekBar sb_descuento;
    CheckBox cb_retiro;
    EditText et_direccion;
    CheckBox cb_terminos;
    Button bu_publicar;
    LinearLayout componentePorcentaje;
    TextView lbl_direccion;
    TextView lbl_porcentaje_descuento;
    Button selector_categoria;
    TextView nombre_categoria;
    Integer categoria_seleccionada;
    private static Pattern patron;
    private static Pattern patron_correo;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1001){
            if(resultCode== Activity.RESULT_OK) {
                nombre_categoria.setText(data.getExtras().getString("NOMBRE"));
                nombre_categoria.setBackgroundColor(Color.parseColor(data.getExtras().getString("COLOR")));
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        patron = Pattern.compile("([a-zA-Z]|,|\\d|\\.|\\n|\\s)*");
        patron_correo = Pattern.compile("([a-zA-Z]|\\d|\\.)+@([a-zA-Z]|\\d|\\.)+");

        et_titulo = findViewById(R.id.pt_titulo);
        et_descripcion = findViewById(R.id.pt_descripcion);
        et_correo = findViewById(R.id.em_correo);
        et_precio = findViewById(R.id.nu_precio);
        sp_categoria = findViewById(R.id.sp_categoria);
        sw_descuento = findViewById(R.id.sw_descuento);
        sb_descuento = findViewById(R.id.sb_descuento);
        cb_retiro = findViewById(R.id.cb_retiro);
        lbl_direccion = findViewById(R.id.tv_direccion);
        et_direccion = findViewById(R.id.pa_direccion);
        cb_terminos = findViewById(R.id.cb_terminos);
        bu_publicar = findViewById(R.id.bu_publicar);
        componentePorcentaje = findViewById(R.id.componentePorcentaje);
        lbl_porcentaje_descuento = findViewById(R.id.porcentaje_descuento);
        categoria_seleccionada=-1;
        selector_categoria = findViewById(R.id.selector_categoria);
        nombre_categoria = findViewById(R.id.nombre_categoria);

        selector_categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,CategoriaActivity.class);
                i.putExtra("SELECCION",categoria_seleccionada);
                startActivityForResult(i,1001);
            }
        });

        sw_descuento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                
                if(isChecked){
                    componentePorcentaje.setVisibility(View.VISIBLE);
                }else{
                    componentePorcentaje.setVisibility(View.GONE);
                }
            }
        });

        sb_descuento.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                lbl_porcentaje_descuento.setText(progress+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cb_retiro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    et_direccion.setVisibility(View.VISIBLE);
                    lbl_direccion.setVisibility(View.VISIBLE);
                }else{
                    et_direccion.setVisibility(View.GONE);
                    lbl_direccion.setVisibility(View.GONE);
                }
            }
        });

        cb_terminos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                bu_publicar.setEnabled(isChecked);
            }
        });

        bu_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> salida = validar();
                if(salida.isEmpty()){
                    Toast.makeText(getApplicationContext(),"El clasificado se creó exitosamente",Toast.LENGTH_SHORT).show();
                }
                else{
                    String mensaje="Revisá lo siguiente:\n";
                    for(String s : salida){
                        mensaje+="* "+s+"\n";
                    }
                    Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private Boolean cadenaValida(String cadena){
        return patron.matcher(cadena).matches();
    }
    private Boolean correoValido(String cadena){
        return patron_correo.matcher(cadena).matches();
    }

    private ArrayList<String> validar(){
       ArrayList<String> mensajes = new ArrayList<>();
       if(TextUtils.isEmpty(et_titulo.getText())) {
           mensajes.add("El campo título es obligatorio.");
       }else{
           if(!cadenaValida(et_titulo.getText().toString())){
               mensajes.add("El título solo debe contener letras,números,comas o puntos.");
           }
       }

       if(!cadenaValida(et_descripcion.getText().toString())){
           mensajes.add("El descripción solo debe contener letras,números,comas o puntos.");
       }

       if(!correoValido(et_correo.getText().toString())){
           mensajes.add("El correo electrónico tiene un formato incorrecto.");
       }

       if(TextUtils.isEmpty(et_precio.getText())) {
           mensajes.add("El campo precio es obligatorio.");
       }else{
           if(Double.parseDouble(et_precio.getText().toString())<=0.0){
               mensajes.add("El precio debe ser mayor a $0.");
           }
       }
        if(TextUtils.isEmpty(nombre_categoria.getText())) {
            mensajes.add("Debe seleccionar una categoría.");
        }


       if(cb_retiro.isChecked()) {
           if(TextUtils.isEmpty(et_direccion.getText())) {
               mensajes.add("El campo dirección de retiro es obligatorio.");
           }else{
               if(!cadenaValida(et_direccion.getText().toString())){
                   mensajes.add("La dirección de retiro solo debe contener letras,números,comas o puntos.");
               }
           }
       }
       if(sw_descuento.isChecked() && sb_descuento.getProgress()==0) {
           mensajes.add("El descuento de envío debe ser mayor a 0%.");
       }

       return mensajes;
    }
}

