package tgid.service;

import java.util.List;
import tgid.entity.Empresa;

public interface EmpresaService {
    void registrarEmpresa(String cnpj, String nome, Double saldo, double taxaDeposito, double taxaSaque);

    void mudarTaxaValorEmpresa(Long empresaId, String tipoTaxa, double valor);

    List<Empresa> listarTodasEmpresas();

    void deleteEmpresa(Long id);
}

