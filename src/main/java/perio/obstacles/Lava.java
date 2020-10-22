package perio.obstacles;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

import java.util.List;

/**
 * @author Geurian Bouw & Iliass El Kaddouri
 *
 * Lava is een spelobject dat lava representeert. Dit object kan botsen met een object van de klasse Player.
 * Een Player object verliest levens het collide met dit object.
 */
public class Lava extends SpriteObject implements IObstacle, ICollidableWithGameObjects {
    private Sound lavaSound;

    /**
     * Constructor
     *
     * @param lavaSound Geluid dat moet klinken wanneer een Player object collide met dit object.
     */
    public Lava(Sound lavaSound) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("obstacles/lavaSprite.png")));
        this.lavaSound = lavaSound;
    }

    /**
     * Speelt lava geluid af.
     */
    @Override
    public void handleTarget() {
        lavaSound.rewind();
        lavaSound.play();
    }

    @Override
    public void update() {
        // Leeg
    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for (GameObject go : collidedGameObjects) {
            if (go instanceof Player) {
                handleTarget();

                // Verzet Player object op de map naar links of naar rechts afhankelijk van de collision kant.
                if (go.getCenterX() > this.getCenterX()) {
                    go.setX(go.getX() - 100);
                } else {
                    go.setX(go.getX() + 100);
                }

                // Player  object verliest 1 leven
                ((Player) go).setHealth(((Player) go).getHealth() - 1);
            }
        }
    }
}
