/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore.utils.commonnms;

public enum ClientVersion {

    v1_7_2(4),
    v1_7_6(5),
    v1_8(47);
    ;
    
    private int clientVersion;
    
    ClientVersion(int clientVersion) {
        this.clientVersion = clientVersion;
    }

    public int getClientVersion() {
        return clientVersion;
    }
    
    public static ClientVersion valueOf(int clientVersion) {
        for (ClientVersion version : values()) {
            if (version.getClientVersion() == clientVersion) {
                return version;
            }
        }
        return null;
    }
}
