package com.romands.Repository;

import com.romands.Entity.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferenciaRepository extends JpaRepository<Transferencia,Long> {
   List<Transferencia> findAllByOrderByCreatedAt();

}
