/*
 *  Created by Filip P. on 2/12/15 12:42 AM.
 */

package me.pauzen.alphacore.places;

public interface PlaceGetter<E> {
    
    public Place getPlace(EventContainer<E> eventContainer);
    
}
