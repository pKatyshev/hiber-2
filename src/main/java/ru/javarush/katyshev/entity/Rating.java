package ru.javarush.katyshev.entity;

import static java.util.Objects.isNull;

public enum Rating {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC_17");

    private final String value;

    Rating(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

//    public static Rating getRatingByValue(String value) {
//        if (isNull(value) || value.isEmpty()) {
//            return null;
//        }
//
//        Rating[] values = Rating.values();
//        for(Rating v : values) {
//            if (v.value.equals(value)) {
//                return v;
//            }
//        }
//
//        return null;
//    }
}
