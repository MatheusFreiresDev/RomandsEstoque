package com.romands.Controller.Response;

import com.romands.Entity.ItemPedido;
import com.romands.Entity.TransferenciaStatus;
import com.romands.Entity.User;
import lombok.Builder;

import java.util.List;
@Builder
public record TransferenciaResponse (String user, List<ItemPedido> produtos, TransferenciaStatus status){
}
