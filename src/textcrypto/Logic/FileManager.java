/*
 * Copyright (C) 2018 Rafael S. Müller <rafael.mueller1@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package textcrypto.Logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class reads and writes byte[] arrays into and from files, respectively.
 *
 * @author Rafael S. Müller <rafael.mueller1@gmail.com>
 */
public class FileManager {

    /**
     * Reads a file and returns the content as a byte array.
     *
     * @param file the file from which should be read
     * @return a byte array which contains the input from the file.
     * @throws java.io.FileNotFoundException
     */
    public static byte[] read(File file) throws FileNotFoundException, IOException {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            byte[] concent = new byte[(int) file.length()];
            inputStream.read(concent);
            
            return concent;
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        } catch (IOException ex) {
            throw new IOException("Could not read file!", ex);
        }
    }

    /**
     * Writes a byte array into a file.
     *
     * @param file
     * @param concent
     * @throws java.io.FileNotFoundException
     */
    public static void write(File file, byte[] concent) throws FileNotFoundException, IOException {

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(concent);

        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        } catch (IOException ex) {
            throw new IOException("Could not write file!", ex);
        }
    }
}
