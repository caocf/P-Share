package com.azhuoinfo.pshare.db;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import com.azhuoinfo.pshare.model.Message;

import java.util.List;

import mobi.cangol.mobile.db.Dao;
import mobi.cangol.mobile.db.DeleteBuilder;
import mobi.cangol.mobile.db.QueryBuilder;

public class MessageService implements BaseService<Message> {
	private static final String TAG = "MessageService";
	private Dao<Message, Integer> dao;

	public MessageService(Context context) {
		try {
			DatabaseHelper dbHelper = DatabaseHelper
					.createDataBaseHelper(context);
			dao = dbHelper.getDao(Message.class);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "MessageService init fail!");
		}
	}
	
	/**
	 * @return the row ID of the newly inserted row or updated row, or -1 if an error occurred
	 */
	@Override
	public int save(Message obj) {
		int result = -1;
		try {
			if (obj.get_id() > 0 && obj.get_id() != -1) {
				result = dao.update(obj);
				if(result > 0){
					result = obj.get_id();
				}
			}else {
				result = dao.create(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "MessageService save fail!");
		}
		return result;
	}

	@Override
	public void refresh(Message obj) {
		try {
			dao.refresh(obj);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "MessageService refresh fail!");
		}
	}

	@Override
	public void delete(Integer id) {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "MessageService delete fail!");
		}

	}

	@Override
	public Message find(Integer id) {
		try {
			return dao.queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "MessageService find fail!");
		}
		return null;
	}

	@Override
	public int getCount() {
		try {
			return dao.queryForAll().size();
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "MessageService getCount fail!");
		}
		return -1;
	}

	public List<Message> getAllList() {
		try {
			return dao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "MessageService getAllList fail!");
		}
		return null;
	}

	@Override
	public List<Message> findList(QueryBuilder queryBuilder) {
		return dao.query(queryBuilder);
	}

	public List<Message> findUnReadList(String userId) {
		QueryBuilder queryBuilder = new QueryBuilder(Message.class);
		queryBuilder.addQuery("userId", userId, "=");
		queryBuilder.addQuery("status", 0, "=");
		queryBuilder.orderBy("timestamp asc");
		return dao.query(queryBuilder);
	}
	public List<Message> findList(String userId) {
		QueryBuilder queryBuilder = new QueryBuilder(Message.class);
		queryBuilder.addQuery("userId", userId, "=");
		queryBuilder.addQuery("status", -1, "!=");
		queryBuilder.orderBy("timestamp asc");
		return dao.query(queryBuilder);
	}
	public int deleteAll(String userId) {
		DeleteBuilder deleteBuilder = new DeleteBuilder(Message.class);
		deleteBuilder.addQuery("userId", userId, "=");
		return dao.delete(deleteBuilder);
	}
}
