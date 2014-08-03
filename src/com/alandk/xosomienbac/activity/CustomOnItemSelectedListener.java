package com.alandk.xosomienbac.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {
	private ThongkeItnhieuActivity thongketItnhieuActivity;

	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		Toast.makeText(parent.getContext(), "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
		thongketItnhieuActivity.showResultThongkeItnhieuByDate(Integer.valueOf(parent.getItemAtPosition(pos).toString()));
	}

	public CustomOnItemSelectedListener(ThongkeItnhieuActivity thongketItnhieuActivity) {
		this.thongketItnhieuActivity = thongketItnhieuActivity;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

}