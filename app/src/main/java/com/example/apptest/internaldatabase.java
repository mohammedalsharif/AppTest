package com.example.apptest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class internaldatabase extends SQLiteOpenHelper {
    public static final String db_NAME="Cars_db";
    public static final String table_NAME="cars";
    public static final String table_id="id";
    public static final String table_model="model";
    public static final String table_color="Color";
    public static final String table_imag="imag";
    public static final String table_distanseberlater="distanceberltaer";
    public static final String table_Discrbtion="Discrbtion";
    public static final int virsion_number=1;

    public internaldatabase(Context context ) {
        super(context,db_NAME,null,virsion_number);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ table_NAME+ " ("+table_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+table_model+" TEXT UNIQUE,COLOR TEXT NOT NULL," +
                " "+table_distanseberlater+"REAL"+table_color+"text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop Table if exists "+table_NAME);
        onCreate(db);
    }
    public boolean insertCar(Car car){
        SQLiteDatabase db= getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(table_model,car.getModel());
        values.put(table_color,car.getColor());
        values.put(table_distanseberlater,car.getDpl());
        values.put(table_imag,car.getImag());
        values.put(table_Discrbtion,car.getDescription());

        long ruslt= db.insert(table_NAME,null,values);

    return ruslt!=-1;
    }

    public boolean updateCar(Car car){
        SQLiteDatabase db= getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(table_model,car.getModel());
        values.put(table_color,car.getColor());
        values.put(table_distanseberlater,car.getDpl());
        values.put(table_imag,car.getImag());
        values.put(table_Discrbtion,car.getDescription());
        String args []={car.getId()+""};
        int ruslt= db.update(table_NAME,values,"id=?",args);

        return ruslt>0;
    }

    public long getCarCuont(){

        SQLiteDatabase db=getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db,table_NAME);
    }

    public boolean deleteCar(Car car){
        SQLiteDatabase db=getWritableDatabase();
        String args[]={car.getId()+""};
     int ruselt=db.delete(table_NAME,"id=?",args);

     return ruselt > 0;
    }

    public ArrayList<Car> getallCars(){
        ArrayList<Car> cars=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
       Cursor cursor= db.rawQuery("SELECT * From "+table_NAME,null);
      boolean ruslt= cursor.moveToFirst();
      if (ruslt){
          do {
              int id= cursor.getInt(cursor.getColumnIndex(table_id));
              String model=cursor.getString(cursor.getColumnIndex(table_model));
              double dbl=cursor.getDouble(cursor.getColumnIndex(db_NAME));
              String color=cursor.getString(cursor.getColumnIndex(table_color));

              Car c=new Car(id,model,color,dbl,null,null);
              cars.add(c);

          }while(cursor.moveToNext());

          cursor.close();

      }
        return cars;
    }

}
