package com.dovile.bankspaymentstransfer.externalwebforinfo;

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
     * @return just new hashMap, but it can be override and change value
     */
    public abstract String getCountry(String ipAddress);
}
