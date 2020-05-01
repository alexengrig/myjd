package dev.alexengrig.myjdi.ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class JStackFramePane extends JPanel {
    private final List<Consumer<Integer>> listeners;
    private final DefaultListModel<String> model;
    private final JList<String> list;

    public JStackFramePane() {
        listeners = new ArrayList<>();
        model = new DefaultListModel<>();
        list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(new SelectionListener());
        add(list);
    }

    public void updateValues(List<String> values) {
        model.removeAllElements();
        for (String value : values) {
            model.addElement(value);
        }
        list.setSelectedIndex(0);
    }

    public void addSelectionListener(Consumer<Integer> listener) {
        listeners.add(listener);
    }

    private class SelectionListener implements ListSelectionListener {
        private Integer index = list.getSelectedIndex();

        @Override
        public void valueChanged(ListSelectionEvent ignore) {
            int selectedIndex = list.getSelectedIndex();
            if (index != selectedIndex) {
                for (Consumer<Integer> listener : listeners) {
                    listener.accept(selectedIndex);
                }
                index = selectedIndex;
            }
        }
    }
}
