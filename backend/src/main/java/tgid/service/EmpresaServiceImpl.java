package tgid.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tgid.dto.EmpresaDTO;
import tgid.entity.Empresa;
import tgid.exception.*;
import tgid.repository.EmpresaRepository;
import tgid.repository.TransacaoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
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

        if (cnpj == null || nome == null || saldo == null) {
            throw new EmpresaRegistroException("Os parâmetros do input não podem ser nulos");
        }

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
            throw new TaxaInvalidoException();
        }
    }

    @Override
    @Cacheable
    public List<EmpresaDTO> listarTodasEmpresas() throws EmpresaNaoEncontradaException {

        List<EmpresaDTO> empresasDTO = new ArrayList<>();

        List<Empresa> empresas = empresaRepository.findAll();

        for (Empresa empresa : empresas) {

            EmpresaDTO dto = new EmpresaDTO();

            dto.setId(empresa.getId());
            dto.setCnpj(empresa.getCnpj());
            dto.setNome(empresa.getNome());
            dto.setTaxaDeposito(empresa.getTaxaDeposito());
            dto.setTaxaSaque(empresa.getTaxaSaque());
            dto.setSaldo(empresa.getSaldo());

            empresasDTO.add(dto);
        }

        return empresasDTO;
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
