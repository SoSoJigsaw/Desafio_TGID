package tgid.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tgid.dto.TransacaoDTO;
import tgid.entity.Cliente;
import tgid.entity.Empresa;
import tgid.entity.Transacao;
import tgid.exception.ClienteNaoEncontradoException;
import tgid.exception.EmpresaNaoEncontradaException;
import tgid.exception.SaldoInsuficienteException;
import tgid.exception.TransacaoNaoEncontradaException;
import tgid.notification.NotificacaoCliente;
import tgid.notification.NotificacaoEmpresa;
import tgid.repository.ClienteRepository;
import tgid.repository.EmpresaRepository;
import tgid.repository.TransacaoRepository;
import tgid.util.CalcularTaxa;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final EmpresaRepository empresaRepository;
    private final ClienteRepository clienteRepository;
    private final CalcularTaxa calcularTaxa;
    private final NotificacaoEmpresa notificacaoEmpresa;
    private final NotificacaoCliente notificacaoCliente;

    public TransacaoServiceImpl(TransacaoRepository transacaoRepository, EmpresaRepository empresaRepository,
                                ClienteRepository clienteRepository, CalcularTaxa calcularTaxa,
                                NotificacaoEmpresa notificacaoEmpresa, NotificacaoCliente notificacaoCliente) {
        this.transacaoRepository = transacaoRepository;
        this.empresaRepository = empresaRepository;
        this.clienteRepository = clienteRepository;
        this.calcularTaxa = calcularTaxa;
        this.notificacaoEmpresa = notificacaoEmpresa;
        this.notificacaoCliente = notificacaoCliente;
    }

    @Override
    public void realizarDeposito(Long empresaId, Long clienteId, double valor) {

        Empresa empresa = empresaRepository.getReferenceById(empresaId);
        Cliente cliente = clienteRepository.getReferenceById(clienteId);

        if (empresa != null) {

            if (cliente != null) {

                // Lógica para calcular a taxa correspondente ao depósito
                double taxa = calcularTaxa.calcularTaxaDeposito(valor, empresa.getTaxaDeposito());

                // Atualiza o saldo da empresa considerando a taxa
                double saldoAtualizado = empresa.getSaldo() + (valor - taxa);
                empresa.setSaldo(saldoAtualizado);
                empresaRepository.atualizarSaldo(empresa.getId(), empresa.getSaldo());

                // Registra a transação
                Transacao transacao = new Transacao(valor, "DEPÓSITO", LocalDateTime.now(), cliente, empresa);
                transacaoRepository.save(transacao);

                String dataTransacao = transacao.getDataTransacao().format(DateTimeFormatter
                        .ofPattern("dd/MM/yy HH:mm:ss"));
                int saldoInt = empresa.getSaldo().intValue();

                // Realizar callback à empresa
                notificacaoEmpresa.enviarCallbackParaEmpresa("https://webhook.site/5897243b-cbcd-492c-843e-ca3830f9de3b",
                        "Transação recebida: Depósito de " + (int) transacao.getValor() + " reais."
                                + "\n Feita por: " + transacao.getCliente().getNome() + ". \n" + dataTransacao + "\n " +
                                "Saldo atual da empresa " + transacao.getEmpresa().getNome() + ": " +
                                 saldoInt + " reais.");

                // Realizar notificação ao cliente por email
                notificacaoCliente.enviarNotificacaoKafka(transacao.getCliente().getEmail(),
                        "Depósito de " + (int) transacao.getValor() + " reais concluída",
                        "Seu depósito no valor de " + (int) transacao.getValor() + " reais na empresa " +
                                transacao.getEmpresa().getNome() + " foi concluída com sucesso. \n Data/Hora da operação: "
                                + dataTransacao);
            } else {
                throw new ClienteNaoEncontradoException("ERRO: o cliente buscado para a operação não existe");
            }

        } else {
            throw new EmpresaNaoEncontradaException("ERRO: a empresa buscada para a operação não existe.");
        }
    }

    @Override
    public void realizarSaque(Long empresaId, Long clienteId, double valor) {

        Empresa empresa = empresaRepository.getReferenceById(empresaId);
        Cliente cliente = clienteRepository.getReferenceById(clienteId);

        if (empresa != null) {

            if (cliente != null) {

                // Lógica para calcular a taxa correspondente ao saque
                double taxa = calcularTaxa.calcularTaxaSaque(valor, empresa.getTaxaSaque());

                if (empresa.getSaldo() >= (valor + taxa)) {

                    // Atualiza o saldo da empresa considerando a taxa
                    double saldoAtualizado = empresa.getSaldo() - (valor + taxa);
                    empresa.setSaldo(saldoAtualizado);
                    empresaRepository.atualizarSaldo(empresa.getId(), empresa.getSaldo());

                    // Registra a transação
                    Transacao transacao = new Transacao(valor, "SAQUE", LocalDateTime.now(), cliente, empresa);
                    transacaoRepository.save(transacao);

                    String dataTransacao = transacao.getDataTransacao().format(DateTimeFormatter
                            .ofPattern("dd/MM/yy HH:mm:ss"));
                    int saldoInt = empresa.getSaldo().intValue();

                    // Realizar callback à empresa
                    notificacaoEmpresa.enviarCallbackParaEmpresa("https://webhook.site/5897243b-cbcd-492c-843e-ca3830f9de3b",
                            "Transação recebida: Saque de " + (int) transacao.getValor() + " reais."
                                    + "\n Feita por: " + transacao.getCliente().getNome() + ". \n" + dataTransacao + "\n " +
                                    "Saldo atual da empresa " + transacao.getEmpresa().getNome() + ": " +
                                    saldoInt + " reais.");

                    // Realizar notificação ao cliente por email
                    notificacaoCliente.enviarNotificacaoKafka(transacao.getCliente().getEmail(),
                            "Saque de " + (int) transacao.getValor() + " reais concluída",
                            "Seu saque no valor de " + (int) transacao.getValor() + " reais na empresa " +
                                    transacao.getEmpresa().getNome() + " foi concluída com sucesso. \n Data/Hora da operação: "
                                    + dataTransacao);

                } else {
                    throw new SaldoInsuficienteException("Não foi possível concluir a operação: " +
                            "saldo insuficiente para a transição.");
                }
            } else {
                throw new ClienteNaoEncontradoException("ERRO: o cliente buscaso para a operação não existe.");
            }
        } else {
            throw new EmpresaNaoEncontradaException("ERRO: a empresa buscada para a operação não existe.");
        }
    }

    @Override
    public List<TransacaoDTO> listarTodasTransacoes() {

        List<TransacaoDTO> transacoesDTO = new ArrayList<>();

        List<Transacao> transacoes = transacaoRepository.findAll();

        for (Transacao transacao : transacoes) {

            TransacaoDTO dto = new TransacaoDTO();

            String dataTransacao = transacao.getDataTransacao().format(DateTimeFormatter
                                    .ofPattern("dd/MM/yy HH:mm:ss"));

            dto.setId(transacao.getId());
            dto.setTipo(transacao.getTipo());
            dto.setValor(transacao.getValor());
            dto.setDataTransacao(dataTransacao);
            dto.setClienteNome(transacao.getCliente().getNome());
            dto.setEmpresaNome(transacao.getEmpresa().getNome());

            transacoesDTO.add(dto);
        }
        return transacoesDTO;
    }

    @Override
    @Transactional
    public void deleteTransacao(Long id) {
        Transacao transacao = transacaoRepository.getReferenceById(id);

        if (transacao == null) {
            throw new TransacaoNaoEncontradaException(id);
        }

        transacaoRepository.delete(transacao);
    }
}
