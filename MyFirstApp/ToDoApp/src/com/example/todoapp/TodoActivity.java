package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Activity one for listing items
 *
 */
public class TodoActivity extends Activity {

	// request code can be anything for now
	private final int REQUEST_CODE = 20;
	
	private ArrayList<String> todoItems;
	// array adapter translates array to string
	private ArrayAdapter<String> todoAdapter;
	
	private ListView lvItems;
	private EditText etNewItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		
		lvItems = (ListView) findViewById(R.id.lvItems);
		
		//populateArrayItems();
		readItems();  // Populate array list from file
		
		// Adapter translates data from list items into todoItems
		todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
		
		// populate array using this adapter
		lvItems.setAdapter(todoAdapter);
		
		setupListViewListener();
		setupClickListener();
		
		// adapter is proxy for array data
		// todoAdapter.add("Item 4");
	}

	/**
	 * On long clicks, the item shall be deleted
	 */
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item,
					int position, long id) {
				
				// if change underlying data, then must notify adapter
				todoItems.remove(position);
				todoAdapter.notifyDataSetChanged();
				writeItems();  // update file upon deletion
				return false;
			}
		});
	}

	/**
	 * Click instead of long click any item, the user is taken to
	 * the Edit form for that item.
	 * 
	 */
	private void setupClickListener() {
		lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View item,
					int position, long id) {
				
				// first parameter is the context, second is the class of new activity
				Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
				
				// pass data using the extras, position and text
				i.putExtra("position", position);
				i.putExtra("item", todoItems.get(position));
				
				// start the new activity
				startActivityForResult(i, REQUEST_CODE);
			}
		});
	}
	
	/**
	 * Method to handle return from activity
	 * 
	 * @param requestCode specific request num
	 * @param resultCode status value
	 * @param data return value
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// REQUEST_CODE is defined above
		if ( resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			// Extract name value from result extras
			int position = data.getIntExtra("position", 0);
			
			String item = data.getStringExtra("item");
			
			// update the todoList
			todoItems.set(position, item);
			todoAdapter.notifyDataSetChanged();
			
			// persist data
			writeItems();
		}
	}
	
	
	/**
	 * 
	 * @param v view activating this button
	 */
	public void onAddedItem(View v) {
		String itemText = etNewItem.getText().toString();
		todoAdapter.add(itemText);
		
		// clear out text field after added
		etNewItem.setText("");
		
		writeItems();  // update file upon addition
	}

	/**
	 * Method to populate the todoItems field
	 */
	private void populateArrayItems() {
		todoItems = new ArrayList<String>();
		todoItems.add("Item 1");
		todoItems.add("Item 2");
		todoItems.add("Item 3");
	}
	
	/**
	 * Setting array direct list directly from the file
	 */
	public void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			todoItems = new ArrayList<String>();
		}
	}
	
	/**
	 * Write items from array list to file and serialized.
	 */
	public void writeItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, todoItems);
		} catch(IOException e ) {
			e.printStackTrace();
		}
	}
	
}
