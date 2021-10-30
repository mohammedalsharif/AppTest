package com.example.apptest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class EXdbacses {

    private SQLiteDatabase db;
    private SQLiteOpenHelper obenhelper;
    private static EXdbacses instance;

    private EXdbacses(Context context){
        this.obenhelper=new EXternalDatabase(context);

    }

    public static EXdbacses getInstance(Context context){
        if (instance==null){

            instance=new EXdbacses(context);
        }
        return instance;

    }
    public void open(){
        this.db= obenhelper.getWritableDatabase();
    }


    public void close(){
        if (this.db!=null){
            db.close();
        }
    }

    public boolean insertCar(Car car ){
        ContentValues values= new ContentValues();
        values.put(EXternalDatabase.table_model,car.getModel());
        values.put(EXternalDatabase.table_color,car.getColor());
        values.put(EXternalDatabase.table_distanseberlater,car.getDpl());
        values.put(EXternalDatabase.table_imag,car.getImag());
        values.put(EXternalDatabase.table_Discrption,car.getDpl());
        long ruselt = db.insert(EXternalDatabase.table_NAME,null,values);
         if(ruselt != -1){
             return true;
         }else
             return false;
    }

    public boolean updateCar(Car car ){

        ContentValues values=new ContentValues();
        values.put(EXternalDatabase.table_model,car.getModel());
        values.put(EXternalDatabase.table_color,car.getColor());
        values.put(EXternalDatabase.table_distanseberlater,car.getDpl());
        values.put(EXternalDatabase.table_imag,car.getImag());
        values.put(EXternalDatabase.table_Discrption,car.getDescription());
        String [] args={car.getId()+""};
        int ruslt = db.update(EXternalDatabase.table_NAME,values,"id=?",args);
        if (ruslt>=0){
            return true;
        }else {
            return false;
        }
    }
    public long getCarsCount(){

        return DatabaseUtils.queryNumEntries(db,EXternalDatabase.table_NAME);

    }

    public boolean DeletCar(Car car ){

        String [] args={car.getId()+""};
        int ruslt = db.delete(EXternalDatabase.table_NAME,"id=?",args);

        return ruslt >0;

    }
    //استرجاع البيانات
    public ArrayList<Car> getdatacar (){
        ArrayList<Car> cars =new ArrayList<>();
        Cursor cursor=db.rawQuery("SELECT * FROM " +EXternalDatabase.table_NAME,null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex(EXternalDatabase.table_id));
                String modle = cursor.getString(cursor.getColumnIndex(EXternalDatabase.table_model));
                String color= cursor.getString(cursor.getColumnIndex(EXternalDatabase.table_color));
                double instance= cursor.getDouble(cursor.getColumnIndex(EXternalDatabase.table_distanseberlater));
                String imag= cursor.getString(cursor.getColumnIndex(EXternalDatabase.table_imag));
                String Descrption= cursor.getString(cursor.getColumnIndex(EXternalDatabase.table_Discrption));
                Car car=new Car(id,modle,color,instance,imag,Descrption);
                cars.add(car);
            }
            while (cursor.moveToNext());
            cursor.close();
        }

        return  cars;
    }
    public Car getCar (int carid){

        Cursor cursor=db.rawQuery("SELECT * FROM " +EXternalDatabase.table_NAME+" WHERE "+EXternalDatabase.table_id+"= ?",new String[]{carid+""});
        if (cursor.moveToFirst()){

                int id = cursor.getInt(cursor.getColumnIndex(EXternalDatabase.table_id));
                String modle = cursor.getString(cursor.getColumnIndex(EXternalDatabase.table_model));
                String color= cursor.getString(cursor.getColumnIndex(EXternalDatabase.table_color));
                double instance= cursor.getDouble(cursor.getColumnIndex(EXternalDatabase.table_distanseberlater));
                String imag= cursor.getString(cursor.getColumnIndex(EXternalDatabase.table_imag));
                String Descrption= cursor.getString(cursor.getColumnIndex(EXternalDatabase.table_Discrption));
                Car car=new Car(id,modle,color,instance,imag,Descrption);
                cursor.close();
                return car;
            }
        return null;
    }
    public ArrayList<Car> getdatacar (String Serchmodel){
        ArrayList<Car> cars =new ArrayList<>();
        Cursor cursor=db.rawQuery("SELECT * FROM " +EXternalDatabase.table_NAME+" WHERE "+EXternalDatabase.table_model+" LIKE ?",new String[]{Serchmodel+"%"});
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex(EXternalDatabase.table_id));
                String modle = cursor.getString(cursor.getColumnIndex(EXternalDatabase.table_model));
                String color= cursor.getString(cursor.getColumnIndex(EXternalDatabase.table_color));
                double instance= cursor.getDouble(cursor.getColumnIndex(EXternalDatabase.table_distanseberlater));
                String imag= cursor.getString(cursor.getColumnIndex(EXternalDatabase.table_imag));
                String Descrption= cursor.getString(cursor.getColumnIndex(EXternalDatabase.table_Discrption));
                Car car=new Car(id,modle,color,instance,imag,Descrption);
                cars.add(car);
            }
            while (cursor.moveToNext());
            cursor.close();
        }

        return  cars;
    }
}
