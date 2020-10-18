package com.dovile.bankspaymentstransfer.externalwebforinfo;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class GetInfoFromIPFactory {

    /**
     *
     * @return new ReadReturnCountry(), but if where will be more page, where will be possibility add new class with other source
     * then need add condition in this method
     */
    public InfoFromIP getInfoFromIP(){
        return new ReadReturnCountry();
    }
}
