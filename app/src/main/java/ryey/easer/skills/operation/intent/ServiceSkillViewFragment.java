package ryey.easer.skills.operation.intent;

import androidx.annotation.NonNull;

import ryey.easer.commons.local_skill.InvalidDataInputException;

public class ServiceSkillViewFragment extends IntentSkillViewFragment<ServiceOperationData> {
    @NonNull
    @Override
    public ServiceOperationData getData() throws InvalidDataInputException {
        return new ServiceOperationData(super.getIntentData());
    }
}
