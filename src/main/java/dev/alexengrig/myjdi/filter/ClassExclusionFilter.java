package dev.alexengrig.myjdi.filter;

import java.util.Collection;

public interface ClassExclusionFilter {
    void addClassExclusionFilter(String classPattern);

    void addClassExclusionFilters(Collection<String> classPatterns);
}
