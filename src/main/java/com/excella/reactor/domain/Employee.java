package com.excella.reactor.domain;

import java.io.Serializable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.data.annotation.Id;

@Value
@Builder
@Data
@Entity
public class Employee implements DomainModel, Serializable {
  @NonFinal @Id private Long id;

  @Embedded private Bio bio;

  @Embedded private Contact contact;
}
