package perio.buttons;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.AnimatedSpriteObject;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.obstacles.IObstacle;

import java.util.ArrayList;
import java.util.List;

public abstract class Button extends AnimatedSpriteObject implements ICollidableWithGameObjects {
    protected boolean isOn;
    protected Sound buttonSound;
    protected ArrayList<IObstacle> targets;   // Doelwitten

    /**
     * Create a new AnimatedSpriteObject with a Sprite and set the amount of total frames.
     *
     * @param sprite The Sprite to be used
     */
    public Button(Sprite sprite, Sound buttonSound) {
        super(sprite, 2);
        this.isOn = false;
        this.buttonSound = buttonSound;
        this.targets = new ArrayList<>();

        setCurrentFrameIndex(0);
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


    public void executeButtonAction() {
        // TODO Fix geluid, hij wordt pas afgespeeld wanneer player van de knop afgaat, terwijl hij eigenlijk afgespeeld  moet worden wanneer hij erop staat
        // Ik weet  waarom dit is: deze method wordt  constant opgeroepen in update(); waardoor het geluidje constant gerewind wordt. Hoe kunnen we dit anders doen?
        buttonSound.rewind();
        buttonSound.play();

        for (IObstacle t : targets) {
            t.handleTarget();
        }
    }

    public void addTarget(IObstacle target) {
        targets.add(target);
    }
}
