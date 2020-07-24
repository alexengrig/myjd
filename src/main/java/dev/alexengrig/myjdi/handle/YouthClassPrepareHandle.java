package dev.alexengrig.myjdi.handle;

import com.sun.jdi.event.ClassPrepareEvent;

public interface YouthClassPrepareHandle extends YouthEventHandle<ClassPrepareEvent> {
    @Override
    default Class<? super ClassPrepareEvent> getTargetType() {
        return ClassPrepareEvent.class;
    }
}
