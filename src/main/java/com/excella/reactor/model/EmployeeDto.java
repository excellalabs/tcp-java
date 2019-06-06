package com.excella.reactor.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = EmployeeDto.EmployeeDtoBuilder.class)
public class EmployeeDto {
  @NonNull
  private String firstName;

  @NonNull
  private String lastName;

  private int yearPublished;

  @JsonPOJOBuilder(withPrefix = "")
  public static class EmployeeDtoBuilder {}
}
