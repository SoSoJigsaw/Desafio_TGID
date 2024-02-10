package tgid.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import tgid.dto.EmpresaDTO;
import tgid.entity.Empresa;
import tgid.exception.*;
import tgid.repository.EmpresaRepository;
import tgid.repository.TransacaoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
            log.error("Os parâmetros de entrada não podem ser nulos");
            throw new EmpresaRegistroException("Os parâmetros de entrada não podem ser nulos");
        }

        // Remove todos os caracteres não numéricos do CNPJ para padronizar em seguida o formato
        cnpj = cnpj.replaceAll("[^0-9]", "");

        // Formata o CNPJ com os caracteres especiais
        cnpj = cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");

        Empresa empresa = new Empresa(cnpj, nome, saldo, taxaDeposito, taxaSaque);
        try {
            empresaRepository.save(empresa);
        } catch (DataIntegrityViolationException e) {
            log.error("Violação da constraint cnpj_unique_key. Registro de empresa negado");
            throw new ViolacaoConstraintCnpjException("O CNPJ " + cnpj + " já foi utilizado por outra empresa. " +
                    "Tente Novamente");
        }
    }

    @Override
    public void mudarTaxaValorEmpresa(Long empresaId, String tipoTaxa, double valor) throws TaxaInvalidoException {

        Optional<Empresa> empresa = empresaRepository.findById(empresaId);

        if (empresa.isPresent()) {

            if (Objects.equals(tipoTaxa, "DEPÓSITO")) {
                empresaRepository.atualizarTaxaDeposito(empresaId, valor);
            } else if (Objects.equals(tipoTaxa, "SAQUE")) {
                empresaRepository.atualizarTaxaSaque(empresaId, valor);
            } else {
                log.error("Ao tentar realizar mudança de valor de taxa, foi inserido pelo usuário o " +
                        "tipo de taxa " + tipoTaxa + ". Esse valor não é válido, tente utilizar DEPÓSITO " +
                        "ou SAQUE");
                throw new TaxaInvalidoException();
            }

        } else {
            log.error("Ao tentar realizar mudança do valor de taxa, o usuário utilizou um id inexistente no banco. " +
                    "Procedimento negado.");
            throw new EmpresaNaoEncontradaException("Não há empresa com esse id: " + empresaId);
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

        if (empresasDTO.isEmpty()) {
            log.error("empresasDTO está vazio. Portanto não há empresas registradas na base de dados");
            throw new EmpresaNaoEncontradaException("Não há Empresas registradas na base de dados");
        }

        return empresasDTO;
    }

    @Override
    @Transactional
    public void deleteEmpresa(Long id) throws EmpresaRemocaoException {

        Optional<Empresa> empresaOptional = empresaRepository.findById(id);

        if (empresaOptional.isPresent()) {

            Empresa empresa = empresaRepository.getReferenceById(id);

            try {
                transacaoRepository.deleteApartirDaEmpresa(empresa);
            } catch (Exception e) {
                log.error("Erro ao deletar as transações relacionadas à empresa " + empresa.getNome() +
                        " durante tentativa de deletar essa mesma empresa do banco");
                throw new TransacaoRemocaoException("Não foi possível deletar as transações relacionadas a essa empresa");
            }

            empresaRepository.delete(empresa);

        } else {
            log.error("Não há empresa com esse id: " + id + ". Método de deletar cancelado.");
            throw new EmpresaNaoEncontradaException("Não há empresa com esse id: " + id);
        }
    }

}
