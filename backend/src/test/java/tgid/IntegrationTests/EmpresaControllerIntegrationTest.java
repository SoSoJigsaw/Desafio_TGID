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
public class EmpresaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegistrarEmpresaComSucessoComCaracteres() throws Exception {
        String empresaJson = "{\"cnpj\":\"84.867.778/0001-92\",\"nome\":\"Test\",\"saldo\":5000.0," +
                "\"taxaDeposito\":0.5,\"taxaSaque\":0.5}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/registrar-empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cadastro realizado com sucesso!"));
    }

    @Test
    public void testRegistrarEmpresaComSucessoSemCaracteres() throws Exception {
        String empresaJson = "{\"cnpj\":\"75045159000121\",\"nome\":\"Test\",\"saldo\":5000.0," +
                "\"taxaDeposito\":0.5,\"taxaSaque\":0.5}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/registrar-empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cadastro realizado com sucesso!"));
    }

    @Test
    public void testRegistrarEmpresaCnpjInvalidoComCaracteres() throws Exception {
        String empresaJson = "{\"cnpj\":\"84.847.758/0501-92\",\"nome\":\"Test\",\"saldo\":5000.0," +
                "\"taxaDeposito\":0.5,\"taxaSaque\":0.5}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/registrar-empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"A empresa não pôde ser registrada: CNPJ Inválido\"}"));
    }

    @Test
    public void testRegistrarEmpresaCnpjInvalidoSemCaracteres() throws Exception {
        String empresaJson = "{\"cnpj\":\"75065158050121\",\"nome\":\"Test\",\"saldo\":5000.0," +
                "\"taxaDeposito\":0.5,\"taxaSaque\":0.5}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/registrar-empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"A empresa não pôde ser registrada: CNPJ Inválido\"}"));
    }

    @Test
    public void testRegistrarEmpresaConstraintCnpjUniqueComCaracteres() throws Exception {
        String empresaJson = "{\"cnpj\":\"04.270.558/0001-48\",\"nome\":\"Test\",\"saldo\":5000.0," +
                "\"taxaDeposito\":0.5,\"taxaSaque\":0.5}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/registrar-empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"A empresa não pôde ser registrada: O CNPJ 04.270.558/0001-48 já foi utilizado por outra " +
                        "empresa. Tente Novamente\"}"));
    }

    @Test
    public void testRegistrarEmpresaConstraintCnpjUniqueSemCaracteres() throws Exception {
        String empresaJson = "{\"cnpj\":\"68541284000167\",\"nome\":\"Test\",\"saldo\":5000.0," +
                "\"taxaDeposito\":0.5,\"taxaSaque\":0.5}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/registrar-empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"A empresa não pôde ser registrada: O CNPJ 68.541.284/0001-67 já foi utilizado por outra " +
                        "empresa. Tente Novamente\"}"));
    }

    @Test
    public void testRegistrarEmpresaComCnpjNulo() throws Exception {
        String empresaJson = "{\"cnpj\":\"\",\"nome\":\"Test\",\"saldo\":5000.0," +
                "\"taxaDeposito\":0.5,\"taxaSaque\":0.5}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/registrar-empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"A empresa não pôde ser registrada: CNPJ Inválido\"}"));
    }

    @Test
    public void testRegistrarEmpresaComNomeNulo() throws Exception {
        String empresaJson = "{\"cnpj\":\"16.130.469/0001-85\",\"nome\":\"\",\"saldo\":5000.0," +
                "\"taxaDeposito\":0.5,\"taxaSaque\":0.5}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/registrar-empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"A empresa não pôde ser registrada: Os parâmetros de entrada não podem ser nulos\"}"));
    }

    @Test
    public void testRegistrarEmpresaComSaldoNulo() throws Exception {
        String empresaJson = "{\"cnpj\":\"69.143.662/0001-17\",\"nome\":\"Test\",\"saldo\":null," +
                "\"taxaDeposito\":0.5,\"taxaSaque\":0.5}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/registrar-empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"A empresa não pôde ser registrada: Cannot invoke \\\"java.lang.Double.doubleValue()\\\" " +
                        "because \\\"saldo\\\" is null\"}"));
    }

    @Test
    public void testRegistrarEmpresaComTaxaDepositoNulo() throws Exception {
        String empresaJson = "{\"cnpj\":\"84.227.143/0001-20\",\"nome\":\"Test\",\"saldo\":5000.0," +
                "\"taxaDeposito\":null,\"taxaSaque\":0.5}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/registrar-empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"A empresa não pôde ser registrada: Cannot invoke \\\"java.lang.Double.doubleValue()\\\" " +
                        "because \\\"taxaDeposito\\\" is null\"}"));
    }

    @Test
    public void testRegistrarEmpresaComTaxaSaqueNulo() throws Exception {
        String empresaJson = "{\"cnpj\":\"76.207.485/0001-50\",\"nome\":\"Test\",\"saldo\":5000.0," +
                "\"taxaDeposito\":0.5,\"taxaSaque\":null}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/registrar-empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"A empresa não pôde ser registrada: Cannot invoke \\\"java.lang.Double.doubleValue()\\\" " +
                        "because \\\"taxaSaque\\\" is null\"}"));
    }

    @Test
    public void testRegistrarEmpresaComSaldoNegativo() throws Exception {
        String empresaJson = "{\"cnpj\":\"50.691.255/0001-16\",\"nome\":\"Test\",\"saldo\":-5000.0," +
                "\"taxaDeposito\":0.5,\"taxaSaque\":0.5}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/registrar-empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"A empresa não pôde ser registrada: O valor do saldo não pode ser negativo. " +
                        "Tente Novamente\"}"));
    }

    @Test
    public void testRegistrarEmpresaComTaxaDepositoNegativo() throws Exception {
        String empresaJson = "{\"cnpj\":\"52.378.745/0001-92\",\"nome\":\"Test\",\"saldo\":5000.0," +
                "\"taxaDeposito\":-0.5,\"taxaSaque\":0.5}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/registrar-empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"A empresa não pôde ser registrada: O valor da taxa de depósito não pode ser negativo. " +
                        "Tente Novamente\"}"));
    }

    @Test
    public void testRegistrarEmpresaComTaxaSaqueNegativo() throws Exception {
        String empresaJson = "{\"cnpj\":\"65.341.656/0001-13\",\"nome\":\"Test\",\"saldo\":5000.0," +
                "\"taxaDeposito\":0.5,\"taxaSaque\":-0.5}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/registrar-empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"A empresa não pôde ser registrada: O valor da taxa de saque não pode ser negativo. " +
                        "Tente Novamente\"}"));
    }

    @Test
    public void testAtualizarTaxaSaqueComSucesso() throws Exception {
        String empresaJson = "{\"id\":2,\"tipoTaxa\":\"SAQUE\",\"valor\":0.7}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/atualizar-taxa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Taxa associada com sucesso"));
    }

    @Test
    public void testAtualizarTaxaDepositoComSucesso() throws Exception {
        String empresaJson = "{\"id\":2,\"tipoTaxa\":\"DEPÓSITO\",\"valor\":0.7}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/atualizar-taxa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Taxa associada com sucesso"));
    }

    @Test
    public void testAtualizarTaxaEmpresaInexistente() throws Exception {
        String empresaJson = "{\"id\":99,\"tipoTaxa\":\"SAQUE\",\"valor\":0.7}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/atualizar-taxa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"A atualização da taxa falhou: Não há empresa com esse id: 99\"}"));
    }

    @Test
    public void testAtualizarTaxaComTaxaInvalida() throws Exception {
        String empresaJson = "{\"id\":1,\"tipoTaxa\":\"deposito\",\"valor\":0.8}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/atualizar-taxa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"A atualização da taxa falhou: Tipo de taxa de transação inválida. Valores esperados: " +
                        "DEPÓSITO ou SAQUE\"}"));
    }

    @Test
    public void testAtualizarTaxaNula() throws Exception {
        String empresaJson = "{\"id\":2,\"tipoTaxa\":\"SAQUE\",\"valor\":null}";

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/atualizar-taxa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"A atualização da taxa falhou: O valor da taxa não pode ser nula. Tente Novamente\"}"));
    }

    @Test
    public void testListarTodasEmpresasComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/empresa/listar-empresas")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testDeletarEmpresaComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/empresa/delete-empresa/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Empresa deletada com sucesso!"));
    }

    @Test
    public void testDeletarEmpresaInexistente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/empresa/delete-empresa/{id}", 99L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"NOT_FOUND\",\"mensagem\":" +
                        "\"A empresa não pôde ser deletada: Não há empresa com esse id: 99\"}"));
    }

}
