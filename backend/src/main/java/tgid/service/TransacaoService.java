package tgid.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tgid.dto.TransacaoDTO;
import tgid.entity.Cliente;
import tgid.entity.Empresa;
import tgid.entity.Transacao;
import tgid.exception.objetosExceptions.EmpresaNaoEncontradaException;
import tgid.exception.objetosExceptions.SaldoInsuficienteException;
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
public class TransacaoService {

    @Autowired
    TransacaoRepository transacaoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    CalcularTaxa calcularTaxa;

    @Autowired
    NotificacaoEmpresa notificacaoEmpresa;

    @Autowired
    NotificacaoCliente notificacaoCliente;

    public void realizarDeposito(Long empresaId, Long clienteId, double valor) {

        Empresa empresa = empresaRepository.getReferenceById(empresaId);
        Cliente cliente = clienteRepository.getReferenceById(clienteId);

        if (empresa != null) {

            // Lógica para calcular a taxa correspondente ao depósito
            double taxa = calcularTaxa.calcularTaxaDeposito(valor, empresa.getTaxaDeposito());

            // Atualiza o saldo da empresa considerando a taxa
            double saldoAtualizado = empresa.getSaldo() + (valor - taxa);
            empresa.setSaldo(saldoAtualizado);
            empresaRepository.atualizarSaldo(empresa.getId(), empresa.getSaldo());

            // Registra a transação
            Transacao transacao = new Transacao();
            transacao.setValor(valor);
            transacao.setTipo("DEPÓSITO");
            transacao.setDataTransacao(LocalDateTime.now());
            transacao.setCliente(cliente);
            transacao.setEmpresa(empresa);
            transacaoRepository.save(transacao);

            String dataTransacao = transacao.getDataTransacao().format(DateTimeFormatter
                                                                        .ofPattern("dd/MM/yy HH:mm:ss"));

            // Realizar callback à empresa
            notificacaoEmpresa.enviarCallbackParaEmpresa("http://localhost:8080/callback",
                    "Transação recebida: Depósito de " + transacao.getValor() + " reais."
            + "\n Feita por: " + transacao.getCliente().getNome() + ". \n" + dataTransacao + "\n " +
                            "Saldo atual da empresa " + transacao.getEmpresa().getNome() + ": " +
                            transacao.getEmpresa().getSaldo() + " reais.");

            // Realizar notificação ao cliente por email
            notificacaoCliente.enviarNotificacaoPorEmail(transacao.getCliente().getEmail(),
                    "Depósito de " + transacao.getValor() + " reais concluída",
                    "Seu depósito no valor de " + transacao.getValor() + " reais na empresa " +
                    transacao.getEmpresa().getNome() + " foi concluída com sucesso. \n Data/Hora da operação: "
                    + dataTransacao);

        } else {

            throw new EmpresaNaoEncontradaException("ERRO: a empresa buscada para a operação não existe.");

        }

    }

    public void realizarSaque(Long empresaId, Long clienteId, double valor) {

        Empresa empresa = empresaRepository.getReferenceById(empresaId);
        Cliente cliente = clienteRepository.getReferenceById(clienteId);

        if (empresa != null) {

            // Lógica para calcular a taxa correspondente ao saque
            double taxa = calcularTaxa.calcularTaxaSaque(valor, empresa.getTaxaSaque());

            if (empresa.getSaldo() >= (valor + taxa)) {

                // Atualiza o saldo da empresa considerando a taxa
                double saldoAtualizado = empresa.getSaldo() - (valor + taxa);
                empresa.setSaldo(saldoAtualizado);
                empresaRepository.atualizarSaldo(empresa.getId(), empresa.getSaldo());

                // Registra a transação
                Transacao transacao = new Transacao();
                transacao.setValor(valor);
                transacao.setTipo("SAQUE");
                transacao.setDataTransacao(LocalDateTime.now());
                transacao.setCliente(cliente);
                transacao.setEmpresa(empresa);
                transacaoRepository.save(transacao);

                String dataTransacao = transacao.getDataTransacao().format(DateTimeFormatter
                        .ofPattern("dd/MM/yy HH:mm:ss"));

                // Realizar callback à empresa
                notificacaoEmpresa.enviarCallbackParaEmpresa("http://localhost:8080/callback",
                        "Transação recebida: Saque de " + transacao.getValor() + " reais."
                                + "\n Feita por: " + transacao.getCliente().getNome() + ". \n" + dataTransacao + "\n " +
                                "Saldo atual da empresa " + transacao.getEmpresa().getNome() + ": " +
                                transacao.getEmpresa().getSaldo() + " reais.");

                // Realizar notificação ao cliente por email
                notificacaoCliente.enviarNotificacaoPorEmail(transacao.getCliente().getEmail(),
                        "Saque de " + transacao.getValor() + " reais concluída",
                        "Seu saque no valor de " + transacao.getValor() + " reais na empresa " +
                                transacao.getEmpresa().getNome() + " foi concluída com sucesso. \n Data/Hora da operação: "
                                + dataTransacao);

            } else {

                throw new SaldoInsuficienteException("Não foi possível concluir a operação: " +
                                                     "saldo insuficiente para a transição.");

            }

        } else {

            throw new EmpresaNaoEncontradaException("ERRO: a empresa buscada para a operação não existe.");

        }

    }

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

    @Transactional
    public void deleteTransacao(Long id) {

        Transacao transacao = transacaoRepository.getReferenceById(id);

        transacaoRepository.delete(transacao);

    }

}
