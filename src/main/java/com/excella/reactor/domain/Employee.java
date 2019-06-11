package com.excella.reactor.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.data.annotation.Id;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Value
@Builder
@Data
@Entity
public class Employee implements DomainModel, Serializable {
  @NonFinal
  @Id
  private Long id;

  @Embedded
  private Bio bio;

  @Embedded
  private Contact contact;

}
