package com.ikras.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ikras.bean.DbBean;

public class DbOperator
{
	private DbHelper dbHelper;
	private SQLiteDatabase dbHander;
	private String TABLE_NAME = "bill";

	public DbOperator(Context context)
	{
		this.dbHelper = new DbHelper(context, "Account.db", null, 1);
	}

	/**插入数据
	 * @param item
	 */
	public void add(DbBean item)
	{
		dbHander = dbHelper.getWritableDatabase();
		dbHander.execSQL(
				"insert into bill(type, date, body, money) values(?,?,?,?)",
				new Object[] { item.getType(), item.getDate(), item.getBody(), item
						.getMoney() });
		dbHander.close();
	}

	/**删除数据
	 * @param ids
	 */
	public void del(List<Integer> ids)
	{
		dbHander = dbHelper.getWritableDatabase();
		for (int id : ids)
		{
			dbHander.delete(TABLE_NAME, "id=" + id, null);
		}  
		dbHander.close();
	}

	/**更新数据
	 * @param item
	 */
	public void update(DbBean item)
	{
		dbHander = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("type", item.getType());
		values.put("date", item.getDate());
		values.put("body", item.getBody());
		values.put("money", item.getMoney());
		// 不知道为什么不行
		// dbHander.execSQL(
		// "update " + TABLE_NAME +
		// "set type='?',date='?',body='?',money='?' where " + "id=?",
		// new Object[] { item.getType(), item.getDate(), item.getBody(), item
		// .getMoney(), item.getId() });
		dbHander.update(TABLE_NAME, values, "id=" + item.getId(), null);
		dbHander.close();
	}

	/**查询数据库
	 * @return
	 */
	public ArrayList<DbBean> inquire()
	{
		ArrayList<DbBean> lists = new ArrayList<DbBean>();
		DbBean tusers = null;
		dbHander = dbHelper.getWritableDatabase();

		Cursor cursor = dbHander.rawQuery("select * from bill", null);
		while (cursor.moveToNext())
		{
			tusers = new DbBean();
			tusers.setId(cursor.getInt(cursor.getColumnIndex("id")));
			tusers.setType(cursor.getString(cursor.getColumnIndex("type")));
			tusers.setDate(cursor.getString(cursor.getColumnIndex("date")));
			tusers.setBody(cursor.getString(cursor.getColumnIndex("body")));
			tusers.setMoney(cursor.getString(cursor.getColumnIndex("money")));
			lists.add(tusers);
		}
		dbHander.close();

		return lists;
	}
}
