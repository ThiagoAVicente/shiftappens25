package com.shift;

public enum BloodType {
    O_POSITIVE("O+"),
    O_NEGATIVE("O-"),
    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-"),
    ;

    private final String repr;
    private BloodType(String repr) {
        this.repr = repr;
    }

    public String getRepresentation() {
        return repr;
    }
}
