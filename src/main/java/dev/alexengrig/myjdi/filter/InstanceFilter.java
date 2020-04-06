package dev.alexengrig.myjdi.filter;

import com.sun.jdi.ObjectReference;

import java.util.ArrayList;
import java.util.List;

public abstract class InstanceFilter {
    protected final List<ObjectReference> instances;

    protected InstanceFilter() {
        instances = new ArrayList<>();
    }

    public List<ObjectReference> getInstances() {
        return instances;
    }

    public void addInstanceFilter(ObjectReference instance) {
        instances.add(instance);
    }

    public void addInstanceFilters(List<ObjectReference> instances) {
        this.instances.addAll(instances);
    }
}
