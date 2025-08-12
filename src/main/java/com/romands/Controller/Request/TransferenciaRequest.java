package com.romands.Controller.Request;

import com.romands.Entity.ItemPedido;
import com.romands.Entity.TransferenciaStatus;

import java.util.List;

public record TransferenciaRequest(Long lojaId, List<ItemPedido> produtos, TransferenciaStatus transferenciaStatus) {}
