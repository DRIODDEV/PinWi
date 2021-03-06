package com.hatchtact.pinwi.database;


import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hatchtact.pinwi.classmodel.AccessProfile;
import com.hatchtact.pinwi.classmodel.GetFriendsListByLoggedID;

public class DataSource {

	private DataBaseSqlite dataBaseSqlite;
	private SQLiteDatabase sqliteDataBase = null;

	private Context mContext;

	public DataSource(Context mContext) {
		dataBaseSqlite = new DataBaseSqlite(mContext);
		this.mContext = mContext;
	}

	public boolean isDbOpen() {
		if (sqliteDataBase != null && sqliteDataBase.isOpen())
			return true;
		else
			return false;
	}

	public void open() {

		try {
			sqliteDataBase = dataBaseSqlite.getWritableDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			sqliteDataBase.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long addAccessProfile(AccessProfile modelAccesssProfile) {
		long added = -1;

		if (isProfileExist(modelAccesssProfile.getProfileID()) == null) 
		{
			ContentValues values = new ContentValues();
			values.put(DataBaseSqlite.COLUMN_PROFILEID,modelAccesssProfile.getProfileID());
			values.put(DataBaseSqlite.COLUMN_PROFILE_TYPE,modelAccesssProfile.getProfileType());
			values.put(DataBaseSqlite.COLUMN_PROFILE_IMAGE,modelAccesssProfile.getProfileImage());
			values.put(DataBaseSqlite.COLUMN_FIRST_NAME,modelAccesssProfile.getFirstName());
			values.put(DataBaseSqlite.COLUMN_EARNED_POINTS,modelAccesssProfile.getEarnedPoints());
			values.put(DataBaseSqlite.COLUMN_PENDING_POINTS,modelAccesssProfile.getPendingPoints());
			values.put(DataBaseSqlite.COLUMN_PASSCODE, modelAccesssProfile.getPasscode());

			added = sqliteDataBase.insert(DataBaseSqlite.TABLE_ACCESS_PROFILE, null, values);
		} else {
			added = updateProfileData(modelAccesssProfile);
		}

		return added;
	}

	private AccessProfile isProfileExist(int ProfileID) {

		AccessProfile model = null;

		Cursor cursor = sqliteDataBase.query(DataBaseSqlite.TABLE_ACCESS_PROFILE, null, DataBaseSqlite.COLUMN_PROFILEID + " = '" + ProfileID + "'", null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();

			if (cursor.getCount() > 0) {
				model = getProfileData(cursor);
			}

			cursor.close();


		}

		return model;
	}

	private AccessProfile getProfileData(Cursor cursor) {

		AccessProfile modelProfile = new AccessProfile();

		modelProfile.setProfileID(cursor.getInt(0));
		modelProfile.setProfileType(cursor.getInt(1));
		modelProfile.setProfileImage(cursor.getString(2));
		modelProfile.setFirstName(cursor.getString(3));
		modelProfile.setEarnedPoints(cursor.getInt(4));
		modelProfile.setPendingPoints(cursor.getInt(5));
		modelProfile.setPasscode(cursor.getString(6));
		//modelProfile.s(cursor.getString(9));
		return modelProfile;
	}

	private int updateProfileData(AccessProfile modelAccesssProfile) {
		int updated = -1;

		ContentValues values = new ContentValues();
		values.put(DataBaseSqlite.COLUMN_PROFILEID,modelAccesssProfile.getProfileID());
		values.put(DataBaseSqlite.COLUMN_PROFILE_TYPE,modelAccesssProfile.getProfileType());
		values.put(DataBaseSqlite.COLUMN_PROFILE_IMAGE,modelAccesssProfile.getProfileImage());
		values.put(DataBaseSqlite.COLUMN_FIRST_NAME,modelAccesssProfile.getFirstName());
		values.put(DataBaseSqlite.COLUMN_EARNED_POINTS,modelAccesssProfile.getEarnedPoints());
		values.put(DataBaseSqlite.COLUMN_PENDING_POINTS,modelAccesssProfile.getPendingPoints());
		values.put(DataBaseSqlite.COLUMN_PASSCODE, modelAccesssProfile.getPasscode());


		updated = sqliteDataBase.update(DataBaseSqlite.TABLE_ACCESS_PROFILE, values,
				DataBaseSqlite.COLUMN_PROFILEID + " = '" + modelAccesssProfile.getProfileID() + "'", null);


		return updated;
	}

	public ArrayList<AccessProfile> getAccessProfileList() 
	{

		Cursor cursor = sqliteDataBase.query(DataBaseSqlite.TABLE_ACCESS_PROFILE, null,null, null, null, null, null
				, null);

		ArrayList<AccessProfile> listProfile = null;

		if (cursor != null && cursor.getCount() > 0) {
			listProfile = new ArrayList<AccessProfile>();
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				listProfile.add(getProfileData(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}

		return listProfile;
	}

	public boolean deleteAccessProfileData() {
		return (sqliteDataBase.delete(DataBaseSqlite.TABLE_ACCESS_PROFILE,null, null) > 0);
	}


	public long addNetworkModuleData(GetFriendsListByLoggedID modelNetwork) {
		long added = -1;

		if (isNetworkModelExist(modelNetwork.getFriendID()) == null) 
		{
			ContentValues values = new ContentValues();
			values.put(DataBaseSqlite.COLUMN_FriendID,modelNetwork.getFriendID());
			values.put(DataBaseSqlite.COLUMN_ProfileImage,modelNetwork.getProfileImage());
			values.put(DataBaseSqlite.COLUMN_FriendName,modelNetwork.getFriendName());
			values.put(DataBaseSqlite.COLUMN_ChildName,modelNetwork.getChildName());
			values.put(DataBaseSqlite.COLUMN_FStatus,modelNetwork.getFStatus());
			values.put(DataBaseSqlite.COLUMN_LoggedUserName,modelNetwork.getLoggedUserName());

			added = sqliteDataBase.insert(DataBaseSqlite.TABLE_NETWORK_MODULE, null, values);
		} else {
			added = updateNetworkModuleData(modelNetwork);
		}

		return added;
	}

	private GetFriendsListByLoggedID isNetworkModelExist(String ProfileID) {

		GetFriendsListByLoggedID model = null;

		Cursor cursor = sqliteDataBase.query(DataBaseSqlite.TABLE_NETWORK_MODULE, null, DataBaseSqlite.COLUMN_PROFILEID + " = '" + ProfileID + "'", null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();

			if (cursor.getCount() > 0) {
				model = getNetworkModuleData(cursor);
			}

			cursor.close();


		}

		return model;
	}

	private GetFriendsListByLoggedID getNetworkModuleData(Cursor cursor) {

		GetFriendsListByLoggedID model = new GetFriendsListByLoggedID();

		model.setFriendID(cursor.getString(0));
		model.setProfileImage(cursor.getString(1));
		model.setFriendName(cursor.getString(2));
		model.setChildName(cursor.getString(3));
		model.setFStatus(cursor.getString(4));
		model.setLoggedUserName(cursor.getString(5));

		return model;
	}

	private int updateNetworkModuleData(GetFriendsListByLoggedID modelNetwork) {
		int updated = -1;

		ContentValues values = new ContentValues();
		values.put(DataBaseSqlite.COLUMN_FriendID,modelNetwork.getFriendID());
		values.put(DataBaseSqlite.COLUMN_ProfileImage,modelNetwork.getProfileImage());
		values.put(DataBaseSqlite.COLUMN_FriendName,modelNetwork.getFriendName());
		values.put(DataBaseSqlite.COLUMN_ChildName,modelNetwork.getChildName());
		values.put(DataBaseSqlite.COLUMN_FStatus,modelNetwork.getFStatus());
		values.put(DataBaseSqlite.COLUMN_LoggedUserName,modelNetwork.getLoggedUserName());

		updated = sqliteDataBase.update(DataBaseSqlite.TABLE_NETWORK_MODULE, values,
				DataBaseSqlite.COLUMN_FriendID + " = '" + modelNetwork.getFriendID() + "'", null);


		return updated;
	}

	public ArrayList<GetFriendsListByLoggedID> getNetworkModuleList() 
	{

		Cursor cursor = sqliteDataBase.query(DataBaseSqlite.TABLE_NETWORK_MODULE, null,null, null, null, null, null
				, null);

		ArrayList<GetFriendsListByLoggedID> listNetwork = null;

		if (cursor != null && cursor.getCount() > 0) {
			listNetwork = new ArrayList<GetFriendsListByLoggedID>();
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				listNetwork.add(getNetworkModuleData(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}

		return listNetwork;
	}

	public boolean deleteNetworkModuleData() {
		return (sqliteDataBase.delete(DataBaseSqlite.TABLE_NETWORK_MODULE,null, null) > 0);
	}


	public void deleteAll()
	{
		deleteAccessProfileData();
		deleteNetworkModuleData();
	}




}
