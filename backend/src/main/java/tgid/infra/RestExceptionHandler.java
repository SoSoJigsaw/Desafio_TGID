package tgid.infra;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tgid.dto.ErroDTO;
import tgid.dto.SaldoInsuficienteDTO;
import tgid.exception.*;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    private ResponseEntity<?> ClienteNaoEncontradoExceptionHandler(ClienteNaoEncontradoException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.NOT_FOUND, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(ClienteRegistroException.class)
    private ResponseEntity<?> ClienteRegistroExceptionHandler(ClienteRegistroException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ClienteRemocaoException.class)
    private ResponseEntity<?> ClienteRemocaoExceptionHandler(ClienteRemocaoException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.NOT_FOUND, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(CnpjInvalidoException.class)
    private ResponseEntity<?> CnpjInvalidoExceptionHandler(CnpjInvalidoException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(CpfInvalidoException.class)
    private ResponseEntity<?> CnpjInvalidoExceptionHandler(CpfInvalidoException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(EmpresaNaoEncontradaException.class)
    private ResponseEntity<?> EmpresaNaoEncontradaExceptionHandler(EmpresaNaoEncontradaException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.NOT_FOUND, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(EmpresaRegistroException.class)
    private ResponseEntity<?> EmpresaRegistroExceptionHandler(EmpresaRegistroException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(EmpresaRemocaoException.class)
    private ResponseEntity<?> EmpresaRemocaoExceptionHandler(EmpresaRemocaoException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.NOT_FOUND, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(NotificacaoClienteException.class)
    private ResponseEntity<?> NotificacaoClienteExceptionHandler(NotificacaoClienteException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(NotificacaoEmpresaException.class)
    private ResponseEntity<?> NotificacaoEmpresaExceptionHandler(NotificacaoEmpresaException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    private ResponseEntity<?> SaldoInsuficienteExceptionHandler(SaldoInsuficienteException e) {

        Gson gson = new Gson();
        SaldoInsuficienteDTO saldoInsuficiente = gson.fromJson(e.getMessage(), SaldoInsuficienteDTO.class);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(saldoInsuficiente);
    }

    @ExceptionHandler(TaxaInvalidoException.class)
    private ResponseEntity<?> TaxaInvalidoExceptionHandler(TaxaInvalidoException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(TransacaoInvalidaException.class)
    private ResponseEntity<?> TransacaoInvalidaExceptionHandler(TransacaoInvalidaException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(TransacaoNaoEncontradaException.class)
    private ResponseEntity<?> TransacaoNaoEncontradaExceptionHandler(TransacaoNaoEncontradaException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.NOT_FOUND, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(TransacaoRemocaoException.class)
    private ResponseEntity<?> TransacaoRemocaoExceptionHandler(TransacaoRemocaoException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.NOT_FOUND, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(TransacaoZeradaException.class)
    private ResponseEntity<?> TransacaoZeradaExceptionHandler(TransacaoZeradaException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(TransacaoNegativaException.class)
    private ResponseEntity<?> TransacaoNegativaExceptionHandler(TransacaoNegativaException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ViolacaoConstraintCpfException.class)
    private ResponseEntity<?> ViolacaoConstraintCpfExceptionHandler(ViolacaoConstraintCpfException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ViolacaoConstraintCnpjException.class)
    private ResponseEntity<?> ViolacaoConstraintCnpjExceptionHandler(ViolacaoConstraintCnpjException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(SaldoNegativoException.class)
    private ResponseEntity<?> SaldoNegativoExceptionHandler(SaldoNegativoException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(TaxaNegativaException.class)
    private ResponseEntity<?> TaxaNegativaExceptionHandler(TaxaNegativaException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(TaxaNulaException.class)
    private ResponseEntity<?> TaxaNulaExceptionHandler(TaxaNulaException e) {

        ErroDTO erro = new ErroDTO(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

}
