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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that displays the Sound Bank, and assigns a sound to
 * the pad that initiated this class.
 */
public class AssignmentMenu extends ListActivity {

    /*********************************************************************
     * Members ***********************************************************
     *********************************************************************/

    ArrayList<String> _menu_list;
    private String _chosenItem;
    boolean isSoundBank;

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
    private class AssignAdapter<T> extends ArrayAdapter<T> {

        /*****************************************************************
         * Constructor ***************************************************
         *****************************************************************/

        public AssignAdapter(Context context, int resrc, int textView_resrc,
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

            if (isSoundBank) {
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
                } else if (text.getText().toString().contains("conga") ||
                        text.getText().toString().contains("timpani")) {
                    img.setImageResource(R.drawable.wcongtimp48);
                } else if (text.getText().toString().contains("maraca") ||
                        text.getText().toString().contains("shaker")) {
                    img.setImageResource(R.drawable.wshaker);
                } else if (text.getText().toString().contains("cow")) {
                    img.setImageResource(R.drawable.wcowbell48);
                } else if (text.getText().toString().contains("guiro")) {
                    img.setImageResource(R.drawable.wguiro48);
                } else if (text.getText().toString().contains("tambourine")) {
                    img.setImageResource(R.drawable.wtamb48);
                } else if (text.getText().toString().contains("triangle")) {
                    img.setImageResource(R.drawable.wtri48);
                } else {
                    img.setImageResource(R.drawable.wpercother48);
                }
            } else {
                // We're setting a kit, so use that lovely drum-kit icon :)
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
        isSoundBank = this.getIntent().getBooleanExtra("sb", false);

        /* Grab the menu list string array from the intent extra and pass
           it to the array adapter
          */
        if (isSoundBank)
            _menu_list = this.getIntent().getStringArrayListExtra("soundname");
        else
            _menu_list = this.getIntent().getStringArrayListExtra("presetnames");

        AssignAdapter<String> pa_adapter = new AssignAdapter<String>(
                this, R.layout.assign_menu_row, R.id.pa_text, _menu_list);

        // Add header
        ListView listView = getListView();
        LayoutInflater inflater = getLayoutInflater();
        View header = inflater.inflate(R.layout.assign_menu_header, listView, false);
        listView.addHeaderView(header, null, false);

        TextView current_text = (TextView)header.findViewById(R.id.assign_current_current_text);
        TextView content_text = (TextView)header.findViewById(R.id.assign_current_item_text);
        if (isSoundBank) {
            current_text.setText("Current Sound:");
            content_text.setText(this.getIntent().getStringExtra("currentsound"));

        } else {
            current_text.setText("Current Kit:");
            content_text.setText(this.getIntent().getStringExtra("currentkit"));
        }

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
        // We subtract -1 due to the header from the menu counting as a position
        _chosenItem = (String)getListAdapter().getItem(position - 1);
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

        if (isSoundBank)
            returnIntent.putExtra("returnsoundbank", _chosenItem);
        else
            returnIntent.putExtra("returnpresetnames", _chosenItem);

        setResult(RESULT_OK, returnIntent);
        super.finish();
    }
}
