 package com.example.taniya.animations;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

 public class MainActivity extends AppCompatActivity {

     public static final String KEY_ITEM_TEXT = "item_text";
     public static final String KEY_ITEM_POSITION = "item_position";
     public static final int EDIT_TEXT_CODE = 20;

     ArrayList<String> items;
     Button button;
     EditText item;
     RecyclerView list;
     ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        item = findViewById(R.id.addItem);
        list = findViewById(R.id.listView);

        loadItems();
        ItemsAdapter.onLongClickListener onLongClickListener = new ItemsAdapter.onLongClickListener(){

            @Override
            public void onItemLongClicked(int position) {
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_SHORT).show();
            }
        };

        ItemsAdapter.onClickListener onClickListener = new ItemsAdapter.onClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d("MainActivity", "Single click at position"+position);
                Intent i =  new Intent(MainActivity.this, EditActivity.class);
                i.putExtra(KEY_ITEM_TEXT,items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };

        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);
        list.setAdapter(itemsAdapter);
        list.setLayoutManager(new LinearLayoutManager(this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = item.getText().toString();
                items.add(todoItem);
                itemsAdapter.notifyItemInserted(items.size()-1);
                item.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity","Error reading items",e);
            items = new ArrayList<>();
        }
    }

    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity","Error Writing items",e);

        }
    }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (resultCode == RESULT_OK && requestCode == resultCode) {
             String itemText = data.getStringExtra(KEY_ITEM_TEXT);
             int position = data.getExtras().getInt(KEY_ITEM_POSITION);

             items.set(position, itemText);
             itemsAdapter.notifyItemChanged(position);
             saveItems();
             Toast.makeText(getApplicationContext(), "Items updated", Toast.LENGTH_SHORT).show();

         } else {
             Log.w("MainActivity", "Unknown call to onActivityResult");
         }
     }
 }
