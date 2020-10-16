package perio.buttons;

import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import perio.PerioWorld;
import perio.Player;

import java.util.List;

public class SwitchButton extends Button {
    /**
     * Create a new AnimatedSpriteObject with a Sprite and set the amount of total frames.
     */
    public SwitchButton() {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("buttons/switchButtonSprite.png")));
    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for (GameObject go : collidedGameObjects) {
            if (go instanceof Player) {
                if (go.getCenterX() < getCenterX()) {
                    value = true;
                    executeButtonAction();
                } else if (go.getCenterX() > getCenterX()) {
                    value = false;
                }
            }
        }
    }
}
