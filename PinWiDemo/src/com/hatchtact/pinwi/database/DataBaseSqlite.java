package com.hatchtact.pinwi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseSqlite extends SQLiteOpenHelper {

	private static final int VERSION_NO = 1;
	private static final String DATABASE_NAME = "PINWI";

	/*Table names*/
	public static final String TABLE_ACCESS_PROFILE = "TABLE_ACCESS_PROFILE";
	public static final String TABLE_NETWORK_MODULE = "TABLE_NETWORK_MODULE";

	/*Access Profile Fields*/
	public static final String COLUMN_PROFILEID = "ProfileID";
	public static final String COLUMN_PROFILE_TYPE = "ProfileType";
	public static final String COLUMN_PROFILE_IMAGE= "ProfileImage";
	public static final String COLUMN_FIRST_NAME = "FirstName";
	public static final String COLUMN_EARNED_POINTS = "EarnedPoints";
	public static final String COLUMN_PENDING_POINTS = "PendingPoints";
	public static final String COLUMN_PASSCODE = "Passcode";
	//public static final String COLUMN_LASTTIMESTAMP = "LASTTIMESTAMP";

	/*TABLE_NETWORK_MODULE Fields*/
	public static final String COLUMN_FriendID="FriendID";
	public static final String COLUMN_ProfileImage="ProfileImage";
	public static final String COLUMN_FriendName="FriendName";
	public static final String COLUMN_ChildName="ChildName";
	public static final String COLUMN_FStatus="FStatus";
	public static final String COLUMN_LoggedUserName="LoggedUserName";

	public DataBaseSqlite(Context context) {
		super(context, DATABASE_NAME, null, VERSION_NO);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE_ACCESSPROFILE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	private static final String CREATE_TABLE_ACCESSPROFILE = "create table " + TABLE_ACCESS_PROFILE + " ("
			+ COLUMN_PROFILEID + " integer primary key, "
			+ COLUMN_PROFILE_TYPE + " integer,"
			+ COLUMN_PROFILE_IMAGE + " text,"
			+ COLUMN_FIRST_NAME + " text,"
			+ COLUMN_EARNED_POINTS + " text,"
			+ COLUMN_PENDING_POINTS + " text,"
			+ COLUMN_PASSCODE + " text)";

	private static final String CREATE_TABLE_NETWORK_MODULE = "create table " + TABLE_NETWORK_MODULE + " ("
			+ COLUMN_FriendID + " text primary key, "
			+ COLUMN_ProfileImage + " text,"
			+ COLUMN_FriendName + " text,"
			+ COLUMN_ChildName + " text,"
			+ COLUMN_FStatus + " text,"
			+ COLUMN_LoggedUserName + " text)";
}
