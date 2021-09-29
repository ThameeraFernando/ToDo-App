package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBhandler extends SQLiteOpenHelper {

    private static final int VERSION=1;
    private static final String DB_NAME="todo";
    private static final String TABLE_NAME="todo";
    private static final String ID="id";
    private static final String TITLE ="title";
    private static final String DESCRIPTION ="description";
    private static final String STARED ="started";
    private static final String FINISHED="finished";


    public DBhandler(@Nullable  Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    //create tables
    //this is automatically execute
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql="CREATE TABLE "+TABLE_NAME+" "+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+TITLE+" TEXT,"+DESCRIPTION+" TEXT,"+
                STARED+" TEXT,"+FINISHED+" TEXT"+");";

        db.execSQL(sql);


    }


    //update database
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String DROP_TABLE_QUERY="DROP TABLE IF EXISTS "+ TABLE_NAME;
        //drop older table if exist
        db.execSQL(DROP_TABLE_QUERY);
        //Create tables again
        onCreate(db);


    }
    public void addToDo(ToDo toDo){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();


        ContentValues contentValues=new ContentValues();


        contentValues.put(TITLE,toDo.getTitle());
        contentValues.put(DESCRIPTION,toDo.getDescription());
        contentValues.put(STARED,toDo.getStarted());
        contentValues.put(FINISHED,toDo.getFinished());

        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        sqLiteDatabase.close();




    }

    public int countTodo(){

        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+ TABLE_NAME;

        Cursor cursor=db.rawQuery(query,null);

        return cursor.getCount();
    }

    public List<ToDo> getAllToDo(){

        List<ToDo> toDos=new ArrayList();
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME;

        Cursor cursor=db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                ToDo toDo=new ToDo();

                toDo.setId(cursor.getInt(0));
                toDo.setTitle(cursor.getString(1));
                toDo.setDescription(cursor.getString(2));
                toDo.setStarted(cursor.getLong(3));
                toDo.setFinished(cursor.getLong(4));

                toDos.add(toDo);
            }while(cursor.moveToNext());
            
        }
        return toDos;

    }

    public void deleteToDo(int id){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE_NAME,ID +" =?",new String[]{String.valueOf(id)});
        db.close();

    }

    public ToDo getSingleTodo(int id){
        SQLiteDatabase db=getReadableDatabase();

        Cursor cursor=db.query(TABLE_NAME,new String[]{ID,TITLE,DESCRIPTION,STARED,FINISHED},ID+" =?",new String[]{String.valueOf(id)},null,null,null);

        ToDo toDo;
        if (cursor != null){
                cursor.moveToFirst();

            toDo=new ToDo(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getLong(3),
                    cursor.getLong(4)
            );
            return toDo;
        }
        return null;
    }

    public int UpdateSngleToDo(ToDo toDo){
        SQLiteDatabase db=getWritableDatabase();

        ContentValues contentValues=new ContentValues();


        contentValues.put(TITLE,toDo.getTitle());
        contentValues.put(DESCRIPTION,toDo.getDescription());
        contentValues.put(STARED,toDo.getStarted());
        contentValues.put(FINISHED,toDo.getFinished());

        int status=db.update(TABLE_NAME,contentValues,ID+ "=?",new String[]{String.valueOf(toDo.getId())});

        db.close();

        return status;
    }





}
