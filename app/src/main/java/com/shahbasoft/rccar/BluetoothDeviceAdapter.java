package com.shahbasoft.rccar;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BluetoothDeviceAdapter extends RecyclerView.Adapter<BluetoothDeviceAdapter.MyViewHolder>{

    Context context;


    private List<BluetoothDevice> OfferList;


    public class MyViewHolder extends RecyclerView.ViewHolder {



        TextView  deviceName,deviceMACAdress;

        public MyViewHolder(View view) {
            super(view);

            deviceName=(TextView)view.findViewById(R.id.deviceName);
            deviceMACAdress=(TextView)view.findViewById(R.id.deviceMACAdress);


        }

    }


    public BluetoothDeviceAdapter(Context context, List<BluetoothDevice> offerList) {
        this.OfferList = offerList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_device, parent, false);


        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final BluetoothDevice device = OfferList.get(position);
        holder.deviceName.setText(device.getName());
        holder.deviceMACAdress.setText(device.getAddress());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.service.connect(device);
                Common.foundDevice = device;
                Intent i = new Intent(context, ControlMethodsActivity.class);
                context.startActivity(i);
            }
        });

    }



    @Override
    public int getItemCount() {
        return OfferList.size();

    }

}


