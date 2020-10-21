package perio.tiles;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.tile.Tile;

/**
 * @author Geurian Bouwman & Iliass El Kaddouri
 *
 * LadderTile is een Tile object dat dient als een ladder.
 * Player objecten kunnen door deze Tile langzaam omhoog klimmen.
 */
public class LadderTile extends Tile {
    /**
     * Constructor
     *
     * @param sprite    De sprite die aan dit object gekoppeld moet worden.
     */
    public LadderTile(Sprite sprite) {
        super(sprite);
    }
}
