package ryey.easer.skills.usource.location;

import org.junit.Assert;
import org.junit.Test;

public class LocationUtilsTest {
    @Test
    public void When_locationIsNull_IsAcceptable_Should_ReturnFalse(){
        Assert.assertFalse(
            LocationUtils.isAcceptable(new LocationUSourceDataFactory().dummyData(), null)
        );
    }
}
