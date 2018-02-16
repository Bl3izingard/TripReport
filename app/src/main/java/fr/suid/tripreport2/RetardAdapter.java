package fr.suid.tripreport2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.List;

import aeroplan.Retard;

/**
 * Created by bleizingard on 31/01/18.
 */

public class RetardAdapter extends ArrayAdapter<Retard>
{


	public RetardAdapter(@NonNull Context context, int resource, @NonNull List<Retard> objects)
	{
		super(context, resource, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(convertView == null)
		{
			convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.activity_retard_list, parent, false);
		}

		RetardViewHolder viewHolder = (RetardViewHolder) convertView.getTag();


		if(viewHolder == null)
		{
			viewHolder = new RetardViewHolder();
			viewHolder.mTextViewIdRetard = (TextView) convertView.findViewById(R.id.mTextViewListeIdRetard);
			viewHolder.mTextViewCommentaire= (TextView) convertView.findViewById(R.id.mTextViewListeCommentaire);
			viewHolder.mTextViewImpliqueAeroport = (TextView) convertView.findViewById(R.id.mTextViewListeImpliqueAeroport);
			viewHolder.mTextViewDuree = (TextView) convertView.findViewById(R.id.mTextViewListeDuree);
			viewHolder.mTextViewTypeRetard = (TextView) convertView.findViewById(R.id.mTextViewListeTypeRetard);

			convertView.setTag(viewHolder);
		}

		//getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
		Retard retard = getItem(position);



		if(retard != null)
		{
			viewHolder.mTextViewIdRetard.setText(String.valueOf(position));
			viewHolder.mTextViewCommentaire.setText(retard.getCommentaire());
			viewHolder.mTextViewDuree.setText(String.valueOf(retard.getDuree()));
			viewHolder.mTextViewTypeRetard.setText(retard.getType().getLibelle());
			if(retard.getImpliqueAeroport())
			{
				viewHolder.mTextViewImpliqueAeroport.setText(R.string.aeroport_implique);
			}
			else
			{
				viewHolder.mTextViewImpliqueAeroport.setText(R.string.aeroport_non_implique);
			}
		}


		return convertView;
	}

	private class RetardViewHolder
	{
		public TextView mTextViewIdRetard;
		public TextView mTextViewCommentaire;
		public TextView mTextViewDuree;
		public TextView mTextViewImpliqueAeroport;
		public TextView mTextViewTypeRetard;
	}
}