package tgid.service;

import java.util.List;

import tgid.dto.EmpresaDTO;
import tgid.entity.Empresa;

public interface EmpresaService {
    void registrarEmpresa(String cnpj, String nome, Double saldo, Double taxaDeposito, Double taxaSaque);

    void mudarTaxaValorEmpresa(Long empresaId, String tipoTaxa, Double valor);

    List<EmpresaDTO> listarTodasEmpresas();

    void deleteEmpresa(Long id);
}

