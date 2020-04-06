package dev.alexengrig.myjdi.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ClassPatternFilter {
    protected final List<String> classPatterns;

    public ClassPatternFilter() {
        classPatterns = new ArrayList<>();
    }

    public List<String> getClassPatterns() {
        return classPatterns;
    }

    public void addClassFilter(String classPattern) {
        classPatterns.add(classPattern);
    }

    public void addClassFilters(Collection<String> classPatterns) {
        this.classPatterns.addAll(classPatterns);
    }
}
