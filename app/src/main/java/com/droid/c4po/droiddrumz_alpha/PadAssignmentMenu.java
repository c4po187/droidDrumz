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

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Class that displays the Sound Bank, and assigns a sound to
 * the pad that initiated this class.
 *
 * TODO: Create the Sound bank, and implement it into the menu instead of our test array of strings.
 */
public class PadAssignmentMenu extends ListActivity {

    /*********************************************************************
     * Members ***********************************************************
     *********************************************************************/

    ArrayList<String> _sound_bank;
    private String _chosenItem;

    /*********************************************************************
     * Methods ***********************************************************
     *********************************************************************/

    /**
     * This method is called immediately.
     *
     * @param bundle :
     *               Parameter represents a bundle, such as instance states etc...
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        _sound_bank = this.getIntent().getStringArrayListExtra("soundname");
        ArrayAdapter<String> pa_adapter = new ArrayAdapter<String>(
                this, R.layout.pad_assign_menu_row, R.id.pa_text, _sound_bank);
        setListAdapter(pa_adapter);
    }

    /**
     * This method detects whether a list item has been clicked.
     *
     * @param listView :
     *                 Parameter represents the list view.
     * @param view     :
     *                 Parameter represents the main view.
     * @param position :
     *                 Parameter represents the item position in the list.
     * @param id       :
     *                 Parameter represents the ID of the list item.
     */
    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        _chosenItem = (String)getListAdapter().getItem(position);
        finish();
    }

    /**
     * This method is called upon completion of this activity, and any
     * values that are needed by the callee are returned to them attached
     * to a key along with a response on whether this activity succeeded.
     */
    @Override
    public void finish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("returnKey", _chosenItem);
        setResult(RESULT_OK, returnIntent);
        super.finish();
    }
}
