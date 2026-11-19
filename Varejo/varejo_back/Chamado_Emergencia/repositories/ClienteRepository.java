package Chamado_Emergencia.repositories;

import Chamado_Emergencia.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
