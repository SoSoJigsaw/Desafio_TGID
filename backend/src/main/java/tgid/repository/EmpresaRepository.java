package tgid.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tgid.entity.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Empresa e SET e.taxaDeposito = :novoValor WHERE e.id = :empresaId")
    void atualizarTaxaDeposito(@Param("empresaId") Long empresaId, @Param("novoValor") double novoValor);

    @Modifying
    @Transactional
    @Query("UPDATE Empresa e SET e.taxaSaque = :novoValor WHERE e.id = :empresaId")
    void atualizarTaxaSaque(@Param("empresaId") Long empresaId, @Param("novoValor") double novoValor);

    @Modifying
    @Transactional
    @Query("UPDATE Empresa e SET e.saldo = :novoSaldo WHERE e.id = :id")
    void atualizarSaldo(@Param("id") Long id, @Param("novoSaldo") double novoSaldo);

}
