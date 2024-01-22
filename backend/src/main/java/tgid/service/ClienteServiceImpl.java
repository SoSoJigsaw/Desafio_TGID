package tgid.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tgid.entity.Cliente;
import tgid.repository.ClienteRepository;
import tgid.repository.TransacaoRepository;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final TransacaoRepository transacaoRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository, TransacaoRepository transacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    public void registrarCliente(String cpf, String nome, String email, Double saldo) {
        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setSaldo(saldo);
        clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> listarTodosClientes() {
        return clienteRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteCliente(Long id) {
        Cliente cliente = clienteRepository.getReferenceById(id);

        if (cliente != null) {
            transacaoRepository.deleteApartirDoCliente(cliente);
            clienteRepository.delete(cliente);
        }
    }
}
