package com.inclutec.louis.lib;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.SerialInputOutputManager;
import com.inclutec.louis.interfaces.ArduinoDeviceConnector;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by martin on 8/20/15.
 */
public class LouisDeviceConnector implements ArduinoDeviceConnector {

    private Context context;
    private LogListener mLogListener;

    private UsbSerialDriver mUseSerialDriver;
    private UsbManager mUsbManager;

    private SerialInputOutputManager mSerialIoManager;

    private SerialInputOutputManager.Listener mListener;

    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public final static String TAG = LouisDeviceConnector.class.getSimpleName();

    public LouisDeviceConnector(){

    }

    public void setArduinoListener(SerialInputOutputManager.Listener aListener){
        mListener = aListener;
    }

    public void setLogListener(LogListener aListener){
        mLogListener = aListener;
    }

    @Override
    public void initialize(){
        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
    }

    @Override
    public int connect(){
        int errorCode = 0;

        mUseSerialDriver = UsbSerialProber.acquire(mUsbManager);
        if (mUseSerialDriver == null) {
            //there is no device connected!
            Log.d(TAG, "No USB serial device connected.");
            writeToAllLogs("No USB serial device connected.");
            errorCode = -1;
        } else {
            try {
                //open the device
                mUseSerialDriver.open();

                //set the communication speed
                mUseSerialDriver.setBaudRate(115200); //make sure this matches your device's setting!

                writeToAllLogs("Device connected");

            } catch (IOException err) {
                Log.e(TAG, "Error setting up USB device: " + err.getMessage(), err);
                writeToAllLogs("Error setting up USB device: " + err.getMessage());

                try {
                    //something failed, so try closing the device
                    mUseSerialDriver.close();
                    errorCode = -2;
                } catch (IOException ex2) {
                    //couldn't close, but there's nothing more to do!
                    errorCode = -3;
                    Log.e(TAG, "Unable to close the device", ex2);
                }
                mUseSerialDriver = null;
            }
        }

        return errorCode;

    }

    @Override
    public int disconnect(){

        int errorCode = 0;

        if (mUseSerialDriver != null) {
            try {
                mUseSerialDriver.close();
                writeToAllLogs("Disconnected");

            } catch (IOException e) {
                //we couldn't close the device, but there's nothing we can do about it!
                Log.e(TAG, "Unable to close the device", e);
                writeToAllLogs("Unable to close the device");
                errorCode = -1;
            }

            //remove the reference to the device
            mUseSerialDriver = null;
        }
        else{
            errorCode = -2;
            writeToAllLogs("Serial driver is not null!");

        }

        return errorCode;
    }

    @Override
    public void setContext(Context aContext) {
        context = aContext;
    }

    @Override
    public int write(String data){

        int errorCode = 0;

        byte[] dataToSend = data.getBytes();
        //send the color to the serial device

        if (mUseSerialDriver != null){
            try{
                mUseSerialDriver.write(dataToSend, dataToSend.length);
                writeToAllLogs(String.format("Sent [%s] to device", data));
            }
            catch (IOException e){
                writeToAllLogs("couldn't write bytes to serial device");

                errorCode = -1;
            }
        } else {
            writeToAllLogs("device is null");
            errorCode = -2;
        }

        return errorCode;
    }

    @Override
    public int stopIoManager() {
        int errorCode = 0;

        writeToAllLogs("Stopping io manager ..");
        if (mSerialIoManager != null) {
            mSerialIoManager.stop();
            writeToAllLogs("Stopped io manager");
            mSerialIoManager = null;
        }
        else{
            writeToAllLogs("Serial driver is null");
            errorCode = -1;
        }

        return errorCode;
    }

    @Override
    public int startIoManager() {
        int errorCode = 0;
        writeToAllLogs("Starting IO Manager...");
        if (mUseSerialDriver != null) {

            mSerialIoManager = new SerialInputOutputManager(mUseSerialDriver, mListener);
            mExecutor.submit(mSerialIoManager);
            writeToAllLogs("IO Manager start sent");
        }else{
            errorCode = -1;
            writeToAllLogs("Serial driver is null");
        }

        return errorCode;
    }


    public interface LogListener {
        void writeLog(String data);
    }

    private void writeToAllLogs(String data){
        //Toast aToast = Toast.makeText(context, data, Toast.LENGTH_LONG);
        //aToast.show();

        Log.i(TAG, data);
        if (mLogListener != null){
            mLogListener.writeLog(data);
        }
    }
}