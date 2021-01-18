package com.example.a12_ormliteandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
