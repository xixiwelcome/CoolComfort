package com.cool.comfort.util;

import android.content.Context;

import com.cool.comfort.DurationActivity;
import com.cool.comfort.R;

public class Utility {

	/**
	 * 时间（秒）数组转化为ListView用的字符串
	 */
	public static String secondToString(Context context, int i, int purpose) {
		String str_tmp = "";
		if (i == DurationActivity.TIME_ZERO) {
			switch (purpose) {
			case DurationActivity.GET_DURATION:
				str_tmp = context.getString(R.string.duration_always);
				break;
			case DurationActivity.GET_TIMING:
				str_tmp = context.getString(R.string.timing_off);
				break;
			default:
				str_tmp = i + context.getString(R.string.second);
				break;
			}

		} else {

			if (i / (3600) > 0) {
				str_tmp = (i / 3600) + context.getString(R.string.hour);
			}
			if ((i % 3600) / 60 > 0) {
				str_tmp += ((i % 3600) / 60)
						+ context.getString(R.string.minute);
			}
			if ((i % 60) > 0) {
				str_tmp += (i % 60) + context.getString(R.string.second);
			}
		}
		return str_tmp;
	}
	
	public static String secondToString(Context context, int i) {
		return secondToString(context, i, DurationActivity.NO_PURPOSE);
	}
}
