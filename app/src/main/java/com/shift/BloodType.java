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

    public static BloodType fromComponents(int bloodType, int plusMinus) {
        // A : 0
        // B : 1
        // AB : 2
        // O : 3

        // + : 0
        // - : 1

        boolean isPlus = plusMinus == 0;
        if (isPlus) {
            switch (bloodType) {
                case 0:
                    return A_POSITIVE;
                case 1:
                    return B_POSITIVE;
                case 2:
                    return AB_POSITIVE;
                default:
                    return O_POSITIVE;
            }
        } else {
            switch (bloodType) {
                case 0:
                    return A_NEGATIVE;
                case 1:
                    return B_NEGATIVE;
                case 2:
                    return AB_NEGATIVE;
                default:
                    return O_NEGATIVE;
            }
        }

    }
}
