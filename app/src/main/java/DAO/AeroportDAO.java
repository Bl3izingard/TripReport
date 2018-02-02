package DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import aeroplan.Aeroport;

/**
 * Created by bleizingard on 29/01/18.
 */

public class AeroportDAO extends DAO.DAOSqlLite<Aeroport>
{

	public AeroportDAO(Context context)
	{
		super(context);
	}

	@Override
	public boolean add(Aeroport o) throws Exception
	{
		return false;
	}

	@Override
	public boolean update(Aeroport o) throws Exception
	{
		return false;
	}

	@Override
	public boolean delete(Aeroport o) throws Exception
	{
		return false;
	}

	@Override
	public boolean erase() throws Exception
	{
		SQLiteDatabase db = getWritableDatabase();

		//db.execSQL("DELETE FROM TypeRetard");

		return db.delete("Aeroport", null, null) > 0;
	}

	@Override
	public ArrayList<Aeroport> getAll() throws Exception
	{
		ArrayList<Aeroport> liste = new ArrayList<Aeroport>();

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM Aeroport;", null);

		try
		{
			if(cursor.moveToFirst())
			{
				do
				{
					liste.add(new Aeroport(
							cursor.getString(cursor.getColumnIndex("oaci")),
							cursor.getString(cursor.getColumnIndex("aita")),
							cursor.getString(cursor.getColumnIndex("nom")),
							cursor.getFloat(cursor.getColumnIndex("latitude")),
							cursor.getFloat(cursor.getColumnIndex("longitude"))
					));
				} while(cursor.moveToNext());
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

		return liste;
	}

	@Override
	public ArrayList<Aeroport> getAll(String clause, String value) throws Exception
	{
		ArrayList<Aeroport> liste = new ArrayList<Aeroport>();

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(String.format("SELECT * FROM Aeroport WHERE %s='%s';", clause, value), null);

		try
		{
			if(cursor.moveToFirst())
			{
				do
				{
					liste.add(new Aeroport(
							cursor.getString(cursor.getColumnIndex("oaci")),
							cursor.getString(cursor.getColumnIndex("aita")),
							cursor.getString(cursor.getColumnIndex("nom")),
							cursor.getFloat(cursor.getColumnIndex("latitude")),
							cursor.getFloat(cursor.getColumnIndex("longitude"))
					));
				} while(cursor.moveToNext());
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

		return liste;
	}

	@Override
	public Aeroport get(int id) throws Exception
	{
		Aeroport aeroport = null;
		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(String.format("SELECT * FROM Aeroport WHERE id='%s';", id), null);

		try
		{
			if(cursor.moveToFirst())
			{
				aeroport = new Aeroport(
						cursor.getString(cursor.getColumnIndex("oaci")),
						cursor.getString(cursor.getColumnIndex("aita")),
						cursor.getString(cursor.getColumnIndex("nom")),
						cursor.getFloat(cursor.getColumnIndex("latitude")),
						cursor.getFloat(cursor.getColumnIndex("longitude"))
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

		return aeroport;
	}

	public Aeroport get(String oaci) throws Exception
	{
		Aeroport aeroport = null;
		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(String.format("SELECT * FROM Aeroport WHERE oaci='%s';", oaci), null);

		try
		{
			if(cursor.moveToFirst())
			{
				aeroport = new Aeroport(
						cursor.getString(cursor.getColumnIndex("oaci")),
						cursor.getString(cursor.getColumnIndex("aita")),
						cursor.getString(cursor.getColumnIndex("nom")),
						cursor.getFloat(cursor.getColumnIndex("latitude")),
						cursor.getFloat(cursor.getColumnIndex("longitude"))
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

		return aeroport;
	}
}
