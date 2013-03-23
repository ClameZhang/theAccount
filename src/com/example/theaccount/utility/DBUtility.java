package com.example.theaccount.utility;

import java.io.File;
import java.io.IOException;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBUtility {
	private SQLiteDatabase db;
	private File dbFile;
	private static final String className = "DBUtility";
	
	public DBUtility() {
		// TODO Auto-generated constructor stub
	}
	
	public static void checkDBFile() {
		File dbf = getDBFile();        
		if(!dbf.exists()){
			try{                 
        		dbf.createNewFile();
        		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbf, null);
//        		db.execSQL("create table");
        	}
        	catch(IOException ioex){}
		}
	}
	
	public static File getDBFile() {
		String appPath = CommonUtility.getAppPath();
		String db_path = appPath+"/"+"203Account.db";
		File dbf = new File(db_path);
		
		return dbf;
	}
	
	public void setDBFile(File dbFile) {
		this.dbFile = dbFile;
	}
	
	public void openDB() {
		this.db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
	}

	public void claoseDB() {
		this.db.close();
	}
	
	public void execSQL(String sql) {
		try {
			this.db.execSQL(sql);
		} catch (SQLException e) {
			Log.e(className, e.getMessage());
		}
	}
	
	public void insertRecord(String tableName, String values) {
		String insertSQL = "insert into " + tableName + " values " + values;
		try {
			this.db.execSQL(insertSQL);
		} catch (SQLException e) {
			Log.e(className, e.getMessage());
		}
	}
	
	public void selectRecord(String tableName, String values, String condition) {
		String insertSQL = "insert into " + tableName + " values " + values;
		try {
			this.db.execSQL(insertSQL);
		} catch (SQLException e) {
			Log.e(className, e.getMessage());
		}
	}
}
