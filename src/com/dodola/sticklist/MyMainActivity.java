package com.dodola.sticklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.astuetz.PagerSlidingTabStrip;
import com.dodola.sticklist.R;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;

public class MyMainActivity extends FragmentActivity {
	private PagerSlidingTabStrip mPagerSlidingTabStrip;
	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;
	private ScrollViewSuperExtend mScrollView;
	private TypedValue mTypedValue = new TypedValue();
	private int mActionBarHeight;

	private OnPageChangeListener changeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			SampleListFragment sampleListFragment = fragments.get(arg0);
			if (sampleListFragment != null) {
				mScrollView.setOnInterceptTouchListener(sampleListFragment);
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		int currentItem = mViewPager.getCurrentItem();
		SampleListFragment sampleListFragment = fragments.get(currentItem);
		if (sampleListFragment != null) {
			mScrollView.setOnInterceptTouchListener(sampleListFragment);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_main);

		mScrollView = (ScrollViewSuperExtend) this.findViewById(R.id.scroll);
		mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(4);

		mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

		mViewPager.setAdapter(mPagerAdapter);

		mPagerSlidingTabStrip.setViewPager(mViewPager);
		mPagerSlidingTabStrip.setOnPageChangeListener(changeListener);
		mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				mViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				mViewPager.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mScrollView
						.getMeasuredHeight() - mPagerSlidingTabStrip.getMeasuredHeight() - getActionBarHeight()));
			}
		});
	}
	public int getActionBarHeight() {
		if (mActionBarHeight != 0) {
			return mActionBarHeight;
		}

		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
			getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
		} else {
			getTheme().resolveAttribute(R.attr.actionBarSize, mTypedValue, true);
		}

		mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, getResources().getDisplayMetrics());

		return mActionBarHeight;
	}
	private Map<Integer, SampleListFragment> fragments = new HashMap<Integer, SampleListFragment>();

	public class PagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = {"Page 1", "Page 2", "Page 3", "Page 4"};

		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			SampleListFragment fragment = SampleListFragment.newInstance(position);
			fragments.put(position, fragment);
			mScrollView.setOnInterceptTouchListener(fragment);
			return fragment;
		}
	}
}
