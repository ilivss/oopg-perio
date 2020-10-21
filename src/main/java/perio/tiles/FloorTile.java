package perio.tiles;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.tile.Tile;

/**
 * @author Geurian Bouwman & Iliass El Kaddouri
 *
 * FloorTile is een Tile object dat dient als vloer.
 * Player objecten kunnen niet door deze Tile heen bewegen.
 */
public class FloorTile extends Tile {
    /**
     * Constructor
     *
     * @param sprite    De sprite die aan dit object gekoppeld moet worden.
     */
    public FloorTile(Sprite sprite) {
        super(sprite);
    }
}
