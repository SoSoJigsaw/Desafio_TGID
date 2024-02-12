package tgid.exception;

import com.google.gson.Gson;
import tgid.dto.SaldoInsuficienteDTO;

public class SaldoInsuficienteException extends RuntimeException {

    private final String saldoInsuficiente;

    public SaldoInsuficienteException(SaldoInsuficienteDTO saldoInsuficiente) {
        Gson gson = new Gson();
        this.saldoInsuficiente = gson.toJson(saldoInsuficiente);
    }

    @Override
    public String getMessage() {
        return saldoInsuficiente;
    }

}

