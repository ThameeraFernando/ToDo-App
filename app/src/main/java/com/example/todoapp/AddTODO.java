package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTODO extends AppCompatActivity {


    EditText et1,et2;
    Button btn;
    DBhandler dBhandler;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        et1=findViewById(R.id.etTitleEdit);
        et2=findViewById(R.id.etDescriptionEdit);
        btn=findViewById(R.id.btnnewEdit);
        context=this;
        dBhandler=new DBhandler(context);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title=et1.getText().toString();
                String description=et2.getText().toString();
                long started=System.currentTimeMillis();

                ToDo toDo=new ToDo(title,description,started,0);
                dBhandler.addToDo(toDo);

                startActivity(new Intent(context,MainActivity.class));


            }
        });

    }
}