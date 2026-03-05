package com.zorroe.cloud.excelexport.strategy;

import com.zorroe.cloud.excelexport.entity.dto.ExcelDTO;
import com.zorroe.cloud.excelexport.entity.dto.ProductExcelDTO;
import com.zorroe.cloud.excelexport.entity.enums.ExcelTemplateType;
import com.zorroe.cloud.excelexport.entity.validator.ValidateError;
import com.zorroe.cloud.excelexport.listener.AbstractExcelListener;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
public class ProductImportStrategy implements ExcelImportStrategy<ProductExcelDTO>{

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public String getSupportTemplateType() {
        return ExcelTemplateType.PRODUCT.getCode();
    }

    @Override
    public AbstractExcelListener<ProductExcelDTO> getListener() {
        return new AbstractExcelListener<ProductExcelDTO>() {
            @Override
            protected void validate(ProductExcelDTO data, int rowNum) {
                Set<ConstraintViolation<ProductExcelDTO>> violations = validator.validate(data);
                for (ConstraintViolation<ProductExcelDTO> violation : violations) {
                    errorList.add(new ValidateError(rowNum, violation.getPropertyPath().toString(), violation.getMessage()));
                }

                if (dataList.stream().anyMatch(d -> d.getProductCode().equals(data.getProductCode()))) {
                    errorList.add(new ValidateError(rowNum, "productCode", "商品编码重复"));
                }

                if (data.getPrice() != null && data.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                    errorList.add(new ValidateError(rowNum, "price", "商品价格不能为负数"));
                }
            }
        };
    }

    @Override
    public boolean saveData(List<? extends ExcelDTO> dataList) {
        return true;
    }

    @Override
    public Class<ProductExcelDTO> getDataClass() {
        return ProductExcelDTO.class;
    }
}
