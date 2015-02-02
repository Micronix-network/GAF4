/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author kobo
 */
public class FileUtil {

    
	/**
	 * Dato un file ne restituisce l'array di byte
	 * @param file da convertire in byte
	 * @return l'array di byte del contenuto del file
	 * @throws IOException
	 */
	public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        is.close();
        return bytes;
    }

	/**
	 * Scrive in un file un array di byte 
	 * @param data
	 * @param filename
	 * @throws IOException
	 */
    public static void writeByteToFile(byte[] data, String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        fos.write(data);
        fos.close();
    }
}
