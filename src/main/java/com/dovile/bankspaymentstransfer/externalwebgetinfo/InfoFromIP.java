package com.dovile.bankspaymentstransfer.externalwebgetinfo;

import java.io.InputStream;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public abstract class InfoFromIP {
    /**
     * @return InputStream with DocumentBuilderFactory parse the data by tag
     */
    abstract InputStream getUrl(String url, String ipAddress);

    /**
     * @return just a string, which has country name by ip address
     */
    public abstract String getCountry(String ipAddress);
}
