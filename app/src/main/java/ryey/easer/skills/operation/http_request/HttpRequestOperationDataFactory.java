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

import androidx.annotation.NonNull;

import ryey.easer.commons.local_skill.IllegalStorageDataException;
import ryey.easer.commons.local_skill.ValidData;
import ryey.easer.commons.local_skill.operationskill.OperationDataFactory;
import ryey.easer.plugin.PluginDataFormat;

class HttpRequestOperationDataFactory implements OperationDataFactory<HttpRequestOperationData> {
    @NonNull
    @Override
    public Class<HttpRequestOperationData> dataClass() {
        return HttpRequestOperationData.class;
    }

    @ValidData
    @NonNull
    @Override
    public HttpRequestOperationData dummyData() {
        HttpRequestOperationData.RequestMethod requestMethod = HttpRequestOperationData.RequestMethod.GET;
        String url = "http://localhost";
        String requestHeader = "h: v";
        String contentType = "text/plain";
        String data = "d";
        return new HttpRequestOperationData(requestMethod, url, requestHeader, contentType, data);
    }

    @ValidData
    @NonNull
    @Override
    public HttpRequestOperationData parse(@NonNull String data, @NonNull PluginDataFormat format, int version) throws IllegalStorageDataException {
        return new HttpRequestOperationData(data, format, version);
    }
}
