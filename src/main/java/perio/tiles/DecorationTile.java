package perio.tiles;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.tile.Tile;

/**
 * @author Geurian Bouwman & Iliass El Kaddouri
 *
 * DecorationTile is een Tile object dat dient ter decoratie van de wereld.
 * Dit object heeft verder geen functie.
 */
public class DecorationTile extends Tile {
    /**
     * Constructor
     *
     * @param sprite    De sprite die aan dit object gekoppeld moet worden.
     */
    public DecorationTile(Sprite sprite) {
        super(sprite);
    }
}
