package tgid.IntegrationTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TransacaoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDepositoRealizadoComSucesso() throws Exception {
        String transacaoJson = "50.0";

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/deposito/{empresaId}/{clienteId}",
                                3L, 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Transação de Depósito realizada com sucesso"));
    }

    @Test
    public void testDepositoRealizadoComValorNegativo() throws Exception {
        String transacaoJson = "-50.0";

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/deposito/{empresaId}/{clienteId}",
                                3L, 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"Erro no processamento da transação de depósito: O valor do depósito não pode ser " +
                        "negativo\"}"));
    }

    @Test
    public void testDepositoRealizadoComValorZerado() throws Exception {
        String transacaoJson = "0.0";

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/deposito/{empresaId}/{clienteId}",
                                3L, 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"Erro no processamento da transação de depósito: O valor do depósito não pode ser zero\"}"));
    }

    @Test
    public void testDepositoRealizadoComValorNulo() throws Exception {
        String transacaoJson = "null";

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/deposito/{empresaId}/{clienteId}",
                                3L, 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testDepositoEmpresaInexistente() throws Exception {
        String transacaoJson = "50.0";

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/deposito/{empresaId}/{clienteId}",
                                99L, 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"Erro no processamento da transação de depósito: A empresa buscada para a operação não existe.\"}"));
    }

    @Test
    public void testDepositoClienteInexistente() throws Exception {
        String transacaoJson = "50.0";

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/deposito/{empresaId}/{clienteId}",
                                3L, 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"Erro no processamento da transação de depósito: O cliente buscado para a operação não existe\"}"));
    }

    @Test
    public void testDepositoSaldoInsuficiente() throws Exception {
        String transacaoJson = "9999999999.0";

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/deposito/{empresaId}/{clienteId}",
                                3L, 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"Erro no processamento da transação de depósito: {\\\"entidade\\\":\\\"Cliente\\\",\\\"" +
                        "nome\\\":\\\"Pedro Santos\\\",\\\"saldo\\\":2000,\\\"operacao\\\":\\\"Depósito\\\",\\\"" +
                        "valorTransacao\\\":2147483647}\"}"));
    }

    @Test
    public void testSaqueRealizadoComSucesso() throws Exception {
        String transacaoJson = "50.0";

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/saque/{empresaId}/{clienteId}",
                                3L, 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Transação de Saque realizada com sucesso"));
    }

    @Test
    public void testSaqueRealizadoComValorNegativo() throws Exception {
        String transacaoJson = "-50.0";

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/saque/{empresaId}/{clienteId}",
                                3L, 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"Erro no processamento da transação de saque: O valor do saque não pode ser negativo\"}"));
    }

    @Test
    public void testSaqueRealizadoComValorZerado() throws Exception {
        String transacaoJson = "0.0";

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/saque/{empresaId}/{clienteId}",
                                3L, 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"Erro no processamento da transação de saque: O valor do saque não pode ser zero\"}"));
    }

    @Test
    public void testSaqueRealizadoComValorNulo() throws Exception {
        String transacaoJson = "null";

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/saque/{empresaId}/{clienteId}",
                                3L, 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void testSaqueEmpresaInexistente() throws Exception {
        String transacaoJson = "50.0";

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/saque/{empresaId}/{clienteId}",
                                99L, 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"Erro no processamento da transação de saque: A empresa buscada para a operação não existe.\"}"));
    }

    @Test
    public void testSaqueClienteInexistente() throws Exception {
        String transacaoJson = "50.0";

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/saque/{empresaId}/{clienteId}",
                                3L, 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"Erro no processamento da transação de saque: O cliente buscado para a operação não existe.\"}"));
    }

    @Test
    public void testSaqueSaldoInsuficiente() throws Exception {
        String transacaoJson = "9999999999.0";

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/saque/{empresaId}/{clienteId}",
                                3L, 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"BAD_REQUEST\",\"mensagem\":" +
                        "\"Erro no processamento da transação de saque: {\\\"entidade\\\":\\\"Empresa\\\",\\\"nome\\\"" +
                        ":\\\"InovaTech Solutions\\\",\\\"saldo\\\":100000,\\\"operacao\\\":\\\"Saque\\\",\\\"" +
                        "valorTransacao\\\":2147483647}\"}"));
    }

    @Test
    public void testListarTodasTransacoesComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/transacoes/listar-transacoes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testDeletarTransacaoComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/transacoes/delete-transacao/{id}", 7L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("TransaÃ§Ã£o deletada com sucesso!"));
    }

    @Test
    public void testDeletarTransacaoInexistente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/transacoes/delete-transacao/{id}", 99L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"NOT_FOUND\",\"mensagem\":" +
                        "\"O registro de transação não pôde ser deletado: O registro dessa transação não existe no banco\"}"));
    }
    
}
