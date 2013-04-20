package caa4444.cowmoney.misc;

public enum Loots {
    Cowhides(1739),
    Bones(526),
    RawBeef(2132);

    private final int ITEM_ID;

    Loots(int iid) {
        ITEM_ID = iid;
    }

    public int getITEM_ID() {
        return ITEM_ID;
    }
}
