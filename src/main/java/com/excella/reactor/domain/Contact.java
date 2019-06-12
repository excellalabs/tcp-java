package com.excella.reactor.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.Data;

@Data
@Embeddable
class Contact implements Serializable {
  private String email;
  private String phoneNumber;
  @Embedded
  private Address address;
}
