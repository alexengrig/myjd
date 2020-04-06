package dev.alexengrig.myjdi.filter;

import com.sun.jdi.ThreadReference;

import java.util.Collection;

public interface ThreadFilter {
    void addThreadFilter(ThreadReference thread);

    void addThreadFilters(Collection<ThreadReference> threads);
}
