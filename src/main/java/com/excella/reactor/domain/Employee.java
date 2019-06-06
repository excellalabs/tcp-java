package com.excella.reactor.domain;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class Employee {
  @NonFinal
  @Id
  private Long id;

  @NotNull
  private String firstName;

  @NotNull
  private String lastName;

}
