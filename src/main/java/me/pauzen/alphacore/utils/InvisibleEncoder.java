/*
 *  Created by Filip P. on 2/21/15 2:46 PM.
 */

package me.pauzen.alphacore.utils;

import org.bukkit.ChatColor;

public class InvisibleEncoder {

    /**
     * Max value of ASCII character is 255 (2^8 - 1), this means that two hex values (2^4)
     * can represent any possible character in ASCII.
     * This is useful because Minecraft uses a single hexadecimal value for a chat color, (0x0 = BLACK, 0x6 = GOLD, 0xA = LIGHT GREEN, 0xF = WHITE).
     * In Minecraft chat colors are invisible and their existence is only known of to the player when they see the effects it has on text.
     * Combining different chat colors has no effect on their visibility.
     * <p>
     * (WHITE << 4) + WHITE = 255, (BLACK << 4) + BLACK = 0.
     */

    public static String encode(String text) {
        char[] chars = text.toCharArray();

        StringBuilder encoded = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
            int charValue = (int) chars[i];

            String lsh = Integer.toHexString(charValue & 0xF);
            String msh = Integer.toHexString(charValue >> 4);

            String invisiblePart = ChatColor.getByChar(msh) + "" + ChatColor.getByChar(lsh);

            encoded.append(invisiblePart);
        }

        return encoded.toString();
    }

    public static String decode(String encoded) {

        encoded = encoded.replace("\u00a7", "");

        StringBuilder decoded = new StringBuilder();

        while (!encoded.isEmpty()) {
            String sectionToProcess = encoded.substring(0, 2);

            encoded = encoded.substring(2, encoded.length());

            char lsh = sectionToProcess.charAt(1);
            char msh = sectionToProcess.charAt(0);

            int charValue = (Integer.parseInt(msh + "", 16) << 4) + Integer.parseInt(lsh + "", 16);

            decoded.append((char) charValue);
        }

        return decoded.toString();
    }
    
    public static boolean contains(String string1, String string2) {
        return string1.contains(encode(string2));
    }
    
    public static int indexOf(String string1, String string2) {
        return string1.indexOf(encode(string2));
    }

}
