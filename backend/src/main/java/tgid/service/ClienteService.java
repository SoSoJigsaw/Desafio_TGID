package tgid.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tgid.controller.ClienteController;
import tgid.dto.TransacaoDTO;
import tgid.entity.Cliente;
import tgid.entity.Transacao;
import tgid.repository.ClienteRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public void registrarCliente(String cpf, String nome, String email, Double saldo) {

        Cliente cliente = new Cliente();

        cliente.setCpf(cpf);
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setSaldo(saldo);

        clienteRepository.save(cliente);

    }

    public List<Cliente> listarTodosClientes() {

        List<Cliente> clientes = clienteRepository.findAll();

        return clientes;

    }

    @Transactional
    public void deleteCliente(Long id) {

        Cliente cliente = clienteRepository.getReferenceById(id);

        clienteRepository.delete(cliente);

    }

}
