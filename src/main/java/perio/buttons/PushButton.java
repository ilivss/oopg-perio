package perio.buttons;

import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

import java.util.List;

/**
 * @author Geurian Bouw & Iliass El Kaddouri
 *
 * Een pushbutton is een spelobject dat geactiveert kan worden als en alleen als een object van de spelersklasse
 * er bovenop staat.
 */
public class PushButton extends Button {
    /**
     * Constructor
     *
     * @param pushButtonSound   Geluid dat moet klinken wanneer de knop geactiveerd wordt.
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
