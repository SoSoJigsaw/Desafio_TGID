package tgid.service;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tgid.entity.Empresa;
import tgid.exception.*;
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
    public void registrarEmpresa(String cnpj, String nome, Double saldo, double taxaDeposito, double taxaSaque)
            throws EmpresaRegistroException {
        Empresa empresa = new Empresa(cnpj, nome, saldo, taxaDeposito, taxaSaque);
        empresaRepository.save(empresa);


    }

    @Override
    public void mudarTaxaValorEmpresa(Long empresaId, String tipoTaxa, double valor) throws TaxaInvalidoException {


        Empresa empresa = empresaRepository.getReferenceById(empresaId);

        if (empresa == null) {
            throw new EmpresaNaoEncontradaException("Não há empresa com esse id: " + empresaId);
        }

        if (Objects.equals(tipoTaxa, "DEPÓSITO")) {
            empresaRepository.atualizarTaxaDeposito(empresaId, valor);
        } else if (Objects.equals(tipoTaxa, "SAQUE")) {
            empresaRepository.atualizarTaxaSaque(empresaId, valor);
        } else {
            throw new TaxaInvalidoException("ERRO - Tipo inválido: O tipo deve ser DEPÓSITO ou SAQUE");
        }
    }

    @Override
    public List<Empresa> listarTodasEmpresas() throws EmpresaNaoEncontradaException {
        return empresaRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteEmpresa(Long id) throws EmpresaRemocaoException {
        Empresa empresa = empresaRepository.getReferenceById(id);

        if (empresa == null) {
            throw new EmpresaNaoEncontradaException("Não há empresa com esse id: " + id);
        }

        try {
            transacaoRepository.deleteApartirDaEmpresa(empresa);
        } catch (Exception e) {
            throw new TransacaoRemocaoException("Não foi possível deletar a transação");
        }

        empresaRepository.delete(empresa);
    }
}
