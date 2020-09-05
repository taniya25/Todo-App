 package com.example.taniya.animations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

 public class MainActivity extends AppCompatActivity {
     ArrayList<String> items;
     Button button;
     EditText item;
     RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        item = findViewById(R.id.addItem);
        list = findViewById(R.id.listView);

        items = new ArrayList<>();
        items.add("Buy Milk");
        items.add("Go to the Gym");
        items.add("Have fun");

        ItemsAdapter itemsAdapter = new ItemsAdapter(items);
        list.setAdapter(itemsAdapter);
        list.setLayoutManager(new LinearLayoutManager(this));
    }
    
}
