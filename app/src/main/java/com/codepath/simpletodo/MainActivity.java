package com.codepath.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    TodoCursorAdapter todoAdapter;
    ListView lvItems;

    private final int REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        Cursor todoCursor = Item.fetchResultCursor();
        todoAdapter = new TodoCursorAdapter(this, todoCursor);
        lvItems.setAdapter(todoAdapter);
        setupListViewListener();
    }

    // for debugging
    private void toastId (long id) {
        Context context = getApplicationContext();
        CharSequence text = "id" + id;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter,
                                           View item, int pos, long id){
                Item it = Item.load(Item.class, id);
                it.delete();
                todoAdapter.changeCursor(Item.fetchResultCursor());
                return true;
            }
        });
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                            View item, int pos, long id) {
                        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                        Item it = Item.load(Item.class, id);
                        i.putExtra("item_text", it.name);
                        i.putExtra("item_id", id);
                        startActivityForResult(i, REQUEST_CODE);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String savedText = data.getExtras().getString("saved_item_text");
            long itemId = (long) data.getExtras().get("item_id");
            Item it = Item.load(Item.class, itemId);
            it.name = savedText;
            it.save();
            todoAdapter.changeCursor(Item.fetchResultCursor());
        }
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        Item item = new Item();
        item.name = itemText;
        item.save();
        todoAdapter.changeCursor(Item.fetchResultCursor());
        etNewItem.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
