package perio.collectibles;

import nl.han.ica.oopg.objects.Sprite;
import perio.PerioWorld;

public class Gem extends Collectible {
    public Gem(PerioWorld world) {
        super(world, new Sprite(PerioWorld.MEDIA_PATH.concat("collectibles/gemSprite.png")), 2, PerioWorld.MEDIA_PATH.concat("collectibles/gemSound.mp3"));
    }
}
