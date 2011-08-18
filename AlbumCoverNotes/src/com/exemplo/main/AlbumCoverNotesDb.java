
/*
 * Por favor, ao usar, divulgar ou modificar, fazer referência ao material do autor.
 * 
 * Please, you can use, share or edit, but make reference to the author's material.
 * 
 * Je vous en prie, vous pouvez utiliser, partager et modifier, mais faites reference au materiel d'auteur.
 * 
 * Disponível em/Available in/Disponible à: 
 *     
 *         SVN: http://album-cover-notes.googlecode.com/svn/trunk/
 *
 * 
 * @author Giorgio P. F. G. Torres
 */

package com.exemplo.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlbumCoverNotesDb {
	
	public static final String KEY_ROWID = "id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_AUTHOR = "author";
	public static final String KEY_COVERPATH = "coverpath";
	
	//private static final String TAG = "ImageNotesDb";
	
	/* Comando para criação do banco de dados */
	private static final String DB_CREATE = "create table albumcovernotes " +
			"(id integer primary key autoincrement, title text not null, author text, " +
			"coverpath text not null);";
	
	private static final String [] allColumns = new String[] {KEY_ROWID, KEY_TITLE, KEY_AUTHOR, KEY_COVERPATH};
	
	private static final String DB_NAME = "albumcoverdata";
	private static final String DB_TABLE = "albumcovernotes";
	private static final int DB_VERSION = 1;
	
	private DbHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mContext;
	
	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context ctx) {
			super(ctx, DB_NAME, null, DB_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(DB_CREATE);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
			onCreate(db);
		}
		
	}
	
	/*
	 * O banco de dados SQLiteDatabase precisa de um contexto para ser criado.
	 * Esse contexto deve vir da Activity que utilizará o banco de dados.
	 */
	public AlbumCoverNotesDb(Context ctx) {
		this.mContext = ctx;
	}
	
	public AlbumCoverNotesDb open() throws SQLException {
		mDbHelper = new DbHelper(mContext);
		mDb = mDbHelper.getWritableDatabase();
		
		return this;
	}
	
	public void close() {
		mDbHelper.close();
	}
	
	public long createImageNote(String title, String descr, String imagePath) {
		ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_AUTHOR, descr);
        initialValues.put(KEY_COVERPATH, imagePath);
        
        return mDb.insert(DB_TABLE, null, initialValues);
	}
	
	public boolean deleteNote(long rowId) {
		/* O método delete de SQLiteDatabase retorna o número de linhas afetadas pelo comando */
		/* Veja o link:
		 * 
		 * http://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html#delete%28java.lang.String,%20java.lang.String,%20java.lang.String[]%29
		 * 
		 */
		return mDb.delete(DB_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public Cursor fetchAllImageNotes() {
		/* Veja o link:
		 * 
		 * http://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html#query%28java.lang.String,%20java.lang.String[],%20java.lang.String,%20java.lang.String[],%20java.lang.String,%20java.lang.String,%20java.lang.String%29
		 * 
		 */
		
		return mDb.query(DB_TABLE, allColumns, null, null, null, null, null);
	}
	
	public Cursor fetchImageNote(long rowId) {
		
		Cursor c = mDb.query(DB_TABLE, allColumns, KEY_ROWID + "=" + rowId, null, null, null, null, null);
	
		if(c != null) {
			c.moveToFirst();
		}
		
		return c;
	}
	
	public Row getImageNote(long rowId) {
		
		Row row = null;
		Cursor c = fetchImageNote(rowId);
		
		if(c != null) {
			row = new Row();
			row.setId(Long.parseLong(c.getString(c.getColumnIndex(KEY_ROWID))));
			row.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
			row.setAuthor(c.getString(c.getColumnIndex(KEY_AUTHOR)));
			row.setImagePath(c.getString(c.getColumnIndex(KEY_COVERPATH)));
		}
		
		return row;
	}
	
	public boolean updateImageNote(long rowId, String title, String descr, String imagePath) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_TITLE, title);
        newValues.put(KEY_AUTHOR, descr);
        newValues.put(KEY_COVERPATH, imagePath);

        return mDb.update(DB_TABLE, newValues, KEY_ROWID + "=" + rowId, null) > 0;
    }
	
	public void dropTable() {
		mDbHelper.onUpgrade(mDb, 1, 2);
	}
	
}
