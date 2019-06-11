package com.excella.reactor.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
class Contact implements Serializable {
  private String email;
  private String phoneNumber;
}
