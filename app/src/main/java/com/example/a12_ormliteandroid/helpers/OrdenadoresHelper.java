package com.example.a12_ormliteandroid.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.a12_ormliteandroid.configuraciones.Configuracion;
import com.example.a12_ormliteandroid.modelos.Ordenador;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

// Necesita heredar de la librería ORM Helper
public class OrdenadoresHelper extends OrmLiteSqliteOpenHelper {



    // Atributo DAO que va a trabajar sobre la clase Ordenador y su PK será int
    private Dao<Ordenador, Integer> daoOrdenador;
    // (necesario su get)
    public Dao<Ordenador, Integer> getDaoOrdenador() throws SQLException {
        if (daoOrdenador == null){
            daoOrdenador = this.getDao(Ordenador.class);
        }
        return daoOrdenador;
    }

    // Modificamos el const super y dejamos solo en context como argumento, y factory a null
    public OrdenadoresHelper(Context context) {
        super(context, Configuracion.DB_NAME, null, Configuracion.DB_VERSION);
    }

    // Obligatorio implementar onCreate & onUpgrade
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        // Crea la tabla en base a la clase Ordenador
        try {
            TableUtils.createTable(connectionSource, Ordenador.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
