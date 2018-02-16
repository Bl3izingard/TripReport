package fr.suid.tripreport2;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import DAO.MouvementDAO;
import DAO.RetardDAO;
import aeroplan.Mouvement;
import aeroplan.Retard;

public class RetardActivity extends AppCompatActivity
{

	private ListView retardListView;
	private int idMouvement;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retard);

		idMouvement = getIntent().getIntExtra("idMouvement", 0);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_retard);

		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_retard);
		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent i = new Intent(RetardActivity.this, RetardFormActivity.class);
				i.putExtra("idMouvement", idMouvement);
				startActivityForResult(i, 1);
			}
		});

		retardListView = findViewById(R.id.listViewRetard);

		//Récupération de la liste des mouvements depuis le SERVEUR PRINCIPAL
		try
		{
			ArrayList<Retard> rListe = new MouvementDAO(this.getApplicationContext()).get(this.idMouvement).getLesRetards();
			RetardAdapter retardAdapter = new RetardAdapter(this, R.layout.activity_retard_list, rListe);
			retardListView.setAdapter(retardAdapter);

		}
		catch (Exception e)
		{
			Log.e("RERROR", e.toString());
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

					retardListView = findViewById(R.id.listViewRetard);

					//Récupération de la liste des mouvements depuis le SERVEUR PRINCIPAL
					try
					{
						ArrayList<Retard> rListe = new MouvementDAO(this.getApplicationContext()).get(this.idMouvement).getLesRetards();
						RetardAdapter retardAdapter = new RetardAdapter(this, R.layout.activity_retard_list, rListe);
						retardListView.setAdapter(retardAdapter);

						t.show();

					}
					catch (Exception e)
					{
						Log.e("RERROR", e.toString());
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
