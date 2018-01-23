package fr.suid.tripreport2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

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

	}

}
