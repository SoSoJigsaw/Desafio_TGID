package tgid.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tgid.entity.Cliente;
import tgid.entity.Empresa;
import tgid.entity.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Transacao t WHERE t.empresa = :empresa")
    void deleteApartirDaEmpresa(@Param("empresa") Empresa empresa);

    @Modifying
    @Transactional
    @Query("DELETE FROM Transacao t WHERE t.cliente = :cliente")
    void deleteApartirDoCliente(@Param("cliente") Cliente cliente);

}
