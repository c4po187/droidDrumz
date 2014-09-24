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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Class that aids in the management of I/O Streams.
 *
 * NOTE:
 *     All methods are static, hence the lack of a constructor.
 *     Also, each method is enforced by implicit exception
 *     declarations. So in order to call any of these methods,
 *     one must do so within a try-catch block.
 */
public class IO_Man {

    /*********************************************************************
     * Members ***********************************************************
     *********************************************************************/

    private static BufferedReader  _reader;
    private static FileInputStream _fileInputStream;

    /*********************************************************************
     * Methods ***********************************************************
     *********************************************************************/

    /**
     * Method takes a stream as input and outputs a string.
     *
     * @param is            :
     *                      Parameter represents an input stream.
     * @return              :
     *                      Returns a string.
     * @throws Exception
     */

    public static String convertStreamToString(InputStream is) throws Exception {
        _reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = _reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        _reader.close();
        return stringBuilder.toString();
    }

    /**
     * Method outputs a string from the data within the provided file.
     *
     * @param file          :
     *                      Parameter represents a file.
     * @return              :
     *                      Returns a string.
     * @throws Exception
     */
    public static String getStringFromFile(File file) throws Exception {
        _fileInputStream = new FileInputStream(file);
        String data = convertStreamToString(_fileInputStream);
        _fileInputStream.close();
        return data;
    }

    /**
     * Method takes a stream as input and outputs an Array List of type String.
     *
     * @param is            :
     *                      Parameter represents an input stream.
     * @return              :
     *                      Returns a Array List of type String.
     * @throws Exception
     */
    public static ArrayList<String> convertStreamToStringList(InputStream is) throws Exception {
        _reader = new BufferedReader(new InputStreamReader(is));
        ArrayList<String> filenames_list = new ArrayList<String>();
        String line;
        while ((line = _reader.readLine()) != null) {
            filenames_list.add(line);
        }
        _reader.close();
        return filenames_list;
    }

    /**
     * Method outputs a list of Strings from the data in the provided file.
     *
     * @param file          :
     *                      Parameter represents a file.
     * @return              :
     *                      Returns an Array List of type String.
     * @throws Exception
     */
    public static ArrayList<String> getStringArrayListFromFile(File file) throws Exception {
        _fileInputStream = new FileInputStream(file);
        ArrayList<String> data = convertStreamToStringList(_fileInputStream);
        _fileInputStream.close();
        return data;
    }
}
