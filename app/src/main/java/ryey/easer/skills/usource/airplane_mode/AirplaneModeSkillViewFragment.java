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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ryey.easer.R;
import ryey.easer.commons.local_skill.InvalidDataInputException;
import ryey.easer.commons.local_skill.ValidData;
import ryey.easer.skills.SkillViewFragment;

public class AirplaneModeSkillViewFragment extends SkillViewFragment<AirplaneModeUSourceData> {

    RadioButton rb_airplane_mode_off, rb_airplane_mode_on;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.skill_usource__airplane_mode, container, false);

        rb_airplane_mode_off = view.findViewById(R.id.radioButton_airplane_mode_off);
        rb_airplane_mode_on = view.findViewById(R.id.radioButton_airplane_mode_on);

        return view;
    }

    @Override
    protected void _fill(@ValidData @NonNull AirplaneModeUSourceData data) {
        if (data.airplaneMode) {
            rb_airplane_mode_on.setChecked(true);
        } else {
            rb_airplane_mode_off.setChecked(true);
        }
    }

    @ValidData
    @NonNull
    @Override
    public AirplaneModeUSourceData getData() throws InvalidDataInputException {
        boolean enabled = rb_airplane_mode_on.isChecked();
        return new AirplaneModeUSourceData(enabled);
    }
}