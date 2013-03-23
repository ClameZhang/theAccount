package com.example.theaccount;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class MyMainActivityGroup extends ActivityGroup {
	Resources res;
	LocalActivityManager mActivityManager;

	ViewGroup mainRootLayout;
	ViewGroup tabFrame;
	ViewGroup tabWidgetRadioGroup;
	ViewGroup tabImages;

	static final int TAB_LIST = 0;
	static final int TAB_EXPENSE = 1;
	static final int TAB_USER = 2;

	int mBlueColorTabPos = 1;
	int mNewCheckedPos = 1;
	
	int[] hoveredTabRes = {R.drawable.ic_list_selected, R.drawable.ic_detailexpense_selected, R.drawable.ic_members_selected};
	int[] srcTabRes = {R.drawable.ic_list, R.drawable.ic_detailexpense, R.drawable.ic_members};

	protected static class ActivityHistory {
		String className;
		Intent historyIntent;
		boolean hasDestroyed = true;

		ActivityHistory(String _className, Intent _intent) {
			className = _className;
			historyIntent = _intent;
		}

		ActivityHistory(String _className, Intent _intent, boolean _hasDestroyed) {
			className = _className;
			historyIntent = _intent;
			hasDestroyed = _hasDestroyed;
		}
	}

	protected final ArrayList<ActivityHistory> mActivityList = new ArrayList<ActivityHistory>();

	List<String> getCurrAliveActivities() {
		ArrayList<String> aliveList = new ArrayList<String>();
		for (ActivityHistory iter : mActivityList) {
			if (!iter.hasDestroyed) {
				aliveList.add(iter.className);
			}
		}
		aliveList.add(this.getLocalActivityManager().getCurrentId());
		return aliveList;
	}

	void startActivityInGroup(String targetTag, Intent prdIntent) {

		final Window w = this.getLocalActivityManager().startActivity(
				targetTag, prdIntent);

		final View mLaunchedView = w != null ? w.getDecorView() : null;

		tabFrame.removeAllViews();

		tabFrame.addView(mLaunchedView, new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT));

		if (mLaunchedView != null) {
			mLaunchedView.setVisibility(View.VISIBLE);
			mLaunchedView.setFocusableInTouchMode(true);
			((ViewGroup) mLaunchedView)
					.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		}
	}

	void cleanDestroy(String currClassTag) {
		this.getLocalActivityManager().destroyActivity(currClassTag, true);

		try {
			Field mActivitiesField = this.getLocalActivityManager().getClass()
					.getDeclaredField("mActivities");
			mActivitiesField.setAccessible(true);

			((Map) mActivitiesField.get(this.getLocalActivityManager()))
					.remove(currClassTag);

			Field mActivityArrayField = this.getLocalActivityManager()
					.getClass().getDeclaredField("mActivityArray");
			mActivityArrayField.setAccessible(true);

			((ArrayList) mActivityArrayField
					.get(this.getLocalActivityManager()))
					.remove(((ArrayList) mActivityArrayField.get(this
							.getLocalActivityManager())).size() - 1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void clearActivityOtherTab(List<String> aAliveList) {
		for (String iter : aAliveList) {
			cleanDestroy(iter);
		}
		this.mActivityList.clear();
	}

	public void resetTabsBackgroud(int tabPos) {
		for (int i = 0; i < tabWidgetRadioGroup.getChildCount(); i++) {
			TextView tabTv = (TextView) tabWidgetRadioGroup.getChildAt(i);
			if (i == tabPos) {
				tabTv.setTextColor(0xffffffff);
				mBlueColorTabPos = i;

				((ImageView) tabImages.getChildAt(i))
						.setImageResource(hoveredTabRes[i]);

			} else {
				tabTv.setBackgroundResource(R.drawable.bg_menu_btn_gray);
				tabTv.setTextColor(0xffaaaaaa);

				((ImageView) tabImages.getChildAt(i))
						.setImageResource(srcTabRes[i]);

			}

		}
	}

	OnClickListener radioClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			mNewCheckedPos = 0;

			for (int i = 0; i < tabWidgetRadioGroup.getChildCount(); i++) {
				if (v.getId() == tabWidgetRadioGroup.getChildAt(i).getId()) {

					mNewCheckedPos = i;
					break;
				}
			}

			if (mBlueColorTabPos == mNewCheckedPos) {

				return;
			}

			resetTabsBackgroud(mNewCheckedPos);

			List<String> aliveActivities = getCurrAliveActivities();
			switch (mNewCheckedPos) {
			case TAB_LIST: {
				startActivityInGroup(Constants.TAG_LIST, new Intent(
						MyMainActivityGroup.this, HomePageActivity.class));
				break;
			}
			case TAB_EXPENSE: {
				startActivityInGroup(Constants.TAG_EXPENSE, new Intent(
						MyMainActivityGroup.this, PersonalDetailActivity.class));

				break;
			}

			case TAB_USER: {
				startActivityInGroup(Constants.TAG_USER, new Intent(
						MyMainActivityGroup.this, PersonalEditActivity.class));

				break;
			}

			}

			clearActivityOtherTab(aliveActivities);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_mianactivitygroup);
		super.onCreate(savedInstanceState);

		res = getResources();
		mActivityManager = getLocalActivityManager();

		tabFrame = (ViewGroup) findViewById(R.id.main_group_layout);
		tabWidgetRadioGroup = (ViewGroup) findViewById(R.id.menu_charactor_layout);		
		tabImages = (ViewGroup)findViewById(R.id.menu_img_layout);

		for (int i = 0; i < tabWidgetRadioGroup.getChildCount(); i++) {
			((TextView) tabWidgetRadioGroup.getChildAt(i))
					.setOnClickListener(radioClickListener);
		}
		((TextView) tabWidgetRadioGroup.getChildAt(0)).performClick();
	}

}
