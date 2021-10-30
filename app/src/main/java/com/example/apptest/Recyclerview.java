package com.example.apptest;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Recyclerview extends RecyclerView.Adapter<Recyclerview.Carviewnholder> {

    private ArrayList<Car>cars;
    private OnRecyclerViewitemClickListener Listener;

     public  Recyclerview (ArrayList<Car> Cars,OnRecyclerViewitemClickListener Listener) {
         this.cars=Cars;
         this.Listener=Listener;

     }

    public ArrayList<Car> getCars() {
         return cars;
    }

    public void setCars(ArrayList<Car> cars) {
         this.cars = cars;
    }

    @NonNull
    @Override
    public Carviewnholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_car_layout,null,false);

    return new Carviewnholder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull Carviewnholder holder, int position) {
         Car c=cars.get(position);
         if(c.getImag()!=null && !c.getImag().isEmpty()){
             Uri x =Uri.parse(c.getImag());
             holder.image.setImageURI(x);
         }
         else {
             holder.image.setImageResource(R.drawable.car_custom);
         }
         holder.model.setText(c.getModel());
         holder.Color.setText(c.getColor());
         holder.dpl.setText(String.valueOf(c.getDpl()));
         holder.id=c.getId();
    }

    @Override
    public int getItemCount() {

         return cars.size();

    }

    class Carviewnholder extends RecyclerView.ViewHolder{

        TextView model,Color,dpl;
        ImageView image;
        int id;



        public Carviewnholder(@NonNull View itemView) {
            super(itemView);
            image =itemView.findViewById(R.id.imageView2);
            model=itemView.findViewById(R.id.custome_car_tv_model);
            Color=itemView.findViewById(R.id.custome_car_tv_color);
            dpl=itemView.findViewById(R.id.custome_car_tv_dbl);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Listener.onItemClick(id);

                }
            });


        }

    }
}
