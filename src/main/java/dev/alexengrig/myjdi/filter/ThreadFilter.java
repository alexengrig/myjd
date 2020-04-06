package dev.alexengrig.myjdi.filter;

import com.sun.jdi.ThreadReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ThreadFilter {
    protected final List<ThreadReference> threads;

    public ThreadFilter() {
        threads = new ArrayList<>();
    }

    public List<ThreadReference> getThreads() {
        return threads;
    }

    public void addThreadFilter(ThreadReference thread) {
        threads.add(thread);
    }

    public void addThreadFilters(Collection<ThreadReference> threads) {
        this.threads.addAll(threads);
    }
}
