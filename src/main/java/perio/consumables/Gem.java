package perio.consumables;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import perio.PerioWorld;
import perio.Player;

public class Gem extends Consumable {

    public Gem(PerioWorld world, Sound gemSound) {
        super(new Sprite(PerioWorld.MEDIA_PATH.concat("consumables/gemSprite.png")), world, gemSound);
    }

    @Override
    protected void handleConsume(Player player) {
        if (player.getPlayerNo() == 2) {
            // Speel geluid af
            super.consumableSound.rewind();
            super.consumableSound.play();

            // Laat speler deze consumable verzamelen
            player.setPoints(player.getPoints() + 1);

            // Verwijder deze consumable uit de wereld
            super.world.deleteGameObject(this);
        }
    }
}
