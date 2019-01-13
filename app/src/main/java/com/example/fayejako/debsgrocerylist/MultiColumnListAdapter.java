package com.example.fayejako.debsgrocerylist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MultiColumnListAdapter extends ArrayAdapter<Items> {
    private LayoutInflater mInflater;
    private ArrayList<Items> items;
    private int mViewResourceId;

    public MultiColumnListAdapter(Context context, int textViewResourceId, ArrayList<Items> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        Items item = items.get(position);

        if (item != null) {
            TextView itemName = (TextView) convertView.findViewById(R.id.textView1);
            TextView itemStatus = (CheckBox) convertView.findViewById(R.id.checkBox);
            if (itemName != null) {
                itemName.setText(item.getItemName());
            }
            if (itemStatus != null) {
                ((CheckBox) itemStatus).setChecked(item.getItemStatus());
            }
        }
        return convertView;
    }
}
