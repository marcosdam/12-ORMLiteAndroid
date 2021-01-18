package com.example.a12_ormliteandroid;

import android.os.Bundle;

import com.example.a12_ormliteandroid.adapters.OrdenadoresAdapter;
import com.example.a12_ormliteandroid.databinding.ActivityMainBinding;
import com.example.a12_ormliteandroid.helpers.OrdenadoresHelper;
import com.example.a12_ormliteandroid.modelos.Ordenador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

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
                Ordenador ordenador = new Ordenador("Acer", "Aspire", 8, 1.5f);
                if (daoOrdenadores != null){
                    try {
                        int lastID = daoOrdenadores.create(ordenador);   // INSERT en la Base de datos
                        ordenador.setId(lastID);    // Darle el ID
                        listaOrdenadores.add(ordenador);    // Añadirlo a la lista
                        adapter.notifyDataSetChanged();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
    }
}