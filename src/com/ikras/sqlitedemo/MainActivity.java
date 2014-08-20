package com.ikras.sqlitedemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.ikras.bean.DbBean;
import com.ikras.db.DbAdapter;
import com.ikras.db.DbOperator;

public class MainActivity extends Activity
{
	private ListView accountList;
	private Button addBtn;
	private String TAG = "MainActivity";
	private ArrayList<DbBean> mList;
	private DbOperator dbHander;
	private Map<Integer, Boolean> isCheckedMap;
	private Button delBtn;
	protected ArrayList<Integer> ids;
	protected ArrayList<DbBean> acs;
	private DbAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		accountList = (ListView) findViewById(R.id.accountList);
		delBtn = (Button)findViewById(R.id.delBtn);
		addBtn = (Button) findViewById(R.id.addBtn);

		init();
	}

	private void init()
	{
		dbHander = new DbOperator(getApplicationContext());
		mList = dbHander.inquire();

		isCheckedMap = new HashMap<Integer, Boolean>();
		for (int i = 0; i < mList.size(); i++)
		{
			isCheckedMap.put(i, false);
		}

		listAdapter = new DbAdapter(this, mList, isCheckedMap);
		accountList.setAdapter(listAdapter);

		accountList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						EditActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("id", mList.get(position).getId());
				bundle.putString("type", mList.get(position).getType());
				bundle.putString("date", mList.get(position).getDate());
				bundle.putString("body", mList.get(position).getBody());
				bundle.putString("money", mList.get(position).getMoney());
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
				System.gc();
			}
		});

		delBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				ids  = new ArrayList<Integer>();
				acs = new ArrayList<DbBean>();
				if (isCheckedMap != null && isCheckedMap.size() > 0)
				{
					for (int i = 0; i < isCheckedMap.size(); i++)
					{
						if (isCheckedMap.get(i))
						{
							ids.add(mList.get(i).getId());// 要删除的所在Id;
							acs.add(mList.get(i));
						}
					}	
					for (DbBean a : acs)
					{
						mList.remove(a);// 移除对象
					}
					dbHander.del(ids);

					listAdapter.notifyDataSetChanged();
					Log.d(TAG, "刷新成功");
				}
			}
		});

		addBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
				System.gc();
				Intent intent = new Intent(MainActivity.this,
						EditActivity.class);
				startActivity(intent);
			}
		});

	}
}
