/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.books;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public final class BookReader {

    private BookReader() {
    }

    public static String readBook(ItemStack book) {
        if (!book.hasItemMeta()) {
            book.setItemMeta(Bukkit.getItemFactory().getItemMeta(Material.WRITTEN_BOOK));
        }
        return readBook((BookMeta) book.getItemMeta());
    }

    public static String readBook(BookMeta bookMeta) {
        StringBuilder pages = new StringBuilder();
        for (String page : bookMeta.getPages()) {
            pages.append(page);
        }
        return pages.toString();
    }

}
