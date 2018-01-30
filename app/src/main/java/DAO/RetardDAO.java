package DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import aeroplan.Retard;
import aeroplan.TypeRetard;

/**
 * Created by bleizingard on 29/01/18.
 */

public class RetardDAO extends DAOSqlLite<Retard>
{


	public RetardDAO(Context context)
	{
		super(context);
	}

	@Override
	public boolean add(Retard o) throws Exception
	{
		return false;
	}

	@Override
	public boolean update(Retard o) throws Exception
	{
		return false;
	}

	@Override
	public boolean delete(Retard o) throws Exception
	{
		return false;
	}

	@Override
	public ArrayList<Retard> getAll() throws Exception
	{
		ArrayList<Retard> liste = new ArrayList<Retard>();

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM Retard;", null);

		try
		{
			if(cursor.moveToFirst())
			{
				do
				{
					TypeRetard typeRetard = new TypeRetardDAO(super.context.getApplicationContext()).get(cursor.getInt(cursor.getColumnIndex("id")));

					liste.add(new Retard(
							cursor.getInt(cursor.getColumnIndex("id")),
							cursor.getString(cursor.getColumnIndex("commentaire")),
							typeRetard,
							cursor.getInt(cursor.getColumnIndex("duree")),
							cursor.getInt(cursor.getColumnIndex("impliqueAeroport")) > 0
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
	public ArrayList<Retard> getAll(String clause, String value) throws Exception
	{
		ArrayList<Retard> liste = new ArrayList<Retard>();

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(String.format("SELECT * FROM Retard WHERE %s='%s';", clause, value), null);

		try
		{
			if(cursor.moveToFirst())
			{
				do
				{
					TypeRetard typeRetard = new TypeRetardDAO(super.context.getApplicationContext()).get(cursor.getInt(cursor.getColumnIndex("id")));

					liste.add(new Retard(
							cursor.getInt(cursor.getColumnIndex("id")),
							cursor.getString(cursor.getColumnIndex("commentaire")),
							typeRetard,
							cursor.getInt(cursor.getColumnIndex("duree")),
							cursor.getInt(cursor.getColumnIndex("impliqueAeroport")) > 0
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
	public Retard get(int id) throws Exception
	{
		Retard retard = null;

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(String.format("SELECT * FROM Retard WHERE id='%s';", id), null);

		try
		{
			if(cursor.moveToFirst())
			{
				TypeRetard typeRetard = new TypeRetardDAO(super.context.getApplicationContext()).get(cursor.getInt(cursor.getColumnIndex("id")));

				retard = new Retard(
						cursor.getInt(cursor.getColumnIndex("id")),
						cursor.getString(cursor.getColumnIndex("commentaire")),
						typeRetard,
						cursor.getInt(cursor.getColumnIndex("duree")),
						cursor.getInt(cursor.getColumnIndex("impliqueAeroport")) > 0
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

		return retard;
	}
}
