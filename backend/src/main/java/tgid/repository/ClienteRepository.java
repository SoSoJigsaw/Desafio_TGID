package tgid.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tgid.entity.Cliente;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Cliente c SET c.saldo = :novoSaldo WHERE c.id = :id")
    void atualizarSaldo(@Param("id") Long id, @Param("novoSaldo") double novoSaldo);

    Optional<Cliente> findByEmail(String email);
}
