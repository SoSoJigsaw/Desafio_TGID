package tgid.exception;

import org.springframework.beans.factory.annotation.Autowired;
import tgid.dto.SaldoInsuficienteDTO;
import com.google.gson.Gson;

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

