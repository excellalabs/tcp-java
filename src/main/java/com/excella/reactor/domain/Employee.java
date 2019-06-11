package com.excella.reactor.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Value
@Builder
@Data
public class Employee implements DomainModel {
  @NonFinal
  @Id
  private Long id;

  private Bio bio;

  private Contact contact;

}
