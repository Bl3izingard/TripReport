package fr.suid.tripreport2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import DAO.DAOSqlLite;
import DAO.MouvementDAO;
import aeroplan.Mouvement;

public class HomeActivity extends AppCompatActivity
{

	private ListView mouvementListView;
	private MouvementAdapter mouvementAdapter;
	private ArrayList<Mouvement> mListe;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);



		setContentView(R.layout.activity_home);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent i = new Intent(HomeActivity.this, MouvementFormActivity.class);

				startActivityForResult(i, 1);
			}
		});

		mouvementListView = findViewById(R.id.listViewMouvement);

		//Récupération de la liste des mouvements depuis le SERVEUR PRINCIPAL
		try
		{
			mListe = new MouvementDAO(this.getApplicationContext()).getAll();
			mouvementAdapter = new MouvementAdapter(this, R.layout.activity_mouvement_list, mListe);
			mouvementListView.setAdapter(mouvementAdapter);

			mouvementListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
					alertDialog.setTitle("Etes vous sûr ?");
					alertDialog.setMessage("Etes vous sûr de vouloir supprimer ce mouvement ?");
					final int pos = position;
					alertDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int which) {
							final int pos = position;
							try
							{
								new MouvementDAO(getApplicationContext()).delete(mListe.get(pos));
								mListe.remove(pos);
								mouvementAdapter.notifyDataSetChanged();

							} catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					});

					alertDialog.setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});

					alertDialog.show();

					return true;
					}
			});


			mouvementListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{
					Intent i = new Intent(HomeActivity.this, RetardActivity.class);

					i.putExtra("idMouvement", mListe.get(position).getId());

					startActivityForResult(i, 2);
				}
			});
		}
		catch (Exception e)
		{
			Log.e("HERROR", e.toString());
		}

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		Toast t = null;
		switch (requestCode)
		{
			case 1:
				//Ajout d'un mouvement
				if(resultCode == 1)
				{
					t = Toast.makeText(getApplicationContext(), getString(R.string.succes_ajout_mouvement), Toast.LENGTH_LONG);

					mouvementListView = findViewById(R.id.listViewMouvement);

					//Récupération de la liste des mouvements depuis le SERVEUR PRINCIPAL
					try
					{

						mListe.clear();

						for (Mouvement m : new MouvementDAO(this.getApplicationContext()).getAll())
						{
							mListe.add(m);
						}

/*
						mouvementListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
							@Override
							public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
								return true;
							}
						});

						mouvementListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
						{
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id)
							{
								Intent i = new Intent(HomeActivity.this, RetardActivity.class);

								i.putExtra("idMouvement", mListe.get(position).getId());

								startActivityForResult(i, 2);
							}
						});
						*/
						t.show();

						mouvementAdapter.notifyDataSetChanged();
					}
					catch (Exception e)
					{
						Log.e("HERROR", e.toString());
					}

				}
				else if (resultCode == 0)
				{
					;
				}
				else
				{
					t = Toast.makeText(getApplicationContext(), getString(R.string.erreur_ajout_mouvement), Toast.LENGTH_LONG);
					t.show();
				}
			case 2:
				break;
		}

	}
}
