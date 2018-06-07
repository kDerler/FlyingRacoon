package loanmain;

import java.util.EventObject;

public class Event extends EventObject {

    public Event(Object source) {
        super(source);
    }

    public Object getChangedItem() {
        return getSource();
    }
}