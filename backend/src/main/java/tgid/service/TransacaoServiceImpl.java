package tgid.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tgid.dto.SaldoInsuficienteDTO;
import tgid.dto.TransacaoDTO;
import tgid.entity.Cliente;
import tgid.entity.Empresa;
import tgid.entity.Transacao;
import tgid.exception.*;
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
import java.util.Optional;

@Slf4j
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
    public ResponseEntity<?> realizarDeposito(Long empresaId, Long clienteId, Double valor) throws TransacaoInvalidaException {

        Optional<Empresa> empresaOptional = empresaRepository.findById(empresaId);

        if (empresaOptional.isPresent()) {

            Optional<Cliente> clienteOptional = clienteRepository.findById(clienteId);

            if (clienteOptional.isPresent()) {

                Empresa empresa = empresaRepository.getReferenceById(empresaId);
                Cliente cliente = clienteRepository.getReferenceById(clienteId);

                // Lógica para calcular a taxa correspondente ao depósito
                double taxa = calcularTaxa.calcularTaxaDeposito(valor, empresa.getTaxaDeposito());

                if ((cliente.getSaldo() - valor) < 0) {

                    int saldo = cliente.getSaldo().intValue();
                    SaldoInsuficienteDTO saldoInsuficienteDTO = new SaldoInsuficienteDTO(
                            "Cliente", cliente.getNome(), saldo, "Depósito", valor.intValue());

                    // Realizar callback à empresa de falha por falta de saldo na transação
                    notificacaoEmpresa.enviarCallbackKafka("https://webhook.site/b4809686-59b4-4819-9918-95a2e9031d26",
                            notificacaoEmpresa.formatCallbackFalhaCliente("Depósito", valor.intValue(),
                                    cliente.getNome(), empresa.getNome(),
                                    LocalDateTime.now(), empresa.getSaldo(),
                                    empresa.getSaldo() + (valor - taxa)));


                    // Realizar notificação ao cliente por email de falha por falta de saldo na transação
                    notificacaoCliente.enviarNotificacaoKafka(cliente.getEmail(),
                            notificacaoCliente.formatAssuntoOperacaoNegada("Depósito", valor.intValue()),
                            notificacaoCliente.formatCorpoOperacaoNegadaCliente("depósito", valor.intValue(),
                                    empresa.getNome(), LocalDateTime.now(), cliente.getSaldo()));

                    log.error("Cliente " + cliente.getNome() + " tentou realizar depósito, mas a transação iria" +
                            " negativar o seu saldo. Transação negada.");
                    throw new SaldoInsuficienteException(saldoInsuficienteDTO);
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
                notificacaoEmpresa.enviarCallbackKafka("https://webhook.site/b4809686-59b4-4819-9918-95a2e9031d26",
                        notificacaoEmpresa.formatCallbackSucesso("Depósito", (int) transacao.getValor(),
                                transacao.getCliente().getNome(), transacao.getEmpresa().getNome(),
                                transacao.getDataTransacao(), empresa.getSaldo()));

                // Realizar notificação ao cliente por email de sucesso na transação
                notificacaoCliente.enviarNotificacaoKafka(transacao.getCliente().getEmail(),
                        notificacaoCliente.formatAssuntoOperacaoRealizada("Depósito", (int) transacao.getValor()),
                        notificacaoCliente.formatCorpoOperacaoRealizada("depósito", (int) transacao.getValor(),
                                transacao.getEmpresa().getNome(), transacao.getDataTransacao(), cliente.getSaldo()));
            } else {
                log.error("Durante tentativa de transação de depósito, foi informado um cliente como " +
                        "parâmetro que não existe. Operação negada.");
                throw new ClienteNaoEncontradoException("O cliente buscado para a operação não existe");
            }

        } else {
            log.error("Durante tentativa de transação de depósito, foi informado uma empresa como parâmetro " +
                    "que não existe. Operação negada");
            throw new EmpresaNaoEncontradaException("A empresa buscada para a operação não existe.");
        }

        return ResponseEntity.ok().body("Transação de Depósito realizada com sucesso");
    }

    @Override
    public ResponseEntity<?> realizarSaque(Long empresaId, Long clienteId, Double valor) throws TransacaoInvalidaException {

        Optional<Empresa> empresaOptional = empresaRepository.findById(empresaId);

        if (empresaOptional.isPresent()) {

            Optional<Cliente> clienteOptional = clienteRepository.findById(clienteId);

            if (clienteOptional.isPresent()) {

                Empresa empresa = empresaRepository.getReferenceById(empresaId);
                Cliente cliente = clienteRepository.getReferenceById(clienteId);

                // Lógica para calcular a taxa correspondente ao saque
                double taxa = calcularTaxa.calcularTaxaSaque(valor, empresa.getTaxaSaque());

                if (empresa.getSaldo() >= (valor + taxa)) {

                    // Atualiza o saldo do cliente
                    double saldoClienteAtualizado = cliente.getSaldo() + valor;
                    cliente.setSaldo(saldoClienteAtualizado);
                    clienteRepository.atualizarSaldo(cliente.getId(), cliente.getSaldo());

                    // Atualiza o saldo da empresa considerando a taxa
                    double saldoEmpresaAtualizado = empresa.getSaldo() - (valor + taxa);
                    empresa.setSaldo(saldoEmpresaAtualizado);
                    empresaRepository.atualizarSaldo(empresa.getId(), empresa.getSaldo());

                    // Registra a transação
                    Transacao transacao = new Transacao(valor, "SAQUE", LocalDateTime.now(), cliente, empresa);
                    transacaoRepository.save(transacao);

                    // Realizar callback à empresa de sucesso na transação
                    notificacaoEmpresa.enviarCallbackKafka("https://webhook.site/b4809686-59b4-4819-9918-95a2e9031d26",
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
                            "Empresa", empresa.getNome(), saldo, "Saque", valor.intValue());

                    // Realizar callback à empresa de falha por falta de saldo na transação
                    notificacaoEmpresa.enviarCallbackKafka("https://webhook.site/b4809686-59b4-4819-9918-95a2e9031d26",
                            notificacaoEmpresa.formatCallbackFalhaEmpresa("Saque", valor.intValue(),
                                    cliente.getNome(), empresa.getNome(),
                                    LocalDateTime.now(), empresa.getSaldo(),
                                    empresa.getSaldo() - (valor + taxa)));


                    // Realizar notificação ao cliente por email de falha por falta de saldo na transação
                    notificacaoCliente.enviarNotificacaoKafka(cliente.getEmail(),
                            notificacaoCliente.formatAssuntoOperacaoNegada("Saque", valor.intValue()),
                            notificacaoCliente.formatCorpoOperacaoNegadaEmpresa("Saque", valor.intValue(),
                                    empresa.getNome(), LocalDateTime.now(), cliente.getSaldo()));

                    log.error("Cliente " + cliente.getNome() + " tentou realizar saque na empresa " + empresa.getNome() +
                            ", mas a transação iria negativar o saldo da empresa. Transação negada.");
                    throw new SaldoInsuficienteException(saldoInsuficienteDTO);
                }
            } else {
                log.error("Durante tentativa de transação de saque, foi informado um cliente como " +
                        "parâmetro que não existe. Operação negada.");
                throw new ClienteNaoEncontradoException("O cliente buscado para a operação não existe.");
            }
        } else {
            log.error("Durante tentativa de transação de saque, foi informado uma empresa como " +
                    "parâmetro que não existe. Operação negada.");
            throw new EmpresaNaoEncontradaException("A empresa buscada para a operação não existe.");
        }

        return ResponseEntity.ok().body("Transação de Saque realizada com sucesso");
    }

    @Override
    @Cacheable
    public List<TransacaoDTO> listarTodasTransacoes() throws TransacaoNaoEncontradaException {

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

        if (transacoesDTO.isEmpty()) {
            log.error("transacoesDTO é nulo. Não há portanto transações registrados na base de dados");
            throw new TransacaoNaoEncontradaException("Não há Transações registradas na base de dados");
        }

        return transacoesDTO;
    }

    @Override
    @Transactional
    public void deleteTransacao(Long id) throws TransacaoRemocaoException {

        Optional<Transacao> transacaoOptional = transacaoRepository.findById(id);

        if (transacaoOptional.isPresent()) {

            Transacao transacao = transacaoRepository.getReferenceById(id);

            transacaoRepository.delete(transacao);

        } else {
            log.error("Não há transação com esse id: " + id + ". Método de deletar cancelado");
            throw new TransacaoNaoEncontradaException("O registro dessa transação não existe no banco");
        }
    }

}
