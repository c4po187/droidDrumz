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

import java.util.Comparator;

/**
 * Class that implements the Comparator in order to
 * sort the Samples by their Resource Names.
 */
public class SortSamplesByResourceName implements Comparator<Sample> {

    /**
     * Method that compares an instance of Sample to another
     * based on resource name.
     *
     * @param s1    :
     *              Parameter represents a Sample.
     * @param s2    :
     *              Parameter represents another Sample.
     * @return      :
     *              Returns -1 if s1 comes before s2 alphabetically,
     *              Returns 1 if s1 comes after s2 alphabetically,
     *              Returns 0 if s1 & s2 are equal alphabetically.
     */
    @Override
    public int compare(Sample s1, Sample s2) {
        return (s1.get_resource_name().compareToIgnoreCase(s2.get_resource_name()));
    }
}
