package com.masaibar.ormaqueryobservablesample;

import android.content.Context;

public class OrmaUtil {
    private static OrmaDatabase sOrma = null;

    public synchronized static OrmaDatabase getInstance(Context context) {
        if (sOrma == null) {
            sOrma = OrmaDatabase.builder(context).build();
        }

        return sOrma;
    }
}
