package perio.consumables;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

public class Health extends Consumable {
    public Health(PerioWorld world, Sound healthSound) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("consumables/healthSprite.png")), world, healthSound);
    }

    @Override
    protected void handleConsume(Player player) {
        // Speel geluid af
        super.consumableSound.rewind();
        super.consumableSound.play();

        // Speler krijgt 1 health erbij.
        player.setHealth(player.getHealth() + 1);

        // Verwijder deze consumable uit de wereld
        super.world.deleteGameObject(this);
    }
}
