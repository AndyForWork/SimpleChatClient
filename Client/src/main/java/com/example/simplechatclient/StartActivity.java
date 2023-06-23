package com.example.simplechatclient;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity_layout);

        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            if (extras.containsKey("ip"))
                ((TextView) findViewById(R.id.ipAdr)).setText(extras.getString("ip"));
            if (extras.containsKey("port"))
                ((TextView) findViewById(R.id.portEdit)).setText(extras.getString("port"));
            if (extras.containsKey("error")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Ошибка")
                        .setMessage("Ошибка при подключении !!! Проверьте правильно ли вы ввели порь и ip адрес!")
                        .setPositiveButton("Закрыть", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        }

    }

    public void startChat(View v){
        String ip = ((EditText)findViewById(R.id.ipEdit)).getText().toString();
        String port = ((EditText)findViewById(R.id.portEdit)).getText().toString();
        Intent i = new Intent(StartActivity.this,Chat.class);
        i.putExtra("ip",ip);
        i.putExtra("port",port);
        startActivity(i);
    }


}
