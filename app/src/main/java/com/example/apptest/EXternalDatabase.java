package com.example.apptest;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class EXternalDatabase extends SQLiteAssetHelper {
    public static final String db_NAME="CARS.db";
    public static final String table_NAME="cars";
    public static final String table_id="id";
    public static final String table_model="model";
    public static final String table_color="Color";
    public static final String table_imag="imag";
    public static final String table_Discrption="Discrbtion";
    public static final String table_distanseberlater="distanceberltaer";
    public static final int virsion_number=1;
    public EXternalDatabase(Context context) {
        super(context, db_NAME, null, virsion_number);
    }

}
