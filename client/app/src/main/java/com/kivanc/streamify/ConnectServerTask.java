package com.kivanc.streamify;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.Enumeration;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Kivanc on 5.06.2018.
 */

public class ConnectServerTask extends AsyncTask<Void, Void, Boolean> {

    private static Socket socket;
    private static PrintWriter out;

    private String serverIPStr;
    private int portInt;

    private boolean isConnected = false;

    private ConnectActivity connectActivity;


    public ConnectServerTask(ConnectActivity connectActivity, String IPAndPortInfo) {
        this.connectActivity = connectActivity;
        this.serverIPStr = IPAndPortInfo.split(":")[0];
        this.portInt = Integer.parseInt(IPAndPortInfo.split(":")[1]);

        Log.i("Server IP", serverIPStr);
        Log.i("Port", Integer.toString(portInt));
    }

    //
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        boolean result = false;
        try {
            socket = new Socket(serverIPStr, portInt);

            result = true;
            if(result){
                try{
                    out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()),true); //create output stream to send data to server
                }catch (IOException printWriterError){
                    Log.e("PrintWriterError","PrintWriter Couldn't Create.");
                    result = false;
                }
            }
        } catch (IOException e) {
            Log.e("SocketError","Socket Couldn't Create.");
            result = false;
        }
        return result;
    }


    @Override
    protected void onPostExecute(Boolean result)
    {

        isConnected = result;

        if(isConnected) {
            Toast.makeText(connectActivity.getApplicationContext(), "Connected to the Server!", Toast.LENGTH_SHORT).show();

            Intent myIntent = new Intent(connectActivity, VitamioPlayerActivity.class);

            connectActivity.startActivity(myIntent);
        }

        else {
            Toast.makeText(connectActivity.getApplicationContext(), "Couldn't Connect to the Server! Please Make Sure You Enter the Correct Server IP Address and Port Number Informations", Toast.LENGTH_LONG).show();
        }
    }

    public static Socket getSocket() {
        return socket;
    }

    public static PrintWriter getOut() {
        return out;
    }
}

