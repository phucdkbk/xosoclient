package com.alandk.xosomienbac.database;

import java.util.List;
import java.util.Random;

import com.example.xosomienbac.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class TestDatabaseActivity extends ListActivity {
	private CommentsDataSource datasource;
	private LotteryDataSource lotteryDatasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlite);

		datasource = new CommentsDataSource(this);
		lotteryDatasource = new LotteryDataSource(this);
		
		datasource.open();
		lotteryDatasource.open();

		List<Comment> values = datasource.getAllComments();
		
		LotteryDBResult lotteryResult = lotteryDatasource.getAllLotteryResultByDate(20090101);

		// use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	// Will be called via the onClick attribute
	// of the buttons in main.xml
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
		Comment comment = null;
		switch (view.getId()) {
		case R.id.add:
			String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
			int nextInt = new Random().nextInt(3);
			// save the new comment to the database
			comment = datasource.createComment(comments[nextInt]);
			lotteryDatasource.createLotteryResult(20090101, "Hello");
			adapter.add(comment);
			break;
		case R.id.delete:
			if (getListAdapter().getCount() > 0) {
				comment = (Comment) getListAdapter().getItem(0);
				datasource.deleteComment(comment);
				adapter.remove(comment);
			}
			break;
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

}