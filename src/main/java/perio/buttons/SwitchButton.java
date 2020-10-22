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
 * Een switchbutton is een spelobject dat geschakeld kan worden wanneer een speler er van links naar rechts of visa versa doorheen loopt.
 */
public class SwitchButton extends Button {
    /**
     * Constructor
     *
     * @param switchButtonsound Geluid dat moet klinken wanneer de knop geactiveerd wordt.
     */
    public SwitchButton(Sound switchButtonsound) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("buttons/switchButtonSprite.png")), switchButtonsound);
    }

    @Override
    public void update() {
        if (super.isOn) {
            setCurrentFrameIndex(1);
            super.executeButtonAction();

        } else {
            setCurrentFrameIndex(0);
        }
    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {

        for (GameObject go : collidedGameObjects) {
            if (go instanceof Player) {
                if (go.getX() > go.getPrevX()) {
                    super.isOn = true;
                } else {
                    super.isOn = false;
                }
            }
        }
    }
}
