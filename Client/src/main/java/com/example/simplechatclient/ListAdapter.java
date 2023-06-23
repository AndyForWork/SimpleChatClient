package com.example.simplechatclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> messageArrayList;

    @Override
    public int getCount(){
        return messageArrayList.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.list_item, null);
        TextView msg = (TextView) convertView.findViewById(R.id.msgText);
        msg.setText(messageArrayList.get(position));

        return convertView;
    }

    public ListAdapter(Context context, ArrayList<String> messageArrayList) {
        super(context, 0, messageArrayList);
        this.context = context;
        this.messageArrayList = messageArrayList;
    }

    public ArrayList<String> getMessageArrayList() {
        return messageArrayList;
    }
}
