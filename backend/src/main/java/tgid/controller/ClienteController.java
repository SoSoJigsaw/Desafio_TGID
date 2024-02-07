package tgid.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tgid.entity.Cliente;
import tgid.exception.ClienteNaoEncontradoException;
import tgid.exception.ClienteRegistroException;
import tgid.exception.ClienteRemocaoException;
import tgid.exception.CpfInvalidoException;
import tgid.service.ClienteService;
import tgid.validation.CPFValidator;

import java.util.List;

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
    public ResponseEntity<?> registrarCliente(@RequestBody Cliente cliente) {

        try {
            // Validação do CPF
            if (!cpfValidator.isValid(cliente.getCpf(), null)) {
                System.err.println("CPF Inválido. Tente novamente");
                return ResponseEntity.badRequest().body("CPF Inválido");
            }

            clienteService.registrarCliente(cliente.getCpf(), cliente.getNome(), cliente.getEmail(), cliente.getSaldo());

        } catch (ClienteRegistroException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro não realizado");
        }

        return ResponseEntity.ok().body("Cadastro realizado com sucesso!");
    }
    
    @GetMapping("listar-clientes")
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> listarTodosClientes() {

        try {

            return clienteService.listarTodosClientes();

        } catch (ClienteNaoEncontradoException e) {
            throw new ClienteNaoEncontradoException("Não há nenhum registro de clientes disponível");
        }

    }
    
    @DeleteMapping("delete-cliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCliente(@PathVariable("id") Long id) {

        try {
            clienteService.deleteCliente(id);
        } catch (ClienteRemocaoException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível deletar o cliente");
        }

        return ResponseEntity.ok("Cliente deletado com sucesso!");
    }

    @ControllerAdvice
    public static class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(CpfInvalidoException.class)
        public ResponseEntity<Object> handleCpfInvalidoException(CpfInvalidoException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
