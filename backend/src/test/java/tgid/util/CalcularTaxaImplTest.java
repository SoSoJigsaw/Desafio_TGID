package tgid.util;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@Import(CalcularTaxaImpl.class)
public class CalcularTaxaImplTest {

    // Deve retornar o valor correto para calcularTaxaDeposito com valores de entrada válidos
    @Test
    public void test_calcularTaxaDeposito_validInput() {
        CalcularTaxaImpl calcularTaxaImpl = new CalcularTaxaImpl();
        double valor = 100.0;
        double taxa = 0.1;
        double expected = 10.0;

        double result = calcularTaxaImpl.calcularTaxaDeposito(valor, taxa);

        assertEquals(expected, result, 0.0001);
    }

    // Deve retornar o valor correto para calcularTaxaSaque com valores de entrada válidos
    @Test
    public void test_calcularTaxaSaque_validInput() {
        CalcularTaxaImpl calcularTaxaImpl = new CalcularTaxaImpl();
        double valor = 100.0;
        double taxa = 0.1;
        double expected = 10.0;

        double result = calcularTaxaImpl.calcularTaxaSaque(valor, taxa);

        assertEquals(expected, result, 0.0001);
    }

    // Deve retornar 0 quando o valor for 0 para ambos calcularTaxaDeposito e calcularTaxaSaque
    @Test
    public void test_calcularTaxa_zeroValor() {
        CalcularTaxaImpl calcularTaxaImpl = new CalcularTaxaImpl();
        double valor = 0.0;
        double taxa = 0.1;
        double expected = 0.0;

        double resultDeposito = calcularTaxaImpl.calcularTaxaDeposito(valor, taxa);
        double resultSaque = calcularTaxaImpl.calcularTaxaSaque(valor, taxa);

        assertEquals(expected, resultDeposito, 0.0001);
        assertEquals(expected, resultSaque, 0.0001);
    }

    // Deve retornar o valor correto para calcularTaxaDeposito quando o valor for o valor máximo do tipo double
    @Test
    public void test_calcularTaxaDeposito_maxDoubleValor() {
        CalcularTaxaImpl calcularTaxaImpl = new CalcularTaxaImpl();
        double valor = Double.MAX_VALUE;
        double taxa = 0.1;
        double expected = valor * taxa;

        double result = calcularTaxaImpl.calcularTaxaDeposito(valor, taxa);

        assertEquals(expected, result, 0.0001);
    }

}
