package com.zorroe.cloud.excelexport.converter;

import cn.hutool.core.text.CharSequenceUtil;
import com.zorroe.cloud.excelexport.entity.enums.BankTypeEnum;
import org.apache.fesod.sheet.converters.Converter;
import org.apache.fesod.sheet.enums.CellDataTypeEnum;
import org.apache.fesod.sheet.metadata.GlobalConfiguration;
import org.apache.fesod.sheet.metadata.data.WriteCellData;
import org.apache.fesod.sheet.metadata.property.ExcelContentProperty;

public class BankTypeConverter implements Converter<String> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }


    @Override
    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String name = BankTypeEnum.getNameByCode(value);
        if (CharSequenceUtil.isNotEmpty(name)) {
            return new WriteCellData<>(name);
        }
        return new WriteCellData<>(value);
    }
}
