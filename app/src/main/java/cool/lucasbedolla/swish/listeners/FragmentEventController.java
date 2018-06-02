package cool.lucasbedolla.swish.listeners;

import android.view.View;

public interface FragmentEventController {
    void submitEvent(int fragmentPos, View v, int action);
}