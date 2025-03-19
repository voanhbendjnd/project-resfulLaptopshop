package com.laptopshopResful.utils;

import java.lang.reflect.Field;
import java.util.Optional;

public class UpdateNotNull {
    public static <T> void handle(T request, T db) {
        Field[] fields = request.getClass().getDeclaredFields();
        for (Field x : fields) {
            x.setAccessible(true); // allow access private element
            try {
                Object value = x.get(request);
                if (value != null) {
                    x.set(db, value);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
