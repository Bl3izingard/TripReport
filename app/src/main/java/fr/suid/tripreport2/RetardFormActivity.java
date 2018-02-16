package fr.suid.tripreport2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import DAO.RetardDAO;
import DAO.TypeRetardDAO;
import aeroplan.Mouvement;
import aeroplan.Retard;
import aeroplan.TypeRetard;

public class RetardFormActivity extends AppCompatActivity
{
	private int idMouvement;

	//editText
	private EditText editTextCommentaire;
	private EditText editTextDuree;

	//
	private Spinner spinnerTypeRetard;
	private Switch switchImpliqueAeroport;

	private Button saveButton;

	private ArrayList<TypeRetard> typeRetardArrayList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retard_form);

		idMouvement = getIntent().getIntExtra("idMouvement", 0);

		try
		{
			typeRetardArrayList = new TypeRetardDAO(this).getAll();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		editTextCommentaire = findViewById(R.id.editTextFormCommentaire);
		editTextDuree = findViewById(R.id.editTextFormDuree);
		switchImpliqueAeroport = findViewById(R.id.switchFormImplqueAeroport);
		spinnerTypeRetard = findViewById(R.id.spinnerFormTypeRetard);

		saveButton = findViewById(R.id.buttonRetardSave);

		ArrayList<String> list = new ArrayList<String>();
		for (TypeRetard typeRetard: typeRetardArrayList)
		{
			list.add(typeRetard.getLibelle());
		}

		spinnerTypeRetard.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list));

		saveButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String toastMessage = null;

				//Vérification que les champs soient remplies
				if(editTextCommentaire.getText().length() == 0)
				{
					editTextCommentaire.requestFocusFromTouch();

					toastMessage = getString(R.string.commentaire);
				}
				else if(editTextDuree.getText().length() == 0)
				{
					editTextDuree.requestFocusFromTouch();

					toastMessage = getString(R.string.duree);
				}


				//Envoie du Toast
				if(toastMessage != null)
				{
					Toast t = Toast.makeText(getApplicationContext(), getString(R.string.toast_champs) + " " + toastMessage + " " + getString(R.string.toast_not_empty), Toast.LENGTH_LONG);
					t.show();
				}
				else
				{
					String commentaire = editTextCommentaire.getText().toString();
					int duree = Integer.parseInt(editTextDuree.getText().toString());
					boolean impliqueAeroport = switchImpliqueAeroport.isChecked();
					TypeRetard typeRetard = typeRetardArrayList.get((int) spinnerTypeRetard.getSelectedItemId());
					try
					{
						new RetardDAO(getApplicationContext()).add(new Retard(0, commentaire, typeRetard, duree, impliqueAeroport), idMouvement);

						setResult(1, null);
						//finish must be declared here to send the result to parent activity
						finish();

					} catch (Exception e)
					{
						Toast t = Toast.makeText(getApplicationContext(), getString(R.string.erreur_maj_SQLite), Toast.LENGTH_LONG);
						t.show();
					}

				}

			}
		});
	}
}
