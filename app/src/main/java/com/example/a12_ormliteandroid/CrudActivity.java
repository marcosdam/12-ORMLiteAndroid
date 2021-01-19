package com.example.a12_ormliteandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.a12_ormliteandroid.databinding.ActivityCrudBinding;
import com.example.a12_ormliteandroid.helpers.OrdenadoresHelper;
import com.example.a12_ormliteandroid.modelos.Ordenador;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class CrudActivity extends AppCompatActivity {

    // Binding
    private ActivityCrudBinding binding;
    // Helper para acceder a la BD
    private OrdenadoresHelper helper;
    // DAO para acceder al Ordenador con Clave Primaria
    private Dao<Ordenador, Integer> daoOrdenadores;
    // Ordenador para obtener id
    private Ordenador ordenador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrudBinding.inflate(getLayoutInflater()); // Obtenemos Layout del binding
        setContentView(binding.getRoot());

        // Obtener el id del Ordenador a mostrar
        int id = getIntent().getExtras().getInt("ID");

        // Inicializar acceso a Base de Datos
        helper = OpenHelperManager.getHelper(this, OrdenadoresHelper.class);
        try {
            daoOrdenadores = helper.getDaoOrdenador();
            ordenador = daoOrdenadores.queryForId(id);

            // Eliminar (pedir confirmación con otro Alert Dialog)
            binding.btnEliminarCrud.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CrudActivity.this);
                    builder.setTitle("¿Estás seguro? ¿Segurísimo?");
                    builder.setNegativeButton("CANCELAR", null);

                    builder.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CrudActivity.this);
                            builder.setTitle("¿Estás seguro de verdad? No podrás recuperar el dato eliminado");
                            builder.setPositiveButton("SÍ, ELIMINAR", null);
                            try {
                                daoOrdenadores.delete(ordenador);
                                finish();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    });
                    // Mostrar Alert Dialog
                    builder.show();
                }
            });

            // BTN Actualizar
            binding.btnActualizarCrud.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ordenador.setMarca(binding.txtMarcaCrud.getText().toString());
                    ordenador.setModelo(binding.txtModeloCrud.getText().toString());
                    ordenador.setRam(Integer.parseInt(binding.txtRamCrud.getText().toString()));
                    ordenador.setHd(Float.parseFloat(binding.txtHdCrud.getText().toString()));

                    try {
                        daoOrdenadores.update(ordenador);
                        finish();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}