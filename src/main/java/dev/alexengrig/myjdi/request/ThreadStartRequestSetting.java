package dev.alexengrig.myjdi.request;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.request.ThreadStartRequest;
import dev.alexengrig.myjdi.filter.ThreadFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ThreadStartRequestSetting extends EventRequestSetting<ThreadStartRequest> implements ThreadFilter {
    protected final List<ThreadReference> filterThreads;

    public ThreadStartRequestSetting() {
        filterThreads = new ArrayList<>();
    }

    @Override
    protected void doApply(ThreadStartRequest request) {
        super.doApply(request);
        applyThreadFilters(request);
    }

    protected void applyThreadFilters(ThreadStartRequest request) {
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
