package dev.alexengrig.myjdi.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ClassExclusionFilter {
    protected final List<String> classExclusionPatterns;

    public ClassExclusionFilter() {
        classExclusionPatterns = new ArrayList<>();
    }

    public List<String> getClassExclusionPatterns() {
        return classExclusionPatterns;
    }

    public void addClassExclusionFilter(String classPattern) {
        classExclusionPatterns.add(classPattern);
    }

    public void addClassExclusionFilters(Collection<String> classPatterns) {
        classExclusionPatterns.addAll(classPatterns);
    }
}
