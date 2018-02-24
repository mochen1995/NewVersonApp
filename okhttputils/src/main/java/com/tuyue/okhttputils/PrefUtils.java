package com.tuyue.okhttputils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreference的工具类
 * 

 * 
 */
public class PrefUtils {

	private static final String SHARE_PREFS_NAME = "config";

	private static SharedPreferences pref = null;

	public static void putBoolean(Context ctx, String key, boolean value) {
		if (pref==null)
		{
			pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}
		pref.edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(Context ctx, String key,
									 boolean defaultValue) {
		if (pref==null)
		{
			pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		return pref.getBoolean(key, defaultValue);
	}

	public static void putString(Context ctx, String key, String value) {
		if (pref==null)
		{
			pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		pref.edit().putString(key, value).commit();
	}

	public static String getString(Context ctx, String key, String defaultValue) {
		if (pref==null)
		{
			pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		return pref.getString(key, defaultValue);
	}

	public static void clearString(Context ctx, String key){

		if (pref==null)
		{
			pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		pref.edit().remove(key);

	}
	public static void clearInt(Context ctx, String key){

		if (pref==null)
		{
			pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		pref.edit().remove(String.valueOf(key));

	}

	public static void putInt(Context ctx, String key, int value) {
		if (pref==null)
		{
			pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		pref.edit().putInt(key, value).commit();
	}

	public static int getInt(Context ctx, String key, int defaultValue) {
		if (pref==null)
		{
			pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		return pref.getInt(key, defaultValue);
	}


}
