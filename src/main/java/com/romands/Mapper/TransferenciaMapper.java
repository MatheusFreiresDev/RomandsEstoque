package com.romands.Mapper;

import com.romands.Controller.Request.TransferenciaRequest;
import com.romands.Controller.Response.TransferenciaResponse;
import com.romands.Entity.Transferencia;
import com.romands.Entity.User;

public class TransferenciaMapper {

    public static Transferencia toEntity(User loja, TransferenciaRequest dto) {
        if (dto == null) return null;

        return Transferencia.builder()
                .loja(loja)
                .produtos(dto.produtos())
                .status(dto.transferenciaStatus())
                .build();
    }

    public static TransferenciaResponse toDTO(Transferencia entity) {
        if (entity == null) return null;

        return TransferenciaResponse.builder()
                .user(entity.getLoja().getUsername())
                .produtos(entity.getProdutos())
                .status(entity.getStatus())
                .build();
    }
}
