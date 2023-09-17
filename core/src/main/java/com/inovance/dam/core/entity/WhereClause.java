package com.inovance.dam.core.entity;

import java.util.List;

/**
 * Represents a WHERE clause for specifying conditions in a query.
 */
public class WhereClause {

    private List<Expression> expressions;

    private String operator;

    public List<Expression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "WhereClause{" +
                "expressions=" + expressions +
                ", operator='" + operator + '\'' +
                '}';
    }

    /**
     * Represents an individual expression within a WHERE clause.
     */
    public static class Expression {

        private String attribute;

        private String operator;

        private String value;

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Expression{" +
                    "attribute='" + attribute + '\'' +
                    ", operator='" + operator + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
