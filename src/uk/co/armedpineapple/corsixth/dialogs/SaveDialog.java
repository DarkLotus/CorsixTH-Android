/*
 *   Copyright (C) 2012 Alan Woolley
 *   
 *   See LICENSE.TXT for full license
 */
package uk.co.armedpineapple.corsixth.dialogs;

import uk.co.armedpineapple.corsixth.R;
import uk.co.armedpineapple.corsixth.SDLActivity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;

public class SaveDialog extends FilesDialog {

	private AlertDialog newSaveDialog;

	public SaveDialog(SDLActivity context, String path) {
		super(context, path, R.layout.files_dialog, true);
		setTitle(R.string.save_game_dialog_title);

		final EditText editTextBox = new EditText(context);
		Builder builder = new Builder(context);
		builder.setMessage(R.string.save_game_dialog_message);
		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				onSelectedFile(editTextBox.getText().toString() + ".sav");
				dismiss();
			}

		});

		builder.setView(editTextBox);
		newSaveDialog = builder.create();

	}

	@Override
	public void onSelectedFile(String file) {
		Log.d(getClass().getSimpleName(), "Saving: " + file);
        
        // Reset the game speed - we don't want to save when the game is paused!
		SDLActivity.cthGameSpeed(ctx.config.getGameSpeed());
        
        // Save the game
		SDLActivity.cthSaveGame(file);
        
		dismiss();

	}

	@Override
	public void onNewClicked() {
		newSaveDialog.show();

	}

}
