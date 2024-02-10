package tgid.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import tgid.entity.Cliente;
import tgid.entity.Empresa;
import tgid.entity.Transacao;

import java.time.LocalDateTime;


@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Transactional
public class TransacaoRepositoryTest {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EntityManager entityManager;

    // Deveria deletar o registro de transação a partir da empresa que participou da transação
    @Test
    public void testDeleteApartirDaEmpresa() {

        Empresa empresa = new Empresa();
        empresa.setNome("Teste");
        empresa.setCnpj("04.252.011/0001-10");
        empresa.setSaldo(5000.0);
        empresa.setTaxaDeposito(0.5);
        empresa.setTaxaSaque(0.5);
        empresaRepository.save(empresa);
        entityManager.flush();

        Cliente cliente = new Cliente();
        cliente.setCpf("417.577.918-33");
        cliente.setEmail("felipesobral_@hotmail.com");
        cliente.setNome("Felipe Sobral");
        cliente.setSaldo(1000.0);
        clienteRepository.save(cliente);
        entityManager.flush();

        Transacao transacao = new Transacao();
        transacao.setTipo("DEPÓSITO");
        transacao.setEmpresa(empresa);
        transacao.setCliente(cliente);
        transacao.setValor(500);
        transacao.setDataTransacao(LocalDateTime.now());
        transacaoRepository.save(transacao);
        entityManager.flush();


        transacaoRepository.deleteApartirDaEmpresa(empresa);
        entityManager.flush();
        entityManager.clear();

        Transacao result = transacaoRepository.findById(transacao.getId()).orElse(null);
        Assertions.assertNull(result);
    }

    // Deveria deletar o registro de transação a partir do cliente que participou da transação
    @Test
    public void testDeleteApartirDoCliente() {

        Empresa empresa = new Empresa();
        empresa.setNome("Teste");
        empresa.setCnpj("04.252.011/0001-10");
        empresa.setSaldo(5000.0);
        empresa.setTaxaDeposito(0.5);
        empresa.setTaxaSaque(0.5);
        empresaRepository.save(empresa);
        entityManager.flush();

        Cliente cliente = new Cliente();
        cliente.setCpf("417.577.918-33");
        cliente.setEmail("felipesobral_@hotmail.com");
        cliente.setNome("Felipe Sobral");
        cliente.setSaldo(1000.0);
        clienteRepository.save(cliente);
        entityManager.flush();

        Transacao transacao = new Transacao();
        transacao.setTipo("DEPÓSITO");
        transacao.setEmpresa(empresa);
        transacao.setCliente(cliente);
        transacao.setValor(500);
        transacao.setDataTransacao(LocalDateTime.now());
        transacaoRepository.save(transacao);
        entityManager.flush();


        transacaoRepository.deleteApartirDoCliente(cliente);
        entityManager.flush();
        entityManager.clear();

        Transacao result = transacaoRepository.findById(transacao.getId()).orElse(null);
        Assertions.assertNull(result);

    }

}
