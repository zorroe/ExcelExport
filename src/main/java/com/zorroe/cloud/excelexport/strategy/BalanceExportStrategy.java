package com.zorroe.cloud.excelexport.strategy;

import com.zorroe.cloud.excelexport.entity.param.BalanceExportParam;
import com.zorroe.cloud.excelexport.entity.vo.BalanceExportVo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class BalanceExportStrategy implements ExcelExportStrategy<BalanceExportParam, BalanceExportVo> {
    @Override
    public String getExportType() {
        return "BALANCE";
    }

    @Override
    public Class<BalanceExportParam> getQueryClass() {
        return BalanceExportParam.class;
    }

    @Override
    public Class<BalanceExportVo> getExportDataClass() {
        return BalanceExportVo.class;
    }

    @Override
    public Collection<BalanceExportVo> getExportData(BalanceExportParam query) {
        List<BalanceExportVo> balanceExportVos = new ArrayList<>();
        balanceExportVos.add(new BalanceExportVo("6213001987", "账户1", new BigDecimal("100.0012"), new BigDecimal("100.0012"), LocalDateTime.now(), "BOC"));
        balanceExportVos.add(new BalanceExportVo("6213001988", "账户2", new BigDecimal("300.0089"), new BigDecimal("300.0089"), LocalDateTime.now(), "ICBC"));
        return balanceExportVos;
    }
}
