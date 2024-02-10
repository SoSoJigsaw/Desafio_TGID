package tgid.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CalcularTaxaImpl implements CalcularTaxa {

    @Override
    public double calcularTaxaDeposito(double valor, double taxa) {

        log.info("Calculando taxa incidente de depósito à empresa. Valor transacao: " + valor + "; " +
                "Valor da taxa de depósito incidente nessa empresa: " + taxa + "; Total de taxa a pagar: " +
                valor * taxa);

        return valor * taxa;
    }

    @Override
    public double calcularTaxaSaque(double valor, double taxa) {

        log.info("Calculando taxa incidente de saque à empresa. Valor transacao: " + valor + "; " +
                "Valor da taxa de depósito incidente nessa empresa: " + taxa + "; Total de taxa a pagar: " +
                valor * taxa);

        return valor * taxa;
    }
}
