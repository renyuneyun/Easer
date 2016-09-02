/*
 * Copyright (c) 2016 Rui Zhao <renyuneyun@gmail.com>
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

package ryey.easer.plugins.event.celllocation;

import java.util.ArrayList;
import java.util.List;

import ryey.easer.commons.EventData;

public class CellLocationEventData implements EventData {
    protected List<CellLocationSingleData> data;

    {
        data = new ArrayList<>();
    }

    public static CellLocationEventData fromString(String repr) {
        CellLocationEventData cellLocationEventData = new CellLocationEventData();
        cellLocationEventData.set(repr);
        if (cellLocationEventData.isValid())
            return cellLocationEventData;
        else
            return null;
    }

    @Override
    public Object get() {
        return data;
    }

    @Override
    public void set(Object obj) {
        if (obj instanceof String) {
            String[] parts = ((String) obj).split(",");
            for (String single : parts) {
                CellLocationSingleData singleData = new CellLocationSingleData();
                singleData.set(single.trim());
                if (singleData.isValid())
                    data.add(singleData);
            }
        } else {
            throw new RuntimeException("illegal data");
        }
    }

    @Override
    public boolean isValid() {
        if (data.size() == 0)
            return false;
        return true;
    }

    public boolean add(CellLocationSingleData singleData) {
        if (contains(singleData))
            return false;
        data.add(singleData);
        return true;
    }

    public String toString() {
        String str = "";
        if (data.size() > 0) {
            str += data.get(0).toString();
            for (int i = 1; i < data.size(); i++) {
                CellLocationSingleData singleData = data.get(i);
                str += "," + singleData.toString();
            }
        }
        return str;
    }

    public boolean contains(CellLocationSingleData singleData) {
        for (CellLocationSingleData singleData1 : data) {
            if (singleData.equals(singleData1))
                return true;
        }
        return false;
    }
}
