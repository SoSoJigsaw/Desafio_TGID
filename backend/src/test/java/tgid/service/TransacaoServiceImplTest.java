package tgid.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import tgid.entity.Cliente;
import tgid.entity.Empresa;
import tgid.entity.Transacao;
import tgid.notification.NotificacaoCliente;
import tgid.notification.NotificacaoEmpresa;
import tgid.repository.ClienteRepository;
import tgid.repository.EmpresaRepository;
import tgid.repository.TransacaoRepository;
import tgid.util.CalcularTaxa;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class TransacaoServiceImplTest {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    TransacaoServiceImpl transacaoService;


    // Deveria criar um novo registro de transação e com todos os mesmos dados passados como parâmetro
    @Test
    void testCadastrarTransacao() {

        Cliente cliente = new Cliente();
        cliente.setCpf("529.982.247-25");
        cliente.setNome("João Da Silva");
        cliente.setEmail("joao.silva@hotmail.com");
        cliente.setSaldo(1000.0);
        clienteRepository.save(cliente);

        Empresa empresa = new Empresa();
        empresa.setNome("Teste");
        empresa.setCnpj("04.252.011/0001-10");
        empresa.setTaxaDeposito(0.5);
        empresa.setTaxaSaque(0.5);
        empresa.setSaldo(5000.0);
        empresaRepository.save(empresa);

        Transacao transacao = new Transacao();
        transacao.setTipo("DEPÓSITO");
        transacao.setCliente(cliente);
        transacao.setEmpresa(empresa);
        transacao.setValor(500);
        transacao.setDataTransacao(LocalDateTime.now());
        transacaoRepository.save(transacao);

        Transacao novaTransacao = transacaoRepository.findByDataTransacao(transacao.getDataTransacao())
                .orElse(null);

        Assertions.assertNotNull(novaTransacao);
        Assertions.assertEquals(transacao.getTipo(), novaTransacao.getTipo());
        Assertions.assertEquals(transacao.getCliente().getCpf(), novaTransacao.getCliente().getCpf());
        Assertions.assertEquals(transacao.getEmpresa().getCnpj(), novaTransacao.getEmpresa().getCnpj());
        Assertions.assertEquals(transacao.getValor(), novaTransacao.getValor());

        transacaoService.deleteTransacao(novaTransacao.getId());
        clienteService.deleteCliente(cliente.getId());
        empresaService.deleteEmpresa(empresa.getId());
    }

    // Deveria listar todos as transações
    @Test
    void testListarTodasTransacoes() {

        List<Transacao> transacoes = transacaoRepository.findAll();

        Assertions.assertNotNull(transacoes);

        for (Transacao transacao : transacoes) {
            Assertions.assertTrue(transacaoRepository.findById(transacao.getId()).isPresent());
        }
    }

    // Deveria deletar uma transacao por seu id
    @Test
    void testDeleteTransacao() {

        Cliente cliente = new Cliente();
        cliente.setCpf("529.982.247-25");
        cliente.setNome("João Da Silva");
        cliente.setEmail("joao.silva@hotmail.com");
        cliente.setSaldo(1000.0);
        clienteRepository.save(cliente);

        Empresa empresa = new Empresa();
        empresa.setNome("Teste");
        empresa.setCnpj("04.252.011/0001-10");
        empresa.setTaxaDeposito(0.5);
        empresa.setTaxaSaque(0.5);
        empresa.setSaldo(5000.0);
        empresaRepository.save(empresa);

        Transacao transacao = new Transacao();
        transacao.setTipo("DEPÓSITO");
        transacao.setCliente(cliente);
        transacao.setEmpresa(empresa);
        transacao.setValor(500);
        transacao.setDataTransacao(LocalDateTime.now());
        transacaoRepository.save(transacao);

        Transacao novaTransacao = transacaoRepository.findByDataTransacao(transacao.getDataTransacao())
                .orElse(null);

        transacaoService.deleteTransacao(novaTransacao.getId());

        Transacao transacaoDeletada = transacaoRepository.findById(novaTransacao.getId()).orElse(null);

        Assertions.assertNull(transacaoDeletada);

        clienteService.deleteCliente(cliente.getId());
        empresaService.deleteEmpresa(empresa.getId());
    }
}