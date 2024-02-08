package tgid.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CalcularTaxaImpl implements CalcularTaxa {

    @Override
    public double calcularTaxaDeposito(double valor, double taxa) {
        return valor * taxa;
    }

    @Override
    public double calcularTaxaSaque(double valor, double taxa) {
        return valor * taxa;
    }
}
