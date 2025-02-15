+++
pre = "<b>6.11. </b>"
title = "Proxy"
weight = 11
chapter = true
+++

## DatabaseProtocolFrontendEngine

| *SPI 名称*                       | *详细说明*                                      |
| ------------------------------- | ---------------------------------------------- |
| DatabaseProtocolFrontendEngine  | 用于ShardingSphere-Proxy解析与适配访问数据库的协议 |

| *已知实现类*              | *详细说明*                                      |
| ------------------------ | ---------------------------------------------- |
| MySQLFrontendEngine      | 基于 MySQL 的数据库协议实现                      |
| PostgreSQLFrontendEngine | 基于 PostgreSQL 的SQL 解析器实现                 |

## JDBCDriverURLRecognizer

| *SPI 名称*               | *详细说明*                           |
| ----------------------- | ------------------------------------ |
| JDBCDriverURLRecognizer | 使用 JDBC 驱动执行 SQL                |

| *已知实现类*             | *详细说明*                           |
| ----------------------- | ----------------------------------- |
| MySQLRecognizer         |  使用 MySQL 的 JDBC 驱动执行 SQL      |
| PostgreSQLRecognizer    |  使用 PostgreSQL 的 JDBC 驱动执行 SQL |
| OracleRecognizer        |  使用 Oracle 的 JDBC 驱动执行 SQL     |
| SQLServerRecognizer     |  使用 SQLServer 的 JDBC 驱动执行 SQL  |
| H2Recognizer            |  使用 H2 的 JDBC 驱动执行 SQL         |
| P6SpyDriverRecognizer   |  使用 P6Spy 的 JDBC 驱动执行 SQL      |

## AuthorityProvideAlgorithm

| *SPI 名称*                       | *详细说明*                    |
| ------------------------------- | ---------------------------- |
| AuthorityProvideAlgorithm       | 用户权限加载逻辑                |

| *已知实现类*                                            | *Type*                      | *详细说明*                                                                           |
| ----------------------------------------------------- | --------------------------- | ----------------------------------------------------------------------------------- |
| NativeAuthorityProviderAlgorithm                      | NATIVE                      | 基于后端数据库存取 server.yaml 中配置的权限信息。如果用户不存在，则自动创建用户并默认赋予最高权限。 |
| AllPrivilegesPermittedAuthorityProviderAlgorithm      | ALL_PRIVILEGES_PERMITTED    | 默认授予所有权限（不鉴权），不会与实际数据库交互。                                           |
| SchemaPrivilegesPermittedAuthorityProviderAlgorithm   | SCHEMA_PRIVILEGES_PERMITTED | 通过属性 user-schema-mappings 配置的权限。                                           |
