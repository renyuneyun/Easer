package ryey.easer.skills.usource.location;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

public class LocationUtilsTest {
    @Test
    public void testIsAcceptableWhenLocationIsNull() {
        Assert.assertFalse(
            LocationUtils.isAcceptable(new LocationUSourceDataFactory().dummyData(), null)
        );
    }
          

    @Test
    public void testIsInside() throws ParseException {
        Assert.assertTrue(
                LocationUtils.inside(LatLong.fromString("50, 50"), 150000, LatLong.fromString("50, 51"))
        );
        Assert.assertFalse(
                LocationUtils.inside(LatLong.fromString("50, 50"), 100000, LatLong.fromString("50, 51"))
        );
    }
}
