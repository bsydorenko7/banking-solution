package com.sydorenko.bankingsolution.util;

import java.math.BigDecimal;

public class MiscTool {

    public static boolean isLessThan(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) < 0;
    }
}
