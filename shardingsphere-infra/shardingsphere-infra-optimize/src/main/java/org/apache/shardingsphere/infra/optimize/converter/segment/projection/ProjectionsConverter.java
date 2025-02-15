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

package org.apache.shardingsphere.infra.optimize.converter.segment.projection;

import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlIdentifier;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.shardingsphere.infra.optimize.converter.segment.SQLSegmentConverter;
import org.apache.shardingsphere.infra.optimize.converter.segment.projection.impl.AggregationProjectionConverter;
import org.apache.shardingsphere.infra.optimize.converter.segment.projection.impl.ColumnProjectionConverter;
import org.apache.shardingsphere.infra.optimize.converter.segment.projection.impl.ExpressionProjectionConverter;
import org.apache.shardingsphere.infra.optimize.converter.segment.projection.impl.ShorthandProjectionConverter;
import org.apache.shardingsphere.infra.optimize.converter.segment.projection.impl.SubqueryProjectionConverter;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.item.AggregationProjectionSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.item.ColumnProjectionSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.item.ExpressionProjectionSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.item.ProjectionSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.item.ProjectionsSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.item.ShorthandProjectionSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.item.SubqueryProjectionSegment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Projection converter.
 */
public final class ProjectionsConverter implements SQLSegmentConverter<ProjectionsSegment, SqlNodeList> {
    
    @Override
    public Optional<SqlNodeList> convertToSQLNode(final ProjectionsSegment segment) {
        Collection<SqlNode> projectionSQLNodes = new ArrayList<>(segment.getProjections().size());
        for (ProjectionSegment each : segment.getProjections()) {
            getProjectionSQLNode(each).ifPresent(projectionSQLNodes::add);
        }
        return Optional.of(new SqlNodeList(projectionSQLNodes, SqlParserPos.ZERO));
    }
    
    private Optional<SqlNode> getProjectionSQLNode(final ProjectionSegment segment) {
        if (segment instanceof ColumnProjectionSegment) {
            return new ColumnProjectionConverter().convertToSQLNode((ColumnProjectionSegment) segment);
        } else if (segment instanceof ExpressionProjectionSegment) {
            return new ExpressionProjectionConverter().convertToSQLNode((ExpressionProjectionSegment) segment);
        } else if (segment instanceof ShorthandProjectionSegment) {
            return new ShorthandProjectionConverter().convertToSQLNode((ShorthandProjectionSegment) segment);
        } else if (segment instanceof SubqueryProjectionSegment) {
            return new SubqueryProjectionConverter().convertToSQLNode((SubqueryProjectionSegment) segment);
        } else if (segment instanceof AggregationProjectionSegment) {
            return new AggregationProjectionConverter().convertToSQLNode((AggregationProjectionSegment) segment);
        }
        // TODO process other projection
        return Optional.empty();
    }
    
    @Override
    public Optional<ProjectionsSegment> convertToSQLSegment(final SqlNodeList sqlNode) {
        Collection<ProjectionSegment> projections = new LinkedList<>();
        for (SqlNode each : sqlNode) {
            getProjectionSegment(each).ifPresent(projections::add);
        }
        int startIndex = sqlNode.get(0).getParserPosition().getColumnNum() - 1;
        int stopIndex = sqlNode.get(sqlNode.size() - 1).getParserPosition().getEndColumnNum() - 1;
        ProjectionsSegment result = new ProjectionsSegment(startIndex, stopIndex);
        result.getProjections().addAll(projections);
        return Optional.of(result);
    }
    
    private Optional<ProjectionSegment> getProjectionSegment(final SqlNode sqlNode) {
        if (sqlNode instanceof SqlIdentifier) {
            return new ColumnProjectionConverter().convertToSQLSegment(sqlNode).map(optional -> optional);
        } else if (sqlNode instanceof SqlBasicCall) {
            return new ExpressionProjectionConverter().convertToSQLSegment(sqlNode).map(optional -> optional);
        }
        // TODO process other projection
        return Optional.empty();
    }
}
