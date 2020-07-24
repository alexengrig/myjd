package dev.alexengrig.myjdi.request;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.request.EventRequest;

public interface YouthEventRequest extends EventRequest {
    static YouthEventRequest delegate(EventRequest request) {
        return new Delegate(request);
    }

    class Delegate implements YouthEventRequest {
        protected final EventRequest request;

        public Delegate(EventRequest request) {
            this.request = request;
        }

        @Override
        public boolean isEnabled() {
            return request.isEnabled();
        }

        @Override
        public void setEnabled(boolean val) {
            request.setEnabled(val);
        }

        @Override
        public void enable() {
            request.enable();
        }

        @Override
        public void disable() {
            request.disable();
        }

        @Override
        public void addCountFilter(int count) {
            request.addCountFilter(count);
        }

        @Override
        public void setSuspendPolicy(int policy) {
            request.setSuspendPolicy(policy);
        }

        @Override
        public int suspendPolicy() {
            return request.suspendPolicy();
        }

        @Override
        public void putProperty(Object key, Object value) {
            request.putProperty(key, value);
        }

        @Override
        public Object getProperty(Object key) {
            return request.getProperty(key);
        }

        @Override
        public VirtualMachine virtualMachine() {
            return request.virtualMachine();
        }

        @Override
        public String toString() {
            return request.toString();
        }
    }
}
