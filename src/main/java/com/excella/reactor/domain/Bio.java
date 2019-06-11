package com.excella.reactor.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Data
@Embeddable
class Bio {
  private String firstName;
  private String middleName;
  private String lastName;
  private LocalDate birthDate;
  private Gender gender;
  private Ethnicity ethnicity;
  private boolean usCitizen;
}
