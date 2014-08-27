package com.droid.c4po.droiddrumz_alpha;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.media.AudioManager;


public class DroidDrumzAlphaMain extends Activity {

    private Container _container;
    private SoundManager _soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_droid_drumz_alpha_main);
        _soundManager = new SoundManager(12, AudioManager.STREAM_MUSIC, 0, this);
        _container = new Container(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.droid_drumz_alpha_main, menu);
        _container.buttonRoutine(_soundManager);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_about) {
            final AlertDialog.Builder about_builder = new AlertDialog.Builder(this);
            about_builder.setTitle(R.string._action_about).setMessage(R.string.about_content);
            about_builder.setPositiveButton(R.string.action_about_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog about_dialog = about_builder.create();
            about_dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
