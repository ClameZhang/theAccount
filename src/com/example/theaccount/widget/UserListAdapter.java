package com.example.theaccount.widget;

import java.util.List;

import com.example.theaccount.R;
import com.example.theaccount.db.table.UserInfo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserListAdapter extends BaseAdapter {
	private Activity context;
	private List<UserInfo> userList;

	public UserListAdapter(Activity context, List<UserInfo> userList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.userList = userList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = context.getLayoutInflater();
		View itemView = inflater.inflate(R.layout.cv_userlist, null);
		UserInfo user = userList.get(position);
		ImageView imageView = (ImageView) itemView .findViewById(R.id.cv_userlist_iv_icon);
		TextView textView = (TextView) itemView.findViewById(R.id.cv_userlist_tv_name);
		textView.setText(user.getName());
		imageView.setImageBitmap(user.getUserBitmap());
		return itemView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return userList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return userList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}
