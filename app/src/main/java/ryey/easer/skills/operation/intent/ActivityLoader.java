package ryey.easer.skills.operation.intent;

import android.content.Context;

import androidx.annotation.NonNull;

public class ActivityLoader extends IntentLoader<ActivityOperationData> {

    public ActivityLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    public void _load(@NonNull ActivityOperationData data, @NonNull OnResultCallback callback) {
        context.startActivity(this.getIntent(data));
        callback.onResult(true);
    }
}
