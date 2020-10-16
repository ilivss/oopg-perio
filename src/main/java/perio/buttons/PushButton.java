package perio.buttons;

import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import perio.PerioWorld;
import perio.Player;

import java.util.List;

public class PushButton extends Button {
    /**
     * Create a new AnimatedSpriteObject with a Sprite and set the amount of total frames.
     */
    public PushButton() {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("buttons/pushButtonSprite.png")));
    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects)  {
        for (GameObject go : collidedGameObjects) {
            if (go instanceof Player) {
                go.setY(this.y - go.getHeight() + 20);
                value = true;
                executeButtonAction();
            }
        }
    }

    @Override
    public void update() {
        super.update();

        // Reset button state
        value = false;
    };
}
