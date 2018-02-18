package DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
		SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");


		SQLiteDatabase db = getWritableDatabase();

		db.execSQL(String.format("INSERT INTO Mouvement (numeroVol, distance, nbPassagers, estIntracom, dateHeureDepart, dateHeureArrivee, dureeVol, AeroportDepart_oaci, AeroportArrivee_oaci, Avion_id) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s' )",
				o.getNumeroVol(),
				o.getDistance(),
				o.getNbPassagers(),
				0,
				dateFormatter.format(o.getDateHeureDepart().getTime()),
				dateFormatter.format(o.getDateHeureArrivee().getTime()),
				o.getDureeVol(),
				o.getAeroportDepart().getOaci(),
				o.getAeroportArrivee().getOaci(),
				o.getAvionUtilise().getId()));

		return true;
	}

	@Override
	public boolean update(Mouvement o) throws Exception
	{
		return false;
	}

	@Override
	public boolean delete(Mouvement o) throws Exception
	{

		SQLiteDatabase db = getWritableDatabase();



		if (o.getLesRetards().size() > 0)
		{
			db.delete("Retard", String.format("Mouvement_id = %s", String.valueOf(o.getId())), null);
		}




		return db.delete("Mouvement", String.format("id = %s", String.valueOf(o.getId())), null) > 0;
	}


	@Override
	public boolean erase() throws Exception
	{
		SQLiteDatabase db = getWritableDatabase();

		//db.execSQL("DELETE FROM TypeRetard");

		return db.delete("Mouvement", null, null) > 0;
	}

	@Override
	public ArrayList<Mouvement> getAll() throws Exception
	{
		ArrayList<Mouvement> mListe = new ArrayList<Mouvement>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Calendar dateHeureDepart = Calendar.getInstance();
		Calendar dateHeureArrivee = Calendar.getInstance();
		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM Mouvement;", null);

		try
		{
			if (cursor.moveToFirst())
			{
				do
				{
					ArrayList<Retard> rListe = new RetardDAO(super.context.getApplicationContext()).getAll("Mouvement_id", cursor.getString(cursor.getColumnIndex("id")));
					Avion avion = new AvionDAO(super.context.getApplicationContext()).get(cursor.getInt(cursor.getColumnIndex("Avion_id")));
					Aeroport aeroportDepart = new AeroportDAO(super.context.getApplicationContext()).get(cursor.getString(cursor.getColumnIndex("AeroportDepart_oaci")));
					Aeroport aeroportArrivee = new AeroportDAO(super.context.getApplicationContext()).get(cursor.getString(cursor.getColumnIndex("AeroportArrivee_oaci")));

					dateHeureDepart.setTime(dateFormatter.parse(cursor.getString(cursor.getColumnIndex("dateHeureDepart"))));
					dateHeureArrivee.setTime(dateFormatter.parse(cursor.getString(cursor.getColumnIndex("dateHeureArrivee"))));


					Mouvement mouvement = new Mouvement(
							cursor.getInt(cursor.getColumnIndex("id")),
							cursor.getString(cursor.getColumnIndex("numeroVol")),
							dateHeureDepart,
							dateHeureArrivee,
							cursor.getInt(cursor.getColumnIndex("dureeVol")),
							avion,
							aeroportDepart,
							aeroportArrivee
					);

					if(!rListe.isEmpty())
						mouvement = feedRetard(mouvement, rListe);


					mListe.add(mouvement);
				} while(cursor.moveToNext());
			}
		} catch (SQLException e)
		{
			Log.e("MVDAO", "Error while getting data from BDD");
		} finally
		{
			cursor.close();
		}

		return mListe;
	}

	@Override
	public ArrayList<Mouvement> getAll(String clause, String value) throws Exception
	{
		ArrayList<Mouvement> mListe = new ArrayList<Mouvement>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Calendar dateHeureDepart = Calendar.getInstance();
		Calendar dateHeureArrivee = Calendar.getInstance();
		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(String.format("SELECT * FROM Mouvement WHERE %s='%s';", clause, value), null);

		try
		{
			if (cursor.moveToFirst())
			{
				do
				{
					ArrayList<Retard> rListe = new RetardDAO(super.context.getApplicationContext()).getAll("Mouvement_id", cursor.getString(cursor.getColumnIndex("id")));
					Avion avion = new AvionDAO(super.context.getApplicationContext()).get(cursor.getColumnIndex("Avion_id"));
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

					if(!rListe.isEmpty())
						mouvement = feedRetard(mouvement, rListe);

					mListe.add(mouvement);
				} while(cursor.moveToNext());
			}
		} catch (SQLException e)
		{
			Log.e("MVDAO", "Error while getting data from BDD");
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
		SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Calendar dateHeureDepart = Calendar.getInstance();
		Calendar dateHeureArrivee = Calendar.getInstance();
		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(String.format("SELECT * FROM Mouvement WHERE id='%s';", id), null);

		try
		{
			if (cursor.moveToFirst())
			{
				ArrayList<Retard> rListe = new RetardDAO(super.context.getApplicationContext()).getAll("Mouvement_id", cursor.getString(cursor.getColumnIndex("id")));
				Avion avion = new AvionDAO(super.context.getApplicationContext()).get(cursor.getColumnIndex("Avion_id"));
				Aeroport aeroportDepart = new AeroportDAO(super.context.getApplicationContext()).get(cursor.getInt(cursor.getColumnIndex("AeroportDepart_oaci")));
				Aeroport aeroportArrivee = new AeroportDAO(super.context.getApplicationContext()).get(cursor.getInt(cursor.getColumnIndex("AeroportArrivee_oaci")));
				dateHeureDepart.setTime(dateFormatter.parse(cursor.getString(cursor.getColumnIndex("dateHeureDepart"))));
				dateHeureArrivee.setTime(dateFormatter.parse(cursor.getString(cursor.getColumnIndex("dateHeureArrivee"))));


				mouvement = new Mouvement(
						cursor.getInt(cursor.getColumnIndex("id")),
						cursor.getString(cursor.getColumnIndex("numeroVol")),
						dateHeureDepart,
						dateHeureArrivee,
						cursor.getInt(cursor.getColumnIndex("dureeVol")),
						avion,
						aeroportDepart,
						aeroportArrivee
				);

				if(!rListe.isEmpty())
					mouvement = feedRetard(mouvement, rListe);
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

	private Mouvement feedRetard(Mouvement m, ArrayList<Retard> rListe)
	{
		for (Retard r : rListe)
		{
			m.ajouteRetard(r);
		}
		return  m;
	}
}
