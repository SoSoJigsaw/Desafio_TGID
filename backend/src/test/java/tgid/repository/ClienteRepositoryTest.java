package tgid.repository;

import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import tgid.entity.Cliente;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class ClienteRepositoryTest {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    private EntityManager entityManager;


    // Deveria atualizar o saldo do cliente com sucesso
    @Test
    public void testAtualizarSaldo() {

        Cliente cliente = new Cliente();
        cliente.setCpf("417.577.918-33");
        cliente.setNome("Felipe Sobral");
        cliente.setEmail("felipesobral_@hotmail.com");
        cliente.setSaldo(1000.0);
        clienteRepository.save(cliente);
        entityManager.flush();

        clienteRepository.atualizarSaldo(cliente.getId(), 2000.0);
        entityManager.flush();
        entityManager.clear();

        Cliente clienteAtualizado = clienteRepository.findById(cliente.getId()).orElse(null);
        Assertions.assertNotNull(clienteAtualizado);
        Assertions.assertEquals(2000.0, clienteAtualizado.getSaldo());
    }
}
