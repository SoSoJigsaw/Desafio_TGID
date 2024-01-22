package tgid.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tgid.controller.ClienteController;
import tgid.entity.Cliente;
import tgid.entity.Empresa;
import tgid.exception.objetosExceptions.TaxaInvalidoException;
import tgid.repository.EmpresaRepository;

import java.util.List;
import java.util.Objects;


@Service
public class EmpresaService {

    @Autowired
    EmpresaRepository empresaRepository;

    public void registrarEmpresa(String cnpj, String nome, Double saldo, double taxaDeposito, double taxaSaque) {

        Empresa empresa = new Empresa();

        empresa.setCnpj(cnpj);
        empresa.setNome(nome);
        empresa.setSaldo(saldo);
        empresa.setTaxaDeposito(taxaDeposito);
        empresa.setTaxaSaque(taxaSaque);

        empresaRepository.save(empresa);

    }

    public void mudarTaxaValorEmpresa(Long empresaId, String tipoTaxa, double valor) {

        Empresa empresa = empresaRepository.getReferenceById(empresaId);

        if (Objects.equals(tipoTaxa, "DEPÓSITO")) {

            empresaRepository.atualizarTaxaDeposito(empresaId, valor);

        } else if (Objects.equals(tipoTaxa, "SAQUE")) {

            empresaRepository.atualizarTaxaSaque(empresaId, valor);

        } else { throw new TaxaInvalidoException("ERRO - Tipo inválido: O tipo deve ser DEPÓSITO ou SAQUE"); }

    }

    public List<Empresa> listarTodasEmpresas() {

        List<Empresa> empresas = empresaRepository.findAll();

        return empresas;

    }

    @Transactional
    public void deleteEmpresa(Long id) {

        Empresa empresa = empresaRepository.getReferenceById(id);

        empresaRepository.delete(empresa);

    }

}
