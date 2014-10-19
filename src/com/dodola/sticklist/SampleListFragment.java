package com.dodola.sticklist;

import java.util.ArrayList;

import com.dodola.sticklist.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SampleListFragment extends Fragment implements ScrollViewSuperExtend.OnInterceptTouchListener {

	private static final String ARG_POSITION = "position";

	private ListView mListView;
	private ArrayList<String> mListItems;

	private int mPosition;

	public static SampleListFragment newInstance(int position) {
		SampleListFragment f = new SampleListFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}
	public boolean needIntercept(ListView paramListView) {
		Log.d("=====", "needIntercept");
		if (paramListView == null) {
			return true;
		}
		if (paramListView.getCount() < 1) {
			return true;
		}

		if (paramListView.getFirstVisiblePosition() == 0) {// 第一项显示
			View localView;
			int[] arrayOfInt1;
			int[] arrayOfInt2;
			localView = paramListView.getChildAt(0);
			arrayOfInt1 = new int[2];
			arrayOfInt2 = new int[2];

			localView.getLocationInWindow(arrayOfInt1);
			paramListView.getLocationInWindow(arrayOfInt2);
			Log.d("=====", "arrayOfInt1:" + arrayOfInt1[1] + ",arrayOfInt2:" + arrayOfInt2[1]);
			if (arrayOfInt1[1] == arrayOfInt2[1]) {// 并且没有移位
				return true;
			}
		}
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPosition = getArguments().getInt(ARG_POSITION);

		mListItems = new ArrayList<String>();

		for (int i = 1; i <= 100; i++) {
			mListItems.add(i + ". item - currnet page: " + (mPosition + 1));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_list, null);
		mListView = (ListView) v.findViewById(R.id.listView);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item, android.R.id.text1, mListItems));
	}

	@Override
	public boolean needIntercept() {
		return needIntercept(mListView);
	}

}