package com.comert.mhl.database.common.model.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class IdAndName {

    private final Integer value;
    private final String label;

    public IdAndName(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        IdAndName that = (IdAndName) o;

        return new EqualsBuilder()
                .append(value, that.value)
                .append(label, that.label)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .append(label)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .append("label", label)
                .toString();
    }

}
