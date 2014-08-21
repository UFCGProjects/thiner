
package com.thiner.model;

import com.google.common.base.Preconditions;

public class PhoneNumber {

    private final String operadora;
    private final String number;
    private final String DDD;

    public PhoneNumber(final String DDD, final String number, final String operadora) {
        this.DDD = Preconditions.checkNotNull(DDD);
        this.number = Preconditions.checkNotNull(number);
        this.operadora = Preconditions.checkNotNull(operadora);
    }

    public String getOperadora() {
        return operadora;
    }

    public String getNumber() {
        return number;
    }

    public String getDDD() {
        return DDD;
    }

    @Override
    public String toString() {
        return "PhoneNumber [operadora=" + operadora + ", number=" + number + ", DDD=" + DDD + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (DDD == null ? 0 : DDD.hashCode());
        result = prime * result + (number == null ? 0 : number.hashCode());
        result = prime * result + (operadora == null ? 0 : operadora.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PhoneNumber other = (PhoneNumber) obj;
        if (DDD == null) {
            if (other.DDD != null) {
                return false;
            }
        } else if (!DDD.equals(other.DDD)) {
            return false;
        }
        if (number == null) {
            if (other.number != null) {
                return false;
            }
        } else if (!number.equals(other.number)) {
            return false;
        }
        if (operadora == null) {
            if (other.operadora != null) {
                return false;
            }
        } else if (!operadora.equals(other.operadora)) {
            return false;
        }
        return true;
    }

}
