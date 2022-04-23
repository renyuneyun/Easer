package ryey.easer.skills.operation.intent;

import android.net.Uri;

import java.util.ArrayList;

import ryey.easer.skills.reusable.ExtraItem;
import ryey.easer.skills.reusable.Extras;

public class IntentOperationDataUtil {
    public static IntentData getDummyIntentData() {
        IntentData intentData = new IntentData();
        intentData.action = "testAction";
        intentData.category = new ArrayList<>();
        intentData.category.add("testCategory");
        intentData.type = "myType";
        intentData.data = Uri.parse("myprot://seg1/seg2");
        ArrayList<ExtraItem> extras = new ArrayList<>();
        ExtraItem extraItem = new ExtraItem("extra_key1", "extra_value1", "string");
        extras.add(extraItem);
        intentData.extras = Extras.mayConstruct(extras);
        return intentData;
    }
}
