package tgid.entity.testEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import org.junit.Test;
import tgid.entity.Cliente;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class TestCliente {

    // Creating a new instance of Cliente with valid parameters should set the attributes correctly.
    @Test
    public void test_createClienteWithValidParameters() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setCpf("123456789");
        cliente.setNome("John Doe");
        cliente.setSaldo(100.0);

        assertEquals(Optional.of(1L), cliente.getId());
        assertEquals("123456789", cliente.getCpf());
        assertEquals("John Doe", cliente.getNome());
        assertEquals(100.0, cliente.getSaldo(), 0.01);
    }

    // Modifying the attributes of an existing Cliente instance should update them correctly.
    @Test
    public void test_modifyClienteAttributes() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setCpf("123456789");
        cliente.setNome("John Doe");
        cliente.setSaldo(100.0);

        cliente.setCpf("987654321");
        cliente.setNome("Jane Smith");
        cliente.setSaldo(200.0);

        assertEquals(Optional.of(1L), cliente.getId());
        assertEquals("987654321", cliente.getCpf());
        assertEquals("Jane Smith", cliente.getNome());
        assertEquals(200.0, cliente.getSaldo(), 0.01);
    }

    // Saving a new instance of Cliente to the database should persist it correctly.
    @Test
    public void test_saveClienteToDatabase() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setCpf("123456789");
        cliente.setNome("John Doe");
        cliente.setSaldo(100.0);

        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();

        Cliente savedCliente = em.find(Cliente.class, 1L);

        assertEquals(Optional.of(1L), savedCliente.getId());
        assertEquals("123456789", savedCliente.getCpf());
        assertEquals("John Doe", savedCliente.getNome());
        assertEquals(100.0, savedCliente.getSaldo(), 0.01);

        em.close();
        emf.close();
    }

    // Creating a new instance of Cliente with a null cpf attribute should raise an exception.
    @Test(expected = IllegalArgumentException.class)
    public void test_createClienteWithNullCpf() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setCpf(null);
        cliente.setNome("John Doe");
        cliente.setSaldo(100.0);
    }

    // Creating a new instance of Cliente with a cpf attribute that already exists in the database should raise an exception.
    @Test(expected = PersistenceException.class)
    public void test_createClienteWithExistingCpf() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setCpf("123456789");
        cliente1.setNome("John Doe");
        cliente1.setSaldo(100.0);

        em.getTransaction().begin();
        em.persist(cliente1);
        em.getTransaction().commit();

        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setCpf("123456789");
        cliente2.setNome("Jane Smith");
        cliente2.setSaldo(200.0);

        em.getTransaction().begin();
        em.persist(cliente2);
        em.getTransaction().commit();

        em.close();
        emf.close();
    }

    // Creating a new instance of Cliente with a negative saldo attribute should raise an exception.
    @Test(expected = IllegalArgumentException.class)
    public void test_createClienteWithNegativeSaldo() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setCpf("123456789");
        cliente.setNome("John Doe");
        cliente.setSaldo(-100.0);
    }

}
