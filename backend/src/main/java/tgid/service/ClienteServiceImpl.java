package tgid.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tgid.entity.Cliente;
import tgid.exception.ClienteNaoEncontradoException;
import tgid.exception.ClienteRegistroException;
import tgid.exception.ClienteRemocaoException;
import tgid.repository.ClienteRepository;
import tgid.repository.TransacaoRepository;
import java.util.List;

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
    public List<Cliente> listarTodosClientes() throws ClienteNaoEncontradoException {
        return clienteRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteCliente(Long id) throws ClienteRemocaoException {
        Cliente cliente = clienteRepository.getReferenceById(id);

        if (cliente == null) {
            throw new ClienteRemocaoException("Não há cliente com esse id: " + id);
        }

        transacaoRepository.deleteApartirDoCliente(cliente);
        clienteRepository.delete(cliente);
    }
}
