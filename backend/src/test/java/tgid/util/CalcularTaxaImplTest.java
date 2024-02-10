package tgid.util;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalcularTaxaImplTest {
    
    @InjectMocks
    private CalcularTaxaImpl calcularTaxa;

    // Deve retornar o valor correto para calcularTaxaDeposito com valores de entrada válidos
    @Test
    public void testRetornaValorCorretoParaCalcularTaxaDeposito() {
        double valor = 100.0;
        double taxa = 0.1;
        double expected = 10.0;

        double result = calcularTaxa.calcularTaxaDeposito(valor, taxa);

        assertEquals(expected, result, 0.0001);
    }

    // Deve retornar o valor correto para calcularTaxaSaque com valores de entrada válidos
    @Test
    public void testRetornaValorCorretoParaCalcularTaxaSaque() {
        double valor = 100.0;
        double taxa = 0.1;
        double expected = 10.0;

        double result = calcularTaxa.calcularTaxaSaque(valor, taxa);

        assertEquals(expected, result, 0.0001);
    }

    // Deve retornar 0 quando o valor for 0 para ambos calcularTaxaDeposito e calcularTaxaSaque
    @Test
    public void testCalcularTaxaZeroValor() {
        double valor = 0.0;
        double taxa = 0.1;
        double expected = 0.0;

        double resultDeposito = calcularTaxa.calcularTaxaDeposito(valor, taxa);
        double resultSaque = calcularTaxa.calcularTaxaSaque(valor, taxa);

        assertEquals(expected, resultDeposito, 0.0001);
        assertEquals(expected, resultSaque, 0.0001);
    }

    // Deve retornar o valor correto para calcularTaxaDeposito quando o valor for o valor máximo do tipo double
    @Test
    public void testCalcularTaxaDepositoMaxDoubleValor() {
        double valor = Double.MAX_VALUE;
        double taxa = 0.1;
        double expected = valor * taxa;

        double result = calcularTaxa.calcularTaxaDeposito(valor, taxa);

        assertEquals(expected, result, 0.0001);
    }

}
