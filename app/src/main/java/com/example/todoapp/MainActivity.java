package com.example.todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button add;
    ListView listView;
    TextView count;
    Context context;
    DBhandler dBhandler;
    List<ToDo> toDos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add=findViewById(R.id.btnAdd);
        listView=findViewById(R.id.listView);
        count=findViewById(R.id.tvTotal);
        context=this;
        dBhandler=new DBhandler(context);

        int countX=dBhandler.countTodo();

        count.setText("You have "+countX+" todo");

        toDos=new ArrayList<>();
        toDos=dBhandler.getAllToDo();
        ToDoAdaptor  toDoAdaptor=new ToDoAdaptor(context,R.layout.singleraw,toDos);
        listView.setAdapter(toDoAdaptor);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(context,AddTODO.class));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ToDo selectTodo=toDos.get(i);
                String title=selectTodo.getTitle();
                String description=selectTodo.getDescription();

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle(title);
                builder.setMessage(description);
                builder.setPositiveButton("Finished", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            selectTodo.setFinished(System.currentTimeMillis());
                            dBhandler.UpdateSngleToDo(selectTodo);
                            startActivity(new Intent(context,MainActivity.class));
                    }
                });

                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    dBhandler.deleteToDo(selectTodo.getId());
                    startActivity(new Intent(context,MainActivity.class));
                    }
                });
                builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(context,EditTODO.class);
                        intent.putExtra("id",String.valueOf(selectTodo.getId()));
                        startActivity(intent);
                    }
                });
                builder.show();

            }
        });


    }
}