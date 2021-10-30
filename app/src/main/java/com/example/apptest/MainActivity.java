package com.example.apptest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rView;
    FloatingActionButton floatingAButton;
    Recyclerview recyclerview;
    EXdbacses db;
    private static final int ADD_REQ_CODE=1;
    private static final int EDIT_CAR_REQ_CODE=1;
    public static final String CAR_KEY="Car_KEY";
    private static final int PERMISSIO_REQ_CODE=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE )
              != PackageManager.PERMISSION_GRANTED){
          ActivityCompat.requestPermissions(this,new String[]{Manifest.permission
                   .WRITE_EXTERNAL_STORAGE}, PERMISSIO_REQ_CODE);}

        rView=findViewById(R.id.mainrecuclerv);
        floatingAButton=findViewById(R.id.floatingabt);
        floatingAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),VeiwCardactivity.class);
                startActivityForResult(intent,ADD_REQ_CODE);
            }
        });

        toolbar=findViewById(R.id.ditaels_toolbar);
        setSupportActionBar(toolbar);
        db=EXdbacses.getInstance(this);
        db.open();
        ArrayList<Car>cars=db.getdatacar();
        db.close();

        recyclerview=  new Recyclerview(cars, new OnRecyclerViewitemClickListener() {
            @Override
            public void onItemClick(int carId) {
                Intent intent=new Intent(getBaseContext(),VeiwCardactivity.class);
                intent.putExtra(CAR_KEY,carId);
                startActivityForResult(intent,EDIT_CAR_REQ_CODE);
            }
        });
        rView.setAdapter(recyclerview);
        RecyclerView.LayoutManager lm=new GridLayoutManager(this,2);
        rView.setLayoutManager(lm);
        rView.setHasFixedSize(true);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchView sv= (SearchView) menu.findItem(R.id.main_serch).getActionView();
        sv.setSubmitButtonEnabled(true);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                db.open();
                ArrayList<Car>cars=db.getdatacar(query);
                db.close();
                recyclerview.setCars(cars);
                recyclerview.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                db.open();
                ArrayList<Car>cars=db.getdatacar(newText);
                db.close();
                recyclerview.setCars(cars);
                recyclerview.notifyDataSetChanged();
                return false;
            }



        });

        sv.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Deleted");
                builder.setMessage("Deleted");
                builder.setIcon(R.drawable.delete);
                builder.show();
                db.open();
                ArrayList<Car>cars=db.getdatacar();
                db.close();
                recyclerview.setCars(cars);
                recyclerview.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ADD_REQ_CODE){
            db.open();
            ArrayList<Car>cars=db.getdatacar();
            db.close();
            recyclerview.setCars(cars);
            recyclerview.notifyDataSetChanged();

        }



    }

   @Override
       public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
          switch (requestCode){
         case PERMISSIO_REQ_CODE:
              if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                  Toast.makeText(getApplicationContext(),"تم الحصول على الصلاحية",Toast.LENGTH_LONG).show();

                    //تم الحصول على صلاحية
             }else {

               }
               break;
       }
   }
}