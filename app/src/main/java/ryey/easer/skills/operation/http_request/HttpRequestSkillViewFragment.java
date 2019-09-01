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

package ryey.easer.skills.operation.http_request;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ryey.easer.R;
import ryey.easer.commons.local_skill.InvalidDataInputException;
import ryey.easer.commons.local_skill.ValidData;
import ryey.easer.skills.SkillViewFragment;

public class HttpRequestSkillViewFragment extends SkillViewFragment<HttpRequestOperationData> {

    private RadioButton rb_get;
    private RadioButton rb_post;
    private EditText editText_url;
    private EditText editText_header;
    private EditText editText_contentType;
    private EditText editText_postData;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plugin_operation__http_request, container, false);
        rb_get = view.findViewById(R.id.radioButton_get);
        rb_post = view.findViewById(R.id.radioButton_post);
        editText_url = view.findViewById(R.id.editText_url);
        editText_header = view.findViewById(R.id.editText_header);
        editText_contentType = view.findViewById(R.id.editText_contentType);
        editText_postData = view.findViewById(R.id.editText_postData);
        return view;
    }

    @Override
    protected void _fill(@ValidData @NonNull HttpRequestOperationData data) {
        switch (data.requestMethod) {
            case GET:
                rb_get.setChecked(true);
                break;
            case POST:
                rb_post.setChecked(true);
                break;
            default:
                throw new IllegalAccessError();
        }
        editText_url.setText(data.url);
        editText_header.setText(data.requestHeader);
        editText_contentType.setText(data.contentType);
        editText_postData.setText(data.postData);
    }

    @ValidData
    @NonNull
    @Override
    public HttpRequestOperationData getData() throws InvalidDataInputException {
        HttpRequestOperationData.RequestMethod requestMethod;
        if (rb_get.isChecked())
            requestMethod = HttpRequestOperationData.RequestMethod.GET;
        else if (rb_post.isChecked())
            requestMethod = HttpRequestOperationData.RequestMethod.POST;
        else
            throw new IllegalStateException("This line ought to be unreachable");

        final String url = editText_url.getText().toString();
        final String header = editText_header.getText().toString();
        final String contentType = editText_contentType.getText().toString();
        final String postData = editText_postData.getText().toString();

        return new HttpRequestOperationData(requestMethod, url, header, contentType, postData);
    }
}
