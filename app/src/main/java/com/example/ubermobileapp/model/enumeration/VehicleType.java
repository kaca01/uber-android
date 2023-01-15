package com.example.ubermobileapp.model.enumeration;

public enum VehicleType {
    STANDARD,
    LUXURY,
    VAN;

    public static VehicleType fromInteger(int x) {
        switch(x) {
            case 0:
                return STANDARD;
            case 1:
                return LUXURY;
            case 2:
                return VAN;
        }
        return null;
    }
}
