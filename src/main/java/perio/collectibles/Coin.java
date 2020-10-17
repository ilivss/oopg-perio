package perio.collectibles;

import nl.han.ica.oopg.objects.Sprite;
import perio.PerioWorld;

public class Coin extends Collectible {
    public Coin(PerioWorld world) {
        super(world, new Sprite(PerioWorld.MEDIA_PATH.concat("collectibles/coinSprite.png")), 1, PerioWorld.MEDIA_PATH.concat("collectibles/coinSound.mp3"));
    }
}
