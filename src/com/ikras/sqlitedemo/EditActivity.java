package com.ikras.sqlitedemo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

public class EditActivity extends Activity
{
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private Spinner type;
	private Button ensureBtn;
	private Button cancelBtn;
	private Button dateBtn;
	private TextView body;
	private TextView money;
	private Calendar mCalendar;
	private DatePickerDialog datePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		type = (Spinner) findViewById(R.id.type);
		dateBtn = (Button) findViewById(R.id.dateBtn);
		body = (TextView) findViewById(R.id.body);
		money = (TextView) findViewById(R.id.money);
		cancelBtn = (Button) findViewById(R.id.cancelBtn);
		ensureBtn = (Button) findViewById(R.id.ensureBtn);

		init();
	}

	private void init()
	{
		//获取日历
		mCalendar = Calendar.getInstance();
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.spinner_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		type.setAdapter(adapter);

		type.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				// TODO Auto-generated method stub
				// An item was selected. You can retrieve the selected item
				// using parent.getItemAtPosition(pos)
				parent.getItemAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// TODO Auto-generated method stub
				// Another interface callback
			}
		});

		dateBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				datePicker = new DatePickerDialog(EditActivity.this,
						new DatePickerDialog.OnDateSetListener()
						{
							public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
							{
								mCalendar.set(Calendar.YEAR, year);
								mCalendar.set(Calendar.MONTH, monthOfYear);
								mCalendar
										.set(Calendar.DAY_OF_MONTH, dayOfMonth);
								updateDateButtonText();
							}
						}, mCalendar.get(Calendar.YEAR), mCalendar
								.get(Calendar.MONTH), mCalendar
								.get(Calendar.DAY_OF_MONTH));
				datePicker.show();
			}
		});
		updateDateButtonText();
	}

	// 更新日期按钮文字
	private void updateDateButtonText()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		String dateForButton = dateFormat.format(mCalendar.getTime());
		dateBtn.setText(dateForButton);
	}

}