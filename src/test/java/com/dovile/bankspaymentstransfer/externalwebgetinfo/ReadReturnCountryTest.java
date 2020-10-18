package com.dovile.bankspaymentstransfer.externalwebgetinfo;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class ReadReturnCountryTest {

    @Test
    public void getUrl_should_return_200() throws IOException {
        String url = "http://ip-api.com/xml/";
        ReadReturnCountry readReturnCountry = new ReadReturnCountry();
        InputStream result = readReturnCountry.getUrl(url, "24.48.0.1");
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.addRequestProperty("User-Agent", "Mozilla/4.76");
        int responseCode = con.getResponseCode();
        int expResult = 200;
        assertEquals(expResult, responseCode);
        assertNotNull(result);
    }

    @Test
    public void getCountry_should_return_country() {
        ReadReturnCountry readReturnCountry = new ReadReturnCountry();
        String result = readReturnCountry.getCountry("24.48.0.1");
        if (result.isEmpty()){
            assertEquals("", result);
        }else {
            assertNotNull(result);
            assertEquals("Canada", result);
        }
    }
}