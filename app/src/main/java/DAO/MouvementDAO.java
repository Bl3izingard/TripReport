package DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import aeroplan.Aeroport;
import aeroplan.Avion;
import aeroplan.Mouvement;
import aeroplan.Retard;

/**
 * Created by bleizingard on 29/01/18.
 */

public class MouvementDAO extends DAOSqlLite<Mouvement>
{

	public MouvementDAO(Context context)
	{
		super(context);
	}

	@Override
	public boolean add(Mouvement o) throws Exception
	{
		return false;
	}

	@Override
	public boolean update(Mouvement o) throws Exception
	{
		return false;
	}

	@Override
	public boolean delete(Mouvement o) throws Exception
	{
		return false;
	}

	@Override
	public ArrayList<Mouvement> getAll() throws Exception
	{
		ArrayList<Mouvement> mListe = null;
		SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		Calendar dateHeureDepart = Calendar.getInstance();
		Calendar dateHeureArrivee = Calendar.getInstance();
		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM Mouvement;", null);

		try
		{
			if (cursor.moveToFirst())
			{
				ArrayList<Retard> rListe = new RetardDAO(super.context.getApplicationContext()).getAll();
				Avion avion = new AvionDAO(super.context.getApplicationContext()).get(cursor.getColumnIndex("avionId"));
				Aeroport aeroportDepart = new AeroportDAO(super.context.getApplicationContext()).get(cursor.getInt(cursor.getColumnIndex("AeroportDepart_oaci")));
				Aeroport aeroportArrivee = new AeroportDAO(super.context.getApplicationContext()).get(cursor.getInt(cursor.getColumnIndex("AeroportArrivee_oaci")));
				dateHeureDepart.setTime(dateFormatter.parse(cursor.getString(cursor.getColumnIndex("dateHeureDepart"))));
				dateHeureArrivee.setTime(dateFormatter.parse(cursor.getString(cursor.getColumnIndex("dateHeureArrivee"))));


				Mouvement mouvement = new Mouvement(
						cursor.getInt(cursor.getColumnIndex("id")),
						cursor.getString(cursor.getColumnIndex("numeroVol")),
						dateHeureDepart,
						dateHeureArrivee,
						cursor.getInt(cursor.getInt(cursor.getColumnIndex("dureeVol"))),
						avion,
						aeroportDepart,
						aeroportArrivee
				);

				feedRetard(mouvement, rListe);

				mListe.add(mouvement);
			}
		} catch (SQLException e)
		{
			Log.d("MVDAO", "Error while getting data from BDD");
		} finally
		{
			cursor.close();
		}

		return mListe;
	}

	@Override
	public ArrayList<Mouvement> getAll(String clause, String value) throws Exception
	{
		ArrayList<Mouvement> mListe = null;
		SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss\"");
		Calendar dateHeureDepart = Calendar.getInstance();
		Calendar dateHeureArrivee = Calendar.getInstance();
		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(String.format("SELECT * FROM Mouvement WHERE %s='%s';", clause, value), null);

		try
		{
			if (cursor.moveToFirst())
			{
				ArrayList<Retard> rListe = new RetardDAO(super.context.getApplicationContext()).getAll();
				Avion avion = new AvionDAO(super.context.getApplicationContext()).get(cursor.getColumnIndex("avionId"));
				Aeroport aeroportDepart = new AeroportDAO(super.context.getApplicationContext()).get(cursor.getInt(cursor.getColumnIndex("AeroportDepart_oaci")));
				Aeroport aeroportArrivee = new AeroportDAO(super.context.getApplicationContext()).get(cursor.getInt(cursor.getColumnIndex("AeroportArrivee_oaci")));
				dateHeureDepart.setTime(dateFormatter.parse(cursor.getString(cursor.getColumnIndex("dateHeureDepart"))));
				dateHeureArrivee.setTime(dateFormatter.parse(cursor.getString(cursor.getColumnIndex("dateHeureArrivee"))));


				Mouvement mouvement = new Mouvement(
						cursor.getInt(cursor.getColumnIndex("id")),
						cursor.getString(cursor.getColumnIndex("numeroVol")),
						dateHeureDepart,
						dateHeureArrivee,
						cursor.getInt(cursor.getInt(cursor.getColumnIndex("dureeVol"))),
						avion,
						aeroportDepart,
						aeroportArrivee
				);

				feedRetard(mouvement, rListe);

				mListe.add(mouvement);
			}
		} catch (SQLException e)
		{
			Log.d("MVDAO", "Error while getting data from BDD");
		} finally
		{
			cursor.close();
		}

		return mListe;
	}

	@Override
	public Mouvement get(int id) throws Exception
	{
		Mouvement mouvement = null;
		SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		Calendar dateHeureDepart = Calendar.getInstance();
		Calendar dateHeureArrivee = Calendar.getInstance();
		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(String.format("SELECT * FROM Mouvement WHERE id='%s';", id), null);

		try
		{
			if (cursor.moveToFirst())
			{
				ArrayList<Retard> rListe = new RetardDAO(super.context.getApplicationContext()).getAll();
				Avion avion = new AvionDAO(super.context.getApplicationContext()).get(cursor.getColumnIndex("avionId"));
				Aeroport aeroportDepart = new AeroportDAO(super.context.getApplicationContext()).get(cursor.getInt(cursor.getColumnIndex("AeroportDepart_oaci")));
				Aeroport aeroportArrivee = new AeroportDAO(super.context.getApplicationContext()).get(cursor.getInt(cursor.getColumnIndex("AeroportArrivee_oaci")));
				dateHeureDepart.setTime(dateFormatter.parse(cursor.getString(cursor.getColumnIndex("dateHeureDepart"))));
				dateHeureArrivee.setTime(dateFormatter.parse(cursor.getString(cursor.getColumnIndex("dateHeureArrivee"))));


				mouvement = new Mouvement(
						cursor.getInt(cursor.getColumnIndex("id")),
						cursor.getString(cursor.getColumnIndex("numeroVol")),
						dateHeureDepart,
						dateHeureArrivee,
						cursor.getInt(cursor.getInt(cursor.getColumnIndex("dureeVol"))),
						avion,
						aeroportDepart,
						aeroportArrivee
				);

				feedRetard(mouvement, rListe);
			}
		} catch (SQLException e)
		{
			Log.d("MVDAO", "Error while getting data from BDD");
		} finally
		{
			cursor.close();
		}

		return mouvement;
	}

	private void feedRetard(Mouvement m, ArrayList<Retard> rListe)
	{
		for (Retard r : rListe)
		{
			m.ajouteRetard(r);
		}
	}
}
