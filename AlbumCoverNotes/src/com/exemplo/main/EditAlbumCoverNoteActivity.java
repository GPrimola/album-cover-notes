
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

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditAlbumCoverNoteActivity extends Activity {

	private static final int SELECT_PICTURE = 1;
	
	private String selectedImagePath;
	private ImageView icon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_albumcover_note);
		
		icon = (ImageView) findViewById(R.id.selectPicImageView);
		icon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
			}
		});
		
		Button addButton = (Button) findViewById(R.id.addButton);
		addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AlbumCoverNotesDb dbHelper = new AlbumCoverNotesDb(view.getContext());
				dbHelper.open();
				
				EditText titleEdit = (EditText) findViewById(R.id.editImageTitle);
				String title = titleEdit.getText().toString();
				
				EditText descrEdit = (EditText) findViewById(R.id.descriptionArea);
				String descr = descrEdit.getText().toString();
				
				if(title.equals(""))
					Toast.makeText(view.getContext(), R.string.null_title, Toast.LENGTH_LONG).show();
				else {
					dbHelper.createImageNote(title, descr, selectedImagePath);
					Toast.makeText(view.getContext(), R.string.operation_succeeded, Toast.LENGTH_LONG).show();
					titleEdit.setText("");titleEdit.requestFocus();
					descrEdit.setText("");
					icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_add));
				}
				
				dbHelper.close();
				
			}
		});
		
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*
		 * Trecho de código tirado de
		 * http://stackoverflow.com/questions/2169649/open-an-image-in-androids-built-in-gallery-app-programmatically
		 * 
		 * */
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();

                //OI FILE Manager
                //filemanagerstring = selectedImageUri.getPath();

                //MEDIA GALLERY
                selectedImagePath = getPath(selectedImageUri);
                icon.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
            }
        }
    }

	/*
	 * Trecho de código tirado de
	 * http://stackoverflow.com/questions/2169649/open-an-image-in-androids-built-in-gallery-app-programmatically
	 * 
	 * */
	private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor != null) {
            int column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        else return null;
    }

	
	
	
	

}
