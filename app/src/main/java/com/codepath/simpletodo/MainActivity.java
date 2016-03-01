package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 20;
    private final int RESULT_CODE = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        items = new ArrayList<>();
        readItems();
        itemAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemAdapter);
       // items.add("First Item");
        //items.add("Second Item");
        setupListViewListener();
        setupListViewClickListener();
    }

    public void onAddItem(View view) {
        EditText etNewItem =(EditText)findViewById(R.id.etNewItem);
        String itemText= etNewItem.getText().toString();
        itemAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    private  void setupListViewListener()
    {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });


    }

    private void setupListViewClickListener()
    {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> adapter, View item, int pos, long id) {

                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("original text", items.get(pos).toString());
                i.putExtra("position", pos);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }


    private void readItems()
    {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try
        {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e)
        {
            items= new ArrayList<String>();
        }
    }

    private void writeItems()
    {
        File fileDir = getFilesDir();
        File todoFile = new File (fileDir,"todo.txt");
        try
        {
            FileUtils.writeLines (todoFile,items);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      //Save button crashes if resultCode==RESULT_CODE
        if (resultCode == RESULT_CODE  && requestCode == REQUEST_CODE) {
           int position =  data.getIntExtra("original pos",0);
            String changedText = data.getStringExtra("text changed");
            items.set(position, changedText);
            itemAdapter.notifyDataSetChanged();
            writeItems();
        }
    }
}





