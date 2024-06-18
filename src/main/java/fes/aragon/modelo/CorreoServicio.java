package fes.aragon.modelo;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import javafx.concurrent.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class CorreoServicio extends Task <Integer>{
    @Override
    protected Integer call() throws Exception {
        String gmail = ""; //Aqui se agrega el email del remitente
        String pswd = ""; //Se agrega la password que se debe generar en google app passwords
        // Recuerda agregar en Persona correos validos para los destinatarios

        Properties p = System.getProperties();
        p.setProperty("mail.smtps.host","smpt.gmail.com");
        p.setProperty("mail.smtps.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        p.setProperty("mail.smtps.socketFactory.fallback","false");
        p.setProperty("mail.smtp.port","465");
        p.setProperty("mail.smtps.socketFactory.port","465");
        p.setProperty("mail.smtps.auth","true");
        p.setProperty("mail.smtp.ssl.trust","smtp.gmail.com");
        p.setProperty("mail.smtps.ssl.trust","smtp.gmail.com");
        p.setProperty("mail.smtp.ssl.quitwait","false");

        //construcción del html
        String msj = SingletonDatos.getInstance().getMsj().replace("\n", "<br>");
        String cadena = "<h2><p>" + msj + "</p></h2></br>";
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Session session = Session.getInstance(p, null);
            MimeMessage message = new MimeMessage(session);

            //correo con archivo
            MimeBodyPart texto = new MimeBodyPart();
            texto.setContent(cadena, "text/html;charset=utf-8");
            //adjuntar la imagen
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(
                    SingletonDatos.getInstance().getArchivo().getAbsoluteFile()
            )));

            adjunto.setFileName("imagen.png");
            Multipart multiple = new MimeMultipart();
            multiple.addBodyPart(texto);
            multiple.addBodyPart(adjunto);
            Transport transport = (Transport) session.getTransport("smtps");
            transport.connect("smtp.gmail.com", gmail, pswd);


            int contador=0;
            for (Persona e : SingletonDatos.getInstance().getListaGeneral()) {
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(e.getEmail(), false));
                message.setSubject(SingletonDatos.getInstance().getAsunto());
                message.setContent(multiple);
                message.setSentDate(new Date());
                transport.sendMessage(message, message.getAllRecipients());
                updateProgress(contador,SingletonDatos.getInstance().getListaGeneral().size());
                Thread.sleep(1200);
                contador++;
            }
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 10;
    }
}
