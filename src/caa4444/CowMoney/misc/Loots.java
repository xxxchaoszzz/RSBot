package caa4444.cowmoney.misc;

public enum Loots {
    COWHIDES(1739),
    BONES(526),
    RAW_BEEF(2132);

    private final int itemId;

    Loots(int iid) {
        itemId = iid;
    }

    public int getItemId() {
        return itemId;
    }
}
