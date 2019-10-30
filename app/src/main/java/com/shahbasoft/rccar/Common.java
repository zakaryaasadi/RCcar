package com.shahbasoft.rccar;

import android.bluetooth.BluetoothDevice;

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;

public class Common {

    public final static BluetoothService service = BluetoothService.getDefaultInstance();;
    public static BluetoothDevice foundDevice;
}
