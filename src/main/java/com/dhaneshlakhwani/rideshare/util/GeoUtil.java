package com.dhaneshlakhwani.rideshare.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class GeoUtil {
    private GeoUtil() {}

    public static double distanceKm(double lat1, double lon1, double lat2, double lon2) {
        final int earthRadius = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    public static BigDecimal latitudeDelta(BigDecimal radiusKm) {
        return radiusKm.divide(BigDecimal.valueOf(111), 7, RoundingMode.HALF_UP);
    }

    public static BigDecimal longitudeDelta(BigDecimal lat, BigDecimal radiusKm) {
        double cos = Math.cos(Math.toRadians(lat.doubleValue()));
        if (Math.abs(cos) < 0.01d) cos = 0.01d;
        return radiusKm.divide(BigDecimal.valueOf(111 * cos), 7, RoundingMode.HALF_UP);
    }
}
