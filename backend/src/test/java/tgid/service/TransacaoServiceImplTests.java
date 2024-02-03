package tgid.service;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

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

@TestConfiguration
@Import(TransacaoServiceImpl.class)
public class TransacaoServiceImplTests {

    @Mock
    private TransacaoRepository transacaoRepositoryMock;

    @Mock
    private EmpresaRepository empresaRepositoryMock;

    @Mock
    private ClienteRepository clienteRepositoryMock;

    @Mock
    private CalcularTaxa calcularTaxaMock;

    @Mock
    private NotificacaoEmpresa notificacaoEmpresaMock;

    @Mock
    private NotificacaoCliente notificacaoClienteMock;

    @InjectMocks
    private TransacaoServiceImpl transacaoService;

    // TransacaoServiceImpl pode realizar um depósito com sucesso, atualizando o saldo da empresa,
    // criando uma transação, enviando uma notificação para o cliente e um callback para a empresa.
    @Test
    public void testRealizarDeposito_ComSucesso() {
        // Criação dos dados de teste
        Long idEmpresa = 1L;
        Long idCliente = 1L;
        double valor = 100.0;

        Empresa empresa = new Empresa();
        empresa.setId(idEmpresa);
        empresa.setSaldo(500.0);
        empresa.setTaxaDeposito(0.05);

        Cliente cliente = new Cliente();
        cliente.setId(idCliente);

        Transacao transacao = new Transacao();
        transacao.setValor(valor);
        transacao.setTipo("DEPÓSITO");
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setCliente(cliente);
        transacao.setEmpresa(empresa);

        // Configuração do comportamento do mock
        Mockito.when(empresaRepositoryMock.getReferenceById(idEmpresa)).thenReturn(empresa);
        Mockito.when(clienteRepositoryMock.getReferenceById(idCliente)).thenReturn(cliente);
        Mockito.when(calcularTaxaMock.calcularTaxaDeposito(valor, empresa.getTaxaDeposito())).thenReturn(5.0);
        Mockito.when(transacaoRepositoryMock.save(Mockito.any(Transacao.class))).thenReturn(transacao);

        // Chamada do método em teste
        transacaoService.realizarDeposito(idEmpresa, idCliente, valor);

        // Verificação das interações com os mocks
        Mockito.verify(empresaRepositoryMock).getReferenceById(idEmpresa);
        Mockito.verify(clienteRepositoryMock).getReferenceById(idCliente);
        Mockito.verify(calcularTaxaMock).calcularTaxaDeposito(valor, empresa.getTaxaDeposito());
        Mockito.verify(transacaoRepositoryMock).save(Mockito.any(Transacao.class));
        Mockito.verify(notificacaoEmpresaMock).enviarCallbackParaEmpresa(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(notificacaoClienteMock).enviarNotificacaoKafka(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

}
