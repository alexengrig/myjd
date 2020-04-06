package dev.alexengrig.myjdi.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SourceNameFilter {
    protected final List<String> sourceNamePatterns;

    public SourceNameFilter() {
        sourceNamePatterns = new ArrayList<>();
    }

    public List<String> getSourceNamePatterns() {
        return sourceNamePatterns;
    }

    public void addSourceNameFilter(String sourceNamePattern) {
        sourceNamePatterns.add(sourceNamePattern);
    }

    public void addSourceNameFilters(Collection<String> sourceNamePatterns) {
        this.sourceNamePatterns.addAll(sourceNamePatterns);
    }
}
