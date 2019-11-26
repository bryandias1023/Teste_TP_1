package com.example.teste_tp_1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.teste_tp_1.R;
import com.example.teste_tp_1.entities.Person;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<Person> {
    public CustomArrayAdapter(Context context, ArrayList<Person> users){
        super(context,0,users);
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent){
        Person p = getItem(position);
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.layout_linha,parent,false);
        }

        ((TextView) convertView.findViewById(R.id.name)).setText(p.getNome());
        ((TextView) convertView.findViewById(R.id.surname)).setText(p.getApelido());

        return convertView;
    }
}
