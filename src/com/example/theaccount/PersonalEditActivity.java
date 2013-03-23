package com.example.theaccount;

import java.io.File;

import com.example.theaccount.db.table.UserInfo;
import com.example.theaccount.utility.DBUtility;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonalEditActivity extends Activity {
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personaledit);
		
        File dbf = DBUtility.getDBFile();        
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbf, null);
        
		Intent intent = this.getIntent();
		int userId = -1;
		if (intent.getExtras() != null) {
			userId = intent.getExtras().getInt("user_id");
		}
        Cursor cursor = db.rawQuery("select name, nickname from person where _id=" + String.valueOf(userId), null);
        
        UserInfo user = null;
        if (cursor.moveToNext()) {
        	user = new UserInfo();
        	user.setName(cursor.getString(0));
        	user.setNickName(cursor.getString(1));
        	Bitmap userBitmap =  BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.icon));
        	user.setUserBitmap(userBitmap);
        }
        db.close();

    	ImageView imageView = (ImageView)findViewById(R.id.iv_edit_personal);
		EditText et_name = (EditText)findViewById(R.id.et_edit_name);
		EditText et_nickname = (EditText)findViewById(R.id.et_edit_nickname);
		EditText et_tel = (EditText)findViewById(R.id.et_edit_tel);
		TextView tv_id = (TextView)findViewById(R.id.tv_edit_id);
		tv_id.setText(String.valueOf(userId));
        if (user != null) {
    		imageView.setImageBitmap(user.getUserBitmap());
    		et_name.setText(user.getName());
    		et_nickname.setText(user.getNickName());
    		et_tel.setText(user.getTelNum());
        }
    	
    	Button btnSave = (Button)findViewById(R.id.btn_edit_save);
    	btnSave.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
        		Intent intent = new Intent();
        		intent.setClass(PersonalEditActivity.this, PersonalDetailActivity.class);
        		
        		EditText et_name = (EditText)findViewById(R.id.et_edit_name);
        		EditText et_nickname = (EditText)findViewById(R.id.et_edit_nickname);
//        		EditText et_tel = (EditText)findViewById(R.id.et_edit_tel);
        		TextView tv_id = (TextView)findViewById(R.id.tv_edit_id);
        		        		
                File dbf = DBUtility.getDBFile();        
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbf, null);
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", et_name.getText().toString());
                contentValues.put("nickname", et_nickname.getText().toString());
//                contentValues.put("tel", et_tel.getText().toString());
                db.update("person", contentValues, "_id=?", new String[]{tv_id.getText().toString()});
                db.close();
        		
        		intent.putExtra("user_id", Integer.parseInt(tv_id.getText().toString()));
    			startActivity(intent);
    			PersonalEditActivity.this.finish();
    		}
    	});
    	
    	Button btnCancel = (Button)findViewById(R.id.btn_edit_cancel);
    	btnCancel.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
        		Intent intent = new Intent();
        		intent.setClass(PersonalEditActivity.this, PersonalDetailActivity.class);
        		TextView tv_id = (TextView)findViewById(R.id.tv_edit_id);
        		intent.putExtra("user_id", Integer.parseInt(tv_id.getText().toString()));
    			startActivity(intent);
    			PersonalEditActivity.this.finish();
    		}
    	});
	}
}
