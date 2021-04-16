package com.pavogt.javaisland.component;

import java.awt.*;

public class Scrollable extends ScrollPane {
    private final Panel panel;

    public Scrollable() {
        super(ScrollPane.SCROLLBARS_AS_NEEDED);

        panel = new Panel();

        super.add(panel);
    }

    public Scrollable(int scrollbarDisplayPolicy) {
        super(scrollbarDisplayPolicy);
        panel = new Panel();
        super.add(panel);
    }

    public Scrollable(LayoutManager layoutManager) {
        super(ScrollPane.SCROLLBARS_AS_NEEDED);
        panel = new Panel(layoutManager);
//        panel.setPreferredSize(new Dimension(0, 0));
        super.add(panel);
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        panel.setLayout(layoutManager);
    }

    @Override
    public Component add(Component comp) {
        return panel.add(comp);
    }

    public Panel getPanel() {
        return panel;
    }
}
