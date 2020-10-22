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

/**
 * @author Geurian Bouw & Iliass El Kaddouri
 *
 * Een Lift is een spelobject dat horizontaal in de wereld beweegt, deze actie kan geactiveerd worden door handleTarget() aan te roepen.
 */
public class Lift extends SpriteObject implements ICollidableWithGameObjects, IObstacle {
    private PerioWorld world;
    private Sound liftSound;
    private float minHeight;
    private float maxHeight;

    /**
     * Constructor
     *
     * @param world             Referentie naar de wereld.
     * @param liftSound         Geluid dat geklonken moet worden als de lift beweegt.
     * @param startPosition     Y coordinaat als start positie van de lift.
     * @param endPosition       Y coordinaat als eind positie van de lift.
     */
    public Lift(PerioWorld world, Sound liftSound, float startPosition, float endPosition) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("obstacles/liftSprite.png")));
        this.world = world;
        this.liftSound = liftSound;
        this.minHeight = startPosition;
        this.maxHeight = endPosition;

        // setGravity zodat lift vanzelf weer naar beneden valt.
        setGravity(0.5f);
    }

    /**
     * Laat de lift bewegen van de start positie naar de eind positie.
     */
    @Override
    public void handleTarget() {
        // TODO Fix geluid, hij wordt pas afgespeeld wanneer player van de knop afgaat, terwijl hij eigenlijk afgespeeld  moet worden wanneer hij erop staat
        // Ik weet  waarom dit is: deze method wordt  constant opgeroepen in update(); waardoor het geluidje constant gerewind wordt. Hoe kunnen we dit anders doen?
        liftSound.rewind();
        liftSound.play();

        setDirectionSpeed(0, 5);
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
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for (GameObject go : collidedGameObjects) {
            if (go instanceof Player) {
                if (go.getY() + go.getHeight() < getCenterY()) {
                    go.setY(getY() - go.getHeight());
                }
            }
        }
    }
}
