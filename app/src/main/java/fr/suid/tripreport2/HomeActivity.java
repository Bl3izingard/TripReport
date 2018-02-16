package fr.suid.tripreport2;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import DAO.DAOSqlLite;
import DAO.MouvementDAO;
import aeroplan.Mouvement;

public class HomeActivity extends AppCompatActivity
{

	private ListView mouvementListView;


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
			final ArrayList<Mouvement> mListe = new MouvementDAO(this.getApplicationContext()).getAll();
			MouvementAdapter mouvementAdapter = new MouvementAdapter(this, R.layout.activity_mouvement_list, mListe);
			mouvementListView.setAdapter(mouvementAdapter);

			mouvementListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
					Intent i = new Intent(HomeActivity.this, RetardActivity.class);

					i.putExtra("idMouvement", mListe.get(position).getId());

					startActivityForResult(i, 1);

					return true;
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
						ArrayList<Mouvement> mListe = new MouvementDAO(this.getApplicationContext()).getAll();
						MouvementAdapter mouvementAdapter = new MouvementAdapter(this, R.layout.activity_mouvement_list, mListe);
						mouvementListView.setAdapter(mouvementAdapter);


						t.show();

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
		}

	}
}
