# Excel Export 服务

基于 Spring Boot 的通用 Excel 导出 Web 服务，采用策略模式和工厂模式设计，支持灵活扩展多种导出类型。

## 📋 目录

- [项目简介](#项目简介)
- [技术栈](#技术栈)
- [快速开始](#快速开始)
- [项目结构](#项目结构)
- [核心设计](#核心设计)
- [API 接口](#api-接口)
- [配置说明](#配置说明)
- [扩展示例](#扩展示例)

## 项目简介

本项目是一个通用的 Excel 导出解决方案，主要特点：

- ✅ **REST API 接口**：通过 HTTP 请求触发 Excel 导出
- ✅ **策略模式**：支持多种导出类型的动态扩展
- ✅ **工厂模式**：自动注册和管理所有导出策略
- ✅ **易于扩展**：只需实现接口即可添加新的导出类型
- ✅ **时间戳文件名**：自动生成带时间戳的文件名，避免覆盖

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 1.8 | 开发语言 |
| Spring Boot | 2.7.6 | 核心框架 |
| fesod-sheet | 2.0.1-incubating | Excel 处理库 |
| Hutool | 5.8.42 | Java 工具类库 |
| Lombok | - | 简化代码 |
| Maven | 3.6+ | 构建工具 |

### 主要依赖

```xml
<dependencies>
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Excel 处理 -->
    <dependency>
        <groupId>org.apache.fesod</groupId>
        <artifactId>fesod-sheet</artifactId>
        <version>2.0.1-incubating</version>
    </dependency>
    
    <!-- 工具类 -->
    <dependency>
        <groupId>cn.hutool</groupId>
        <artifactId>hutool-all</artifactId>
        <version>5.8.42</version>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

## 快速开始

### 环境要求

- JDK 1.8+
- Maven 3.6+

### 编译打包

```bash
mvn clean package
```

### 运行应用

**方式一：Maven 直接运行**
```bash
mvn spring-boot:run
```

**方式二：运行 JAR 包**
```bash
java -jar target/excelExport-0.0.1-SNAPSHOT.jar
```

### 访问地址

- 应用端口：http://localhost:8080
- 静态首页：http://localhost:8080/index.html

## 项目结构

```
excelExport/
├── src/main/java/com/zorroe/cloud/excelexport/
│   ├── controller/                    # 控制器层
│   │   └── ExcelExportController.java # Excel 导出接口
│   ├── converter/                     # 数据转换器
│   │   └── BankTypeConverter.java     # 银行类型转换器
│   ├── entity/                        # 实体类
│   │   ├── enums/                     # 枚举类型
│   │   │   └── BankTypeEnum.java      # 银行类型枚举
│   │   ├── param/                     # 参数对象
│   │   │   ├── ExportParam.java       # 导出参数接口
│   │   │   └── AccountExportParam.java# 账户导出参数
│   │   └── vo/                        # 视图对象
│   │       ├── ExportVo.java          # 导出 VO 接口
│   │       └── AccountExportVo.java   # 账户导出 VO
│   ├── factory/                       # 工厂类
│   │   └── ExcelExportFactory.java    # 导出策略工厂
│   ├── strategy/                      # 策略类
│   │   ├── ExcelExportStrategy.java   # 导出策略接口
│   │   └── AccountExportStrategy.java # 账户导出策略（示例）
│   └── util/                          # 工具类
│       └── ExcelExportUtil.java       # Excel 导出工具
├── src/main/resources/
│   ├── static/
│   │   └── index.html                 # 静态首页
│   └── application.properties         # 应用配置文件
├── pom.xml                            # Maven 配置
└── README.md                          # 项目说明
```

## 核心设计

### 1. 策略模式

#### 导出策略接口

`ExcelExportStrategy` 接口定义了导出的标准流程：

```java
public interface ExcelExportStrategy<Q extends ExportParam, T extends ExportVo> {
    // 获取导出类型标识（如 ACCOUNT、BALANCE）
    String getExportType();
    
    // 获取查询条件的 Class 类型（用于参数转换）
    Class<Q> getQueryClass();
    
    // 获取导出数据实体的 Class 类型
    Class<T> getExportDataClass();
    
    // 根据查询条件获取导出数据
    Collection<T> getExportData(Q query);
    
    // 生成导出文件名（默认带时间戳）
    default String getFileName() { ... }
    
    // 自定义 Excel 写入配置（可选）
    default void customizeExcelWriter(ExcelWriterBuilder writerBuilder) { ... }
}
```

#### 导入策略接口

`ExcelImportStrategy` 接口定义了导入的标准流程：

```java
public interface ExcelImportStrategy<T extends ExcelDTO> {
    // 获取支持的模板类型编码
    String getSupportTemplateType();
    
    // 获取数据实体的 Class 类型
    Class<T> getDataClass();
    
    // 获取 Excel 监听器（负责数据读取和校验）
    AbstractExcelListener<T> getListener();
    
    // 保存数据到数据库
    boolean saveData(List<? extends ExcelDTO> dataList);
}
```

### 2. 工厂模式

#### 导出工厂

`ExcelExportFactory` 负责管理所有导出策略：

- 自动注入所有 `ExcelExportStrategy` 实现类
- 通过 `getStrategy(exportType)` 方法获取对应策略
- 找不到策略时抛出 `IllegalArgumentException`

#### 导入工厂

`ExcelImportFactory` 负责管理所有导入策略：

- 自动注入所有 `ExcelImportStrategy` 实现类
- 统一处理导入流程：文件读取 → 数据校验 → 数据保存
- 提供模板下载功能

### 3. 工作流程

#### 导出流程

```
HTTP 请求 → Controller → Factory 获取策略 → Strategy 准备数据 
→ Util 生成 Excel → HttpServletResponse 返回文件
```

#### 导入流程

```
HTTP 请求上传文件 → Controller → Factory 获取策略 
→ Listener 读取并校验数据 → 校验失败返回错误 
→ 校验成功 → Strategy 保存数据 → 返回结果
```

#### 模板下载流程

```
HTTP 请求 → Factory 获取策略 → 生成仅包含表头的 Excel 
→ HttpServletResponse 返回文件
```

## API 接口

### 1. 导出 Excel

**请求地址**：`POST /excel/export`

**请求参数**：

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| exportType | String | 是 | 导出类型标识（由策略实现定义） |
| params | Map<String, Object> | 否 | 查询条件参数（JSON 格式） |

**请求示例**：

```bash
# 导出账户数据
curl -X POST "http://localhost:8080/excel/export?exportType=ACCOUNT" \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 123,
    "startDate": "2024-01-01",
    "endDate": "2024-12-31"
  }'

# 导出余额数据
curl -X POST "http://localhost:8080/excel/export?exportType=BALANCE" \
  -H "Content-Type: application/json" \
  -d '{
    "accountNo": "6213001987"
  }'
```

**响应**：Excel 文件下载（二进制流）

**文件名格式**：`{导出类型}_{yyyyMMddHHmmss}.xlsx`

**支持的导出类型**：
- `ACCOUNT` - 账户导出
- `BALANCE` - 余额导出

---

### 2. 导入 Excel

**请求地址**：`POST /excel/import`

**请求参数**：

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| templateType | String | 是 | 模板类型编码（如 product、customer） |
| file | MultipartFile | 是 | 上传的 Excel 文件 |

**请求示例**：

```bash
# 导入商品数据
curl -X POST "http://localhost:8080/excel/import" \
  -F "templateType=product" \
  -F "file=@/path/to/product.xlsx"

# 导入客户数据
curl -X POST "http://localhost:8080/excel/import" \
  -F "templateType=customer" \
  -F "file=@/path/to/customer.xlsx"
```

**响应示例**：

成功：
```json
{
  "code": 200,
  "message": "导入成功，共导入：50 条数据",
  "data": "导入成功，共导入：50 条数据"
}
```

失败：
```json
{
  "code": 500,
  "message": "数据校验失败，错误行数：3，详情：[...错误详情...]",
  "data": null
}
```

**导入流程**：
1. 文件上传与格式校验
2. 读取 Excel 数据
3. 数据验证（基于注解校验 + 自定义校验）
4. 校验失败返回错误信息
5. 校验成功调用保存逻辑

**支持的导入类型**：
- `product` - 商品导入
- `customer` - 客户导入

---

### 3. 下载模板

**请求地址**：`GET /excel/template`

**请求参数**：

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| templateType | String | 是 | 模板类型编码 |

**请求示例**：

```bash
# 下载商品导入模板
curl -O -J "http://localhost:8080/excel/template?templateType=product"

# 下载客户导入模板
curl -O -J "http://localhost:8080/excel/template?templateType=customer"
```

**响应**：Excel 模板文件下载（仅包含表头）

**文件名格式**：`{模板描述}.xlsx`

**支持的模板类型**：
- `product` - 商品导入模板
- `customer` - 客户导入模板

## 配置说明

在 `application.properties` 中配置：

```properties
# 应用服务 WEB 访问端口
server.port=8080
```

## 扩展示例

### 添加新的导出类型

#### 步骤 1：创建参数类

```java
package com.zorroe.cloud.excelexport.entity.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderExportParam extends ExportParam {
    private Long userId;
    private String orderNo;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
```

#### 步骤 2：创建 VO 类

```java
package com.zorroe.cloud.excelexport.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderExportVo implements ExportVo {
    @ExcelProperty("订单编号")
    private String orderNo;
    
    @ExcelProperty("用户 ID")
    private Long userId;
    
    @ExcelProperty("订单金额")
    private BigDecimal amount;
    
    @ExcelProperty("下单时间")
    private LocalDateTime orderTime;
}
```

#### 步骤 3：实现策略接口

```java
package com.zorroe.cloud.excelexport.strategy;

import com.zorroe.cloud.excelexport.entity.param.OrderExportParam;
import com.zorroe.cloud.excelexport.entity.vo.OrderExportVo;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;

@Component
public class OrderExportStrategy implements ExcelExportStrategy<OrderExportParam, OrderExportVo> {
    
    @Override
    public String getExportType() {
        return "ORDER";
    }
    
    @Override
    public Class<OrderExportParam> getQueryClass() {
        return OrderExportParam.class;
    }
    
    @Override
    public Class<OrderExportVo> getExportDataClass() {
        return OrderExportVo.class;
    }
    
    @Override
    public Collection<OrderExportVo> getExportData(OrderExportParam query) {
        // TODO: 实现数据查询逻辑
        // 根据 query 中的条件查询数据库
        List<OrderExportVo> dataList = ...;
        return dataList;
    }
    
    @Override
    public void customizeExcelWriter(ExcelWriterBuilder writerBuilder) {
        // 可选：自定义 Excel 样式、列宽等配置
        writerBuilder.sheet("订单列表");
    }
}
```

完成以上三步后，新的导出类型会自动注册到工厂中，可以通过 API 使用：

```bash
curl -X POST "http://localhost:8080/excel/export?exportType=ORDER" \
  -H "Content-Type: application/json" \
  -d '{"userId": 456}'
```

## 注意事项

1. **泛型绑定**：策略接口的泛型必须继承自 `ExportParam` 和 `ExportVo`
2. **组件注解**：策略实现类必须添加 `@Component` 注解才能被工厂自动发现
3. **空参数处理**：Controller 会处理 `params` 为 null 的情况，此时查询条件为 null
4. **异常处理**：如果导出类型不存在，会抛出 `IllegalArgumentException`

## 许可证

Copyright © 2026 Zorroe Cloud

## 技术支持

如有问题请联系开发团队。
