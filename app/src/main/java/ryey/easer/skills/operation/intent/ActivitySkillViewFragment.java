package ryey.easer.skills.operation.intent;

import androidx.annotation.NonNull;

import ryey.easer.commons.local_skill.InvalidDataInputException;

public class ActivitySkillViewFragment extends IntentSkillViewFragment<ActivityOperationData> {
    @NonNull
    @Override
    public ActivityOperationData getData() throws InvalidDataInputException {
        return new ActivityOperationData(super.getIntentData());
    }
}
