package loanmain;

import com.google.common.eventbus.EventBus;

public class Initiator {

    private EventBus eventBus = null;

    public Initiator(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public EventBus GetEventBus() {
        return this.eventBus;
    }
}