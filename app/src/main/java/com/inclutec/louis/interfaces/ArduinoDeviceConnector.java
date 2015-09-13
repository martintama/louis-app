package com.inclutec.louis.interfaces;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.SerialInputOutputManager;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by martin on 9/12/15.
 */
public interface ArduinoDeviceConnector {

    public int connect();

    public int disconnect();

    public void initialize();

    public void setContext(Context aContext);

    public int write(String data);

    public int stopIoManager();

    public int startIoManager();
}
