package fr.suid.tripreport2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MouvementFormActivity extends AppCompatActivity
{
	private static final String TAG = "Sample";

	private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";

	private static final String STATE_TEXTVIEW = "STATE_TEXTVIEW";

	private TextView textViewHD;
	private TextView textViewHA;

	private Button saveButton;

	private SwitchDateTimeDialogFragment dateTimeFragmentHD;
	private SwitchDateTimeDialogFragment dateTimeFragmentHA;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mouvement_form);

		textViewHD = (TextView) findViewById(R.id.editTextDateHeureDepart);
		textViewHA = (TextView) findViewById(R.id.editTextDateHeureArrivee);

		if (savedInstanceState != null)
		{
			// Restore value from saved state
			textViewHD.setText(savedInstanceState.getCharSequence(STATE_TEXTVIEW));
			textViewHA.setText(savedInstanceState.getCharSequence(STATE_TEXTVIEW));
		}

		// Construct SwitchDateTimePicker
		dateTimeFragmentHD = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
		dateTimeFragmentHA = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);

		if (dateTimeFragmentHD == null)
		{
			dateTimeFragmentHD = SwitchDateTimeDialogFragment.newInstance(
					getString(R.string.label_datetime_dialog),
					getString(android.R.string.ok),
					getString(android.R.string.cancel)
					//getString(R.string.clean) // Optional
			);

		}

		if (dateTimeFragmentHA == null)
		{
			dateTimeFragmentHA = SwitchDateTimeDialogFragment.newInstance(
					getString(R.string.label_datetime_dialog),
					getString(android.R.string.ok),
					getString(android.R.string.cancel)
					//getString(R.string.clean) // Optional
			);

		}

		// Init format
		final SimpleDateFormat myDateFormat = new SimpleDateFormat("d/MM/yyyy HH:mm", java.util.Locale.getDefault());
		// Assign unmodifiable values
		dateTimeFragmentHA.set24HoursMode(true);
		dateTimeFragmentHA.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
		dateTimeFragmentHA.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());

		dateTimeFragmentHD.set24HoursMode(true);
		dateTimeFragmentHD.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
		dateTimeFragmentHD.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());

		// Define new day and month format
		try
		{
			dateTimeFragmentHA.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
			dateTimeFragmentHD.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
		} catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e)
		{
			Log.e(TAG, e.getMessage());
		}

		// Set listener for date
		// Or use dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
		dateTimeFragmentHA.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener()
		{
			@Override
			public void onPositiveButtonClick(Date date)
			{
				textViewHA.setText(myDateFormat.format(date));
			}

			@Override
			public void onNegativeButtonClick(Date date)
			{
				// Do nothing
			}

			@Override
			public void onNeutralButtonClick(Date date)
			{
				// Optional if neutral button does'nt exists
				textViewHA.setText("");
			}
		});

		dateTimeFragmentHD.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener()
		{
			@Override
			public void onPositiveButtonClick(Date date)
			{
				textViewHD.setText(myDateFormat.format(date));
			}

			@Override
			public void onNegativeButtonClick(Date date)
			{
				// Do nothing
			}

			@Override
			public void onNeutralButtonClick(Date date)
			{
				// Optional if neutral button does'nt exists
				textViewHA.setText("");
			}
		});

		textViewHD.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{

				// Re-init each time
				dateTimeFragmentHD.startAtCalendarView();
				dateTimeFragmentHD.setDefaultDateTime(new GregorianCalendar(2017, Calendar.MARCH, 4, 15, 20).getTime());
				dateTimeFragmentHD.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
			}
		});

		textViewHA.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{

				// Re-init each time
				dateTimeFragmentHA.startAtCalendarView();
				dateTimeFragmentHA.setDefaultDateTime(new GregorianCalendar(2017, Calendar.MARCH, 4, 15, 20).getTime());
				dateTimeFragmentHA.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
			}
		});

		saveButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

			}
		});

	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		// Save the current textView
		savedInstanceState.putCharSequence(STATE_TEXTVIEW, textViewHA.getText());
		super.onSaveInstanceState(savedInstanceState);
	}
}
