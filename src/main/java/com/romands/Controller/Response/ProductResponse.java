package com.romands.Controller.Response;

import com.romands.Entity.Transferencia;
import com.romands.Entity.TransferenciaStatus;
import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record ProductResponse (String nome, String ref, BigDecimal preco, int quantidade){
}
