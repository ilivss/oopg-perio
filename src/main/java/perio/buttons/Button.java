package perio.buttons;

import nl.han.ica.oopg.collision.CollisionSide;
import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.exceptions.TileNotFoundException;
import nl.han.ica.oopg.objects.AnimatedSpriteObject;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import perio.Player;
import perio.obstacles.ITarget;

import java.util.ArrayList;
import java.util.List;

public abstract class Button extends AnimatedSpriteObject implements ICollidableWithGameObjects {
    protected boolean value;
    protected ArrayList<ITarget> targets;   // Doelwitten

    /**
     * Create a new AnimatedSpriteObject with a Sprite and set the amount of total frames.
     *
     * @param sprite      The Sprite to be used
     */
    public Button(Sprite sprite) {
        super(sprite, 2);
        targets = new ArrayList<>();
        setCurrentFrameIndex(0);
    }

    @Override
    public abstract void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects);

    @Override
    public void update() {

        if (value) {
            setCurrentFrameIndex(1);
            executeButtonAction();
        } else {
            setCurrentFrameIndex(0);
        }
    };

    public void executeButtonAction() {
        for (ITarget t : targets) {
            t.handleTarget();
        }
    }

    public void addTarget (ITarget target) {
        targets.add(target);
    }

    public boolean getValues() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
