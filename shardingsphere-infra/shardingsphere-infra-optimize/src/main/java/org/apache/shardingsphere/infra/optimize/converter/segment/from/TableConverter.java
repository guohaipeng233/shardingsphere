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

package org.apache.shardingsphere.infra.optimize.converter.segment.from;

import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlIdentifier;
import org.apache.calcite.sql.SqlJoin;
import org.apache.calcite.sql.SqlNode;
import org.apache.shardingsphere.infra.optimize.converter.segment.SQLSegmentConverter;
import org.apache.shardingsphere.infra.optimize.converter.segment.from.impl.JoinTableConverter;
import org.apache.shardingsphere.infra.optimize.converter.segment.from.impl.SimpleTableConverter;
import org.apache.shardingsphere.infra.optimize.converter.segment.from.impl.SubqueryTableConverter;
import org.apache.shardingsphere.sql.parser.sql.common.segment.generic.table.JoinTableSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.generic.table.SimpleTableSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.generic.table.SubqueryTableSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.generic.table.TableSegment;

import java.util.Optional;

/**
 * Table converter.
 */
public final class TableConverter implements SQLSegmentConverter<TableSegment, SqlNode> {
    
    @Override
    public Optional<SqlNode> convertToSQLNode(final TableSegment segment) {
        if (segment instanceof SimpleTableSegment) {
            return new SimpleTableConverter().convertToSQLNode((SimpleTableSegment) segment);
        } else if (segment instanceof JoinTableSegment) {
            return new JoinTableConverter().convertToSQLNode((JoinTableSegment) segment);
        } else if (segment instanceof SubqueryTableSegment) {
            return new SubqueryTableConverter().convertToSQLNode((SubqueryTableSegment) segment);
        }
        throw new UnsupportedOperationException("Unsupported segment segment type: " + segment.getClass());
    }
    
    @Override
    public Optional<TableSegment> convertToSQLSegment(final SqlNode sqlNode) {
        if (sqlNode instanceof SqlBasicCall || sqlNode instanceof SqlIdentifier) {
            return new SimpleTableConverter().convertToSQLSegment(sqlNode).map(optional -> optional);
        }
        if (sqlNode instanceof SqlJoin) {
            return new JoinTableConverter().convertToSQLSegment(sqlNode).map(optional -> optional);
        }
        return Optional.empty();
    }
}
