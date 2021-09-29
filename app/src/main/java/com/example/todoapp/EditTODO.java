package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTODO extends AppCompatActivity {

    Button btn;
    EditText et1,et2;
    DBhandler dBhandler;
    Context context;
    Long UpdateDate;
    ToDo toDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        btn=findViewById(R.id.btnnewEdit);
        et1=findViewById(R.id.etTitleEdit);
        et2=findViewById(R.id.etDescriptionEdit);
        final String id=getIntent().getStringExtra("id");
        System.out.println(id);
        context =this;
        dBhandler=new DBhandler(context);
       toDo= dBhandler.getSingleTodo(Integer.parseInt(id));

       et1.setText(toDo.getTitle());
       et2.setText(toDo.getDescription());

       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String title= et1.getText().toString();
          String description=et2.getText().toString();
          UpdateDate=System.currentTimeMillis();

          ToDo toDo=new ToDo(Integer.parseInt(id),title,description,UpdateDate,0);
          dBhandler=new DBhandler(context);
         int stat= dBhandler.UpdateSngleToDo(toDo);
               System.out.println(stat);

          startActivity(new Intent(context,MainActivity.class));

           }
       });





    }
}