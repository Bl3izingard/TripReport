package DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import aeroplan.Avion;

/**
 * Created by bleizingard on 29/01/18.
 */

public class AvionDAO extends DAO.DAOSqlLite<Avion>
{

	public AvionDAO(Context context)
	{
		super(context);
	}

	@Override
	public boolean add(Avion o) throws Exception
	{
		return false;
	}

	@Override
	public boolean update(Avion o) throws Exception
	{
		return false;
	}

	@Override
	public boolean delete(Avion o) throws Exception
	{
		return false;
	}

	@Override
	public boolean erase() throws Exception
	{
		SQLiteDatabase db = getWritableDatabase();

		//db.execSQL("DELETE FROM TypeRetard");

		return db.delete("Avion", null, null) > 0;
	}

	@Override
	public ArrayList<Avion> getAll() throws Exception
	{
		ArrayList<Avion> liste = new ArrayList<Avion>();

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM Avion;", null);

		try
		{
			if(cursor.moveToFirst())
			{
				do
				{
					liste.add(new Avion(
							cursor.getInt(cursor.getColumnIndex("id")),
							cursor.getString(cursor.getColumnIndex("modele")),
							cursor.getString(cursor.getColumnIndex("numeroSerie")),
							cursor.getString(cursor.getColumnIndex("codeInterne"))
					));
				} while(cursor.moveToNext());
			}
		}
		catch (SQLException e)
		{
			Log.d("AVDAO","Error while getting data from BDD");
		}
		finally
		{
			cursor.close();
		}

		return liste;
	}

	@Override
	public ArrayList<Avion> getAll(String clause, String value) throws Exception
	{
		ArrayList<Avion> liste = new ArrayList<Avion>();

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(String.format("SELECT * FROM Avion WHERE %s='%s';", clause, value), null);

		try
		{
			if(cursor.moveToFirst())
			{
				do
				{
					liste.add(new Avion(
							cursor.getInt(cursor.getColumnIndex("id")),
							cursor.getString(cursor.getColumnIndex("modele")),
							cursor.getString(cursor.getColumnIndex("numeroSerie")),
							cursor.getString(cursor.getColumnIndex("codeInterne"))
					));
				} while(cursor.moveToNext());
			}
		}
		catch (SQLException e)
		{
			Log.d("AVDAO","Error while getting data from BDD");
		}
		finally
		{
			cursor.close();
		}

		return liste;
	}

	@Override
	public Avion get(int id) throws Exception
	{
		Avion avion = null;
		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(String.format("SELECT * FROM Avion WHERE id='%s';", id), null);

		try
		{
			if(cursor.moveToFirst())
			{
				avion = new Avion(
						cursor.getInt(cursor.getColumnIndex("id")),
						cursor.getString(cursor.getColumnIndex("modele")),
						cursor.getString(cursor.getColumnIndex("numeroSerie")),
						cursor.getString(cursor.getColumnIndex("codeInterne"))
				);
			}
		}
		catch (SQLException e)
		{
			Log.d("TRDAO","Error while getting data from BDD");
		}
		finally
		{
			cursor.close();
		}

		return avion;
	}
}
