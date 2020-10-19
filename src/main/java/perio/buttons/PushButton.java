package perio.buttons;

import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

import java.util.List;

public class PushButton extends Button {
    /**
     * Create a new AnimatedSpriteObject with a Sprite and set the amount of total frames.
     */
    public PushButton(Sound pushButtonSound) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("buttons/pushButtonSprite.png")), pushButtonSound);
    }

    @Override
    public void update() {
        super.update();

        // Reset button state om pushbutton effect te krijgen.
        isOn = false;
    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for (GameObject go : collidedGameObjects) {
            if (go instanceof Player) {
                go.setY(this.y - go.getHeight() + 20);
                isOn = true;
                executeButtonAction();
            }
        }
    }
}
