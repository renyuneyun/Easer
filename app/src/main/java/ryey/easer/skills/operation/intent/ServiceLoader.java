package ryey.easer.skills.operation.intent;

import android.content.Context;

import androidx.annotation.NonNull;

public class ServiceLoader extends IntentLoader<ServiceOperationData> {
    public ServiceLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    public void _load(@NonNull ServiceOperationData data, @NonNull OnResultCallback callback) {
        context.startService(this.getIntent(data));
        callback.onResult(true);
    }
}
