
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

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class AlbumCoverNotesMainActivity extends ListActivity {
	
	private final int ICODIM = 60;
	
	private AlbumCoverNotesDb albumCoverNotesDbHelper;
	
	private static final int INSERT_ID = Menu.FIRST;
	private static final int REFRESHLIST_ID = Menu.FIRST + 1;
	private static final int DELETEALL_ID = Menu.FIRST + 2;
	private static final int INFO_ID = Menu.FIRST + 3;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albumcover_list);
        
        albumCoverNotesDbHelper = new AlbumCoverNotesDb(this);
        albumCoverNotesDbHelper.open();
        
        //setListAdapter(new RowAdapter(this, data()));
        
        refreshList();
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	
    	Row row = albumCoverNotesDbHelper.getImageNote(id);
    	
    	Intent intent = new Intent(this, DisplayAlbumCoverNoteActivity.class);
    	intent.putExtra(AlbumCoverNotesDb.KEY_COVERPATH, row.getImagePath());
    	intent.putExtra(AlbumCoverNotesDb.KEY_TITLE, row.getTitle());
    	intent.putExtra(AlbumCoverNotesDb.KEY_AUTHOR, row.getAuthor());
    	
    	startActivity(intent);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, INSERT_ID, 0, R.string.addItem).setIcon(R.drawable.ic_menu_add);
		menu.add(0, REFRESHLIST_ID, 0, R.string.refresh).setIcon(R.drawable.ic_menu_refresh);
		menu.add(0, DELETEALL_ID, 0, R.string.deleteAll).setIcon(R.drawable.ic_menu_clear_playlist);
		menu.add(0, INFO_ID, 0, R.string.info).setIcon(R.drawable.ic_menu_info_details);
		return super.onCreateOptionsMenu(menu);
	}
	
	private Row [] data() {
		Cursor c = albumCoverNotesDbHelper.fetchAllImageNotes();
		startManagingCursor(c);
		
		Row [] rows = null;
		int numRows = c.getCount();
		if(numRows >= 1) {
			rows = new Row[numRows];
			for(int i=0; c.moveToNext(); i++) {
				rows[i] = new Row();
				rows[i].setId(Long.parseLong(c.getString(c.getColumnIndex(AlbumCoverNotesDb.KEY_ROWID))));
				rows[i].setTitle(c.getString(c.getColumnIndex(AlbumCoverNotesDb.KEY_TITLE)));
				rows[i].setAuthor(c.getString(c.getColumnIndex(AlbumCoverNotesDb.KEY_AUTHOR)));
				rows[i].setImagePath(c.getString(c.getColumnIndex(AlbumCoverNotesDb.KEY_COVERPATH)));
				Bitmap icon = BitmapFactory.decodeFile(rows[i].getImagePath());
				if(icon.getWidth() > icon.getHeight()) {
					rows[i].setImage(Bitmap.createScaledBitmap(icon, ICODIM, (int)(ICODIM*((float)icon.getHeight()/icon.getWidth())), true));
				} else
					rows[i].setImage(Bitmap.createScaledBitmap(icon, (int)(ICODIM*((float)icon.getWidth()/icon.getHeight())), ICODIM, true));
			}
		}
		
		return rows;
	}
	
	private void createImageNote() {
		Intent i = new Intent(this, EditAlbumCoverNoteActivity.class);
		startActivity(i);
	}
	
	private void refreshList() {
		setListAdapter(new RowAdapter(this, data()));
	}
	
	private void deleteAll() {
		albumCoverNotesDbHelper.dropTable();
		refreshList();
	}
	
	private void infoImageNotes() {
		Toast.makeText(this, R.string.info, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case INSERT_ID:
				createImageNote();
				break;
			case REFRESHLIST_ID:
				refreshList();
				break;
			case DELETEALL_ID:
				deleteAll();
				break;
			case INFO_ID:
				infoImageNotes();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
    
}