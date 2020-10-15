package perio;

import nl.han.ica.oopg.objects.GameObject;
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
        /**
         * Dit stukje code zorgt ervoor dat de view port binnen de boundaries van de view blijft.
         */
        if ((playerOne.getY() + playerTwo.getY())/ 2 < (float) PerioWorld.zoomHeight / 2 ){
            setY((float) PerioWorld.zoomHeight / 2);
        } else if ((playerOne.getY() + playerTwo.getY())/ 2  > PerioWorld.worldHeight - ((float) PerioWorld.zoomHeight / 2)) {
            setY(PerioWorld.worldHeight - ((float) PerioWorld.zoomHeight / 2));
        } else {
            setY((playerOne.getY() + playerTwo.getY())/ 2);
        }
    }

    @Override
    public void draw(PGraphics g) {

    }
}
