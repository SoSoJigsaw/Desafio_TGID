package tgid.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class TransacaoServiceImplTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteService clienteService;

    @Mock
    private EmpresaService empresaService;

    @Mock
    CalcularTaxa calcularTaxa;

    @Mock
    NotificacaoEmpresa notificacaoEmpresa;

    @Mock
    NotificacaoCliente notificacaoCliente;

    @InjectMocks
    TransacaoServiceImpl transacaoService;


    // Realize um depósito com sucesso, atualizando o saldo do cliente e da empresa e salvando a transação
    @Test
    public void testRealizarDepositoComSucesso() {
        // Configurar comportamento simulado
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        empresa.setNome("Empresa A");
        empresa.setSaldo(1000.0);
        empresa.setTaxaDeposito(0.5);

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente A");
        cliente.setSaldo(500.0);

        when(empresaRepository.findById(anyLong())).thenReturn(Optional.of(empresa));
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(empresaRepository.getReferenceById(anyLong())).thenReturn(empresa);
        when(clienteRepository.getReferenceById(anyLong())).thenReturn(cliente);
        when(calcularTaxa.calcularTaxaDeposito(anyDouble(), anyDouble())).thenReturn(100.0);

        // Chamar o método em teste
        ResponseEntity<?> response = transacaoService.realizarDeposito(1L, 1L, 200.0);

        // Afirmar o resultado
        Assertions.assertEquals(ResponseEntity.ok().body("Transação de Depósito realizada com sucesso"), response);
        Assertions.assertEquals(300.0, cliente.getSaldo(), 0.01);
        Assertions.assertEquals(1100.0, empresa.getSaldo(), 0.01);
        verify(transacaoRepository, times(1)).save(any(Transacao.class));
        verify(clienteRepository, times(1)).atualizarSaldo(anyLong(), anyDouble());
        verify(empresaRepository, times(1)).atualizarSaldo(anyLong(), anyDouble());
    }

    // Realize um saque com sucesso, atualizando o saldo do cliente e da empresa e salvando a transação
    @Test
    public void testRealizarSaqueComSucesso() {
        // Configurar comportamento simulado
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        empresa.setNome("Empresa A");
        empresa.setSaldo(1000.0);
        empresa.setTaxaSaque(0.5);

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente A");
        cliente.setSaldo(500.0);

        when(empresaRepository.findById(anyLong())).thenReturn(Optional.of(empresa));
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(empresaRepository.getReferenceById(anyLong())).thenReturn(empresa);
        when(clienteRepository.getReferenceById(anyLong())).thenReturn(cliente);
        when(calcularTaxa.calcularTaxaSaque(anyDouble(), anyDouble())).thenReturn(100.0);

        // Chamar o método em teste
        ResponseEntity<?> response = transacaoService.realizarSaque(1L, 1L, 200.0);

        // Afirmar o resultado
        Assertions.assertEquals(ResponseEntity.ok().body("Transação de Saque realizada com sucesso"), response);
        Assertions.assertEquals(700.0, cliente.getSaldo(), 0.01);
        Assertions.assertEquals(700.0, empresa.getSaldo(), 0.01);
        verify(transacaoRepository, times(1)).save(any(Transacao.class));
        verify(clienteRepository, times(1)).atualizarSaldo(anyLong(), anyDouble());
        verify(empresaRepository, times(1)).atualizarSaldo(anyLong(), anyDouble());
    }

    // Deveria criar um novo registro de transação e com todos os mesmos dados passados como parâmetro
    @Test
    void testCadastrarTransacao() {

        // Define o comportamento dos mocks de persistencia de dados
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation ->
        {
            Cliente savedCliente = invocation.getArgument(0);
            savedCliente.setId(1L);
            return savedCliente;
        });
        when(empresaRepository.save(any(Empresa.class))).thenAnswer(invocation ->
        {
            Empresa savedEmpresa = invocation.getArgument(0);
            savedEmpresa.setId(1L);
            return savedEmpresa;
        });
        when(transacaoRepository.save(any(Transacao.class))).thenAnswer(invocation ->
        {
            Transacao savedTransacao = invocation.getArgument(0);
            savedTransacao.setId(1L);
            return savedTransacao;
        });

        // Persistência dos objetos necessários
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

        // Define o comportamento dos mocks nos métodos principais do teste
        when(transacaoRepository.findByDataTransacao(any(LocalDateTime.class))).thenReturn(Optional.of(transacao));

        Transacao novaTransacao = transacaoRepository.findByDataTransacao(transacao.getDataTransacao())
                .orElse(null);

        Assertions.assertNotNull(novaTransacao);
        Assertions.assertEquals(transacao.getTipo(), novaTransacao.getTipo());
        Assertions.assertEquals(transacao.getCliente().getCpf(), novaTransacao.getCliente().getCpf());
        Assertions.assertEquals(transacao.getEmpresa().getCnpj(), novaTransacao.getEmpresa().getCnpj());
        Assertions.assertEquals(transacao.getValor(), novaTransacao.getValor());
    }

    // Deveria listar todos as transações
    @Test
    void testListarTodasTransacoes() {

        // Criação de objetos necessários
        Cliente cliente = new Cliente();
        cliente.setCpf("529.982.247-25");
        cliente.setNome("João Da Silva");
        cliente.setEmail("joao.silva@hotmail.com");
        cliente.setSaldo(1000.0);

        Empresa empresa = new Empresa();
        empresa.setNome("Teste");
        empresa.setCnpj("04.252.011/0001-10");
        empresa.setTaxaDeposito(0.5);
        empresa.setTaxaSaque(0.5);
        empresa.setSaldo(5000.0);

        Transacao transacao = new Transacao();
        transacao.setTipo("DEPÓSITO");
        transacao.setCliente(cliente);
        transacao.setEmpresa(empresa);
        transacao.setValor(500);
        transacao.setDataTransacao(LocalDateTime.now());

        Transacao transacao2 = new Transacao();
        transacao2.setTipo("SAQUE");
        transacao2.setCliente(cliente);
        transacao2.setEmpresa(empresa);
        transacao2.setValor(250);
        transacao2.setDataTransacao(LocalDateTime.now());

        Transacao transacao3 = new Transacao();
        transacao3.setTipo("DEPÓSITO");
        transacao3.setCliente(cliente);
        transacao3.setEmpresa(empresa);
        transacao3.setValor(100);
        transacao3.setDataTransacao(LocalDateTime.now());

        List<Transacao> transacoes = new ArrayList<>();
        transacoes.add(transacao);
        transacoes.add(transacao2);
        transacoes.add(transacao3);

        // Define o comportamento dos mocks
        when(transacaoRepository.findAll()).thenReturn(transacoes);

        List<Transacao> result = transacaoRepository.findAll();

        // Afirmações
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.containsAll(transacoes));
    }

    // Deveria deletar uma transacao por seu id
    @Test
    void testDeleteTransacao() {

        // Persistência dos objetos necessários
        Cliente cliente = new Cliente();
        cliente.setCpf("529.982.247-25");
        cliente.setNome("João Da Silva");
        cliente.setEmail("joao.silva@hotmail.com");
        cliente.setSaldo(1000.0);

        Empresa empresa = new Empresa();
        empresa.setNome("Teste");
        empresa.setCnpj("04.252.011/0001-10");
        empresa.setTaxaDeposito(0.5);
        empresa.setTaxaSaque(0.5);
        empresa.setSaldo(5000.0);

        Transacao transacao = new Transacao();
        transacao.setTipo("DEPÓSITO");
        transacao.setCliente(cliente);
        transacao.setEmpresa(empresa);
        transacao.setValor(500);
        transacao.setDataTransacao(LocalDateTime.now());

        // Define o comportamento dos mocks nos métodos principais do teste
        when(transacaoRepository.findByDataTransacao(any(LocalDateTime.class))).thenReturn(Optional.of(transacao));
        when(transacaoRepository.findById(anyLong())).thenReturn(Optional.of(transacao));
        when(transacaoRepository.getReferenceById(anyLong())).thenReturn(transacao);
        doNothing().when(transacaoRepository).delete(any(Transacao.class));

        // Chamada dos métodos a ser testado
        Transacao novaTransacao = transacaoRepository.findByDataTransacao(transacao.getDataTransacao())
                .orElse(null);

        Assertions.assertNotNull(novaTransacao);

        transacaoService.deleteTransacao(1L);

        verify(transacaoRepository, times(1)).getReferenceById(1L);
        verify(transacaoRepository, times(1)).delete(transacao);

    }
}