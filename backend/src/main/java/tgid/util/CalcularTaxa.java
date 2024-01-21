package tgid.util;

import org.springframework.stereotype.Service;

@Service
public class CalcularTaxa {

    public double calcularTaxaDeposito(double valor, double taxa) {

        return valor * taxa;
    }

    public double calcularTaxaSaque(double valor, double taxa) {

        return valor * taxa;
    }

}
