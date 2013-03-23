package com.example.theaccount;

import java.io.File;

import com.example.theaccount.db.table.UserInfo;
import com.example.theaccount.utility.DBUtility;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonalDetailActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personaldetail);

    	ImageView imageView = (ImageView)findViewById(R.id.iv_personalicon);
		TextView tv_name = (TextView)findViewById(R.id.pd_tv_name);
		TextView tv_nickname = (TextView)findViewById(R.id.pd_tv_nickname);
		TextView tv_id = (TextView)findViewById(R.id.pd_tv_id);
		Button btnEdit = (Button)findViewById(R.id.pd_btn_edit);
		
        File dbf = DBUtility.getDBFile();        
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbf, null);
        
		Intent intent = this.getIntent();
		if (intent.getExtras() != null) {
			int userId = intent.getExtras().getInt("user_id");	
	        Cursor cursor = db.rawQuery("select name, nickname from person where _id=" + String.valueOf(userId), null);
	        UserInfo user = null;
	        if (cursor.moveToNext()) {
	        	user = new UserInfo();
	        	user.setId(userId);
	        	user.setName(cursor.getString(0));
	        	user.setNickName(cursor.getString(1));
	        	Bitmap userBitmap =  BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.icon));
	        	user.setUserBitmap(userBitmap);
	        }
	        db.close();

	        if (user != null) {
	    		imageView.setImageBitmap(user.getUserBitmap());
	    		tv_name.setText(user.getName());
	    		tv_nickname.setText(user.getNickName());
	            tv_id.setText(String.valueOf(user.getId()));
	        }

	        btnEdit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
	        		Intent intent = new Intent();
	        		intent.setClass(PersonalDetailActivity.this, PersonalEditActivity.class);
	        		TextView tv_id = (TextView)findViewById(R.id.pd_tv_id);
	        		intent.putExtra("user_id", Integer.parseInt(tv_id.getText().toString()));
					startActivity(intent);
				}
			});
			
		}
	}

}
