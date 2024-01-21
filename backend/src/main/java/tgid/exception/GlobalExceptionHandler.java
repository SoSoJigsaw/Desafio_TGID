package tgid.exception;

import jakarta.persistence.Entity;
import jakarta.validation.ConstraintViolationException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import tgid.entity.Cliente;
import tgid.entity.Empresa;
import tgid.entity.Transacao;
import tgid.exception.objetosExceptions.*;
import tgid.repository.ClienteRepository;
import tgid.repository.EmpresaRepository;
import tgid.repository.TransacaoRepository;

import java.io.IOException;
import java.sql.SQLException;

import java.util.List;
import java.util.stream.Collectors;


// Tratamento global de exceções em todos os controllers, services, repositories e entities
@ControllerAdvice(annotations = {Controller.class, RestController.class, Service.class, Repository.class, Entity.class})
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    TransacaoRepository transacaoRepository;

    @Autowired
    EmpresaRepository empresaRepository;


    @ExceptionHandler(CpfInvalidoException.class)
    public ResponseEntity<String> handleCpfInvalidoException(CpfInvalidoException ex) {

        logger.error("Erro: {}", ex.getMessage(), ex);

        return ResponseEntity.status(400).body("CPF inválido");
    }

    @ExceptionHandler(CnpjInvalidoException.class)
    public ResponseEntity<String> handleCnpjInvalidoException(CnpjInvalidoException ex) {

        logger.error("Erro: {}", ex.getMessage(), ex);

        return ResponseEntity.status(400).body("CNPJ inválido");
    }

    @ExceptionHandler(TaxaInvalidoException.class)
    public ResponseEntity<String> handleTaxajInvalidoException(TaxaInvalidoException ex) {

        logger.error("Erro: {}", ex.getMessage(), ex);

        return ResponseEntity.status(400).body("Tipo de Taxa Inválida");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {

        String mensagem = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));

        logger.error("Erro de validação: {}", mensagem, ex);

        return ResponseEntity.status(400).body("Erro de validação: " + mensagem);
    }

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<String> handleClienteNaoEncontradoException(ClienteNaoEncontradoException ex) {

        logger.error("Erro: Cliente não encontrado - {}. Preparando a verificação dos objetos Cliente...", ex.getMessage(), ex);

        List<Cliente> clientes = clienteRepository.findAll();

        if (clientes.isEmpty()) {

            logger.error("VERIFICAÇÃO CONCLUÍDA: base de dados de Clientes está vazia.");

        } else {

            logger.error("VERIFICAÇÃO CONCLUÍDA: não há problemas com a base de dados de Clientes. ({} registros)", clientes.size());

        }

        return ResponseEntity.status(404).body("Cliente não encontrado");
    }

    @ExceptionHandler(TransacaoNaoEncontradaException.class)
    public ResponseEntity<String> handleTransacaoNaoEncontradaException(TransacaoNaoEncontradaException ex) {

        logger.error("Erro: Cliente não encontrado - {}. Preparando a verificação dos objetos Transação...", ex.getMessage(), ex);

        List<Transacao> transacoes = transacaoRepository.findAll();

        if (transacoes.isEmpty()) {

            logger.error("VERIFICAÇÃO CONCLUÍDA: base de dados de Transação está vazia.");

        } else {

            logger.error("VERIFICAÇÃO CONCLUÍDA: não há problemas com a base de dados de Transações. ({} registros)", transacoes.size());

        }

        return ResponseEntity.status(404).body("Transação não encontrada");
    }

    @ExceptionHandler(TransacaoInvalidaException.class)
    public ResponseEntity<String> handleTransacaoInvalidaException(TransacaoInvalidaException ex) {

        logger.error("Erro: Transação inválida - {}", ex.getMessage(), ex);

        return ResponseEntity.status(400).body("Transação inválida");
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<String> handleSaldoInsuficienteException(SaldoInsuficienteException ex) {

        logger.error("Erro: Saldo insuficiente - {}", ex.getMessage(), ex);

        return ResponseEntity.status(400).body("Saldo insuficiente");
    }

    @ExceptionHandler(EmpresaNaoEncontradaException.class)
    public ResponseEntity<String> handleEmpresaNaoEncontradaException(EmpresaNaoEncontradaException ex) {

        logger.error("Erro: Empresa não encontrada - {}. Preparando verificação dos objetos Empresa...", ex.getMessage(), ex);

        List<Empresa> empresas = empresaRepository.findAll();

        if (empresas.isEmpty()) {

            logger.error("VERIFICAÇÃO CONCLUÍDA: base de dados de Empresas está vazia.");

        } else {

            logger.error("VERIFICAÇÃO CONCLUÍDA: não há problemas com a base de dados de Empresas. ({} registros)", empresas.size());

        }

        return ResponseEntity.status(404).body("Empresa não encontrada");
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException ex) {

        logger.error("Erro na requisição HTTP: {}", ex.getMessage(), ex);

        return ResponseEntity.status(ex.getRawStatusCode()).body("Erro na requisição HTTP");
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<String> handleResourceAccessException(ResourceAccessException ex) {

        logger.error("Erro de acesso a recurso externo: {}", ex.getMessage(), ex);

        return ResponseEntity.status(400).body("Erro de acesso a recurso externo");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        logger.error("Erro de tipo de argumento no método: {}", ex.getMessage(), ex);

        return ResponseEntity.status(400).body("Erro de tipo de argumento no método");
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSqlException(SQLException ex) {

        logger.error("Erro SQL: {}", ex.getMessage(), ex);

        return ResponseEntity.status(400).body("Erro SQL");
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {

        logger.error("Erro de E/S (Input/Output): {}", ex.getMessage(), ex);

        return ResponseEntity.status(400).body("Erro de E/S (Input/Output)");
    }

    @ExceptionHandler(ClienteRegistroException.class)
    public ResponseEntity<String> handleClienteRegistroException(ClienteRegistroException ex) {
        logger.error("Erro no registro do cliente: {}", ex.getMessage(), ex);
        return ResponseEntity.status(400).body("Erro no registro do cliente: " + ex.getMessage());
    }

    @ExceptionHandler(ClienteRemocaoException.class)
    public ResponseEntity<String> handleClienteRemocaoException(ClienteRemocaoException ex) {
        logger.error("Erro na remoção do cliente: {}", ex.getMessage(), ex);
        return ResponseEntity.status(400).body("Erro na remoção do cliente: " + ex.getMessage());
    }

    @ExceptionHandler(EmpresaRegistroException.class)
    public ResponseEntity<String> handleEmpresaRegistroException(EmpresaRegistroException ex) {
        logger.error("Erro no registro da empresa: {}", ex.getMessage(), ex);
        return ResponseEntity.status(400).body("Erro no registro da empresa: " + ex.getMessage());
    }

    @ExceptionHandler(EmpresaRemocaoException.class)
    public ResponseEntity<String> handleEmpresaRemocaoException(EmpresaRemocaoException ex) {
        logger.error("Erro na remoção da empresa: {}", ex.getMessage(), ex);
        return ResponseEntity.status(400).body("Erro na remoção da empresa: " + ex.getMessage());
    }

    @ExceptionHandler(NotificacaoEmpresaException.class)
    public ResponseEntity<String> handleNotificacaoEmpresaException(NotificacaoEmpresaException ex) {
        logger.error("Erro na notificação à empresa: {}", ex.getMessage(), ex);
        return ResponseEntity.status(500).body("Erro na notificação à empresa");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {

        logger.error("Erro inesperado: {}", ex.getMessage(), ex);

        return ResponseEntity.status(400).body("Erro inesperado");
    }

}
