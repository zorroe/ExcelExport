package com.zorroe.cloud.excelexport.entity.dto;

import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductExcelDTO implements ExcelDTO{

    @ExcelProperty("商品名称")
    @NotBlank(message = "商品名称不能为空")
    private String productName;

    @ExcelProperty("商品编码")
    @NotBlank(message = "商品编码不能为空")
    private String productCode;

    @ExcelProperty("商品价格")
    @NotNull(message = "商品价格不能为空")
    private BigDecimal price;

    @ExcelProperty("商品类别")
    private String category;
}
