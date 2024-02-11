package tgid.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import tgid.dto.ClienteDTO;
import tgid.entity.Cliente;
import tgid.exception.*;
import tgid.repository.ClienteRepository;
import tgid.repository.TransacaoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final TransacaoRepository transacaoRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository, TransacaoRepository transacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    public void registrarCliente(String cpf, String nome, String email, Double saldo)
            throws ClienteRegistroException {

        if (saldo < 0) {
            log.error("O saldo fornecido no registro do cliente é um valor negativo. Procedimento negado");
            throw new SaldoNegativoException("O valor do saldo não pode ser negativo. Tente Novamente");
        }

        // Remove todos os caracteres não numéricos do CPF para padronizar em seguida o formato
        cpf = cpf.replaceAll("[^0-9]", "");

        // Formata o CPF com os caracteres especiais
        cpf = cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");

        Cliente cliente = new Cliente(cpf, nome, email, saldo);

        try {
            clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            log.error("Violação da constraint cpf_unique_key. Registro de cliente negado");
            throw new ViolacaoConstraintCpfException("O CPF " + cpf + " já foi utilizado por outro cliente. " +
                    "Tente Novamente");
        } catch (ConstraintViolationException e) {
            log.error("Os parâmetros de entrada não podem ser nulos");
            throw new ClienteRegistroException("Os parâmetros de entrada não podem ser nulos");
        }
    }

    @Override
    @Cacheable
    public List<ClienteDTO> listarTodosClientes() throws ClienteNaoEncontradoException {

        List<ClienteDTO> clientesDTO = new ArrayList<>();

        List<Cliente> clientes = clienteRepository.findAll();

        for (Cliente cliente : clientes) {

            ClienteDTO dto = new ClienteDTO();

            dto.setId(cliente.getId());
            dto.setCpf(cliente.getCpf());
            dto.setNome(cliente.getNome());
            dto.setEmail(cliente.getEmail());
            dto.setSaldo(cliente.getSaldo());

            clientesDTO.add(dto);
        }

        if (clientesDTO.isEmpty()) {
            log.error("clientesDTO é nulo. Não há portanto clientes registrados na base de dados");
            throw new ClienteNaoEncontradoException("Não há Clientes registrados na base de dados");
        }

        return clientesDTO;
    }

    @Override
    @Transactional
    public void deleteCliente(Long id) throws ClienteRemocaoException {

        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isPresent()) {

            Cliente cliente = clienteRepository.getReferenceById(id);

            try {
                transacaoRepository.deleteApartirDoCliente(cliente);
            } catch (Exception e) {
                log.error("Erro ao deletar as transações relacionadas ao cliente " + cliente.getNome() +
                        " durante tentativa de deletar esse mesmo cliente do banco");
                throw new TransacaoRemocaoException("Não foi possível deletar as transações relacionadas a esse cliente");
            }

            clienteRepository.delete(cliente);

        } else {
            log.error("Não há cliente com esse id: " + id + ". Método de deletar cancelado");
            throw new ClienteNaoEncontradoException("Não há cliente com esse id: " + id);
        }
    }

}
