package com.example.a12_ormliteandroid.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a12_ormliteandroid.CrudActivity;
import com.example.a12_ormliteandroid.R;
import com.example.a12_ormliteandroid.modelos.Ordenador;

import java.util.List;

public class OrdenadoresAdapter extends RecyclerView.Adapter<OrdenadoresAdapter.OrdenadorVH> {

    private Context context;
    private List<Ordenador> objects;
    private int resource;

    public OrdenadoresAdapter(Context context, List<Ordenador> objects, int resource) {
        this.context = context;
        this.objects = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public OrdenadorVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resource, null);   // View
        // Layout params
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new OrdenadorVH(view);   // Devuelve new OrdenadorVH
    }

    @Override
    public void onBindViewHolder(@NonNull OrdenadorVH holder, int position) {
        holder.txtMarca.setText(objects.get(position).getMarca());
        holder.txtModelo.setText(objects.get(position).getModelo());
        holder.txtRam.setText(objects.get(position).getRam()+"");
        holder.txtHd.setText(objects.get(position).getHd()+"");

        // Asignar onClick a toda la fila (a tod0 el ViewHolder)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Crear Bundle con el ID del ordenador en la BD
                Bundle bundle = new Bundle();
                bundle.putInt("ID", objects.get(position).getId());
                // 2. Crear Intent
                // (desde un Adapter no podemos decir que el origen es una Activity, en este caso es el context)
                Intent intent = new Intent(context, CrudActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();  // Devuelve tamaño de lista objects
    }

    public static class OrdenadorVH extends RecyclerView.ViewHolder {

        TextView txtMarca, txtModelo, txtRam, txtHd;

        public OrdenadorVH(@NonNull View itemView) {
            super(itemView);

            txtMarca = itemView.findViewById(R.id.txtMarcaCard);
            txtModelo = itemView.findViewById(R.id.txtModeloCard);
            txtRam = itemView.findViewById(R.id.txtRamCard);
            txtHd = itemView.findViewById(R.id.txtHdCard);
        }
    }
}
