package com.fuenteadictos.fuenteadictos.domain;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

public class HexaDecimalUUIDGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        UUID uuid = UUID.randomUUID();
        String hex = uuid.toString().replace("-", "");
        return hex.substring(0, 16);
    }
}
