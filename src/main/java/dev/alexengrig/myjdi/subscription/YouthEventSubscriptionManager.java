package dev.alexengrig.myjdi.subscription;

import dev.alexengrig.myjdi.event.YouthBreakpointEvent;
import dev.alexengrig.myjdi.event.YouthClassPrepareEvent;

import java.util.function.Consumer;

public interface YouthEventSubscriptionManager {
    YouthEventSubscription subscribe();

    void subscribeOnClassPrepare(Consumer<YouthClassPrepareEvent> listener);

    void notifyOfClassPrepare(YouthClassPrepareEvent event);

    void subscribeOnBreakpoint(Consumer<YouthBreakpointEvent> listener);

    void notifyOfBreakpoint(YouthBreakpointEvent event);
}
