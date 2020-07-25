package dev.alexengrig.myjdi.queue;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventSet;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface YouthEventSet extends EventSet {
    static YouthEventSet delegate(EventSet event) {
        return new Delegate(event);
    }

    @Override
    YouthEventIterator eventIterator();

    class Delegate implements YouthEventSet {
        protected final EventSet set;

        public Delegate(EventSet set) {
            this.set = set;
        }

        @Override
        public YouthEventIterator eventIterator() {
            return new YouthEventIterator.Delegate(set.eventIterator());
        }

        @Override
        public int suspendPolicy() {
            return set.suspendPolicy();
        }

        @Override
        public void resume() {
            set.resume();
        }

        @Override
        public VirtualMachine virtualMachine() {
            return set.virtualMachine();
        }

        @Override
        public String toString() {
            return set.toString();
        }

        @Override
        public int size() {
            return set.size();
        }

        @Override
        public boolean isEmpty() {
            return set.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return set.contains(o);
        }

        @Override
        public Iterator<Event> iterator() {
            return set.iterator();
        }

        @Override
        public Object[] toArray() {
            return set.toArray();
        }

        @Override
        @SuppressWarnings("SuspiciousToArrayCall")
        public <T> T[] toArray(T[] a) {
            return set.toArray(a);
        }

        @Override
        public boolean add(Event event) {
            return set.add(event);
        }

        @Override
        public boolean remove(Object o) {
            return set.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return set.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends Event> c) {
            return set.addAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return set.retainAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return set.removeAll(c);
        }

        @Override
        public void clear() {
            set.clear();
        }

        @Override
        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        public boolean equals(Object o) {
            return set.equals(o);
        }

        @Override
        public int hashCode() {
            return set.hashCode();
        }

        @Override
        public Spliterator<Event> spliterator() {
            return set.spliterator();
        }

        @Override
        public boolean removeIf(Predicate<? super Event> filter) {
            return set.removeIf(filter);
        }

        @Override
        public Stream<Event> stream() {
            return set.stream();
        }

        @Override
        public Stream<Event> parallelStream() {
            return set.parallelStream();
        }

        @Override
        public void forEach(Consumer<? super Event> action) {
            set.forEach(action);
        }
    }
}
