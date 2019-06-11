package com.excella.reactor.domain;

import java.io.Serializable;

public interface DomainModel<ID> extends Serializable {
    ID getId();
    void setId(ID id);
}
