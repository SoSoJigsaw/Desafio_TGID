package tgid.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tgid.entity.Cliente;
import tgid.exception.objetosExceptions.CpfInvalidoException;
import tgid.service.ClienteService;
import tgid.validation.CPFValidator;

import java.util.List;

@RestController
@RequestMapping
public class ClienteController {

    private final ClienteService clienteService;
    private final CPFValidator cpfValidator;

    @Autowired
    public ClienteController(ClienteService clienteService, CPFValidator cpfValidator) {
        this.clienteService = clienteService;
        this.cpfValidator = cpfValidator;
    }

    @CrossOrigin
    @PostMapping("/registrar-cliente")
    public ResponseEntity<?> registrarCliente(@RequestBody @Valid Cliente cliente) {

        // Validação do CPF
        if (!cpfValidator.isValid(cliente.getCpf(), null)) {
            throw new CpfInvalidoException("CPF inválido");
        }

        clienteService.registrarCliente(cliente.getCpf(), cliente.getNome(), cliente.getEmail(), cliente.getSaldo());

        return ResponseEntity.ok("Cadastro realizado com sucesso!");
    }

    @CrossOrigin
    @GetMapping("listar-clientes")
    public List<Cliente> listarTodosClientes() {

        return clienteService.listarTodosClientes();
    }

    @CrossOrigin
    @GetMapping("delete-cliente/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable("id") Long id) {

        clienteService.deleteCliente(id);

        return ResponseEntity.ok("Cliente deletado com sucesso!");
    }

}
