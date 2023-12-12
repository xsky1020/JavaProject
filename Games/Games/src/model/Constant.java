package model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public enum Constant {
    CHESSBOARD_ROW_SIZE(8),CHESSBOARD_COL_SIZE(8);

    private final int num;
    Constant(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    static Map<String, Color> colorMap = new HashMap<>(){{
        put("💎",Color.blue);
        put("⚪",Color.white);
        put("▲",Color.green);
        put("🔶",Color.orange);
    }};
    static Map<Integer, String> findMap = new HashMap<>(){{
        put(1, "💎");
        put(2, "⚪");
        put(3, "▲");
        put(4, "🔶");
    }};
}
