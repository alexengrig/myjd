package dev.alexengrig.myjdi.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MyTabbedPane extends JTabbedPane {
    public MyTabbedPane() {
        super();
    }

    public MyTabbedPane(int tabPlacement) {
        super(tabPlacement);
    }

    public MyTabbedPane(int tabPlacement, int tabLayoutPolicy) {
        super(tabPlacement, tabLayoutPolicy);
    }

    @Override
    public void insertTab(String title, Icon icon, Component component, String tip, int index) {
        super.insertTab(title, icon, component, tip, index);
        int tabIndex = indexOfComponent(component);
        JComponent tabComponent = createTabComponent(title, icon);
        setTabComponentAt(tabIndex, tabComponent);
    }

    private JComponent createTabComponent(String title, Icon icon) {
        JPanel tabComponent = new JPanel(new GridBagLayout());
        tabComponent.setOpaque(false);
        JLabel lblTitle = new JLabel(title);
        JButton btnClose = new CloseButton(ignore -> {
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        tabComponent.add(lblTitle, gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        tabComponent.add(new CloseIcon(), gbc);
        return tabComponent;
    }


    public static class CloseButton extends JButton {
        public CloseButton(ActionListener actionListener) {
            super(new CloseIcon());
            addActionListener(actionListener);
        }
    }

    public static class CloseIcon extends JPanel implements Icon {
        private final int width = 8;
        private final int height = 8;
        private final BasicStroke stroke = new BasicStroke(1);

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            paintIcon(this, g, 0, 0);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setColor(Color.WHITE);
            g2d.fillRect(x + 1, y + 1, width - 2, height - 2);

            g2d.setColor(Color.BLACK);
            g2d.drawRect(x + 1, y + 1, width - 2, height - 2);

            g2d.setColor(Color.RED);

            g2d.setStroke(stroke);
            g2d.drawLine(x + 10, y + 10, x + width - 10, y + height - 10);
            g2d.drawLine(x + 10, y + height - 10, x + width - 10, y + 10);

            g2d.dispose();
        }

        @Override
        public int getIconWidth() {
            return width;
        }

        @Override
        public int getIconHeight() {
            return height;
        }
    }
}
