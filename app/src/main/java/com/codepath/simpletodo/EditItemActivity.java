package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends AppCompatActivity {
    TextView initialText;
     int  pos = 0;

    private final int RESULT_OK = 21;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String originalText = getIntent().getStringExtra("original text");
        int position = getIntent().getIntExtra("position", 0);
        pos = position;
        initialText = (TextView) findViewById(R.id.etChangeText);
        initialText.setText(originalText);

    }

        public void onSaveItems (View view){
        EditText etNewItem = (EditText) findViewById(R.id.etChangeText);
        String itemText = etNewItem.getText().toString();
        Intent data = new Intent();
        data.putExtra("text changed", itemText);
        data.putExtra("original pos", pos);
        setResult(RESULT_OK, data);
        finish();
    }


}

