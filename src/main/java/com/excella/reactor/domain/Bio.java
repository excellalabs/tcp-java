package com.excella.reactor.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Embeddable
public class Bio implements Serializable {
  private String firstName;
  private String middleName;
  private String lastName;
  private LocalDate birthDate;
  private Gender gender;
  private Ethnicity ethnicity;
  private boolean usCitizen;
}
