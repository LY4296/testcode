package com.inovance.dam.core.entity.inovance;

import java.io.Serializable;

/**
 * Represents a generic result container for Inovance data.
 * @author Leon
 * @param <T> Type of the data rows.
 */
public class InovanceResult<T> implements Serializable {

    private static final long serialVersionUID = -7314674209121036284L;

    /**
     * Data rows retrieved from the Inovance query.
     */
    private T rows;

    /**
     * Get the data rows.
     *
     * @return The data rows.
     */
    public T getRows() {
        return rows;
    }

    /**
     * Set the data rows.
     *
     * @param rows The data rows to set.
     */
    public void setRows(T rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "InovanceResult{" +
                "rows=" + rows +
                '}';
    }
}
