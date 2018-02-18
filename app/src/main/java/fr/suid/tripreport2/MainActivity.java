package fr.suid.tripreport2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import DAO.AeroportDAO;
import DAO.AvionDAO;
import DAO.TypeRetardDAO;
import aeroplan.Aeroport;
import aeroplan.Avion;
import aeroplan.TypeRetard;

public class MainActivity extends AppCompatActivity
{

	private static int TripReport_TIME_OUT = 2000;
	private static String URL_VALUE = "http://91.121.67.131:8081/tripreport/getlist.php";
	private static String URL_VALUE_UPLOAD = "http://91.121.67.131:8081/tripreport/putdatas.php";
	private static int URL_PORT = 8081;
	// Progress Dialog
	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Passage à la vue suivante pas de retour possible
		Intent intent = new Intent(MainActivity.this, HomeActivity.class);

		/*
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
				startActivity(homeIntent);
				finish();
			}
		}, TripReport_TIME_OUT);
		*/

		//Récupération des données
		try
		{
			URL url = new URL(URL_VALUE);

			//Récupération des données depuis le serveur maitre
			JSONObject data = new DownloadDataTask().execute(url).get();

			/* Mise à jour des données SQLite */
			if(data != null)
			{
				/* REMPLACEMENT DES DONNEES LOCALES */

				JSONArray jsonArrayArray = (JSONArray) data.get("TypeRetard");
				JSONObject row = null;

				for(int i=0; i < jsonArrayArray.length(); i++)
				{
					row = jsonArrayArray.getJSONObject(i);
					TypeRetard typeRetard = new TypeRetard(row.getInt("id"), row.getString("codeSituation"), row.getString("nom"));

					if(!new TypeRetardDAO(this).add(typeRetard))
					{
						Toast t = Toast.makeText(getApplicationContext(), R.string.erreur_maj_SQLite, Toast.LENGTH_LONG);
						t.show();
					}

				}


				jsonArrayArray = (JSONArray) data.get("Aeroport");

				for(int i=0; i < jsonArrayArray.length(); i++)
				{
					row = jsonArrayArray.getJSONObject(i);

					Aeroport aeroport = new Aeroport(row.getInt("id"), row.getString("oaci"), row.getString("aita"), row.getString("nom"), ((float) row.getLong("latitude")), ((float) row.getLong("longitude")));

					if(!new AeroportDAO(this).add(aeroport))
					{
						Toast t = Toast.makeText(getApplicationContext(), R.string.erreur_maj_SQLite, Toast.LENGTH_LONG);
						t.show();
					}

				}

				jsonArrayArray = (JSONArray) data.get("Avion");

				for(int i=0; i < jsonArrayArray.length(); i++)
				{
					row = jsonArrayArray.getJSONObject(i);

					Avion avion = new Avion(row.getInt("id"), row.getString("modele"), row.getString("numeroSerie"), row.getString("codeInterne"));

					if(!new AvionDAO(this).add(avion))
					{
						Toast t = Toast.makeText(getApplicationContext(), R.string.erreur_maj_SQLite, Toast.LENGTH_LONG);
						t.show();
					}

				}
			}
			else
			{
				Toast t = Toast.makeText(getApplicationContext(), R.string.erreur_internet, Toast.LENGTH_LONG);
				t.show();
			}
			
		} catch (MalformedURLException e)
		{
			Log.e("MainActivity", e.toString());
		} catch (IOException e)
		{
			Log.e("MainActivity", e.toString());
		} catch (InterruptedException e)
		{
			Log.e("MainActivity", e.toString());
		} catch (ExecutionException e)
		{
			Log.e("MainActivity", e.toString());
		} catch (JSONException e)
		{
			Log.e("MainActivity", e.toString());
		} catch (Exception e)
		{
			Log.e("MainActivity", e.toString());
		}

		startActivity(intent);
		finish();
	}


	private class DownloadDataTask extends AsyncTask<URL, Integer, JSONObject>
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Chargement des catégories... Veuillez patienter.");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected JSONObject doInBackground(URL... urls)
		{
			if (isOnline())
			{
				URL url = urls[0];
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost request = new HttpPost();
				HttpResponse response = null;

				try
				{
					request.setURI(url.toURI());
					response = httpClient.execute(request);
					BufferedReader reader = new BufferedReader
							(new InputStreamReader(response.getEntity().getContent()));

					StringBuilder sb = new StringBuilder();
					String line = null;

					// Read Server Response
					while ((line = reader.readLine()) != null)
					{
						sb.append(line);
						break;
					}

					return new JSONObject(sb.toString());
				} catch (IOException e)
				{
					Log.e("MainActivityAsyncTask", e.toString());
				} catch (URISyntaxException e)
				{
					Log.e("MainActivityAsyncTask", e.toString());
				} catch (JSONException e)
				{
					Log.e("MainActivityAsyncTask", e.toString());
				}
			}

			return null;

		}

		protected void onPostExecute()
		{
			// dismiss the dialog after getting all products
			pDialog.dismiss();

		}

		public boolean isOnline()
		{
			ConnectivityManager cm =
					(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			return netInfo != null && netInfo.isConnectedOrConnecting();
		}
	}

	private class UploadDataTask extends AsyncTask<JSONObject, Integer, JSONObject>
	{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Sauvegarde des mouvements en cours...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected JSONObject doInBackground(URL url, JSONObject... arg)
		{
			if (isOnline())
			{


				HttpClient httpClient = new DefaultHttpClient();
				HttpPost request = new HttpPost();
				HttpResponse response = null;

				try
				{
					request.setURI(url.toURI());
					response = httpClient.execute(request);
					BufferedReader reader = new BufferedReader
							(new InputStreamReader(response.getEntity().getContent()));

					StringBuilder sb = new StringBuilder();
					String line = null;

					// Read Server Response
					while ((line = reader.readLine()) != null)
					{
						sb.append(line);
						break;
					}

					return new JSONObject(sb.toString());
				} catch (IOException e)
				{
					Log.e("MainActivityAsyncTask", e.toString());
				} catch (URISyntaxException e)
				{
					Log.e("MainActivityAsyncTask", e.toString());
				} catch (JSONException e)
				{
					Log.e("MainActivityAsyncTask", e.toString());
				}
			}

			return null;

		}

		@Override
		protected JSONObject doInBackground(JSONObject... arg)
		{
			try
			{
				return this.doInBackground(new URL(URL_VALUE_UPLOAD), arg);
			} catch (MalformedURLException e)
			{
				e.printStackTrace();
			}

			return null;
		}

		public boolean isOnline()
		{
			ConnectivityManager cm =
					(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			return netInfo != null && netInfo.isConnectedOrConnecting();
		}
	}
}
