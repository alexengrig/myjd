package dev.alexengrig.myjdi.request;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.request.ThreadDeathRequest;
import dev.alexengrig.myjdi.filter.ThreadFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ThreadDeathRequestSetting extends EventRequestSetting<ThreadDeathRequest> implements ThreadFilter {
    protected final List<ThreadReference> filterThreads;

    public ThreadDeathRequestSetting() {
        filterThreads = new ArrayList<>();
    }

    @Override
    protected void doApply(ThreadDeathRequest request) {
        super.doApply(request);
        applyThreadFilters(request);
    }

    protected void applyThreadFilters(ThreadDeathRequest request) {
        for (ThreadReference filterThread : filterThreads) {
            request.addThreadFilter(filterThread);
        }
    }

//    Thread filter

    @Override
    public void addThreadFilter(ThreadReference thread) {
        filterThreads.add(thread);
    }

    @Override
    public void addThreadFilters(Collection<ThreadReference> threads) {
        filterThreads.addAll(threads);
    }
}
