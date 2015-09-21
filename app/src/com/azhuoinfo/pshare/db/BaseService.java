package com.azhuoinfo.pshare.db;

import android.database.SQLException;

import java.util.List;

import mobi.cangol.mobile.db.QueryBuilder;

public interface BaseService<T> {
	/**
	 * 
	 * @param obj
	 * @return 
	 */
	void refresh(T obj) throws SQLException ;
	/**
	 * 
	 * @param id
	 */
	void delete(Integer id) throws SQLException ;
	/**
	 * 
	 * @param id
	 * @return
	 */
	T find(Integer id) throws SQLException ;
	/**
	 * 
	 * @return
	 */
	int getCount() throws SQLException ;
	/**
	 * 
	 * @param keywords
	 * @return
	 */
	List<T> getAllList() throws SQLException ;
	/**
	 * 
	 * @param obj
	 */
	int save(T obj);

	/**
	 * 
	 * @param queryBuilder
	 * @return
	 */
	List<T> findList(QueryBuilder queryBuilder) ;
}
