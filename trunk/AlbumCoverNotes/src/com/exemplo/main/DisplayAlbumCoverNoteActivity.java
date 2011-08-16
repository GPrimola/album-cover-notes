package com.exemplo.main;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayAlbumCoverNoteActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_albumcover_note);
		
		Bundle b = getIntent().getExtras();
		
		ImageView iv = (ImageView) findViewById(R.id.largeImage);
		iv.setImageBitmap(BitmapFactory.decodeFile(b.getString(AlbumCoverNotesDb.KEY_COVERPATH)));
		
		TextView tv = (TextView) findViewById(R.id.showTitleTextView);
		tv.setText(b.getCharSequence(AlbumCoverNotesDb.KEY_TITLE));
		
		TextView tv2 = (TextView) findViewById(R.id.showDescriptionTextView);
		tv2.setText(b.getCharSequence(AlbumCoverNotesDb.KEY_AUTHOR));
		
	}

}
