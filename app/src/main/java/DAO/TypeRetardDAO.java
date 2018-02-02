package DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import aeroplan.TypeRetard;

/**
 * Created by bleizingard on 29/01/18.
 */

public class TypeRetardDAO extends DAOSqlLite<TypeRetard>
{
	public TypeRetardDAO(Context context)
	{
		super(context);
	}

	@Override
	public boolean add(TypeRetard o) throws Exception
	{
		SQLiteDatabase db = getWritableDatabase();

		db.execSQL(String.format("REPLACE INTO TypeRetard (id, codeSituation, nom) VALUES (%s, '%s', '%s')", o.getId(), o.getCodeSituation(), o.getLibelle()));

		return true;
	}

	@Override
	public boolean update(TypeRetard o) throws Exception
	{
		return false;
	}

	@Override
	public boolean delete(TypeRetard o) throws Exception
	{
		return false;
	}

	@Override
	public boolean erase() throws Exception
	{
		SQLiteDatabase db = getWritableDatabase();

		//db.execSQL("DELETE FROM TypeRetard");

		return db.delete("TypeRetard", null, null) > 0;
	}

	@Override
	public ArrayList<TypeRetard> getAll() throws Exception
	{
		ArrayList<TypeRetard> liste = new ArrayList<TypeRetard>();

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM TypeRetard;", null);

		try
		{
			if (cursor.moveToFirst())
			{
				do
				{
					liste.add(new TypeRetard(
							cursor.getInt(cursor.getColumnIndex("id")),
							cursor.getString(cursor.getColumnIndex("nom")),
							cursor.getString(cursor.getColumnIndex("codeSituation"))
					));
				} while (cursor.moveToNext());
			}
		} catch (SQLException e)
		{
			Log.d("TRDAO", "Error while getting data from BDD");
		} finally
		{
			cursor.close();
		}

		return liste;
	}

	@Override
	public ArrayList<TypeRetard> getAll(String clause, String value) throws Exception
	{
		ArrayList<TypeRetard> liste = new ArrayList<TypeRetard>();

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(String.format("SELECT * FROM TypeRetard WHERE %s='%s';", clause, value), null);

		try
		{
			if (cursor.moveToFirst())
			{
				do
				{
					liste.add(new TypeRetard(
							cursor.getInt(cursor.getColumnIndex("id")),
							cursor.getString(cursor.getColumnIndex("nom")),
							cursor.getString(cursor.getColumnIndex("codeSituation"))
					));
				} while (cursor.moveToNext()) ;

			}

		} catch (SQLException e)
		{
			Log.d("TRDAO", "Error while getting data from BDD");
		} finally
		{
			cursor.close();
		}

		return liste;
	}

	@Override
	public TypeRetard get(int id) throws Exception
	{
		TypeRetard typeRetard = null;
		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(String.format("SELECT * FROM TypeRetard WHERE id='%s';", id), null);

		try
		{
			if (cursor.moveToFirst())
			{
				typeRetard = new TypeRetard(
						cursor.getInt(cursor.getColumnIndex("id")),
						cursor.getString(cursor.getColumnIndex("nom")),
						cursor.getString(cursor.getColumnIndex("codeSituation"))
				);
			}

		} catch (SQLException e)
		{
			Log.d("TRDAO", "Error while getting data from BDD");
		} finally
		{
			cursor.close();
		}

		return typeRetard;
	}
}
