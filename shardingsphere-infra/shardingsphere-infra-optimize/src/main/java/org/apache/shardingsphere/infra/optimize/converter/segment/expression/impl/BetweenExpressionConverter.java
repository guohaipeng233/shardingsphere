/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.infra.optimize.converter.segment.expression.impl;

import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.shardingsphere.infra.optimize.converter.segment.SQLSegmentConverter;
import org.apache.shardingsphere.infra.optimize.converter.segment.expression.ExpressionConverter;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.expr.BetweenExpression;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Between expression converter.
 */
public final class BetweenExpressionConverter implements SQLSegmentConverter<BetweenExpression, SqlNode> {
    
    @Override
    public Optional<SqlNode> convertToSQLNode(final BetweenExpression expression) {
        if (null == expression) {
            return Optional.empty();
        }
        Collection<SqlNode> sqlNodes = new LinkedList<>();
        ExpressionConverter expressionConverter = new ExpressionConverter();
        expressionConverter.convertToSQLNode(expression.getLeft()).ifPresent(sqlNodes::add);
        expressionConverter.convertToSQLNode(expression.getBetweenExpr()).ifPresent(sqlNodes::add);
        expressionConverter.convertToSQLNode(expression.getAndExpr()).ifPresent(sqlNodes::add);
        SqlBasicCall sqlNode = new SqlBasicCall(SqlStdOperatorTable.BETWEEN, sqlNodes.toArray(new SqlNode[]{}), SqlParserPos.ZERO);
        return expression.isNot() ? Optional.of(new SqlBasicCall(SqlStdOperatorTable.NOT, new SqlNode[]{sqlNode}, SqlParserPos.ZERO)) : Optional.of(sqlNode);
    }
    
    @Override
    public Optional<BetweenExpression> convertToSQLSegment(final SqlNode sqlNode) {
        return Optional.empty();
    }
}
