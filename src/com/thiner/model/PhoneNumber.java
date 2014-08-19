
package com.thiner.model;

import com.google.common.base.Preconditions;

public class PhoneNumber {

    private final String operadora;
    private final int number;
    private final int DDD;

    public PhoneNumber(final int DDD, final int number, final String operadora) {
        this.DDD = Preconditions.checkNotNull(DDD);
        this.number = Preconditions.checkNotNull(number);
        this.operadora = Preconditions.checkNotNull(operadora);
    }

    public String getOperadora() {
        return operadora;
    }

    public int getNumber() {
        return number;
    }

    public int getDDD() {
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
        result = prime * result + DDD;
        result = prime * result + number;
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
        if (DDD != other.DDD) {
            return false;
        }
        if (number != other.number) {
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
