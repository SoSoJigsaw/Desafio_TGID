package tgid.service;

import java.util.List;

import tgid.dto.ClienteDTO;
import tgid.entity.Cliente;

public interface ClienteService {
    void registrarCliente(String cpf, String nome, String email, Double saldo);

    List<ClienteDTO> listarTodosClientes();

    void deleteCliente(Long id);

}
