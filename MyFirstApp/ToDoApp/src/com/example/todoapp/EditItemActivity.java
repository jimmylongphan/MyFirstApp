package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Activity two for editing items
 *
 */
public class EditItemActivity extends Activity {

	private int position;
	private String item;
	private EditText etEditItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
		// Access data received from first activity
		position = getIntent().getIntExtra("position", 0);
		item = getIntent().getStringExtra("item");
		
		// Set the data in the text field
		etEditItem = (EditText) findViewById(R.id.etEditItem);
		etEditItem.setText(item);
		
		// set cursor to end of text
		etEditItem.setSelection( etEditItem.getText().length() );
		
		// set focus on editText by default
		etEditItem.requestFocus();
	}
	
	/**
	 * Save the edited contents of the item
	 * 
	 * @param v View that activated this button
	 */
	public void onSaveItem(View v) {
		// Prepare data intent
		Intent data = new Intent();
		
		// Pass relevant data back as a result
		data.putExtra("item", etEditItem.getText().toString());
		data.putExtra("position", position);
		
		// Return the data
		setResult(RESULT_OK, data);
		
		// closes the activity and returns to the first screen
		this.finish();
	}
	

}
