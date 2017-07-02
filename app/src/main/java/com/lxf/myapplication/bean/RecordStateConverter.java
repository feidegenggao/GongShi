package com.lxf.myapplication.bean;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by lxf on 2017/7/1.
 */

public class RecordStateConverter implements PropertyConverter<RecordState, String> {
    @Override
    public RecordState convertToEntityProperty(String databaseValue) {
        return RecordState.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(RecordState entityProperty) {
        return entityProperty.name();
    }
}
