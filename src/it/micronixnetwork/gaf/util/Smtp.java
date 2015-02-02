package it.micronixnetwork.gaf.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Smtp {

	public static void sendEmail(String host, int port, String user, String password,
			String from, ArrayList<String> listaTO, String subject, String body,
			ArrayList<String> attach) throws Exception, MessagingException, AddressException {
		sendEmail(host, port, user, password, from, listaTO, null, null, subject, body, attach);

	}

	public static void sendEmail(String host, int port, String user, String password,
			String from, ArrayList<String> listaTO, ArrayList<String> listaCC,
			ArrayList<String> listaBCC, String subject, String body,
			ArrayList<String> attach) throws Exception, MessagingException, AddressException {

		String p = "SendEmail: ";

		Properties props = System.getProperties();
		// inserisce il nome del server di posta
		System.out.println(p+"   host="+host);
		props.put("mail.smtp.host", host);
		System.out.println(p+"   port="+port);
		props.put("mail.smtp.port", ""+port);





//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.port", "465");
//
//		Session session = Session.getInstance(props,
//		        new javax.mail.Authenticator() {
//		            protected PasswordAuthentication getPasswordAuthentication() {
//		                return new PasswordAuthentication(username, password);
//		            }
//		        });


		Session session = Session.getDefaultInstance(props, null);

		MimeMessage message2 = new MimeMessage(session);
		message2.setFrom(new InternetAddress(from));
		System.out.println(p+"   from="+from);
		// aggiungo destinatari principali
		if (listaTO != null) {
			for (int i = 0; i < listaTO.size(); i++) {
				message2.addRecipient(Message.RecipientType.TO,
						new InternetAddress(listaTO.get(i)));
				System.out.println(p+"     TO="+listaTO.get(i));
			}
		}

		// aggiungo destinatari in cc
		if (listaCC != null) {
			for (int i = 0; i < listaCC.size(); i++) {
				message2.addRecipient(Message.RecipientType.CC,
						new InternetAddress(listaCC.get(i)));
				System.out.println(p+"     CC="+listaCC.get(i));
			}
		}

		// aggiungo destinatari in cc
		if (listaBCC != null) {
			for (int i = 0; i < listaBCC.size(); i++) {
				message2.addRecipient(Message.RecipientType.BCC,
						new InternetAddress(listaBCC.get(i)));
				System.out.println(p+"    BCC="+listaBCC.get(i));
			}
		}

		if(subject!=null){
			System.out.println(p+"Subject="+subject);
			message2.setSubject(subject);
		}
		Multipart multipart = new MimeMultipart();

		// creo un messaggio multipart
		MimeBodyPart messageBodyPart = new MimeBodyPart();

		// fill message body
		if (body != null){
			if (body.toLowerCase().indexOf("<html") > -1)
				messageBodyPart.setContent(body, "text/html");
			else
				messageBodyPart.setContent(body, "text/plain");

			multipart.addBodyPart(messageBodyPart);
			System.out.println(p+"   Body=missing text...");
		}

		if (attach != null) {
			for (int i = 0; i < attach.size(); i++) {
				String attachF = attach.get(i);
				String attachFn = null;

				File f = null;
				if (attachF.indexOf(">") > -1){
					// split file and name
					f = new File(attachF.substring(0,attachF.indexOf(">")));
					attachFn = attachF.substring(attachF.indexOf(">")+1);
				}else {
					f = new File(attachF);
					attachFn = f.getName();
				}

				if (f.exists()) {
					// Part two is attachment
					messageBodyPart = new MimeBodyPart();
					DataSource source = new FileDataSource(f); // set source file
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(attachFn); // set name alias
					multipart.addBodyPart(messageBodyPart);
					System.out.println(p+" Attach="+f.getName());
				}
			}
		}
		message2.setContent(multipart);

		Transport.send(message2);

	}

}
