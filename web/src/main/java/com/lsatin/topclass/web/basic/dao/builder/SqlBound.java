package com.lsatin.topclass.web.basic.dao.builder;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public final class SqlBound {

    private final Statement sqlStatement;

    SqlBound(Builder builder) {
        // 禁用构造函数
        sqlStatement = builder.sqlStatement;
    }

    public String sql(SqlCommandType commandType) {
        String answer;
        switch (commandType) {
            case INSERT:
                answer = insert();
                break;
            case SELECT:
                answer = select();
                break;
            case UPDATE:
                answer = update();
                break;
            case DELETE:
                answer = delete();
                break;
            case COUNT:
                answer = count();
                break;
            default:
                answer = null;
                break;
        }
        return answer;
    }

    public Object[] values(SqlCommandType commandType) {
        Object[] result;
        switch (commandType) {
            case COUNT:
            case SELECT:
                result = readValue();
                break;
            case DELETE:
            case UPDATE:
            case INSERT:
                result = writeValue();
                break;
            default:
                result = new Object[0];
        }
        return result;
    }

    public String count() {
        return sqlStatement.sql(SqlCommandType.COUNT);
    }

    public String insert() {
        return sqlStatement.sql(SqlCommandType.INSERT);
    }

    public String select() {
        return sqlStatement.sql(SqlCommandType.SELECT);
    }

    public String update() {
        return sqlStatement.sql(SqlCommandType.UPDATE);
    }

    public String delete() {
        return sqlStatement.sql(SqlCommandType.DELETE);
    }

    public Object[] writeValue() {
        List<Object> values = new ArrayList<>();
        for (List<Object> value : sqlStatement.writeValue.values()) {
            values.addAll(value);
        }
        return values.toArray();
    }

    public Object[] readValue() {
        List<Object> values = new ArrayList<>();
        for (List<Object> value : sqlStatement.readValue.values()) {
            values.addAll(value);
        }
        return values.toArray();
    }

    private static class SafeAppendable {

        private final Appendable a;
        private boolean empty = true;

        SafeAppendable(Appendable a) {
            super();
            this.a = a;
        }

        public SafeAppendable append(CharSequence s) {
            try {
                if (empty && s != null && s.length() > 0) {
                    empty = false;
                }
                a.append(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        public boolean isEmpty() {
            return empty;
        }

        @Override
        public String toString() {
            return a.toString();
        }
    }

    private static class Statement {

        private static final int READ_TABLE = 0;
        private static final int WRITE_TABLE = 1;
        private static final int LEFT_TABLE = 2;
        private static final int RIGHT_TABLE = 3;
        private static final int INNER_TABLE = 4;

        private static final int ORDER_BY = 9;
        private static final int LIMIT = 10;

        private final Map<Integer, Map<String, String>> tables = new LinkedHashMap<>(); // 表，例子：{key: MAIN_TABLE, value: {key: tableName, value: tableName + alias}}
        private final Map<String, Map<String, String>> readColumn = new LinkedHashMap<>(); // 读列，例子：{key: tableName, value: {key: column, value: tableName.column/alias.column}}
        private final Map<String, Map<String, String>> writeColumn = new LinkedHashMap<>(); // 写列，例子：{key: tableName, value: {key: column, value: tableName.column/alias.column}}
        private final Map<String, List<String>> where = new LinkedHashMap<>(); // 条件，例子：{key: tableName, value: [{tableName.column1 = ?}, {tableName.column2 like ?}, {tableName.column3 != ?}...]}
        private final Map<String, String> alias = new LinkedHashMap<>(); // 表别名，例子：{key: tableName, value: tableName + " " + alias}
        private final Map<String, List<Object>> readValue = new LinkedHashMap<>();
        private final Map<String, List<Object>> writeValue = new LinkedHashMap<>();

        Statement() {
            // 防止合成访问
        }

        public String alias(int offset, int key) {
            return String.valueOf((char) (97 + offset + table(String.valueOf(key)).size()));
        }

        public Map<String, String> table(String key) {
            if (StringUtils.isNumeric(key)) {
                return this.tables.getOrDefault(Integer.valueOf(key), new LinkedHashMap<>());
            } else {
                final Map<String, String> map = new LinkedHashMap<>();
                for (Map<String, String> table : this.tables.values()) {
                    if (table.containsKey(key)) {
                        map.putAll(table);
                    }
                }
                return map;
            }
        }

        public boolean hasJoin() {
            return table(String.valueOf(LEFT_TABLE)).size() > 0 ||
                    table(String.valueOf(RIGHT_TABLE)).size() > 0 ||
                    table(String.valueOf(INNER_TABLE)).size() > 0;
        }

        public Map<String, String> writeColumn(String key) {
            return this.writeColumn.getOrDefault(key, new LinkedHashMap<>());
        }

        public Map<String, String> readColumn(String key) {
            return this.readColumn.getOrDefault(key, new LinkedHashMap<>());
        }

        public List<String> where(String key) {
            return this.where.getOrDefault(key, new ArrayList<>());
        }

        public List<Object> writeWhereValue(String key) {
            return this.writeValue.getOrDefault(key, new ArrayList<>());
        }

        public List<Object> readWhereValue(String key) {
            return this.readValue.getOrDefault(key, new ArrayList<>());
        }

        public String sql(SqlCommandType type) {
            if (type == null) {
                return null;
            }
            SafeAppendable appendable = new SafeAppendable(new StringBuilder(""));
            String answer;
            switch (type) {
                case INSERT:
                    answer = insertSQL(appendable);
                    break;
                case SELECT:
                    answer = selectSQL(appendable);
                    break;
                case UPDATE:
                    answer = updateSQL(appendable);
                    break;
                case DELETE:
                    answer = deleteSQL(appendable);
                    break;
                case COUNT:
                    answer = countSQL(appendable);
                    break;
                default:
                    answer = null;
                    break;
            }
            return answer;
        }

        private void sqlClause(SafeAppendable builder, String keyword, Collection<String> parts, String open, String close, String conjunction) {
            try {
                if (parts != null && !parts.isEmpty()) {
                    if (!builder.isEmpty()) {
                        builder.append(" ");
                    }
                    builder.append(keyword).append(" ").append(open);
                    final Iterator<String> iterator = parts.iterator();
                    boolean first = true;
                    while (iterator.hasNext()) {
                        final String next = iterator.next();
                        if (!first) {
                            builder.append(conjunction);
                        }
                        builder.append(next);
                        first = false;
                    }
                    builder.append(close);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String countSQL(SafeAppendable builder) {
            builder.append("SELECT COUNT(1) FROM (");
            final Collection<String> tables = table(String.valueOf(READ_TABLE)).keySet();
            if (tables.size() > 0) {
                List<String> tmp = new ArrayList<>(1);
                for (String table : tables) {
                    if (hasJoin()) {
                        sqlClause(builder, "SELECT DISTINCT", readColumn(table).values(), "", "", ", ");
                    } else {
                        sqlClause(builder, "SELECT", readColumn(table).values(), "", "", ", ");
                    }
                    tmp.add(table + " " + alias.getOrDefault(table, ""));
                    sqlClause(builder, "FROM", tmp, "", "", ", ");
                    tmp.clear();
                    joins(builder);
                    sqlClause(builder, "WHERE", where(table), "", "", " AND ");
                }
            }
            builder.append(") tmp");
            return builder.toString();
        }

        private String selectSQL(SafeAppendable builder) {
            final Collection<String> tables = table(String.valueOf(READ_TABLE)).keySet();
            if (tables.size() > 0) {
                List<String> tmp = new ArrayList<>(1);
                for (String table : tables) {
                    if (hasJoin()) {
                        sqlClause(builder, "SELECT DISTINCT", readColumn(table).values(), "", "", ", ");
                    } else {
                        sqlClause(builder, "SELECT", readColumn(table).values(), "", "", ", ");
                    }
                    tmp.add(table + " " + alias.getOrDefault(table, ""));
                    sqlClause(builder, "FROM", tmp, "", "", ", ");
                    tmp.clear();
                    joins(builder);
                    sqlClause(builder, "WHERE", where(table), "", "", " AND ");
                    sqlClause(builder, "LIMIT", where(String.valueOf(LIMIT)), "", "", ", ");
                }
            }
            return builder.toString();
        }

        private String insertSQL(SafeAppendable builder) {
            final Collection<String> tables = table(String.valueOf(WRITE_TABLE)).keySet();
            final List<String> tmp = new ArrayList<>();
            for (String table : tables) {
                final Set<String> columns = writeColumn(table).keySet();
                sqlClause(builder, "INSERT INTO", columns, table + "(", ")", ", ");
                for (String column : columns) {
                    tmp.add("?");
                }
                sqlClause(builder, "VALUES(", tmp, "", ")", ", ");
                tmp.clear();
            }
            return builder.toString();
        }

        private String updateSQL(SafeAppendable builder) {
            final Collection<String> tables = table(String.valueOf(WRITE_TABLE)).keySet();
            final Set<String> tmp = new LinkedHashSet<>();
            for (String table : tables) {
                tmp.add(table + " " + alias.getOrDefault(table, ""));
                sqlClause(builder, "UPDATE", tmp, "", "", "");
                tmp.clear();
                final Collection<String> columns = writeColumn(table).values();
                for (String column : columns) {
                    tmp.add(column + " = ?");
                }
                sqlClause(builder, "SET", tmp, "", "", ", ");
                tmp.clear();
                sqlClause(builder, "WHERE", where(table), "", "", " AND ");
            }
            return builder.toString();
        }

        private String deleteSQL(SafeAppendable builder) {
            final Collection<String> tables = table(String.valueOf(WRITE_TABLE)).keySet();
            final Set<String> tmp = new LinkedHashSet<>();
            for (String table : tables) {
                final String _alias = alias.getOrDefault(table, "");
                tmp.add(table + " " + alias.getOrDefault(table, ""));
                sqlClause(builder, "DELETE " + _alias + " FROM", tmp, "", "", "");
                tmp.clear();
                sqlClause(builder, "WHERE", where(table), "", "", " AND ");

            }
            return builder.toString();
        }

        private void joins(SafeAppendable builder) {
            final Collection<String> leftTable = table(String.valueOf(LEFT_TABLE)).values();
            final Collection<String> rightTable = table(String.valueOf(RIGHT_TABLE)).values();
            final Collection<String> innerTable = table(String.valueOf(INNER_TABLE)).values();
            final Set<String> tmp = new LinkedHashSet<>();
            for (String table : leftTable) {
                tmp.add(table);
                sqlClause(builder, "LEFT JOIN", tmp, "", "", "");
                tmp.clear();
                sqlClause(builder, "ON", where(String.valueOf(LEFT_TABLE)), "", "", " AND ");
            }
            for (String table : rightTable) {
                tmp.add(table);
                sqlClause(builder, "RIGHT JOIN", tmp, "", "", "");
                tmp.clear();
                sqlClause(builder, "ON", where(String.valueOf(RIGHT_TABLE)), "", "", " AND ");
            }
            for (String table : innerTable) {
                tmp.add(table);
                sqlClause(builder, "INNER JOIN", tmp, "", "", "");
                tmp.clear();
                sqlClause(builder, "ON", where(String.valueOf(INNER_TABLE)), "", "", " AND ");
            }
        }

    }

    public static class Builder {

        private final Statement sqlStatement;

        public Builder() {
            sqlStatement = new Statement();
        }

        public Builder write(String table, String column, Object value) {
            if (table != null && !table.isEmpty() && column != null && !column.isEmpty()) {
                final Map<String, String> writeColumn = this.sqlStatement.writeColumn(table);
                String alias = this.sqlStatement.alias.get(table);
                writeColumn.put(column, (alias == null ? table : alias) + "." + column);
                this.sqlStatement.writeColumn.put(table, writeColumn);

                final List<Object> whereValue = this.sqlStatement.writeWhereValue(column);
                whereValue.add(value);
                this.sqlStatement.writeValue.put(column, whereValue);
            }
            return this;
        }

        public Builder read(String table, String... column) {
            if (table != null && !table.isEmpty() && column != null && column.length > 0) {
                final Map<String, String> readColumn = this.sqlStatement.readColumn(table);
                final String alias = this.sqlStatement.alias.get(table);
                for (String c : column) {
                    readColumn.put(c, (alias == null ? table : alias) + "." + c);
                }
                this.sqlStatement.readColumn.put(table, readColumn);
            }
            return this;
        }

        public Builder from(String table) {
            if (table != null && !table.isEmpty()) {
                final Map<String, String> readTable = this.sqlStatement.table(String.valueOf(Statement.READ_TABLE));
                final Map<String, String> writeTable = this.sqlStatement.table(String.valueOf(Statement.WRITE_TABLE));
                if (!readTable.containsKey(table) && !writeTable.containsKey(table)) {
                    final String readAlias = this.sqlStatement.alias(0, Statement.READ_TABLE);
                    final String writeAlias = this.sqlStatement.alias(0, Statement.WRITE_TABLE);
                    readTable.put(table, table + " " + readAlias);
                    writeTable.put(table, table + " " + writeAlias);
                    this.sqlStatement.alias.put(table, readAlias);
                    this.sqlStatement.tables.put(Statement.READ_TABLE, readTable);
                    this.sqlStatement.tables.put(Statement.WRITE_TABLE, writeTable);
                }
            }
            return this;
        }

        public Builder leftJoin(String table1, String table2, String table1Column, String table2Column) {
            if (table1 != null && table2 != null && !table1.isEmpty() && !table2.isEmpty() &&
            table1Column != null && table2Column != null && !table1Column.isEmpty() && !table2Column.isEmpty()) {
                final Map<String, String> leftTables = this.sqlStatement.table(String.valueOf(Statement.LEFT_TABLE));
                final List<String> where = this.sqlStatement.where(String.valueOf(Statement.LEFT_TABLE));

                if (!this.sqlStatement.alias.containsKey(table1)) {
                    final String alias = this.sqlStatement.alias(1, Statement.LEFT_TABLE);
                    leftTables.put(table1, table1 + " " + alias);
                    this.sqlStatement.alias.put(table1, alias);
                }

                if (!this.sqlStatement.alias.containsKey(table2)) {
                    final String alias = this.sqlStatement.alias(2, Statement.LEFT_TABLE);
                    leftTables.put(table2, table2 + " " + alias);
                    this.sqlStatement.alias.put(table2, alias);
                }

                where.add(this.sqlStatement.alias.get(table1) + "." + table1Column + " = " + this.sqlStatement.alias.get(table2) + "." + table2Column);
                this.sqlStatement.where.put(String.valueOf(Statement.LEFT_TABLE), where);
                this.sqlStatement.tables.put(Statement.LEFT_TABLE, leftTables);
            }
            return this;
        }

        public Builder rightJoin(String table1, String table2, String table1Column, String table2Column) {
            if (table1 != null && table2 != null && !table1.isEmpty() && !table2.isEmpty() &&
                    table1Column != null && table2Column != null && !table1Column.isEmpty() && !table2Column.isEmpty()) {
                final Map<String, String> rightTables = this.sqlStatement.table(String.valueOf(Statement.RIGHT_TABLE));
                final List<String> where = this.sqlStatement.where(String.valueOf(Statement.RIGHT_TABLE));

                if (!this.sqlStatement.alias.containsKey(table1)) {
                    final String alias = this.sqlStatement.alias(3, Statement.RIGHT_TABLE);
                    rightTables.put(table1, table1 + " " + alias);
                    this.sqlStatement.alias.put(table1, alias);
                }

                if (!this.sqlStatement.alias.containsKey(table2)) {
                    final String alias = this.sqlStatement.alias(4, Statement.RIGHT_TABLE);
                    rightTables.put(table2, table2 + " " + alias);
                    this.sqlStatement.alias.put(table2, alias);
                }
                where.add(this.sqlStatement.alias.get(table1) + "." + table1Column + " = " + this.sqlStatement.alias.get(table2) + "." + table2Column);
                this.sqlStatement.where.put(String.valueOf(Statement.RIGHT_TABLE), where);
                this.sqlStatement.tables.put(Statement.RIGHT_TABLE, rightTables);
            }
            return this;
        }

        public Builder innerJoin(String table1, String table2, String table1Column, String table2Column) {
            if (table1 != null && table2 != null && !table1.isEmpty() && !table2.isEmpty() &&
                    table1Column != null && table2Column != null && !table1Column.isEmpty() && !table2Column.isEmpty()) {
                final Map<String, String> innerTables = this.sqlStatement.table(String.valueOf(Statement.INNER_TABLE));
                final List<String> where = this.sqlStatement.where(String.valueOf(Statement.INNER_TABLE));

                if (!this.sqlStatement.alias.containsKey(table1)) {
                    final String alias = this.sqlStatement.alias(5, Statement.INNER_TABLE);
                    innerTables.put(table1, table1 + " " + alias);
                    this.sqlStatement.alias.put(table1, alias);
                }

                if (!this.sqlStatement.alias.containsKey(table2)) {
                    final String alias = this.sqlStatement.alias(6, Statement.INNER_TABLE);
                    innerTables.put(table2, table2 + " " + alias);
                    this.sqlStatement.alias.put(table2, alias);
                }
                where.add(this.sqlStatement.alias.get(table1) + "." + table1Column + " = " + this.sqlStatement.alias.get(table2) + "." + table2Column);
                this.sqlStatement.where.put(String.valueOf(Statement.INNER_TABLE), where);
                this.sqlStatement.tables.put(Statement.INNER_TABLE, innerTables);
            }
            return this;
        }

        public Builder addLike(String table, String column, Object value) {
            if (table != null && column != null && !table.isEmpty() && !column.isEmpty() && value != null) {
                final List<String> where = this.sqlStatement.where(table);
                final List<Object> readWhereValue = this.sqlStatement.readWhereValue(column);
                final List<Object> writeWhereValue = this.sqlStatement.writeWhereValue(column);
                where.add(this.sqlStatement.alias.getOrDefault(table, table) + "." + column + " LIKE ?");
                readWhereValue.add("%" + value + "%");
                writeWhereValue.add("%" + value + "%");
                this.sqlStatement.where.put(table, where);
                this.sqlStatement.readValue.put(column, readWhereValue);
                this.sqlStatement.writeValue.put(column, writeWhereValue);
            }
            return this;
        }

        public Builder addEqual(String table, String column, Object value) {
            if (table != null && !table.isEmpty() && column != null && !column.isEmpty()) {
                final List<String> where = this.sqlStatement.where(table);
                final List<Object> readWhereValue = this.sqlStatement.readWhereValue(column);
                final List<Object> writeWhereValue = this.sqlStatement.writeWhereValue(column);
                where.add(this.sqlStatement.alias.getOrDefault(table, table) + "." + column + " = ?");
                readWhereValue.add(value);
                writeWhereValue.add(value);
                this.sqlStatement.where.put(table, where);
                this.sqlStatement.readValue.put(column, readWhereValue);
                this.sqlStatement.writeValue.put(column, writeWhereValue);
            }
            return this;
        }

        public Builder addNotEqual(String table, String column, Object value) {
            if (table != null && !table.isEmpty() && column != null && !column.isEmpty()) {
                final List<String> where = this.sqlStatement.where(table);
                final List<Object> readWhereValue = this.sqlStatement.readWhereValue(column);
                final List<Object> writeWhereValue = this.sqlStatement.writeWhereValue(column);
                where.add(this.sqlStatement.alias.getOrDefault(table, table) + "." + column + " != ?");
                readWhereValue.add(value);
                writeWhereValue.add(value);
                this.sqlStatement.where.put(table, where);
                this.sqlStatement.readValue.put(column, readWhereValue);
                this.sqlStatement.writeValue.put(column, writeWhereValue);
            }
            return this;
        }

        public Builder addGreaterThan(String table, String column, Object value) {
            if (table != null && !table.isEmpty() && column != null && !column.isEmpty()) {
                final List<String> where = this.sqlStatement.where(table);
                final List<Object> readWhereValue = this.sqlStatement.readWhereValue(column);
                final List<Object> writeWhereValue = this.sqlStatement.writeWhereValue(column);
                where.add(this.sqlStatement.alias.getOrDefault(table, table) + "." + column + " > ?");
                readWhereValue.add(value);
                writeWhereValue.add(value);
                this.sqlStatement.where.put(table, where);
                this.sqlStatement.readValue.put(column, readWhereValue);
                this.sqlStatement.writeValue.put(column, writeWhereValue);
            }
            return this;
        }

        public Builder addGreaterThanEqual(String table, String column, Object value) {
            if (table != null && !table.isEmpty() && column != null && !column.isEmpty()) {
                final List<String> where = this.sqlStatement.where(table);
                final List<Object> readWhereValue = this.sqlStatement.readWhereValue(column);
                final List<Object> writeWhereValue = this.sqlStatement.writeWhereValue(column);
                where.add(this.sqlStatement.alias.getOrDefault(table, table) + "." + column + " >= ?");
                readWhereValue.add(value);
                writeWhereValue.add(value);
                this.sqlStatement.where.put(table, where);
                this.sqlStatement.readValue.put(column, readWhereValue);
                this.sqlStatement.writeValue.put(column, writeWhereValue);
            }
            return this;
        }

        public Builder addLessThan(String table, String column, Object value) {
            if (table != null && !table.isEmpty() && column != null && !column.isEmpty()) {
                final List<String> where = this.sqlStatement.where(table);
                final List<Object> readWhereValue = this.sqlStatement.readWhereValue(column);
                final List<Object> writeWhereValue = this.sqlStatement.writeWhereValue(column);
                where.add(this.sqlStatement.alias.getOrDefault(table, table) + "." + column + " < ?");
                readWhereValue.add(value);
                writeWhereValue.add(value);
                this.sqlStatement.where.put(table, where);
                this.sqlStatement.readValue.put(column, readWhereValue);
                this.sqlStatement.writeValue.put(column, writeWhereValue);
            }
            return this;
        }

        public Builder addLessThanEqual(String table, String column, Object value) {
            if (table != null && !table.isEmpty() && column != null && !column.isEmpty()) {
                final List<String> where = this.sqlStatement.where(table);
                final List<Object> readWhereValue = this.sqlStatement.readWhereValue(column);
                final List<Object> writeWhereValue = this.sqlStatement.writeWhereValue(column);
                where.add(this.sqlStatement.alias.getOrDefault(table, table) + "." + column + " <= ?");
                readWhereValue.add(value);
                writeWhereValue.add(value);
                this.sqlStatement.where.put(table, where);
                this.sqlStatement.readValue.put(column, readWhereValue);
                this.sqlStatement.writeValue.put(column, writeWhereValue);
            }
            return this;
        }

        public Builder addIn(String table, String column, List<Object> values) {
            if (table != null && !table.isEmpty() && column != null && !column.isEmpty()) {
                final List<String> where = this.sqlStatement.where(table);
                final List<Object> readWhereValue = this.sqlStatement.readWhereValue(column);
                final List<Object> writeWhereValue = this.sqlStatement.writeWhereValue(column);
                StringJoiner joiner = new StringJoiner(", ", this.sqlStatement.alias.getOrDefault(table, table) + "." + column + " IN(", ")");

                for (Object value : values) {
                    joiner.add("?");
                    readWhereValue.add(value);
                    writeWhereValue.add(value);
                }

                where.add(joiner.toString());
                this.sqlStatement.where.put(table, where);
                this.sqlStatement.readValue.put(column, readWhereValue);
                this.sqlStatement.writeValue.put(column, writeWhereValue);
            }
            return this;
        }

        public Builder addNotIn(String table, String column, List<Object> values) {
            if (table != null && !table.isEmpty() && column != null && !column.isEmpty()) {
                final List<String> where = sqlStatement.where(table);
                final List<Object> readWhereValue = sqlStatement.readWhereValue(column);
                final List<Object> writeWhereValue = sqlStatement.writeWhereValue(column);
                StringJoiner joiner = new StringJoiner(", ", this.sqlStatement.alias.getOrDefault(table, table) + "." + column + " NOT IN(", ")");

                for (Object value : values) {
                    joiner.add("?");
                    readWhereValue.add(value);
                    writeWhereValue.add(value);
                }

                where.add(joiner.toString());
                this.sqlStatement.where.put(table, where);
                this.sqlStatement.readValue.put(column, readWhereValue);
                this.sqlStatement.writeValue.put(column, writeWhereValue);
            }
            return this;
        }

        public Builder limit(Integer page, Integer size) {
            if (page != null && size != null) {
                final List<String> where = this.sqlStatement.where(String.valueOf(Statement.LIMIT));
                where.add(String.valueOf(page));
                where.add(String.valueOf(size));
                this.sqlStatement.where.put(String.valueOf(Statement.LIMIT), where);
            }
            return this;
        }

        public SqlBound build() {
            return new SqlBound(this);
        }

    }
}
