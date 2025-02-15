+++
pre = "<b>3.1.2. </b>"
title = "ShardingSphere-Proxy"
weight = 2
+++

## Introduction

ShardingSphere-Proxy is the second product of Apache ShardingSphere.
It defines itself as a transparent database proxy, providing a database server that encapsulates database binary protocol to support heterogeneous languages.
Currently, MySQL and PostgreSQL (compatible with PostgreSQL-based databases, such as openGauss) versions are provided. It can use any kind of terminal (such as MySQL Command Client, MySQL Workbench, etc.) that is compatible of MySQL or PostgreSQL protocol to operate data, which is friendlier to DBAs

* Totally transparent to applications, it can be used directly as MySQL/PostgreSQL.
* Applicable to any kind of client end that is compatible with MySQL/PostgreSQL protocol.

![ShardingSphere-Proxy Architecture](https://shardingsphere.apache.org/document/current/img/shardingsphere-proxy_v2.png)

## Comparison

|                          | *ShardingSphere-JDBC* | *ShardingSphere-Proxy*       |
| ------------------------ | --------------------- | ---------------------------- |
| Database                 | Any                   | `MySQL/PostgreSQL`           |
| Connections Count Cost   | High                  | `Low`                        |
| Supported Languages      | Java Only             | `Any`                        |
| Performance              | Low loss              | `Relatively high loss`       |
| Decentralization         | Yes                   | `No`                         |
| Static Entry             | No                    | `Yes`                        |

The advantages of ShardingSphere-Proxy lie in supporting heterogeneous languages and providing operational entries for DBA.
