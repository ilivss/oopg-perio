package perio.obstacles;

import nl.han.ica.oopg.objects.Sprite;
import perio.PerioWorld;
import perio.buttons.PushButton;

public class DummyTarget extends PushButton implements ITarget {
    @Override
    public void handleTarget() {
        setValue(true);
    }
}
