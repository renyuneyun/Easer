/*
 * Copyright (c) 2016 - 2018 Rui Zhao <renyuneyun@gmail.com>
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

package ryey.easer.plugins.operation.termux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

import ryey.easer.R;
import ryey.easer.commons.local_plugin.InvalidDataInputException;
import ryey.easer.commons.local_plugin.ValidData;
import ryey.easer.plugins.PluginViewFragment;

import static android.provider.DocumentsContract.EXTRA_INITIAL_URI;
import static ryey.easer.plugins.operation.termux.TermuxConstant.COLUMN_ID;

public class TermuxPluginViewFragment extends PluginViewFragment<TermuxOperationData> {
    private static final int READ_REQUEST_CODE = 42;

    private Button scriptSelectorButton;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plugin_operation__termux, container, false);
        scriptSelectorButton = view.findViewById(R.id.scrip_selector);
        scriptSelectorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, TermuxConstant.EXTRA_INITIAL_URI);


                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("*/*");
                    startActivityForResult(intent, READ_REQUEST_CODE);
                }

            }
        });
        return view;
    }

    @Override
    protected void _fill(@ValidData @NonNull TermuxOperationData data) {
        String script = data.get();
        scriptSelectorButton.setText(script);
    }

    @ValidData
    @NonNull
    @Override
    public TermuxOperationData getData() throws InvalidDataInputException {
        return new TermuxOperationData(scriptSelectorButton.getText().toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    Cursor cursor = getActivity().getContentResolver()
                            .query(uri, null, null, null, null, null);
                    try {
                        if (cursor != null && cursor.moveToFirst()) {
                            File file = new File(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                            scriptSelectorButton.setText(file.getAbsolutePath());
                        }
                    } finally {
                        cursor.close();
                    }
                } else {
                    // clean the uri to be usable as a termux script
                    File file = new File(uri.getPath().substring("/document".length()));
                    scriptSelectorButton.setText(file.getAbsolutePath());

                }

            }
        }
    }
}
