package com.zorroe.cloud.excelexport.strategy;

import com.zorroe.cloud.excelexport.entity.param.AccountExportParam;
import com.zorroe.cloud.excelexport.entity.param.ExportParam;
import com.zorroe.cloud.excelexport.entity.vo.AccountExportVo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class AccountExportStrategy implements ExcelExportStrategy<AccountExportParam, AccountExportVo> {
    @Override
    public String getExportType() {
        return "ACCOUNT";
    }

    @Override
    public Class<AccountExportParam> getQueryClass() {
        return AccountExportParam.class;
    }

    @Override
    public Class<AccountExportVo> getExportDataClass() {
        return AccountExportVo.class;
    }

    @Override
    public Collection<AccountExportVo> getExportData(AccountExportParam query) {
        List<AccountExportVo> accountExportVos = new ArrayList<>();
        accountExportVos.add(new AccountExportVo("6213001987", "账户1", new BigDecimal("100.0012"), LocalDateTime.now(), "BOC", "账户1备注信息"));
        accountExportVos.add(new AccountExportVo("6213001988", "账户2", new BigDecimal("300.0089"), LocalDateTime.now(), "ICBC", "账户2备注信息"));
        return accountExportVos;
    }
}
