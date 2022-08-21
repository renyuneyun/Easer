package ryey.easer.skills.operation.intent;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import ryey.easer.Utils;
import ryey.easer.commons.local_skill.ValidData;

public abstract class IntentLoader<T extends IntentOperationData> extends ryey.easer.skills.operation.OperationLoader<T> {
    public IntentLoader(@NonNull Context context) {
        super(context);
    }

    protected Intent getIntent(@ValidData @NonNull IntentOperationData data) {
        IntentData iData = data.data;
        Intent intent = new Intent();
        if (iData.component != null) {
            intent.setPackage(iData.component);
        }
        intent.setAction(iData.action);
        if (iData.category != null)
            for (String category : iData.category) {
                intent.addCategory(category);
            }
        boolean hasType = false, hasData = false;
        if (!Utils.isBlank(iData.type))
            hasType = true;
        if (iData.data != null && !Utils.isBlank(iData.data.toString()))
            hasData = true;
        if (hasType && hasData) {
            intent.setDataAndType(iData.data, iData.type);
        } else if (hasType) {
            intent.setType(iData.type);
        } else if (hasData) {
            intent.setData(iData.data);
        }
        if (iData.extras != null) {
            intent.putExtras(iData.extras.asBundle());
        }
        return intent;
    }
}
