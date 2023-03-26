package com.yty.godbatis.pojo;


import java.util.Objects;

/**
 * @author yty
 * @since 1.0
 * @version 1.0
 * storage sql statement and resultType attribute
 */
public class MappedStatement {
    /**
     * sql query
     */
    private String sql;
    /**
     * return result type: possible null
     */
    private String resultType;

    public MappedStatement() {
    }

    public MappedStatement(String sql, String resultType) {
        this.sql = sql;
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MappedStatement that = (MappedStatement) o;
        return Objects.equals(sql, that.sql) && Objects.equals(resultType, that.resultType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sql, resultType);
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        return "MappedStatement{" +
                "sql='" + sql + '\'' +
                ", resultType='" + resultType + '\'' +
                '}';
    }
}
