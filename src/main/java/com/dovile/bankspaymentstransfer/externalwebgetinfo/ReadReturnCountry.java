package com.dovile.bankspaymentstransfer.externalwebgetinfo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class ReadReturnCountry extends InfoFromIP {

    private final static Logger logger = Logger.getLogger(ReadReturnCountry.class.getName());

    public final String url = "http://ip-api.com/xml/";
    public final String country = "country";

    /**
     * @param url
     * @return return inputStream
     */
    InputStream getUrl(String url, String ipAddress) {
        URL obj = null;
        try {
            obj = new URL(url +ipAddress);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.76");
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");
            InputStream is = connection.getInputStream();
            return is;
        } catch (Exception e) {
            logger.severe("Error with path!" + e.getMessage());
            return null;
        }
    }

    /**
     * @return data from xml file, where will be type and rate
     */
   public String getCountry (String ipAddress) {
        String countryName = null;

        logger.info("Connetion for the parse data");
        InputStream is = getUrl(url, ipAddress);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
       DocumentBuilder db = null;
       try {
           db = dbf.newDocumentBuilder();
           Document doc = db.parse(is);
           NodeList errNodes = doc.getElementsByTagName(country);
           for (int i = 0; i < errNodes.getLength(); i++) {
               Element err = (Element) errNodes.item(i);
               countryName = err.getTextContent();
           }
       } catch (Exception e) {
           logger.severe("Problem with connetion, problem to parse data!" + e.getMessage());
       }
        return countryName;
    }
}
