package dev.alexengrig.myjdi.request;

import com.sun.jdi.request.EventRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EventRequestSetting<R extends EventRequest> {
    protected final Map<Object, Object> properties;

    protected boolean enabled;
    protected int countFilter;
    protected int suspendPolicy;

    public EventRequestSetting() {
        properties = new HashMap<>();
    }

    public void apply(R request) {
        final boolean wasEnabled = request.isEnabled();
        if (wasEnabled) {
            request.disable();
        }
        doApply(request);
        if (wasEnabled || enabled) {
            request.enable();
        }
    }

    protected void doApply(R request) {
        applyProperties(request);
        applyCountFilter(request);
        applySuspendPolice(request);
    }

    protected void applyProperties(R request) {
        for (Object key : properties.keySet()) {
            request.putProperty(key, properties.get(key));
        }
    }

    protected void applyCountFilter(R request) {
        if (countFilter > 0) {
            request.addCountFilter(countFilter);
        }
    }

    protected void applySuspendPolice(R request) {
        request.setSuspendPolicy(suspendPolicy);
    }

//    Properties

    public Object addProperty(Object key, Object value) {
        return properties.put(key, value);
    }

    public Object getProperty(Object key) {
        return properties.get(key);
    }

    public Object removeProperty(Object key) {
        return properties.remove(key);
    }

    public boolean removeProperty(Object key, Object value) {
        return properties.remove(key, value);
    }

    public boolean containsPropertyKey(Object key) {
        return properties.containsKey(key);
    }

    public boolean containsPropertyValue(Object value) {
        return properties.containsValue(value);
    }

//    Enable

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean value) {
        enabled = value;
    }

    public void enable() {
        enabled = true;
    }

    public boolean isDisabled() {
        return !enabled;
    }

    public void setDisabled(boolean value) {
        enabled = !value;
    }

    public void disable() {
        enabled = false;
    }

//    Count filter

    public int getCountFilter() {
        return countFilter;
    }

    public void setCountFilter(int value) {
        countFilter = value;
    }

//    Suspend policy

    public int getSuspendPolicy() {
        return suspendPolicy;
    }

    public void setSuspendPolicy(int value) {
        suspendPolicy = value;
    }

    public boolean isSuspendNone() {
        return EventRequest.SUSPEND_NONE == suspendPolicy;
    }

    public void setSuspendNone() {
        suspendPolicy = EventRequest.SUSPEND_NONE;
    }

    public boolean isSuspendEventThread() {
        return EventRequest.SUSPEND_EVENT_THREAD == suspendPolicy;
    }

    public void setSuspendEventThread() {
        suspendPolicy = EventRequest.SUSPEND_EVENT_THREAD;
    }

    public boolean isSuspendAll() {
        return EventRequest.SUSPEND_ALL == suspendPolicy;
    }

    public void setSuspendAll() {
        suspendPolicy = EventRequest.SUSPEND_ALL;
    }

//

    @Override
    public String toString() {
        return "EventRequestSetting{" +
                "properties=" + properties +
                ", enabled=" + enabled +
                ", countFilter=" + countFilter +
                ", suspendPolicy=" + suspendPolicy +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventRequestSetting<?> that = (EventRequestSetting<?>) o;
        return enabled == that.enabled &&
                countFilter == that.countFilter &&
                suspendPolicy == that.suspendPolicy &&
                Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties, enabled, countFilter, suspendPolicy);
    }
}
