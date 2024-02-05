package tgid.service;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tgid.dto.SaldoInsuficienteDTO;
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
    public ResponseEntity<?> realizarDeposito(Long empresaId, Long clienteId, double valor) {

        Empresa empresa = empresaRepository.getReferenceById(empresaId);
        Cliente cliente = clienteRepository.getReferenceById(clienteId);

        if (empresa != null) {

            // Lógica para calcular a taxa correspondente ao depósito
            double taxa = calcularTaxa.calcularTaxaDeposito(valor, empresa.getTaxaDeposito());

            if (cliente != null) {

                if ((cliente.getSaldo() - valor) < 0) {

                    int saldo = cliente.getSaldo().intValue();
                    SaldoInsuficienteDTO saldoInsuficienteDTO = new SaldoInsuficienteDTO(
                            "Cliente", cliente.getNome(), saldo, "Depósito", (int) valor);

                    // Realizar callback à empresa de falha por falta de saldo na transação
                    notificacaoEmpresa.enviarCallbackParaEmpresa("https://webhook.site/5897243b-cbcd-492c-843e-ca3830f9de3b",
                            notificacaoEmpresa.formatCallbackFalhaCliente("Depósito", (int) valor,
                                    cliente.getNome(), empresa.getNome(),
                                    LocalDateTime.now(), empresa.getSaldo(),
                                    empresa.getSaldo() + (valor - taxa)));


                    // Realizar notificação ao cliente por email de falha por falta de saldo na transação
                    notificacaoCliente.enviarNotificacaoKafka(cliente.getEmail(),
                            notificacaoCliente.formatAssuntoOperacaoNegada("Depósito", (int) valor),
                            notificacaoCliente.formatCorpoOperacaoNegadaCliente("depósito", (int) valor,
                                    empresa.getNome(), LocalDateTime.now(), cliente.getSaldo()));

                    return ResponseEntity.badRequest().body(saldoInsuficienteDTO);
                }

                // Atualiza o saldo do cliente
                double saldoClienteAtualizado = cliente.getSaldo() - valor;
                cliente.setSaldo(saldoClienteAtualizado);
                clienteRepository.atualizarSaldo(cliente.getId(), cliente.getSaldo());

                // Atualiza o saldo da empresa considerando a taxa
                double saldoEmpresaAtualizado = empresa.getSaldo() + (valor - taxa);
                empresa.setSaldo(saldoEmpresaAtualizado);
                empresaRepository.atualizarSaldo(empresa.getId(), empresa.getSaldo());

                // Registra a transação
                Transacao transacao = new Transacao(valor, "DEPÓSITO", LocalDateTime.now(), cliente, empresa);
                transacaoRepository.save(transacao);

                // Realizar callback à empresa de sucesso na transação
                notificacaoEmpresa.enviarCallbackParaEmpresa("https://webhook.site/5897243b-cbcd-492c-843e-ca3830f9de3b",
                        notificacaoEmpresa.formatCallbackSucesso("Depósito", (int) transacao.getValor(),
                                transacao.getCliente().getNome(), transacao.getEmpresa().getNome(),
                                transacao.getDataTransacao(), empresa.getSaldo()));

                // Realizar notificação ao cliente por email de sucesso na transação
                notificacaoCliente.enviarNotificacaoKafka(transacao.getCliente().getEmail(),
                        notificacaoCliente.formatAssuntoOperacaoRealizada("Depósito", (int) transacao.getValor()),
                        notificacaoCliente.formatCorpoOperacaoRealizada("depósito", (int) transacao.getValor(),
                                transacao.getEmpresa().getNome(), transacao.getDataTransacao(), cliente.getSaldo()));
            } else {
                throw new ClienteNaoEncontradoException("ERRO: o cliente buscado para a operação não existe");
            }

        } else {
            throw new EmpresaNaoEncontradaException("ERRO: a empresa buscada para a operação não existe.");
        }

        return ResponseEntity.ok().body("Transação de Depósito realizada com sucesso");
    }

    @Override
    public ResponseEntity<?> realizarSaque(Long empresaId, Long clienteId, double valor) {

        Empresa empresa = empresaRepository.getReferenceById(empresaId);
        Cliente cliente = clienteRepository.getReferenceById(clienteId);

        if (empresa != null) {

            if (cliente != null) {

                // Lógica para calcular a taxa correspondente ao saque
                double taxa = calcularTaxa.calcularTaxaSaque(valor, empresa.getTaxaSaque());

                if (empresa.getSaldo() >= (valor + taxa)) {

                    // Atualiza o saldo do cliente
                    double saldoClienteAtualizado = cliente.getSaldo() + valor;
                    cliente.setSaldo(saldoClienteAtualizado);
                    clienteRepository.atualizarSaldo(cliente.getId(), cliente.getSaldo());

                    // Atualiza o saldo da empresa considerando a taxa
                    double saldoAtualizado = empresa.getSaldo() - (valor + taxa);
                    empresa.setSaldo(saldoAtualizado);
                    empresaRepository.atualizarSaldo(empresa.getId(), empresa.getSaldo());

                    // Registra a transação
                    Transacao transacao = new Transacao(valor, "SAQUE", LocalDateTime.now(), cliente, empresa);
                    transacaoRepository.save(transacao);

                    // Realizar callback à empresa de sucesso na transação
                    notificacaoEmpresa.enviarCallbackParaEmpresa("https://webhook.site/5897243b-cbcd-492c-843e-ca3830f9de3b",
                            notificacaoEmpresa.formatCallbackSucesso("Saque", (int) transacao.getValor(),
                                    transacao.getCliente().getNome(), transacao.getEmpresa().getNome(),
                                    transacao.getDataTransacao(), empresa.getSaldo()));


                    // Realizar notificação ao cliente por email de sucesso na transação
                    notificacaoCliente.enviarNotificacaoKafka(transacao.getCliente().getEmail(),
                            notificacaoCliente.formatAssuntoOperacaoRealizada("Saque", (int) transacao.getValor()),
                            notificacaoCliente.formatCorpoOperacaoRealizada("saque", (int) transacao.getValor(),
                                    transacao.getEmpresa().getNome(), transacao.getDataTransacao(), cliente.getSaldo()));

                } else {
                    int saldo = empresa.getSaldo().intValue();
                    SaldoInsuficienteDTO saldoInsuficienteDTO = new SaldoInsuficienteDTO(
                            "Empresa", empresa.getNome(), saldo, "Saque", (int) valor);

                    // Realizar callback à empresa de falha por falta de saldo na transação
                    notificacaoEmpresa.enviarCallbackParaEmpresa("https://webhook.site/5897243b-cbcd-492c-843e-ca3830f9de3b",
                            notificacaoEmpresa.formatCallbackFalhaEmpresa("Saque", (int) valor,
                                    cliente.getNome(), empresa.getNome(),
                                    LocalDateTime.now(), empresa.getSaldo(),
                                    empresa.getSaldo() - (valor + taxa)));


                    // Realizar notificação ao cliente por email de falha por falta de saldo na transação
                    notificacaoCliente.enviarNotificacaoKafka(cliente.getEmail(),
                            notificacaoCliente.formatAssuntoOperacaoNegada("Saque", (int) valor),
                            notificacaoCliente.formatCorpoOperacaoNegadaEmpresa("Saque", (int) valor,
                                    empresa.getNome(), LocalDateTime.now(), cliente.getSaldo()));

                    return ResponseEntity.badRequest().body(saldoInsuficienteDTO);
                }
            } else {
                throw new ClienteNaoEncontradoException("ERRO: o cliente buscado para a operação não existe.");
            }
        } else {
            throw new EmpresaNaoEncontradaException("ERRO: a empresa buscada para a operação não existe.");
        }

        return ResponseEntity.ok().body("Transação de Saque realizada com sucesso");
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
