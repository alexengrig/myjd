package dev.alexengrig.myjdi.request;

import com.sun.jdi.request.ClassPrepareRequest;
import dev.alexengrig.myjdi.filter.ClassExclusionFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClassPrepareRequestSetting extends EventRequestSetting<ClassPrepareRequest> implements ClassExclusionFilter {
    protected final List<String> filterClassExclusionPatterns;

    public ClassPrepareRequestSetting() {
        filterClassExclusionPatterns = new ArrayList<>();
    }

    @Override
    protected void doApply(ClassPrepareRequest request) {
        super.doApply(request);
        applyClassExclusionFilters(request);
    }

    protected void applyClassExclusionFilters(ClassPrepareRequest request) {
        for (String filterClassPattern : filterClassExclusionPatterns) {
            request.addClassExclusionFilter(filterClassPattern);
        }
    }

//    Class exclusion filter

    @Override
    public void addClassExclusionFilter(String classPattern) {
        filterClassExclusionPatterns.add(classPattern);
    }

    @Override
    public void addClassExclusionFilters(Collection<String> classPatterns) {
        filterClassExclusionPatterns.addAll(classPatterns);
    }
}
