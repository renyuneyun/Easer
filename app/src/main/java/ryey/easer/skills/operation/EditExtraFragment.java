/*
 * Copyright (c) 2016 - 2019 Rui Zhao <renyuneyun@gmail.com>
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

package ryey.easer.skills.operation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import ryey.easer.R;
import ryey.easer.Utils;

public class EditExtraFragment extends Fragment {

    public static EditExtraFragment getInstance() {
        return new EditExtraFragment();
    }

    private List<ExtraItemFragment> m_fragment_extra = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.skill_operation__broadcast_fragment_edit_extra, container, false);

        view.findViewById(R.id.button_add_extra).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtraItemFragment fragment = new ExtraItemFragment();
                getChildFragmentManager().beginTransaction()
                        .add(R.id.layout_extras, fragment)
                        .commit();
                m_fragment_extra.add(fragment);
            }
        });

        return view;
    }

    public void fillExtras(@Nullable Extras extras) {
        if (extras == null)
            return;
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (m_fragment_extra.size() > 0) {
            for (ExtraItemFragment fragment : m_fragment_extra) {
                transaction.remove(fragment);
            }
            m_fragment_extra.clear();
        }
        for (int i = 0; i < extras.extras.size(); i++) {
            ExtraItem item = extras.extras.get(i);
            ExtraItemFragment fragment = ExtraItemFragment.createInstance(item);
            transaction.add(R.id.layout_extras, fragment);
            m_fragment_extra.add(fragment);
        }
        transaction.commit();
    }

    @NonNull
    public Extras getExtras() {
        if (m_fragment_extra.size() > 0) {
            List<ExtraItem> extraItemList = new ArrayList<>(m_fragment_extra.size());
            for (ExtraItemFragment fragment : m_fragment_extra) {
                ExtraItem extraItem = fragment.getData();
                if (extraItem != null)
                    extraItemList.add(extraItem);
            }
            return new Extras(extraItemList);
        }
        return new Extras();
    }

    public static class ExtraItemFragment extends Fragment {

        ExtraItem item;

        static ExtraItemFragment createInstance(ExtraItem item) {
            ExtraItemFragment fragment = new ExtraItemFragment();
            fragment.item = item;
            return fragment;
        }

        EditText editText_key, editText_value;
        Spinner spinner_type;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.skill_operation__broadcast_fragment_extra_item, container, false);
            editText_key = view.findViewById(R.id.editText_key);
            editText_value = view.findViewById(R.id.editText_value);
            spinner_type = view.findViewById(R.id.spinner_type);

            if (item != null) {
                editText_key.setText(item.key);
                editText_value.setText(item.value);
                String[] types = getResources().getStringArray(R.array.extra_type);
                for (int i = 0; i < types.length; i++) {
                    if (types[i].equals(item.type)) {
                        spinner_type.setSelection(i);
                    }
                }
            }

            return view;
        }

        @Nullable
        ExtraItem getData() {
            if (Utils.isBlank(editText_key.getText().toString()))
                return null;
            String key = editText_key.getText().toString().trim();
            String value = editText_value.getText().toString();
            String type = (String) spinner_type.getSelectedItem();
            return new ExtraItem(key, value, type);
        }

    }
}
