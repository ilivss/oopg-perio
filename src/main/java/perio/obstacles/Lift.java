package perio.obstacles;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import nl.han.ica.oopg.sound.Sound;
import nl.han.ica.oopg.userinput.IKeyInput;
import perio.PerioWorld;
import perio.Player;

import java.util.List;

public class Lift extends SpriteObject implements ICollidableWithGameObjects, IObstacle {
    private PerioWorld world;
    private Sound liftSound;
    private float minHeight;
    private float maxHeight;

    /**
     * Create a new SpriteObject with a Sprite object.
     * @param world asdasdasd
     * @param liftSound     Geluid dat afgespeelt moet worden wanneer lift beweegt.
     * @param minHeight     Y Coordinaat wanneer lift omlaag is.
     * @param maxHeight     Y Coordinaat wanneer lift omhoog is.
     */
    public Lift(PerioWorld world, Sound liftSound, float minHeight, float maxHeight) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("obstacles/liftSprite.png")));
        this.world = world;
        this.liftSound = liftSound;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        setGravity(0.5f);
    }

    @Override
    public void update() {
        if (getY() < maxHeight) {
            setSpeed(0);
            setY(maxHeight);
        } else if (getY() >= minHeight) {
            setY(minHeight);
        }
    }

    @Override
    public void handleTarget() {
        // TODO Fix geluid, hij wordt pas afgespeeld wanneer player van de knop afgaat, terwijl hij eigenlijk afgespeeld  moet worden wanneer hij erop staat
        liftSound.rewind();
        liftSound.play();
        setDirectionSpeed(0, 5);

    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for (GameObject go : collidedGameObjects) {
            if (go instanceof Player) {
                go.setY(getY() - go.getHeight());
                if (getY() < maxHeight || getY() > minHeight) {
                    go.setX(getCenterX());
                }
            }
        }
    }
}
