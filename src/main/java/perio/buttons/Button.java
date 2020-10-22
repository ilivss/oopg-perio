package perio.buttons;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.AnimatedSpriteObject;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.obstacles.IObstacle;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Geurian Bouw & Iliass El Kaddouri
 *
 * De abstracte knopklasse.
 */
public abstract class Button extends AnimatedSpriteObject implements ICollidableWithGameObjects {
    protected boolean isOn;
    protected Sound buttonSound;
    protected ArrayList<IObstacle> targets;   // Doelwitten

    /**
     * Constructor
     *
     * @param sprite        Sprite die gebruikt moet worden
     * @param buttonSound   Geluid dat moet klinken als de state van de knop (isOn) veranderd.
     */
    public Button(Sprite sprite, Sound buttonSound) {
        super(sprite, 2);
        this.isOn = false;
        this.buttonSound = buttonSound;
        this.targets = new ArrayList<>();

        setCurrentFrameIndex(0);
    }

    /**
     * Voegt doelwit toe aan knop.
     * @param target    Doelwit dat geschakeld moet worden met deze knop.
     */
    public void addTarget(IObstacle target) {
        targets.add(target);
    }

    /**
     * Speelt knop geluid af en voert de handleTarget() functie van elk doelwit van deze knop uit.
     */
    public void executeButtonAction() {
        // TODO Fix geluid, hij wordt pas afgespeeld wanneer player van de knop afgaat, terwijl hij eigenlijk afgespeeld  moet worden wanneer hij erop staat
        // Ik weet  waarom dit is: deze method wordt  constant opgeroepen in update(); waardoor het geluidje constant gerewind wordt. Hoe kunnen we dit anders doen?
        buttonSound.rewind();
        buttonSound.play();

        for (IObstacle t : targets) {
            t.handleTarget();
        }
    }

    @Override
    public void update() {
        if (isOn) {
            setCurrentFrameIndex(1);
        } else {
            setCurrentFrameIndex(0);
        }
    }

    @Override
    public abstract void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects);
}
