package tgid.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tgid.dto.ClienteDTO;
import tgid.entity.Cliente;
import tgid.exception.*;
import tgid.service.ClienteService;
import tgid.validation.CPFValidator;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;
    private final CPFValidator cpfValidator;

    public ClienteController(ClienteService clienteService, CPFValidator cpfValidator) {
        this.clienteService = clienteService;
        this.cpfValidator = cpfValidator;
    }

    @PostMapping("/registrar-cliente")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> registrarCliente(@RequestBody @Valid ClienteDTO cliente) {

        try {
            // Validação do CPF
            if (!cpfValidator.isValid(cliente.getCpf(), null)) {
                log.error("CPF Inválido. Tente novamente");
                throw new CpfInvalidoException();
            }

            clienteService.registrarCliente(cliente.getCpf(), cliente.getNome(), cliente.getEmail(), cliente.getSaldo());

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ClienteRegistroException(e);
        }

        return ResponseEntity.ok().body("Cadastro realizado com sucesso!");
    }
    
    @GetMapping("listar-clientes")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable
    public List<ClienteDTO> listarTodosClientes() {

        try {

            return clienteService.listarTodosClientes();

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ClienteNaoEncontradoException(e);
        }

    }
    
    @DeleteMapping("delete-cliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCliente(@PathVariable("id") Long id) {

        try {
            clienteService.deleteCliente(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ClienteRemocaoException(e);
        }

        return ResponseEntity.ok("Cliente deletado com sucesso!");
    }

}
