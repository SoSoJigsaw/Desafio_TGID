package tgid.service;

import tgid.dto.EmpresaDTO;

import java.util.List;

public interface EmpresaService {
    void registrarEmpresa(String cnpj, String nome, Double saldo, Double taxaDeposito, Double taxaSaque);

    void mudarTaxaValorEmpresa(Long empresaId, String tipoTaxa, Double valor);

    List<EmpresaDTO> listarTodasEmpresas();

    void deleteEmpresa(Long id);
}

