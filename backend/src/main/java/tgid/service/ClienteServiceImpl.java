package tgid.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tgid.dto.ClienteDTO;
import tgid.entity.Cliente;
import tgid.exception.ClienteNaoEncontradoException;
import tgid.exception.ClienteRegistroException;
import tgid.exception.ClienteRemocaoException;
import tgid.exception.TransacaoRemocaoException;
import tgid.repository.ClienteRepository;
import tgid.repository.TransacaoRepository;

import java.util.ArrayList;
import java.util.List;

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

        if (cpf == null || nome == null || email == null || saldo == null) {
            throw new ClienteRegistroException("Os parâmetros do input não podem ser nulos");
        }

        Cliente cliente = new Cliente(cpf, nome, email, saldo);
        clienteRepository.save(cliente);
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

        return clientesDTO;
    }

    @Override
    @Transactional
    public void deleteCliente(Long id) throws ClienteRemocaoException {
        Cliente cliente = clienteRepository.getReferenceById(id);

        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Não há cliente com esse id: " + id);
        }

        try {
            transacaoRepository.deleteApartirDoCliente(cliente);
        } catch (Exception e) {
            throw new TransacaoRemocaoException(e);
        }

        clienteRepository.delete(cliente);
    }
}
