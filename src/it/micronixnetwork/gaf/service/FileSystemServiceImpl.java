package it.micronixnetwork.gaf.service;

import it.micronixnetwork.gaf.exception.ServiceException;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileSystemServiceImpl extends GAFService implements FileSystemService {

    @Override
    public List<File> getFiles(File parent, String extension, byte ordertype, byte direction) throws ServiceException {
	try {
	    List<File> result = new ArrayList<File>();
	    if (parent == null)
		return result;
	    if (parent.isFile())
		return result;
	    File[] listOfFiles = parent.listFiles(new OnlyFileFilter(extension));
	    if(listOfFiles==null) return result;
	    result = getOrderedFileList(listOfFiles, ordertype, direction);
	    return result;
	} catch (Exception ex) {
	    throw new ServiceException(ex);
	}
    }

    private List<File> getOrderedFileList(File[] files, final byte ordertype, final byte direction) throws Exception {
	if (files == null) {
	    return null;
	}
	List<File> result = new ArrayList(Arrays.asList(files));
	Comparator<File> comparator = new Comparator<File>() {

	    int toReturn = 0;

	    public int compare(File o1, File o2) {
		if (ordertype == FileSystemService.NAME) {
		    if (o1.isFile() && o2.isFile()) {
			toReturn = o1.getName().compareTo(o2.getName());
		    }
		}
		if (ordertype == FileSystemService.TYPE) {
		    if (o1.isFile() && o2.isFile()) {
			int pindex = o1.getName().lastIndexOf(".");
			String type1 = o1.getName().substring(pindex) + 1;
			pindex = o2.getName().lastIndexOf(".");
			String type2 = o2.getName().substring(pindex) + 1;
			toReturn = type1.compareTo(type2);
		    }
		}
		if (ordertype == FileSystemService.DATE) {
		    if (o1.isFile() && o2.isFile()) {
			Long tstamp1 = o1.lastModified();
			Long tstamp2 = o2.lastModified();
			toReturn = tstamp1.compareTo(tstamp2);
		    }
		}

		if (direction == FileSystemService.ASC)
		    return toReturn;
		else
		    return -toReturn;
	    }
	};
	Collections.sort(result, comparator);
	return result;
    }

    @Override
    public void copyfile(File f1, File f2) throws FileNotFoundException, IOException {
	InputStream in = new FileInputStream(f1);

	// For Append the file.
	// OutputStream out = new FileOutputStream(f2,true);

	// For Overwrite the file.
	OutputStream out = new FileOutputStream(f2);

	byte[] buf = new byte[1024];
	int len;
	while ((len = in.read(buf)) > 0) {
	    out.write(buf, 0, len);
	}
	in.close();
	out.close();

    }

    @Override
    public byte[] getBytesFromFile(String fileName) throws IOException {
	return getBytesFromFile(new File(fileName));
    }

    @Override
    public byte[] getBytesFromFile(File file) throws IOException {
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

    @Override
    public void writeByteToFile(String data, String filename) throws IOException {
	writeByteToFile(data.getBytes(), filename);

    }

    @Override
    public void writeByteToFile(byte[] data, String filename) throws IOException {
	FileOutputStream fos = new FileOutputStream(filename);
	fos.write(data);
	fos.close();

    }

}

class OnlyFileFilter implements FileFilter {
    private final String extension;

    public OnlyFileFilter(String extension) {
	if (!extension.equals("*"))
	    this.extension = extension;
	else
	    this.extension = "*";
    }

    @Override
    public boolean accept(File file) {
	if (file.isDirectory())
	    return false;
	if (extension.equals("*"))
	    return true;
	if (file.getName().endsWith(extension)) {
	    return true;
	}
	return false;
    }

}
