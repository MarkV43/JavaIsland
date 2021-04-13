package com.pavogt.javaisland.component;

import java.awt.*;

public class Scrollable extends ScrollPane {
    private Panel panel;

    public Scrollable() {
        super(ScrollPane.SCROLLBARS_AS_NEEDED);

        panel = new Panel();
    }

    public Scrollable(int scrollbarDisplayPolicy) {
        super(scrollbarDisplayPolicy);
        panel = new Panel();
    }

    public Scrollable(LayoutManager layoutManager) {
        super(ScrollPane.SCROLLBARS_AS_NEEDED);

        panel = new Panel(layoutManager);
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
