/*
 * Copyright (c) 2016 - 2022 Rui Zhao <renyuneyun@gmail.com>
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

package ryey.easer.skills.usource.airplane_mode;

import static org.junit.Assert.assertEquals;

import android.os.Parcel;

import org.junit.Test;

import ryey.easer.skills.TestHelper;

public class AirplaneModeUSourceDataTest {

    @Test
    public void testParcel() {
        AirplaneModeUSourceData dummyData = new AirplaneModeUSourceDataFactory().dummyData();
        Parcel parcel = TestHelper.writeToParcel(dummyData);
        AirplaneModeUSourceData parceledData = AirplaneModeUSourceData.CREATOR.createFromParcel(parcel);
        assertEquals(dummyData, parceledData);
    }

}
