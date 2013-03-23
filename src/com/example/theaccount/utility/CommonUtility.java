package com.example.theaccount.utility;

import java.io.File;

public class CommonUtility {
	public static String getAppPath() {
		String appPath = "";
		if (android.os.Environment.MEDIA_MOUNTED.equals(
				android.os.Environment.getExternalStorageState())) {
			appPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"/203Account";
		} else {
			appPath = android.os.Environment.getDataDirectory().getAbsolutePath()+"/203Account";
		}
		
		File app = new File(appPath);
		if(!app.exists()) {
			app.mkdir();
		}
		
		return appPath;
	}
}
