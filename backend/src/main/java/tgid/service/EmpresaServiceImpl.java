package tgid.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tgid.entity.Empresa;
import tgid.exception.EmpresaNaoEncontradaException;
import tgid.exception.TaxaInvalidoException;
import tgid.repository.EmpresaRepository;
import tgid.repository.TransacaoRepository;
import java.util.List;
import java.util.Objects;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final TransacaoRepository transacaoRepository;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository, TransacaoRepository transacaoRepository) {
        this.empresaRepository = empresaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    public void registrarEmpresa(String cnpj, String nome, Double saldo, double taxaDeposito, double taxaSaque) {
        Empresa empresa = new Empresa(cnpj, nome, saldo, taxaDeposito, taxaSaque);
        empresaRepository.save(empresa);
    }

    @Override
    public void mudarTaxaValorEmpresa(Long empresaId, String tipoTaxa, double valor) {
        Empresa empresa = empresaRepository.getReferenceById(empresaId);

        if (Objects.equals(tipoTaxa, "DEPÓSITO")) {
            empresaRepository.atualizarTaxaDeposito(empresaId, valor);
        } else if (Objects.equals(tipoTaxa, "SAQUE")) {
            empresaRepository.atualizarTaxaSaque(empresaId, valor);
        } else {
            throw new TaxaInvalidoException("ERRO - Tipo inválido: O tipo deve ser DEPÓSITO ou SAQUE");
        }
    }

    @Override
    public List<Empresa> listarTodasEmpresas() {
        return empresaRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteEmpresa(Long id) {
        Empresa empresa = empresaRepository.getReferenceById(id);

        if (empresa == null) {
            throw new EmpresaNaoEncontradaException("Não há empresa com esse id: " + id);
        }

        transacaoRepository.deleteApartirDaEmpresa(empresa);
        empresaRepository.delete(empresa);
    }
}
