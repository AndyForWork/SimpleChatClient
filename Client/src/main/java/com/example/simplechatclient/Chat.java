package com.example.simplechatclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.network.TCPConnection;
import com.example.network.TCPConnectionListener;

import java.io.IOException;
import java.util.ArrayList;

public class Chat extends Activity implements TCPConnectionListener {

    private ListView listView;
    private ListAdapter adapter;

    private TCPConnection connection;
    private String ip;
    private String port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        Bundle extras = getIntent().getExtras();
        ip = extras.getString("ip");
        port = extras.getString("port");
        listView = (ListView) findViewById(R.id.chat);

        adapter = new ListAdapter(this, new ArrayList<String>());
        listView.setAdapter(adapter);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    connection = new TCPConnection(Chat.this,ip,Integer.parseInt(port));
                    connection.sendString("ok");
                    Log.i("Test",connection.toString());
                    Log.i("Test",ip);
                    Log.i("Test",port);
                } catch (IOException e) {
                    Intent i = new Intent(Chat.this, StartActivity.class);
                    i.putExtra("error", true);
                    i.putExtra("port",port);
                    i.putExtra("ip",ip);
                    startActivity(i);
                }
            }
        });
        thread.start();
    }




    @Override
    public void onExceptionListener(TCPConnection tcpConnection, Exception e) {
        Log.e("Connection","TCPException: "+e);
    }

    @Override
    public void onConnectionListener(TCPConnection tcpConnection) {
        Log.i("info","привет мир)))");
    }

    @Override
    public void onDisconnectListener(TCPConnection tcpConnection) {
        Intent i = new Intent(Chat.this, StartActivity.class);
        i.putExtra("error", true);
        i.putExtra("port",port);
        i.putExtra("ip",ip);
        startActivity(i);
    }

    @Override
    public void onReceiveListener(TCPConnection tcpConnection, String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.add(data);
                adapter.notifyDataSetChanged();
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        // Select the last row so it will scroll into view...
                        listView.setSelection(adapter.getCount() - 1);
                    }
                });
            }
        });
    }

    public void sendData(View v){
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                connection.sendString(((TextView)findViewById(R.id.textData)).getText().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView)findViewById(R.id.textData)).setText("");
                    }
                });
            }
        });
        th.start();
    }
}
