package com.cool.comfort;

import java.util.ArrayList;
import java.util.List;

import com.cool.comfort.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cool.comfort.util.Utility;

public class DurationActivity extends Activity {

	/**
	 * 当前用途/当前选项
	 */
	private int curPurpose;
	private int selectedPos;

	/**
	 * 默认选项
	 */
	public static final int DUR_DEFAULT_POS = 1;
	public static final int TIMING_DEFAULT_POS = 0;

	public static final int TIME_ZERO = 0;
	/**
	 * 持续启动
	 */
	public static final int DUR_ALWAYS = TIME_ZERO;

	/**
	 * 　延迟启动未开启
	 */
	public static final int TIMING_OFF = TIME_ZERO;

	/**
	 * 数据回馈编号/当前用途
	 */
	public static final int NO_PURPOSE = 0;
	public static final int GET_DURATION = 1;
	public static final int GET_TIMING = 2;

	private int[] arr_time_show;
	public static final int[] ARR_DUR_SECONDS = { 10, 30, 60, 2 * 60, 5 * 60,
			10 * 60, 30 * 60, DUR_ALWAYS };

	public static final int[] ARR_TIMING_SECONDS = { TIMING_OFF, 1 * 3600,
			2 * 3600, 3 * 3600, 5 * 3600, 8 * 3600 };

	private ListView dur_list;
	private List<String> dur_data;
	private ArrayAdapter<String> adapter;
	
	private TextView durationTitle;

	public static void actionStart(Context context, int curPos, int curPurpose) {
		Intent intent = new Intent(context, DurationActivity.class);
		intent.putExtra("curPos", curPos);
		intent.putExtra("curPurpose", curPurpose);
		((Activity) context).startActivityForResult(intent, curPurpose);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duration);

		// 获取当前用途(设置延迟启动/设置持续时间)
		curPurpose = getIntent().getIntExtra("curPurpose", NO_PURPOSE);
		if (curPurpose == NO_PURPOSE) {
			// 未设置用途,BUG!
			finish();
		}
		
		durationTitle = (TextView) findViewById(R.id.duration_title);

		// 获取当前选项
		int defPos;
		if (curPurpose == GET_DURATION) {
			defPos = DUR_DEFAULT_POS;
			arr_time_show = ARR_DUR_SECONDS;
			durationTitle.setText(R.string.title_activity_duration);
		} else {
			defPos = TIMING_DEFAULT_POS;
			arr_time_show = ARR_TIMING_SECONDS;
			durationTitle.setText(R.string.title_activity_timing);
		}
		selectedPos = getIntent().getIntExtra("curPos", defPos);

		dur_list = (ListView) findViewById(R.id.duration_list);
		dur_data = getDuration();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, dur_data);
		dur_list.setAdapter(adapter);
		dur_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		dur_list.setItemChecked(selectedPos, true);
		dur_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				dur_list.setSelection(position);

				if (position != selectedPos) {
					// 变更则修改
					selectedPos = position;
					dur_list.setItemChecked(selectedPos, true);
					Intent intent = new Intent();
					intent.putExtra("newPos", position);
					setResult(RESULT_OK, intent);
				}
				finish();
			}

		});
	}

	/**
	 * 生成列表数据
	 */
	private List<String> getDuration() {
		List<String> duration = new ArrayList<String>();
		for (int i : arr_time_show) {
			duration.add(Utility.secondToString(this, i, curPurpose));
		}
		return duration;
	}

}
