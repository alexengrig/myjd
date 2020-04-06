package dev.alexengrig.myjdi.filter;

import javax.lang.model.type.ReferenceType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class TypeFilter {
    protected final List<ReferenceType> types;

    public TypeFilter() {
        types = new ArrayList<>();
    }

    public List<ReferenceType> getTypes() {
        return types;
    }

    public void addClassFilter(ReferenceType type) {
        types.add(type);
    }

    public void addClassFilters(Collection<ReferenceType> types) {
        this.types.addAll(types);
    }
}
