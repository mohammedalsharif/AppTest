package com.example.apptest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class VeiwCardactivity extends AppCompatActivity {

    private static final int RECST_IMAG_CAR = 1;
   public static final int ADD_CAR_RUSELT_CODE= 2;
    public static final int EDIT_CAR_RUSELT_CODE= 3;
    Toolbar toolbar;
    TextView Details;
    TextView Color;
    TextView Dpl;
    TextView Description;
    ImageView imv;
    EXdbacses db;
    int carId =-1;
    private Uri imguri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiw_cardactivity);
        Details=findViewById(R.id.et_ditaels_details);
        Color=findViewById(R.id.details_et_color);
        Dpl=findViewById(R.id.distance_et_details);
        Description=findViewById(R.id.details_et_Discription);
        imv=findViewById(R.id.ditaels_iv);
        toolbar=findViewById(R.id.ditaels_toolbar);
        setSupportActionBar(toolbar);
        db=EXdbacses.getInstance(this);

        Intent intent=getIntent();
       carId = intent.getIntExtra(MainActivity.CAR_KEY,-1);

       if(carId ==-1){
           //عملية اضافةة
           Enabledfielsd();
           clearfielsd();
       }else {
           //عملية عرض
           disabledfielsd();
           db.open();
          Car c = db.getCar(carId);
           db.close();
           if (c !=null)
               filcartofiled(c);

       }
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,RECST_IMAG_CAR);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.detailsemenu,menu);
        MenuItem save = menu.findItem(R.id.save_menu_ditels);
        MenuItem Edit = menu.findItem(R.id.Edit_menu_ditels);
        MenuItem delet = menu.findItem(R.id.delet_menu_ditels);

       if(carId ==-1){
           save.setVisible(true);
           Edit.setVisible(false);
           delet.setVisible(false);
       }else {

           save.setVisible(false);
           Edit.setVisible(true);
           delet.setVisible(true);

       }






        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String details,color,description;
        String image="";
        double dbl;
        db.open();
        switch (item.getItemId()){
            case R.id.save_menu_ditels :
                details=Details.getText().toString();
                color=Color.getText().toString();
                dbl= Double.parseDouble(Dpl.getText().toString());
                description=Description.getText().toString();
                if (imguri!=null)
                   image=imguri.toString();
                boolean result;
                boolean resultt;
                Car car=new Car(carId , details , color , dbl , image , description);
                if (carId == -1){
                    result =db.insertCar(car);
                   if(result){
                       Toast.makeText(this,"Car added successfully",Toast.LENGTH_LONG).show();
                       setResult(ADD_CAR_RUSELT_CODE,null);
                       finish();
                   }
                } else {
                    resultt = db.updateCar(car);
                    if (resultt) {
                        Toast.makeText(this, "Car Edit successfully", Toast.LENGTH_LONG).show();
                        setResult(EDIT_CAR_RUSELT_CODE, null);
                        finish();
                    }
                }
                return true;

            case R.id.delet_menu_ditels:

                car=new Car(carId,null,null,0,null,null);
                result=db.DeletCar(car);
                if (result){

                    Toast.makeText(getApplicationContext(), "Car delet successfully", Toast.LENGTH_LONG).show();
                    setResult(EDIT_CAR_RUSELT_CODE,null);
                    finish();
                }

                return true;

            case R.id.Edit_menu_ditels:
                Enabledfielsd();
                MenuItem save = toolbar.getMenu().findItem(R.id.save_menu_ditels);
                MenuItem Edit = toolbar.getMenu().findItem(R.id.Edit_menu_ditels);
                MenuItem delet = toolbar.getMenu().findItem(R.id.delet_menu_ditels);
                save.setVisible(true);
                Edit.setVisible(false);
                delet.setVisible(false);
                return true;

        }

        db.close();
        return false;
    }

    private void filcartofiled(Car c){
        if (c.getImag() !=null && !c.getImag().equals("")) {
            imv.setImageURI(Uri.parse(c.getImag()));
    }
          Details.setText(c.getModel());
          Color.setText(c.getColor());
          Dpl.setText(c.getDpl()+"");
          Description.setText(c.getDescription());
    }

   private void disabledfielsd(){
        imv.setEnabled(false);
        Details.setEnabled(false);
        Color.setEnabled(false);
        Dpl.setEnabled(false);
        Description.setEnabled(false);
    }

   private void Enabledfielsd(){
        imv.setEnabled(true);
        Details.setEnabled(true);
        Color.setEnabled(true);
        Dpl.setEnabled(true);
        Description.setEnabled(true);
    }

    private void clearfielsd(){
        imv.setImageURI(null);
        Details.setText("");
        Color.setText("");
        Dpl.setText("");
        Description.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RECST_IMAG_CAR && resultCode==RESULT_OK){
            if(data!=null){
                imguri=data.getData();
               imv.setImageURI(imguri);
            }

        }
    }
}