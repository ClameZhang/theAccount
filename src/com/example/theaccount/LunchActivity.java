package com.example.theaccount;

import com.example.theaccount.utility.DBUtility;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class LunchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		checkAndCreateDBFile();
		
		Thread waitThread = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
				}

				runOnUiThread(new Runnable() {
					// @Override
					public void run() {
						Intent mainGroupIntent = new Intent();
						mainGroupIntent.setClass(LunchActivity.this, MyMainActivityGroup.class);
						startActivity(mainGroupIntent);		
						LunchActivity.this.finish();
					}
				});
			}
		};
		waitThread.start();
	}
	
	// Check and create database
	private void checkAndCreateDBFile() {
		DBUtility.checkDBFile();
	}
}
