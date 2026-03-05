package com.zorroe.cloud.excelexport.entity.vo;

import cn.hutool.core.date.DatePattern;
import com.zorroe.cloud.excelexport.converter.BankTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.fesod.sheet.annotation.ExcelProperty;
import org.apache.fesod.sheet.annotation.format.DateTimeFormat;
import org.apache.fesod.sheet.annotation.format.NumberFormat;
import org.apache.fesod.sheet.annotation.write.style.ColumnWidth;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceExportVo implements ExportVo{

    @ExcelProperty(value = "账户")
    private String accountNo;

    @ColumnWidth(20)
    @ExcelProperty(value = "账户名称")
    private String accountName;

    @ColumnWidth(20)
    @ExcelProperty(value = "账户余额")
    @NumberFormat(value = "#.##", roundingMode = RoundingMode.HALF_UP)
    private BigDecimal balance;

    @ColumnWidth(20)
    @ExcelProperty(value = "可用余额")
    @NumberFormat(value = "#.##", roundingMode = RoundingMode.HALF_UP)
    private BigDecimal availableBalance;

    @ColumnWidth(20)
    @ExcelProperty(value = "余额刷新时间")
    @DateTimeFormat(value = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime balanceTime;

    @ColumnWidth(20)
    @ExcelProperty(value = "银行类型", converter = BankTypeConverter.class)
    private String bankType;

}
