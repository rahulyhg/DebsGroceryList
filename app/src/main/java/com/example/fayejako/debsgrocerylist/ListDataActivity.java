package com.example.fayejako.debsgrocerylist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog.Builder;

import java.util.ArrayList;

/**
 * Created by User on 2/28/2017.
 */

public class ListDataActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    DatabaseHelper mDatabaseHelper;

    private ListView mListView;
    private Button delAll;
    ArrayList<Items> itemClass; //FAYE
    Items items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        //Add back button to the menu
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        delAll = (Button) findViewById(R.id.btnDeleteAll);

        populateListView();


        delAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ListDataActivity.this);
                alert.setTitle("Delete entry");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        mDatabaseHelper.deleteAll();
                        //Update ListView on phone
                        ArrayList<String> listData = new ArrayList<>();
                        ListAdapter adapter = new ArrayAdapter<String>(ListDataActivity.this, android.R.layout.simple_list_item_1, listData);
                        mListView.setAdapter(adapter);
                        listData.clear();
                        ((ArrayAdapter) adapter).notifyDataSetChanged();
                        toastMessage("Cleared Grocery List!");
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        toastMessage("Cancel");
                        dialog.cancel();
                    }
                });
                alert.show();
            }

        });

    }

    //Goes back a page
    @Override
    public boolean onSupportNavigateUp(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    private void populateListView() {
        //get the data and append to a list
        itemClass = new ArrayList<>();
        Cursor data = mDatabaseHelper.getData();
        int numRows = data.getCount();
        Log.d("faye","here");
        if(numRows ==0){
            toastMessage("Nothing to show on your Shopping List!");
        }
        else{
            ArrayList<String> listData = new ArrayList<>();
            while(data.moveToNext()){
                //get the value from the database in column 1
                //listData.add(data.getString(1));
//                cursor.getColumnIndex("<columnName>")
                items = new Items(data.getString(1), data.getInt(2));
                itemClass.add(items);
            }
            MultiColumnListAdapter adapter = new MultiColumnListAdapter(this,R.layout.list_row,itemClass);
            mListView.setAdapter(adapter);


            //STANDARD LIST -----create the list adapter and set the adapter
//            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
//            mListView.setAdapter(adapter);
        }



        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String name = adapterView.getItemAtPosition(i).toString();


                TextView textViewItem = ((TextView) view.findViewById(R.id.textView1));
                // get the clicked item name
                int s =view.getId();
                String listItemText = textViewItem.getText().toString();
                //toastMessage("onItemClick: You Clicked on " + listItemText);
                Cursor data = mDatabaseHelper.getItemID(listItemText); //get the id associated with that name
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(ListDataActivity.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",listItemText);
                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}