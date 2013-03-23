package com.example.theaccount;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.theaccount.db.table.UserInfo;
import com.example.theaccount.utility.DBUtility;
import com.example.theaccount.widget.UserListAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HomePageActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);

		File dbf = DBUtility.getDBFile();
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbf, null);

		Cursor cur = db.query("person", new String[] { "_id", "name",
				"nickname" }, null, null, null, null, null);
		cur.moveToFirst();
		final List<UserInfo> userList = new ArrayList<UserInfo>();

		while (!cur.isAfterLast()) {
			UserInfo user = new UserInfo();
			user.setId(cur.getInt(0));
			user.setName(cur.getString(1));
			Bitmap userBitmap = BitmapFactory.decodeStream(getResources()
					.openRawResource(R.drawable.icon));
			user.setUserBitmap(userBitmap);
			userList.add(user);
			cur.moveToNext();
		}
		db.close();

		final ListView list = (ListView) findViewById(R.id.listView1);
		UserListAdapter adapter = new UserListAdapter(this, userList);
		list.setAdapter(adapter);
		list.setItemsCanFocus(false);

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				UserInfo user = userList.get(position);
				Intent intent = new Intent();
				intent.setClass(HomePageActivity.this,
						PersonalDetailActivity.class);
				intent.putExtra("user_id", user.getId());
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_homepage, menu);
        return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.menu_btn_create:
			createNewAccount();
			break;
		case R.id.menu_btn_exit:
			showIsExit();
			break;
		}

		return true;
	}

	public void createNewAccount() {
		Intent intent = new Intent();
		intent.setClass(HomePageActivity.this, PersonalEditActivity.class);
		intent.putExtra("user_id", -1);
		startActivity(intent);
	}

	public void showIsExit() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.homepage_dialog_title)
				.setMessage(R.string.homepage_dialog_message)
				.setNegativeButton(R.string.homepage_dialog_cancle,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
							}
						})
				.setPositiveButton(R.string.homepage_dialog_exit,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								finish();
							}
						}).show();
	}
}
