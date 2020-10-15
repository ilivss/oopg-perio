package perio;

import nl.han.ica.oopg.objects.GameObject;
import perio.players.Player;
import processing.core.PGraphics;

public class FollowObject extends GameObject {
    private final Player playerOne;
    private final Player playerTwo;

    public FollowObject(PerioWorld world, Player playerOne, Player playerTwo) {
        super();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        setX(840f / 2);
        setY((playerOne.getY() + playerTwo.getY()) / 2 );
    }

    @Override
    public void update() {
        setY(Math.min(350, (playerOne.getY() + playerTwo.getY()) / 2 ));
    }

    @Override
    public void draw(PGraphics g) {

    }
}
