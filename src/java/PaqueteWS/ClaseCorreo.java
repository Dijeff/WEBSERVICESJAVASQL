package PaqueteWS;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.*;

public class ClaseCorreo {

    public Properties props = new Properties();
    public static Session session;
    public static int contadorbien = 0, contadormal = 0;
    public boolean saliobien = true;

    public void propiedadesCorreo(String direccion) {
        try {
            if (direccion.contains("gmail")) {

                props.setProperty("mail.smtp.host", "smtp.gmail.com");
                props.setProperty("mail.smtp.starttls.enable", "true");
                props.setProperty("mail.smtp.port", "587");
                props.setProperty("mail.smtp.user", "Usuario");
                props.setProperty("mail.smtp.auth", "true");
                session = Session.getDefaultInstance(props);
                session.getProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");
                // Preparamos la sesion
            } else {

                props.setProperty("mail.smtp.host", "smtp-MAIL.OUTLOOK.com");
                props.setProperty("mail.smtp.starttls.enable", "true");
                props.setProperty("mail.smtp.port", "587");
                props.setProperty("mail.smtp.user", "Usuario");
                props.setProperty("mail.smtp.auth", "true");
                session = Session.getDefaultInstance(props);
                session.getProperties().put("mail.smtp.ssl.trust", "smtp-MAIL.OUTLOOK.com");
                // Preparamos la sesion
            }

        } catch (Exception e) {
        }

    }

    public void mandarCorreoATodos(String correo, String nombre, String oficinaPaga, String oficinaRecibe,
            double montorecibe, String moneda) throws MessagingException {
        Date objDate = new Date();
        propiedadesCorreo("albertosolanovillalta@gmail.com");
        try {

            MimeMessage message = new MimeMessage(session);

            message.setFrom(
                    new InternetAddress("albertosolanovillalta@gmail.com"));
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(correo));
            message.setSubject("Resumen de su Reservación, GlobalWay");
            message.setText("Hola Señ@r: " + nombre + "\n" + "!Muchas Gracias por confiar en nosotros y escogernos¡" + "\n"
                    + "Resumen de su Orden" + "\n"
                    + "Dia en que se efectua la reserva: " + objDate + "\n"
                    + "Oficina en donde va a pagar: " + oficinaPaga + "\n"
                    + "Oficna donde va a recoger el dinero: " + oficinaRecibe + "\n"
                    + "Monto total a recibir: " + montorecibe + "\n"
                    + "Moneda en la que va a recibir " + moneda + "\n"
                    + "-----------------------------------------" + "\n"
                    + "Este mensaje fue enviado al correo: " + correo);

            // Lo enviamos.
            Transport t = session.getTransport("smtp");

            t.connect("albertosolanovillalta@gmail.com", "3108familia");
            t.sendMessage(message, message.getAllRecipients());
            t.close();

        } catch (Exception e) {
            System.out.println("Fallo");
            saliobien = false;
        }

    }

}
