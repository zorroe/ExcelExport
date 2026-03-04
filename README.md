# Excel Export 服务

基于 Spring Boot 的 Excel 导出 Web 服务，提供灵活可扩展的 Excel 文件导出功能。

## 项目简介

本项目是一个通用的 Excel 导出解决方案，采用策略模式和工厂模式设计，支持多种导出类型的动态扩展。通过 REST API 接收请求，使用 EasyExcel 生成 Excel 文件。

## 技术栈

- **开发语言**: Java 8
- **核心框架**: Spring Boot 2.7.6
- **Excel 处理**: EasyExcel 4.0.3
- **工具库**: Hutool 5.8.42
- **代码简化**: Lombok
- **构建工具**: Maven

## 主要依赖

```xml
- spring-boot-starter-web: Web 服务支持
- easyexcel: Excel 文件生成
- hutool-all: Java 工具类库
- lombok: 简化代码
```

## 项目结构

```
excelExport/
├── src/main/java/com/zorroe/cloud/excelexport/
│   ├── controller/          # 控制器层
│   │   └── ExcelExportController.java    # Excel 导出接口
│   ├── converter/           # 数据转换器
│   │   └── BankTypeConverter.java        # 银行类型转换器
│   ├── entity/              # 实体类
│   │   ├── enums/           # 枚举类型
│   │   │   └── BankTypeEnum.java         # 银行类型枚举
│   │   ├── param/           # 参数对象
│   │   │   ├── ExportParam.java          # 导出参数接口
│   │   │   └── AccountExportParam.java   # 账户导出参数
│   │   └── vo/              # 视图对象
│   │       ├── ExportVo.java             # 导出 VO 接口
│   │       └── AccountExportVo.java      # 账户导出 VO
│   ├── factory/             # 工厂类
│   │   └── ExcelExportFactory.java       # 导出策略工厂
│   ├── strategy/            # 策略类
│   │   ├── ExcelExportStrategy.java      # 导出策略接口
│   │   └── AccountExportStrategy.java    # 账户导出策略
│   └── util/                # 工具类
│       └── ExcelExportUtil.java          # Excel 导出工具
└── src/main/resources/
    ├── static/
    │   └── index.html       # 静态首页
    └── application.properties  # 应用配置文件
```

## 核心设计

### 1. 策略模式

`ExcelExportStrategy` 接口定义了导出的标准流程：

- `getExportType()`: 获取导出类型标识
- `getQueryClass()`: 获取查询条件类型
- `getExportDataClass()`: 获取导出数据类型
- `getExportData(query)`: 根据查询条件获取导出数据
- `getFileName()`: 生成导出文件名
- `customizeExcelWriter()`: 自定义 Excel 写入配置

### 2. 工厂模式

`ExcelExportFactory` 负责管理所有导出策略，根据导出类型返回对应的策略实例。

### 3. 可扩展性

只需实现 `ExcelExportStrategy` 接口并添加 `@Component` 注解，即可自动注册新的导出类型。

## API 接口

### 导出 Excel

**请求地址**: `POST /excel/export`

**请求参数**:
- `exportType` (必填): 导出类型标识
- `params` (可选): 查询条件参数（JSON 格式）

**示例**:
```bash
curl -X POST "http://localhost:8080/excel/export?exportType=ACCOUNT" \
  -H "Content-Type: application/json" \
  -d '{"userId": 123, "startDate": "2024-01-01"}'
```

**响应**: Excel 文件下载

## 运行项目

### 环境要求

- JDK 1.8+
- Maven 3.6+

### 编译打包

```bash
mvn clean package
```

### 运行应用

```bash
# 使用 Maven 运行
mvn spring-boot:run

# 或运行 JAR 包
java -jar target/excelExport-0.0.1-SNAPSHOT.jar
```

### 访问地址

- 应用端口：http://localhost:8080
- 静态首页：http://localhost:8080/index.html

## 配置说明

在 `application.properties` 中配置：

```properties
# 应用服务 WEB 访问端口
server.port=8080
```

## 添加新的导出类型

1. 创建参数类（继承 `ExportParam`）
2. 创建 VO 类（实现 `ExportVo`）
3. 实现 `ExcelExportStrategy` 接口
4. 添加 `@Component` 注解

示例：
```java
@Component
public class CustomExportStrategy implements ExcelExportStrategy<CustomParam, CustomVo> {
    @Override
    public String getExportType() {
        return "CUSTOM";
    }
    
    @Override
    public Class<CustomParam> getQueryClass() {
        return CustomParam.class;
    }
    
    @Override
    public Class<CustomVo> getExportDataClass() {
        return CustomVo.class;
    }
    
    @Override
    public Collection<CustomVo> getExportData(CustomParam query) {
        // 实现数据查询逻辑
        return dataList;
    }
}
```

## 许可证

Copyright © 2026 Zorroe Cloud

## 技术支持

如有问题请联系开发团队。
