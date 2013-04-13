package caa4444.hideCraft;

public enum Items {
    GLOVES(29, 25875, "Gloves"),
    BOOTS(33, 25821, "Boots"),
    BODY(37, 1131, "Bodies"),
    SHIELD(41, 25808, "Shields");

    private final int WIDGET_ID;
    private final int ITEM_ID;
    private final String ITEM_NAME;

    Items(int wid, int iid, String name) {
        WIDGET_ID = wid;
        ITEM_ID = iid;
        ITEM_NAME = name;
    }

    public int getWIDGET_ID() {
        return WIDGET_ID;
    }

    public int getITEM_ID() {
        return ITEM_ID;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }
}
