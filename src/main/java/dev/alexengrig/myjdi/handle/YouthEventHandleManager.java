package dev.alexengrig.myjdi.handle;

import com.sun.jdi.event.ClassPrepareEvent;

import java.util.List;
import java.util.function.Consumer;

public interface YouthEventHandleManager {
    YouthClassPrepareHandle createClassPrepareHandle(Consumer<ClassPrepareEvent> handler);

    List<YouthClassPrepareHandle> classPrepareHandles();
}
