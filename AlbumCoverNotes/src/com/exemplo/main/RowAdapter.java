
/*
 * Por favor, ao usar, divulgar ou modificar, fazer referência ao material do autor.
 * 
 * Please, you can use, share or edit, but make reference to the author's material.
 * 
 * Je vous en prie, vous pouvez utiliser, partager et modifier, mais faites reference au materiel d'auteur.
 * 
 * Disponível/Available/Disponible em/in/à: 
 *     
 *         SVN: http://album-cover-notes.googlecode.com/svn/trunk/
 *
 * 
 * @author Giorgio P. F. G. Torres
 */

package com.exemplo.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RowAdapter extends BaseAdapter {
	
	private Context mCtx;
	private Row [] rows;
	
	public RowAdapter(Context ctx, Row [] rows) {
		super();
		mCtx = ctx;
		this.rows = rows;
	}

	@Override
	public int getCount() {
		if(rows != null)
			return rows.length;
		
		return 0;
	}

	@Override
	public Row getItem(int position) {
		return rows[position];
	}

	@Override
	public long getItemId(int position) {
		Row row = getItem(position);
		if(row != null)
			return row.getId();
		
		return -1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		View v = convertView;
		if(rows != null) {
			Row row = rows[position];
			if(v == null) {
				LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.albumcover_row, null);
			}
			
			ImageView image = (ImageView) v.findViewById(R.id.imageIcon);
			image.setImageBitmap(row.getImage());
			
			TextView imageTitle = (TextView) v.findViewById(R.id.imageTitle);
			imageTitle.setText(row.getTitle());
		}
		
				
		return v;
	}

}
