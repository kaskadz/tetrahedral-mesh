package common;

public class Attributes {
    public static final String XY = "xy";
    public static final String LABEL = "ui.label";
    public static final String CLASS = "ui.class";
    public static final String STYLESHEET = "ui.stylesheet";
    public static final String ICON = "ui.icon";
    public static final String FROZEN_LAYOUT = "layout.frozen";

    public static final String LEVEL = "custom.node.level";

    public static class EdgeType {
        public static final String PARENT = "custom.edge.parent";
        public static final String SAME_LEVEL = "custom.edge.samelvl";
    }

    public static class NodeType {
        public static final String REGULAR = "custom.node.regular";
        public static final String INTERIOR = "custom.node.interior";
    }
}
