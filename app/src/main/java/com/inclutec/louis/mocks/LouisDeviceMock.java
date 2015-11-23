package com.inclutec.louis.mocks;

import android.content.Context;
import android.widget.Toast;

import com.inclutec.louis.interfaces.ArduinoDeviceConnector;

/**
 * Created by martin on 9/12/15.
 */
public class LouisDeviceMock implements ArduinoDeviceConnector {

    private Context context;
    @Override
    public int connect() {
        return 0;
    }

    @Override
    public int disconnect() {
        return 0;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void setContext(Context aContext) {
        this.context = aContext;
    };

    @Override
    public int write(String data) {

        //transform character
        switch(data){
            case "14":
                data = "7";
                break;
            case "25":
                data = "8";
                break;
            case "36":
                data = "9";
                break;
            case "123456":
                data = "A";
                break;
        }

        String message = String.format("Sent %s to device", data);
        Toast aToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        aToast.show();

        return 0;
    }

    @Override
    public int stopIoManager() {
        return 0;
    }

    @Override
    public int startIoManager() {
        return 0;
    }
}
