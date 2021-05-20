package com.example.contatos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contatos.databinding.ViewContatoBinding;

import java.util.ArrayList;

public class ContatosAdapter extends ArrayAdapter<Contato> {
    public ContatosAdapter(Context contexto, int layout, ArrayList<Contato>contatosList) {
        super(contexto, layout, contatosList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewContatoBinding viewContatoBinding;
        ContatoViewHolder contatoViewHolder;
        if(convertView == null){
            viewContatoBinding = ViewContatoBinding.inflate(LayoutInflater.from(getContext()));
            convertView = viewContatoBinding.getRoot();

            //referencias para as views internas da celula com o holder
            contatoViewHolder = new ContatoViewHolder();
            contatoViewHolder.nomeContatoTv = viewContatoBinding.nomeContatoTv;
            contatoViewHolder.emailContatoTv = viewContatoBinding.emailContatoTv;
            convertView.setTag(contatoViewHolder);
        }
        contatoViewHolder = (ContatoViewHolder) convertView.getTag();

        Contato contato = getItem(position);
        contatoViewHolder.nomeContatoTv.setText(contato.getNome());
        contatoViewHolder.emailContatoTv.setText(contato.getEmail());

        return convertView;
    }

    private class ContatoViewHolder{
        public TextView nomeContatoTv;
        public TextView emailContatoTv;
    }
}
