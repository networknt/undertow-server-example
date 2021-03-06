package com.networknt.example.sagas.ordersandcustomers.customer.domain;



import com.networknt.example.sagas.ordersandcustomers.commondomain.Money;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class Customer {


  private Long id;
  private String name;


  private Money creditLimit;



  private Map<Long, Money> creditReservations;

  Money availableCredit() {
    return creditLimit.subtract(creditReservations.values().stream().reduce(Money.ZERO, Money::add));
  }

  public Customer() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Customer(String name, Money creditLimit) {
  //  this.id = new IdGeneratorImpl().genId().getHi();
    this.name = name;
    this.creditLimit = creditLimit;
  //  this.creditReservations = Collections.emptyMap();
    this.creditReservations = new HashMap<>();
  }

  public Long getId() {
    return id;
  }

  public void reserveCredit(Long orderId, Money orderTotal) {
    if (availableCredit().isGreaterThanOrEqual(orderTotal)) {
      creditReservations.put(orderId, orderTotal);
    } else
      throw new CustomerCreditLimitExceededException();
  }

  public BigDecimal getCreditLimit() {
    if (creditLimit!=null) {
      return creditLimit.getAmount();
    }
    return null;
  }

  public Map<Long, Money> getCreditReservations() {
    return creditReservations;
  }

  public void setCreditReservations(Map<Long, Money> creditReservations) {
    this.creditReservations = creditReservations;
  }

}
