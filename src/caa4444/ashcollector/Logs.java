package caa4444.ashcollector;

public enum Logs {
    //70755,70757,70758,70761,70764,70765
    WILLOW(1519, 00, 170, "Willow"),
    MAPLE(1517, 70761, 210, "Maple"),
    OAK(1521, 00, 140, "Oak"),
    NORMAL(1511, 00, 120, "Normal");

    private final int FIRE_ID;
    private final int ITEM_ID;
    private final int BURN_TIME;
    private final String ITEM_NAME;

    Logs(int iID, int fID, int burnTime, String name) {
        FIRE_ID = fID;
        ITEM_ID = iID;
        BURN_TIME = burnTime;
        ITEM_NAME = name;
    }

    public int getFIRE_ID() {
        return FIRE_ID;
    }

    public int getITEM_ID() {
        return ITEM_ID;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    private int getBURN_TIME() {
        return BURN_TIME;
    }
}
