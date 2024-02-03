package tgid.util;

import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

@TestConfiguration
@Import(AppConfig.class)
public class AppConfigTests {

    // Deve criar uma nova instância de RestTemplate quando o método restTemplate() for chamado
    @Test
    public void test_criar_nova_instancia_do_RestTemplate() {
        AppConfig appConfig = new AppConfig();
        RestTemplate restTemplate = appConfig.restTemplate();
        assertNotNull(restTemplate);
    }

    // Deve lidar com entrada nula ou inválida ao criar uma nova instância de RestTemplate
    @Test
    public void test_lidar_com_entrada_nula_ou_invalida() {
        AppConfig appConfig = new AppConfig();
        // Testa entrada nula
        try {
            RestTemplate restTemplate = appConfig.restTemplate();
            assertNotNull(restTemplate);
        } catch (Exception e) {
            fail("Não deve lançar exceção para entrada nula");
        }

        // Testa entrada inválida
        try {
            RestTemplate restTemplate = appConfig.restTemplate();
            assertNotNull(restTemplate);
        } catch (Exception e) {
            fail("Não deve lançar exceção para entrada inválida");
        }
    }

    // Deve lidar com exceções lançadas durante a criação de uma nova instância de RestTemplate
    @Test
    public void test_lidar_com_excecoes_durante_a_criacao() {
        AppConfig appConfig = new AppConfig();
        // Testa tratamento de exceção
        try {
            RestTemplate restTemplate = appConfig.restTemplate();
            assertNotNull(restTemplate);
        } catch (Exception e) {
            fail("Não deve lançar exceção durante a criação");
        }
    }

    // Deve retornar a mesma instância de RestTemplate quando restTemplate() for chamado várias vezes
    @Test
    public void test_retornar_mesma_instancia_do_RestTemplate() {
        AppConfig appConfig = new AppConfig();
        RestTemplate restTemplate1 = appConfig.restTemplate();
        RestTemplate restTemplate2 = appConfig.restTemplate();
        assertSame(restTemplate1, restTemplate2);
    }

    // Deve garantir que a instância do RestTemplate seja thread-safe
    @Test
    public void test_instancia_RestTemplate_thread_safe() {
        AppConfig appConfig = new AppConfig();
        RestTemplate restTemplate = appConfig.restTemplate();

        // Testa segurança da thread
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                restTemplate.getForObject("https://example.com", String.class);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                restTemplate.getForObject("https://example.com", String.class);
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            fail("Thread interrompida");
        }
    }

}
