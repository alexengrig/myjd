package dev.alexengrig.myjdi.filter;

public abstract class CountFilter {
    protected int count;

    public int getCount() {
        return count;
    }

    public void addCountFilter(int count) {
        this.count = count;
    }
}
