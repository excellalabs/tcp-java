package com.excella.reactor.domain;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
class Contact {
  private String email;
  private String phoneNumber;
  private Address address;
}
