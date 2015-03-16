package com.example.bluetoothchat;

import java.util.ArrayList;
import java.util.Set;

import android.support.v7.app.ActionBarActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	TextView nameLabel;
	EditText nameEditText;
	Button continueButton;
	ListView deviceListView;
	
	ArrayAdapter<String> listAdapter;
	BluetoothAdapter btAdapter;
	Set<BluetoothDevice> devicesArray;
	ArrayList<String> deviceNameList;
	Handler mHandler;

	private static final int SOCKET_CONNECTED = 1;
	private static final int MESSAGE_READ = 2;
	private boolean nameSet = false, 
					deviceSelected = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		nameLabel = (TextView) findViewById(R.id.nameLabel);
		nameEditText = (EditText) findViewById(R.id.nameEditText);
		continueButton = (Button) findViewById(R.id.continueButton);
		deviceListView = (ListView) findViewById(R.id.deviceListView);
		
		continueButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(nameSet && deviceSelected)
				{
					Intent activityIntent = new Intent(getApplicationContext(), ChatScreen.class);
					startActivity(activityIntent);
				}
				
			}});
		
		/**
		 * Bluetooth code
		 */
		listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, deviceNameList);
		deviceListView.setAdapter(listAdapter);
		
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		
		if(btAdapter == null)
		{
			Toast.makeText(getApplicationContext(), "No Bluetooth detected", Toast.LENGTH_SHORT);
		}
		else
		{
			if(!btAdapter.isEnabled())
			{
				Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(intent, 1);
			}

			devicesArray = btAdapter.getBondedDevices();
			if(devicesArray.size() > 0)
			{
				for(BluetoothDevice device: devicesArray)
				{
					listAdapter.add(device.getName() + "\n" + device.getAddress());
					deviceNameList.add(device.getName());
				}
				listAdapter.notify();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	/**
	 * Helper Functions
	 */

}
