package it.micronixnetwork.gaf.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class Fax {


	public static String sendToHylafaxByMail(String host, int port, String user, String password,
			String from, ArrayList<String> listaTO, String faxDomain, String subject, ArrayList<String> attach) throws Exception, MessagingException, AddressException {


		String ret = getBodyName();


		// creo un fax per ogni destinatario
		ArrayList<String> faxes = new ArrayList<String>();
		for (int i = 0; i < listaTO.size(); i++) {
			String recipient = listaTO.get(i)+"@"+faxDomain;
			faxes.add(recipient);

			Smtp.sendEmail(host, port, user, password,
						from, faxes, subject+" (" + ret +")", null, attach);

		}

		return ret;

	}

	@SuppressWarnings("deprecation")
	private static String getBodyName(){
		Date now = new Date();

	    final String STR_RND = "012345789ABCDEFGHIJKLMNOPQRSTUVWXYZ012345789ABCDEFGHIJKLMNOPQRSTUVWXYZ";


	    String ret = "X";
	    try{
	    int pos = now.getMinutes();
	    ret += STR_RND.substring(pos,pos+1);
	    pos = now.getSeconds();
	    ret += STR_RND.substring(pos,pos+1);
	    pos = now.getDay();
	    ret += STR_RND.substring(pos,pos+1);
	    pos = randInt(0, STR_RND.length());
	    ret += STR_RND.substring(pos,pos+1);
	    } catch (Exception e) {
			ret += "(e)";
		}

		return ret;

	}

	/**
	 * Returns a psuedo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimim value
	 * @param max Maximim value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	private static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}
