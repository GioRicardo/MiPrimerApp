package com.giordanricardo.miprimerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.giordanricardo.miprimerapp.modelos.Usuario;

import java.util.ArrayList;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.viewHolderUsuarios> {

    ArrayList<Usuario> listaUsuarios;

    public AdapterUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    @NonNull
    @Override
    public viewHolderUsuarios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list,null,false);
        return new viewHolderUsuarios(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderUsuarios holder, int position) {
        holder.nombre.setText(listaUsuarios.get(position).getNombre());
        holder.correo.setText(listaUsuarios.get(position).getCorreo());
        holder.contrasena.setText(listaUsuarios.get(position).getContrasena());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class viewHolderUsuarios extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView correo;
        TextView contrasena;
        public viewHolderUsuarios(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.viewNombre);
            correo = itemView.findViewById(R.id.viewCorreo);
            contrasena = itemView.findViewById(R.id.viewContrasena);

        }
    }
}
