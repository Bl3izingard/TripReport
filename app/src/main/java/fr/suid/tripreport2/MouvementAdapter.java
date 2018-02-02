package fr.suid.tripreport2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import aeroplan.Mouvement;

/**
 * Created by bleizingard on 31/01/18.
 */

public class MouvementAdapter extends ArrayAdapter<Mouvement>
{


	public MouvementAdapter(@NonNull Context context, int resource, @NonNull List<Mouvement> objects)
	{
		super(context, resource, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(convertView == null)
		{
			convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.activity_mouvement_list, parent, false);
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

		MouvementViewHolder viewHolder = (MouvementViewHolder) convertView.getTag();


		if(viewHolder == null)
		{
			viewHolder = new MouvementViewHolder();
			viewHolder.mListTextViewNumeroVol = (TextView) convertView.findViewById(R.id.mListTextViewNumeroVol);
			viewHolder.mListTextViewAeroportDepart = (TextView) convertView.findViewById(R.id.mListTextViewAeroportDepart);
			viewHolder.mListTextViewAeroportArrivee = (TextView) convertView.findViewById(R.id.mListTextViewAeroportArrivee);
			viewHolder.mListTextViewAvion = (TextView) convertView.findViewById(R.id.mListTextViewAvion);
			viewHolder.mListTextViewHeureDepart = (TextView) convertView.findViewById(R.id.mListTextViewHeureDepart);
			viewHolder.mListTextViewHeureArrivee = (TextView) convertView.findViewById(R.id.mListTextViewHeureArrivee);

			convertView.setTag(viewHolder);
		}

		//getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
		Mouvement mouvement = getItem(position);



		if(mouvement != null)
		{
			viewHolder.mListTextViewNumeroVol.setText(mouvement.getNumeroVol());
			viewHolder.mListTextViewAeroportDepart.setText(mouvement.getAeroportDepart().getOaci());
			viewHolder.mListTextViewAeroportArrivee.setText(mouvement.getAeroportArrivee().getOaci());
			viewHolder.mListTextViewAvion.setText(mouvement.getAvionUtilise().getNumeroSerie());
			viewHolder.mListTextViewHeureDepart.setText(dateFormat.format(mouvement.getDateHeureDepart().getTime()));
			viewHolder.mListTextViewHeureArrivee.setText(dateFormat.format(mouvement.getDateHeureArrivee().getTime()));
		}


		return convertView;
	}

	private class MouvementViewHolder
	{
		public TextView mListTextViewNumeroVol;
		public TextView mListTextViewAeroportDepart;
		public TextView mListTextViewAeroportArrivee;
		public TextView mListTextViewAvion;
		public TextView mListTextViewHeureDepart;
		public TextView mListTextViewHeureArrivee;
	}
}