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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class that displays the Sound Bank, and assigns a sound to
 * the pad that initiated this class.
 */
public class PadAssignmentMenu extends ListActivity {

    /*********************************************************************
     * Members ***********************************************************
     *********************************************************************/

    ArrayList<String> _sound_bank;
    private String _chosenItem;

    /*********************************************************************
     * Internal Private Class ********************************************
     *********************************************************************/

    /**
     * Class that extends from ArrayAdapter to help further customize
     * row items in the ListView.
     *
     * @param <T> :
     *            Parameter represents any type.
     */
    private class PadAssignAdapter<T> extends ArrayAdapter<T> {

        /*****************************************************************
         * Constructor ***************************************************
         *****************************************************************/

        public PadAssignAdapter(Context context, int resrc, int textView_resrc,
                                List<T> objects) {
            super(context, resrc, textView_resrc, objects);
        }

        /*****************************************************************
         * Methods *******************************************************
         *****************************************************************/

        /**
         * Method grabs the view of the Array Adapter.
         *
         * @param position      :
         *                      Parameter represents the current position
         *                      in the list.
         * @param convertView   :
         *                      Parameter represents the view of the Array
         *                      Adapter.
         * @param parent        :
         *                      Parameter represents the parent of the
         *                      Array Adapter.
         * @return              :
         *                      Returns a view.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = super.getView(position, convertView, parent);
            ImageView img = (ImageView)itemView.findViewById(R.id.pa_icon);
            TextView text = (TextView)itemView.findViewById(R.id.pa_text);

            /* Check row text for specified sub strings and set
               row images accordingly
             */
            if (text.getText().toString().contains("snare")) {
                img.setImageResource(R.drawable.wsnare48);
            } else if (text.getText().toString().contains("kick")) {
                img.setImageResource(R.drawable.wdrum648);
            } else if (text.getText().toString().contains("tom")) {
                img.setImageResource(R.drawable.tom48);
            } else if (text.getText().toString().contains("cymbal") ||
                    text.getText().toString().contains("hat") ||
                    text.getText().toString().contains("closed") ||
                    text.getText().toString().contains("open") ||
                    text.getText().toString().contains("crash") ||
                    text.getText().toString().contains("ride")) {
                img.setImageResource(R.drawable.wcymbals48);
            } else {
                img.setImageResource(R.drawable.wperc48);
            }

            return itemView;
        }
    }

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

        /* Grab the sound bank string array from the intent extra and pass
           it to the array adapter
          */
        _sound_bank = this.getIntent().getStringArrayListExtra("soundname");

        // Sort the list alphabetically
        Collections.sort(_sound_bank, new Comparator<String>() {
            @Override
            public int compare(String s, String s2) {
                return s.compareToIgnoreCase(s2);
            }
        });

        PadAssignAdapter<String> pa_adapter = new PadAssignAdapter<String>(
                this, R.layout.pad_assign_menu_row, R.id.pa_text, _sound_bank);

        // Finally, set the adapter and display it
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
