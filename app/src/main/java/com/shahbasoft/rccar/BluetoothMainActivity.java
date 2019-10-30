package com.shahbasoft.rccar;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;

import java.util.ArrayList;
import java.util.List;

public class BluetoothMainActivity extends AppCompatActivity {


    private BluetoothAdapter btAdapter;
    private List<BluetoothDevice> list;
    private RecyclerView recyclerView;
    private BluetoothDeviceAdapter bAdapter;
    private ImageView reload;
    private final static int ALLOW = -1;
    private final static int DENY = 0;
    private SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_main);
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        list = new ArrayList<>();

        reload = findViewById(R.id.reload);


        swipe = findViewById(R.id.swipe);
        swipe.setColorSchemeResources(R.color.orange, R.color.twittercolor, R.color.redBg);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Common.service.startScan();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        bAdapter = new BluetoothDeviceAdapter(this, list);
        recyclerView.setAdapter(bAdapter);


        Common.service.setOnScanCallback(new BluetoothService.OnBluetoothScanCallback() {
            @Override
            public void onDeviceDiscovered(BluetoothDevice device, int rssi) {
                if (!isExist(device)) {
                    list.add(device);
                    bAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onStartScan() {
                swipe.setRefreshing(true);
                reload.setVisibility(View.GONE);
                bluetoothOn();
            }

            @Override
            public void onStopScan() {
                swipe.setRefreshing(false);
                if (list.isEmpty())
                    reload.setVisibility(View.VISIBLE);
            }
        });


        Common.service.startScan();
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.service.startScan();

            }
        });
    }

    @Override
    public void finish() {
        Common.service.stopScan();
        Common.service.disconnect();
        Common.foundDevice = null;
        list.clear();
        bAdapter.notifyDataSetChanged();
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == DENY) {
                finish();
                System.exit(0);
            }else if(resultCode == ALLOW){
                Common.service.startScan();
            }
        }
    }

    public void bluetoothOn() {
        if (btAdapter.isEnabled())
            return;

        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, 1);
        while (!btAdapter.isEnabled()) {
            if (btAdapter.disable())
                break;
        }
    }

    private boolean isExist(BluetoothDevice d) {
        for (BluetoothDevice item : list) {
            if (d.getAddress().equals(item.getAddress()))
                return true;
        }
        return false;
    }

}
