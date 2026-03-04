package com.zorroe.cloud.excelexport.converter;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.zorroe.cloud.excelexport.entity.enums.BankTypeEnum;

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
