/*
 * Copyright (c) 2016 - 2019 Rui Zhao <renyuneyun@gmail.com>
 *
 * This file is part of Easer.
 *
 * Easer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Easer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Easer.  If not, see <http://www.gnu.org/licenses/>.
 */

package ryey.easer.skills.usource.location;

import android.location.Criteria;
import android.location.Location;

public class LocationUtils {

    public static final int METERS_PER_DEGREE = 111139;

    static Criteria getCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setSpeedRequired(false);
        criteria.setAccuracy(Criteria.NO_REQUIREMENT);
        return criteria;
    }

    static boolean inside(LatLong center, int radius, double latitude, double longtitude) {
        return inside(center, radius, new LatLong(latitude, longtitude));
    }

    static boolean inside(LatLong center, int radius, LatLong point) {
        return Math.sqrt(Math.pow(center.latitude - point.latitude, 2)
                + Math.pow(center.longitude - point.longitude, 2) ) * METERS_PER_DEGREE < radius;
    }

    static boolean isAcceptable(LocationUSourceData data, Location location) {
        if (location == null)
            return false;
        if (location.getAccuracy() > data.thresholdAccuracy)
            return false;
        if ((System.currentTimeMillis() - location.getTime()) > data.thresholdAge * LocationUSourceData.UNIT_THRESHOLD_TIME)
            return false;
        return true;
    }

    static boolean isInside(LocationUSourceData data, Location location) {
        for (LatLong center : data.locations) {
            if (inside(center, data.radius, location.getLatitude(), location.getLongitude())) {
                return true;
            }
        }
        return false;
    }
}
