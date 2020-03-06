package defaultPackage;

import java.awt.Panel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class Launcher {
 
    
	public static void main(String arg[]) throws TwitterException, IOException {
            
		
		File input = new File("srcpages/index.html");
		Document doc = Jsoup.connect("https://es.wikipedia.org/wiki/Anexo:Libros_m%C3%A1s_vendidos").get();

                    
		//Extraer datos del dom
//		Document doc = Jsoup.connect("https://listado.mercadolibre.com.co/playstation-4#D[A:playstation%204]").get();
//		Elements search = doc.getElementsByClass("item__info item--hide-right-col ");
//		System.out.println(doc.title());
//		for(Element element: search) {
//			Elements titulos = element.getElementsByClass("main-title");
//			Elements precios = element.getElementsByClass("price__fraction");
//			for(Element titulo:titulos) {
//				System.out.print(titulo.text()+" ");
//			}
//			for(Element precio:precios) {
//				System.out.println(precio.text());
//			}
//		}

		//Extraer datos de tablas
//		Elements tablas = doc.getElementsByTag("table");
//		for(Element tabla:tablas) {
//			Elements filas = tabla.getElementsByTag("tr");
//			for(Element fila:filas) {
//				Elements celdas=fila.getElementsByTag("td");
//				Elements titulos=fila.getElementsByTag("th");
//				for(Element titulo:titulos) {
//					System.out.print(titulo.text()+" ");
//				}
//				System.out.println();
//				for(Element celda:celdas) {
//					System.out.print(celda.text()+" ");
//				}
//				System.out.println();
//			}
//		}
		
		String moreLikesTweet=null;
		String moreLikesUser=null;
		int likes=0;
		
		//Inicialización
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey("2oyNn2o3uDESonxGxFxPGdsk2")
		  .setOAuthConsumerSecret("mXSZVGs8OVCIVpU2bKUubgvEvSCQ9Frh5P0Jq3gGJtXnn2f49c")
		  .setOAuthAccessToken("1234141760001118208-wWFl8VLMkq6omL66qKLl3eK5jCklyZ")
		  .setOAuthAccessTokenSecret("sAVd27BhDjoZJ6cZ3meJA1NFjWkqRAkUyESdpIWGzhjvF");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		//Realizar una consulta a través de twitter
//		Query query = new Query("colombia universidad distrital 40");
//		query.setCount(100);
//	    QueryResult result = twitter.search(query);
	    
//	    for (Status status : result.getTweets()) {
//	        System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
//	    }
		
		//Extraer el timeline de un usuario.
	    List<Status> statuses = twitter.getUserTimeline("udistrital", new Paging(1, 40));
	    System.out.println("Showing home timeline.");
	    for (Status status : statuses) {
                if(!status.isRetweet()){
                    System.out.println(status.getUser().getName() + ":" +
                                       status.getText());
                    System.out.println("Likes: "+status.getRetweetCount());
                    if(status.getRetweetCount()>likes) {
                            moreLikesTweet = status.getText();
                            moreLikesUser= status.getUser().getName();
                            likes=status.getRetweetCount();           
                    }
                }
	    }
        System.out.println("---------------------------------------------------\n");   
            System.out.println(moreLikesUser + ":" + moreLikesTweet);
            System.out.println("Likes: "+likes);            
            
            
            
            final String username = "diegorom27@gmail.com";
        final String password = "jdkaxhedaizapuxl";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("cristianthrashx@gmail.com, diegorom27@gmail.com")
            );
            message.setSubject("Diego Roman Cod:20191020008");
            message.setText("Hola Cristian el tweet con más likes es: "+moreLikesUser+" : "+moreLikesTweet +" Likes: "+likes);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

	    //Envío de un correo electrónico
//        Properties propiedad = new Properties();
//        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
//        propiedad.setProperty("mail.smtp.starttls.enable", "true");
//        propiedad.setProperty("mail.smtp.port", "587");
//        propiedad.setProperty("mail.smtp.auth", "true");
//        
//        
//        Session sesion = Session.getDefaultInstance(propiedad);
//        String correoEnvia = "****************";
//        String contrasena = "****************";
//        String receptor = "****************";
//        String asunto = "Correo Desde Java";
//        String mensaje="Hola Cristian el tweet con más likes es: "+moreLikesUser+" : "+moreLikesTweet +" Likes: "+likes;
//        
//        MimeMessage mail = new MimeMessage(sesion);
//        try {
//            mail.setFrom(new InternetAddress (correoEnvia));
//            mail.addRecipient(Message.RecipientType.TO, new InternetAddress (receptor));
//            mail.setSubject(asunto);
//            mail.setText(mensaje);
//            
//            Transport transportar = sesion.getTransport("smtp");
//            transportar.connect(correoEnvia,contrasena);
//            transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));          
//            transportar.close();
//            
//            JOptionPane.showMessageDialog(null, "Listo, revise su correo");
//            
//            
//        } catch (AddressException ex) {
//        } catch (MessagingException ex) {
//        }
	}
}
