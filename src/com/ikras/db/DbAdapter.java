package com.ikras.db;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.ikras.bean.DbBean;
import com.ikras.sqlitedemo.R;

public class DbAdapter extends BaseAdapter
{
	private ArrayList<DbBean> mList;
	private Map<Integer, Boolean> isCheckedMap;
	private LayoutInflater inflater;

	public DbAdapter(Context context, ArrayList<DbBean> mList,Map<Integer, Boolean> isCheckedMap)
	{
		super();
		this.mList = mList;
		this.isCheckedMap = isCheckedMap;
		inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	}

	// 在适配器中有多少数据项、
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return mList.size();
	}

	// 获取集合中指定位置数据项
	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	// 获取列表中指定行的Id
	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return mList.get(position).getId();
	}

	// 设置item显示内容
	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		DbBean mBean = mList.get(position);

		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_row, null);
			holder.accountChecked = (CheckBox) convertView
					.findViewById(R.id.accountChecked);
			holder.type = (TextView) convertView.findViewById(R.id.typeView);
			holder.date = (TextView) convertView.findViewById(R.id.dateView);
			holder.body = (TextView) convertView.findViewById(R.id.bodyView);
			holder.money = (TextView) convertView.findViewById(R.id.moneyView);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.accountChecked.setChecked(isCheckedMap.get(position));
		holder.accountChecked.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				// TODO Auto-generated method stub
				if (isChecked)
				{
					isCheckedMap.put(position, true);
				}
				else
				{
					isCheckedMap.put(position, false);
				}
			}
		});
		
		holder.type.setText(mBean.getType());
		holder.date.setText(mBean.getDate());
		holder.body.setText(mBean.getBody());
		holder.money.setText(mBean.getMoney());

		return convertView;
	}

	static class ViewHolder
	{
		private CheckBox accountChecked;
		private TextView type;
		private TextView date;
		private TextView body;
		private TextView money;
	}
}
