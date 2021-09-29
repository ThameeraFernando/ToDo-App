package com.example.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ToDoAdaptor extends ArrayAdapter<ToDo> {

    Context context;
    int resource;
    List<ToDo> toDos;


    public ToDoAdaptor(Context context, int resource, List<ToDo> toDos){
        super(context,resource,toDos);

        this.context=context;
        this.resource=resource;
        this.toDos=toDos;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater=LayoutInflater.from(context);
        View row=inflater.inflate(resource,parent,false);

        TextView title=row.findViewById(R.id.tvTitle);
        TextView description =row.findViewById(R.id.tvdescription);
        ImageView imageView=row.findViewById(R.id.imageView);

        ToDo toDo=toDos.get(position);
        title.setText(toDo.getTitle());
        description.setText(toDo.getDescription());
        imageView.setVisibility(row.INVISIBLE);

        if (toDo.getFinished()>0){
            imageView.setVisibility(View.VISIBLE);

        }

        return row;
    }

}
