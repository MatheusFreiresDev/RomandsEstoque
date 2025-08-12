package com.romands.Entity;

public enum TransferenciaStatus {
        PENDENTE("Pendente"),
        FEITA("FEITA"),
        CANCELADA("CANCELADA");

        private String status;
        TransferenciaStatus (String Status){
            this.status = Status;
        }
}
