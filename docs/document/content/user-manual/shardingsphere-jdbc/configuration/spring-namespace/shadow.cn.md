+++
title = "影子库"
weight = 4
+++

## 配置项说明

命名空间：[http://shardingsphere.apache.org/schema/shardingsphere/shadow/shadow-5.0.0.xsd](http://shardingsphere.apache.org/schema/shardingsphere/shadow/shadow-5.0.0.xsd)

\<shadow:rule />

| *名称* | *类型*  | *说明* |
| ----- | ------ | ------ |
| id    | 属性    | Spring Bean Id |
| enable | 属性   | Shadow功能开关。 可选值：true/false，默认为false |
| data-source(?)  | 标签  | 影子数据源配置 |
| shadow-table(?) | 标签  | 影子表配置 |

\<shadow:data-source />

| *名称* | *类型*  | *说明* |
| ----- | ------ | ------ |
| id | 属性 | Spring Bean Id |
| source-data-source-name | 属性 | 生产数据源名称 |
| shadow-data-source-name | 属性 | 影子数据源名称 |

\<shadow:shadow-table />

| *名称* | *类型*  | *说明* |
| ----- | ------ | ------ |
| name | 属性 | 影子表名称 |
| data-sources | 属性 | 影子表关联影子数据源名称列表（多个值用","隔开）|
| algorithm (?) | 标签  | 影子表关联影子算法配置 |

\<shadow:algorithm />

| *名称* | *类型*  | *说明* |
| ----- | ------ | ------ |
| shadow-algorithm-ref | 属性 | 影子表关联影子算法名称 |

\<shadow:shadow-algorithm />

| *名称*    | *类型* | *说明*        |
| --------- | ----- | ------------- |
| id        | 属性  | 影子算法名称    |
| type      | 属性  | 影子算法类型    |
| props (?) | 标签  | 影子算法属性配置 |
