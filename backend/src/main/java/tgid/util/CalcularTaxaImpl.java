package tgid.util;

import org.springframework.stereotype.Service;

@Service
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
