package DAO;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import fr.suid.tripreport2.R;

import static android.content.ContentValues.TAG;

/**
 * Created by bleizingard on 25/01/18.
 */

public abstract class DAOSqlLite<T> extends SQLiteOpenHelper
{
	private static final String DB_NAME = "tripreport";

	private static final int DB_VERSION = 4;

	protected DAOSqlLite<T> sInstance;

	protected Context context;

	public DAOSqlLite(Context context)
	{
		super(context, DB_NAME, null, DB_VERSION);

		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database)
	{
		InputStream is = null;
		String line;
		StringBuilder sb = new StringBuilder();

		try
		{
			is = context.getResources().openRawResource(R.raw.createbdd);

			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			while ((line = rd.readLine()) != null)
			{
				sb.append(line);
			}

			rd.close();

			for (String query: sb.toString().split(";"))
			{
				database.execSQL(query);
			}

		} catch (IOException e)
		{
			Log.e(TAG, "Error loading init SQL from raw", e);
		} catch (SQLException e)
		{
			Log.e(TAG, "Error executing init SQL", e);
		} finally
		{
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		if (oldVersion < newVersion)
		{
			InputStream is = null;
			String line;
			StringBuilder sb = new StringBuilder();

			try
			{
				is = context.getResources().openRawResource(R.raw.deletebdd);

				BufferedReader rd = new BufferedReader(new InputStreamReader(is));

				while ((line = rd.readLine()) != null)
				{
					sb.append(line);
				}

				rd.close();

				for (String query: sb.toString().split(";"))
				{
					db.execSQL(query);
				}


				onCreate(db);
			} catch (IOException e)
			{
				Log.e(TAG, "Error executing IO", e);
			} catch (SQLException e)
			{
				Log.e(TAG, "Error executing init SQL", e);
			} finally
			{
			}

		}
	}


	public abstract boolean add(T o) throws Exception;
	public abstract boolean update(T o) throws Exception;
	public abstract boolean delete(T o) throws Exception;
	public abstract ArrayList<T> getAll() throws Exception;
	public abstract ArrayList<T> getAll(String clause, String value) throws Exception;
	public abstract T get(int id) throws Exception;

}
