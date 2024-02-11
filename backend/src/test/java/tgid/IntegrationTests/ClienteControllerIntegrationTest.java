package tgid.IntegrationTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegistrarClienteComSucessoComCaracteres() throws Exception {
        String clienteJson = "{\"cpf\":\"624.404.711-78\",\"nome\":\"Test\",\"email\":\"test@test.com\",\"saldo\":100.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/registrar-cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cadastro realizado com sucesso!"));
    }

    @Test
    public void testRegistrarClienteComSucessoSemCaracteres() throws Exception {
        String clienteJson = "{\"cpf\":\"35593186537\",\"nome\":\"Test\",\"email\":\"test@test.com\",\"saldo\":100.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/registrar-cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cadastro realizado com sucesso!"));
    }

    @Test
    public void testRegistrarClienteCpfInvalidoComCaracteres() throws Exception {
        String clienteJson = "{\"cpf\":\"623.404.711-78\",\"nome\":\"Test\",\"email\":\"test@test.com\",\"saldo\":100.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/registrar-cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"O cliente não pôde ser registrado: CPF Inválido\"}"));
    }

    @Test
    public void testRegistrarClienteCpfInvalidoSemCaracteres() throws Exception {
        String clienteJson = "{\"cpf\":\"62340471178\",\"nome\":\"Test\",\"email\":\"test@test.com\",\"saldo\":100.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/registrar-cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"O cliente não pôde ser registrado: CPF Inválido\"}"));
    }

    @Test
    public void testRegistrarClienteConstraintCpfUniqueComCaracteres() throws Exception {
        String clienteJson = "{\"cpf\":\"411.012.171-03\",\"nome\":\"Test\",\"email\":\"test@test.com\",\"saldo\":100.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/registrar-cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\"," +
                        "\"mensagem\":\"O cliente não pôde ser registrado: O CPF 411.012.171-03 já foi utilizado por " +
                        "outro cliente. Tente Novamente\"}"));
    }

    @Test
    public void testRegistrarClienteConstraintCpfUniqueSemCaracteres() throws Exception {
        String clienteJson = "{\"cpf\":\"46662505226\",\"nome\":\"Test\",\"email\":\"test@test.com\",\"saldo\":100.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/registrar-cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"O cliente não pôde ser registrado: O CPF 466.625.052-26 já foi utilizado por outro cliente. " +
                        "Tente Novamente\"}"));
    }

    @Test
    public void testRegistrarClienteComCpfNulo() throws Exception {
        String clienteJson = "{\"cpf\":\"\",\"nome\":\"Test\",\"email\":\"test@test.com\",\"saldo\":100.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/registrar-cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"O cliente não pôde ser registrado: CPF Inválido\"}"));
    }

    @Test
    public void testRegistrarClienteComNomeNulo() throws Exception {
        String clienteJson = "{\"cpf\":\"686.618.265-43\",\"nome\":\"\",\"email\":\"test@test.com\",\"saldo\":100.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/registrar-cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(
                        "{\"status\":\"BAD_REQUEST\",\"mensagem\":\"O cliente não pôde ser " +
                                "registrado: Os parâmetros de entrada não podem ser nulos\"}"));
    }

    @Test
    public void testRegistrarClienteComEmailNulo() throws Exception {
        String clienteJson = "{\"cpf\":\"824.443.078-87\",\"nome\":\"Test\",\"email\":\"\",\"saldo\":100.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/registrar-cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(
                        "{\"status\":\"BAD_REQUEST\",\"mensagem\":\"O cliente não pôde ser " +
                                "registrado: Os parâmetros de entrada não podem ser nulos\"}"));
    }

    @Test
    public void testRegistrarClienteComSaldoNulo() throws Exception {
        String clienteJson = "{\"cpf\":\"312.665.837-77\",\"nome\":\"Test\",\"email\":\"test@test.com\",\"saldo\":null}";

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/registrar-cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(
                        "{\"status\":\"BAD_REQUEST\",\"mensagem\":\"O cliente não pôde ser registrado: " +
                                "Cannot invoke \\\"java.lang.Double.doubleValue()\\\" because \\\"saldo\\\" is null\"}"));
    }

    @Test
    public void testRegistrarClienteComSaldoNegativo() throws Exception {
        String clienteJson = "{\"cpf\":\"781.385.118-85\",\"nome\":\"Test\",\"email\":\"test@test.com\",\"saldo\":-100.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/registrar-cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"O cliente não pôde ser registrado: O valor do saldo não pode ser negativo. " +
                        "Tente Novamente\"}"));
    }

    @Test
    public void testListarTodosClientesComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cliente/listar-clientes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testDeletarClienteComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cliente/delete-cliente/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cliente deletado com sucesso!"));
    }

    @Test
    public void testDeletarClienteInexistente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cliente/delete-cliente/{id}", 99L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"NOT_FOUND\",\"mensagem\":" +
                        "\"O cliente não pôde ser deletado: Não há cliente com esse id: 99\"}"));
    }

}