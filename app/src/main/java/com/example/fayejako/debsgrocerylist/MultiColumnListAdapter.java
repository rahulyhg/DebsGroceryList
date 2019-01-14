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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MultiColumnListAdapter extends ArrayAdapter<Items> {
    private LayoutInflater mInflater;
    private ArrayList<Items> items;
    private int mViewResourceId;
    DatabaseHelper mDatabaseHelper;

    static class ViewHolder {
        public TextView text;
        public int id;
    }



    public MultiColumnListAdapter(Context context, int textViewResourceId, ArrayList<Items> items) {
        super(context, textViewResourceId, items);
        this.mDatabaseHelper = new DatabaseHelper(context.getApplicationContext());
        this.items = items;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);
        final View rowView = convertView;
        Items item = items.get(position);
        final ViewHolder holder;


        // configure view holder
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.text = (TextView) convertView.findViewById(R.id.textView1);
        viewHolder.id = items.get(position).getItemId();
        convertView.setTag(viewHolder);

        //Populate list
        TextView itemName = (TextView) convertView.findViewById(R.id.textView1);
        final CheckBox itemStatus = (CheckBox) convertView.findViewById(R.id.checkBox);

        if (item != null) {
            if (itemName != null) {
                itemName.setText(item.getItemName());
            }
            if (itemStatus != null) {
                ((CheckBox) itemStatus).setChecked(item.getItemStatus());
                itemStatus.setTag(itemName);
            }
        }

        itemStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            ViewHolder holder = (ViewHolder) rowView.getTag();
            int id = holder.id;

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mDatabaseHelper.updateStatus(1,id);
                    Log.d("Checked","status updated");
                } else {
                    mDatabaseHelper.updateStatus(0,id);
                    Log.d("Unchecked","status updated");
                }
            }
        });

        return convertView;
    }
}
