package com.example.a12_ormliteandroid;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.a12_ormliteandroid.adapters.OrdenadoresAdapter;
import com.example.a12_ormliteandroid.databinding.ActivityMainBinding;
import com.example.a12_ormliteandroid.helpers.OrdenadoresHelper;
import com.example.a12_ormliteandroid.modelos.Ordenador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // viewBinding
    private ActivityMainBinding binding;

    // Base de Datos
    private OrdenadoresHelper helper;
    private Dao<Ordenador, Integer> daoOrdenadores;

    // Objetos para el RecyclerView
    private OrdenadoresAdapter adapter;
    private int resource = R.layout.ordenador_card;
    private RecyclerView.LayoutManager lm;
    private ArrayList<Ordenador> listaOrdenadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Instanciar Base de Datos -> // helper = new OrdenadoresHelper(this);
        // Instanciar Base de Datos (compatible con cualquier versión de ORM Lite)
        helper = OpenHelperManager.getHelper(this, OrdenadoresHelper.class);

        // Instanciar Recycler
        listaOrdenadores = new ArrayList<>();
        lm = new LinearLayoutManager(this);
        adapter = new OrdenadoresAdapter(this, listaOrdenadores, resource);
        binding.contenido.recyclerView.setHasFixedSize(true);
        binding.contenido.recyclerView.setAdapter(adapter);
        binding.contenido.recyclerView.setLayoutManager(lm);



        if (helper != null){
            try {
                daoOrdenadores = helper.getDaoOrdenador();
                // Rellenar lista con datos de la Base de datos
                listaOrdenadores.addAll(daoOrdenadores.queryForAll());
                adapter.notifyDataSetChanged();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Llamar a createOrdenador y mostrarlo (Alert Dialog)
                createOrdenador().show();
            }
        });
    }

    // Func para crear ordenador con el Alert Dialog
    private AlertDialog createOrdenador(){
        // 1. Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Crear Ordenador");
        builder.setCancelable(false);
        View layoutAlert = getLayoutInflater().inflate(R.layout.ordenador_dialog, null);
        // La siguiente línea es lo mismo, para clases que no son actividades necesito el context
        // layoutAlert = LayoutInflater.from(this).inflate(R.layout.ordenador_dialog, null);

        // 2. TextViews para el Alert
        TextView txtMarca = layoutAlert.findViewById(R.id.txtMarcaDialog);
        TextView txtModelo = layoutAlert.findViewById(R.id.txtModeloDialog);
        TextView txtRam = layoutAlert.findViewById(R.id.txtRamDialog);
        TextView txtHD = layoutAlert.findViewById(R.id.txtHDDialog);

        builder.setView(layoutAlert);

        // 3. Botones del Alert Dialog
        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("CREAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!txtMarca.getText().toString().isEmpty()
                    && !txtModelo.getText().toString().isEmpty()
                    && !txtRam.getText().toString().isEmpty()
                    && !txtHD.getText().toString().isEmpty()){
                    // Si los campos están rellenos, creamos ordenador y lo añadimos a la lista
                    Ordenador ordenador = new Ordenador();
                    ordenador.setMarca(txtMarca.getText().toString());
                    ordenador.setModelo(txtModelo.getText().toString());
                    ordenador.setRam(Integer.parseInt(txtRam.getText().toString()));
                    ordenador.setHd(Float.parseFloat(txtHD.getText().toString()));

                    try {
                        daoOrdenadores.create(ordenador);
                        int lastId = daoOrdenadores.extractId(ordenador);
                        ordenador.setId(lastId);
                        listaOrdenadores.add(ordenador);
                        adapter.notifyDataSetChanged();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        // 3. Return AlertDialog
        return builder.create();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Siempre que vuelta de la otra actividad limpiará la lista y la volverá a rellenar con lo obtenido de la query
        listaOrdenadores.clear();
        try {
            listaOrdenadores.addAll(daoOrdenadores.queryForAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }
}