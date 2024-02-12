package tgid.service;

import tgid.dto.ClienteDTO;

import java.util.List;

public interface ClienteService {
    void registrarCliente(String cpf, String nome, String email, Double saldo);

    List<ClienteDTO> listarTodosClientes();

    void deleteCliente(Long id);

}
