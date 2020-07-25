package dev.alexengrig.myjdi.handle;

import com.sun.jdi.event.ClassPrepareEvent;
import dev.alexengrig.myjdi.event.YouthClassPrepareEvent;

public interface YouthClassPrepareHandle extends YouthEventHandle<YouthClassPrepareEvent> {
    @Override
    default Class<? super ClassPrepareEvent> getTargetType() {
        return ClassPrepareEvent.class;
    }
}
