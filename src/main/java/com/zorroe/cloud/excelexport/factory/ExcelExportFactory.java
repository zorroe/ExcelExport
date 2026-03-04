package com.zorroe.cloud.excelexport.factory;

import com.zorroe.cloud.excelexport.entity.param.ExportParam;
import com.zorroe.cloud.excelexport.entity.vo.ExportVo;
import com.zorroe.cloud.excelexport.strategy.ExcelExportStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

@Component
public class ExcelExportFactory {

    private final Map<String, ExcelExportStrategy<? extends ExportParam, ? extends ExportVo>> strategyMap = new HashMap<>();

    @Autowired
    private ExcelExportFactory(Set<ExcelExportStrategy<? extends ExportParam, ? extends ExportVo>> allExportStrategies) {
        for (ExcelExportStrategy<? extends ExportParam, ? extends ExportVo> allExportStrategy : allExportStrategies) {
            strategyMap.put(allExportStrategy.getExportType(), allExportStrategy);
        }
    }

    @SuppressWarnings("unchecked")
    public <Q extends ExportParam, T extends ExportVo> ExcelExportStrategy<Q, T> getStrategy(String exportType) {
        ExcelExportStrategy<? extends ExportParam, ? extends ExportVo> strategy = strategyMap.get(exportType);
        if (strategy == null) {
            throw new IllegalArgumentException("未找到导出类型[" + exportType + "]对应的策略");
        }
        return (ExcelExportStrategy<Q, T>) strategy;
    }
}
