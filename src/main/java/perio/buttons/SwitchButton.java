package perio.buttons;

import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

import java.util.List;

public class SwitchButton extends Button {
    /**
     * Create a new AnimatedSpriteObject with a Sprite and set the amount of total frames.
     */
    public SwitchButton(Sound switchButtonsound) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("buttons/switchButtonSprite.png")), switchButtonsound);
    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {

        for (GameObject go : collidedGameObjects) {
            if (go instanceof Player) {
                if (go.getX() > go.getPrevX()) {
                    super.isOn = true;
                    super.executeButtonAction();
                } else {
                    super.isOn = false;
                }
            }
        }
    }
}
