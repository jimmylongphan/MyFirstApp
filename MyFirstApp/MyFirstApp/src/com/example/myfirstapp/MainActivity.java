package com.example.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public EditText etWords;
	public TextView tvLabel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// R is resource folder
		// layout is type of resource
		setContentView(R.layout.activity_main);
		
		etWords = (EditText) findViewById(R.id.etWords);
		tvLabel = (TextView) findViewById(R.id.tvLabel);
	}
	
	/**
	 * Triggered whenever we press the button.
	 * Needs to be public and matching name. Otherwise there will
	 * be an exception
	 * 
	 * @param v Is the view that activated this method
	 */
	public void onSubmit(View v) {
		String fieldValue = etWords.getText().toString();
		
		// fire when the button is pressed
		
		// We created the toast, but still need to show it afterwards
		// Else we will get a warning.
		Toast.makeText(this, fieldValue, Toast.LENGTH_SHORT).show();
		
		// change the text label
		tvLabel.setText(fieldValue);
	}
}
