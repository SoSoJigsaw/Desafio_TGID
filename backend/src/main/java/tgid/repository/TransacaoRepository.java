package tgid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tgid.entity.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
