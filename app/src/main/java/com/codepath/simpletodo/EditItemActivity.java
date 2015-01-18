package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {

    int itemPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String itemText = getIntent().getStringExtra("item_text");
        itemPos = getIntent().getIntExtra("item_pos", 0);
        setFormText(itemText);
    }

    private void setFormText(String itemText) {
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(itemText);
    }

    public void onSaveItem(View v) {
        EditText editText = (EditText) findViewById(R.id.editText);
        Intent data = new Intent();
        data.putExtra("saved_item_text", editText.getText().toString());
        data.putExtra("item_pos", itemPos);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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