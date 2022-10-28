package com.food.ordering.system.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
    private final BigDecimal amount;

    public static Money ZERO= new Money(BigDecimal.ZERO);

    public Money( BigDecimal amount) {
        this.amount = setScale(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isGreaterThanZero(){
        return this.amount!=null && this.amount.compareTo(BigDecimal.ZERO)>0;
    }

    public boolean isGreaterThan(Money money){
        return this.amount!=null && this.amount.compareTo(money.amount)>0;
    }

    public Money add(Money money){
        return new Money(this.amount.add(money.amount));
    }

    public Money subtract(Money money){
        return new Money(this.amount.subtract(money.amount));
    }

    public Money subtract(BigDecimal multiplier){
        return new Money(this.amount.multiply(multiplier));
    }

    public BigDecimal setScale(BigDecimal input){
      return input.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
