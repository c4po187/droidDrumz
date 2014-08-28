/**
 * DroidDrumz, a simple yet effective, open source drum machine / sampler
 * Copyright (C) 2014  Rici Underwood
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * c4po187@gmail.com
 *
 **/

package com.droid.c4po.droiddrumz_alpha;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.media.AudioManager;

/**
 * This is the main activity class. The application pretty much
 * starts off from here. Many of the other classes in this project
 * will be initialized from here. This class sits at the top of
 * the hierarchy.
 */
public class DroidDrumzAlphaMain extends Activity {

    /**
     * Members
     */
    private SoundManager _soundManager;
    private MenuManager _menuManager;

    /*********************************************************************
     * Methods ***********************************************************
     *********************************************************************/

    /**
     * As soon as the application and this class fires up, objects
     * and the layout need to be created. This method takes care of
     * just that.
     *
     * @param savedInstanceState :
     *                           Parameter that holds a state of a
     *                           previous instance of this application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_droid_drumz_alpha_main);
        _soundManager = new SoundManager(12, AudioManager.STREAM_MUSIC, 0, this);
        Container container = new Container(this, _soundManager);
        _menuManager = new MenuManager(this);
    }

    /**
     * Creates the action bar menu. Currently there is one option,
     * the 'About' option, detailing the application and myself,
     * the author.
     *
     * @param menu :
     *             Parameter that passes a menu, in order to access
     *             the action bar.
     * @return     :
     *             Returns true upon completion.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.droid_drumz_alpha_main, menu);
        return true;
    }

    /**
     * This method is called when an option from the action bar
     * has been selected. The selection is then processed and
     * the appropriate methods are called, reflecting the selected
     * option.
     *
     * @param item :
     *             Parameter represents an item from the action bar.
     * @return     :
     *             Returns base class method, passing item as an
     *             argument.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_about)
            _menuManager.actionBar_AboutSelected();
        if (id == R.id.action_themes)
            _menuManager.themesClicked();
        return super.onOptionsItemSelected(item);
    }
}
