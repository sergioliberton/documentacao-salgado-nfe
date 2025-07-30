import o.famoso.nfe.cliente.Cliente;
import o.famoso.nfe.cliente.Endereco;
import o.famoso.nfe.cliente.TipoDocumento;
import o.famoso.nfe.enums.*;
import o.famoso.nfe.validacao.excecoes.ValidacaoException;
import o.famoso.nfe.validacao.*;
import java.util.List;

/**
 * Teste abrangente do sistema de validacao implementado.
 * 
 * @author O Famoso - Rafael
 */
public class TesteValidacao {

    public static void main(String[] args) {
        System.out.println("=== TESTE DO SISTEMA DE VALIDACAO ===\n");
        
        // Teste 1: Validadores individuais
        testarValidadoresIndividuais();
        
        // Teste 2: Cliente com dados validos
        testarClienteValido();
        
        // Teste 3: Cliente com dados invalidos
        testarClienteInvalido();
        
        // Teste 4: Validacoes de regras de negocio
        testarRegrasNegocio();
        
        System.out.println("\n=== TESTE CONCLUIDO ===");
    }
    
    /**
     * Testa validadores individuais (CNPJ, CPF, CEP, etc.)
     */
    private static void testarValidadoresIndividuais() {
        System.out.println("--- TESTE 1: Validadores Individuais ---");
        
        // Teste CNPJ
        System.out.println("\n1.1. Teste CNPJ:");
        String[] cnpjsTeste = {
            "11.222.333/0001-81",  // Valido
            "11.222.333/0001-80",  // Invalido (digito errado)
            "11.111.111/1111-11",  // Invalido (todos iguais)
            "12345678000195",      // Valido (sem formatacao)
            "123456789"            // Invalido (tamanho)
        };
        
        for (String cnpj : cnpjsTeste) {
            boolean valido = CNPJValidator.validar(cnpj);
            System.out.println("  " + cnpj + " -> " + (valido ? "VALIDO" : "INVALIDO"));
        }
        
        // Teste CPF
        System.out.println("\n1.2. Teste CPF:");
        String[] cpfsTeste = {
            "11144477735",         // Valido
            "111.444.777-35",      // Valido (formatado)
            "111.444.777-34",      // Invalido (digito errado)
            "111.111.111-11",      // Invalido (todos iguais)
            "123456789"            // Invalido (tamanho)
        };
        
        for (String cpf : cpfsTeste) {
            boolean valido = CPFValidator.validar(cpf);
            System.out.println("  " + cpf + " -> " + (valido ? "VALIDO" : "INVALIDO"));
        }
        
        // Teste CEP
        System.out.println("\n1.3. Teste CEP:");
        String[] cepsTeste = {
            "13170-000",           // Valido
            "13170000",            // Valido (sem formatacao)
            "00000-000",           // Invalido (zeros)
            "1234567",             // Invalido (tamanho)
            "12345-67A"            // Invalido (letra)
        };
        
        for (String cep : cepsTeste) {
            boolean valido = CEPValidator.validar(cep);
            System.out.println("  " + cep + " -> " + (valido ? "VALIDO" : "INVALIDO"));
        }
        
        // Teste codigos fiscais
        System.out.println("\n1.4. Teste Codigos Fiscais:");
        System.out.println("  NCM 12345678 -> " + (CodigosFiscaisValidator.validarNCM("12345678") ? "VALIDO" : "INVALIDO"));
        System.out.println("  CFOP 5102 -> " + (CodigosFiscaisValidator.validarCFOP("5102") ? "VALIDO" : "INVALIDO"));
        System.out.println("  CFOP 8102 -> " + (CodigosFiscaisValidator.validarCFOP("8102") ? "VALIDO" : "INVALIDO")); // Invalido
        System.out.println("  Codigo IBGE 3550308 -> " + (CodigosFiscaisValidator.validarCodigoIBGE("3550308") ? "VALIDO" : "INVALIDO"));
    }
    
    /**
     * Testa cliente com dados validos
     */
    private static void testarClienteValido() {
        System.out.println("\n--- TESTE 2: Cliente Valido ---");
        
        Cliente cliente = new Cliente();
        cliente.setNome("EMPRESA TESTE LTDA");
        cliente.setDocumento("11.222.333/0001-81");
        cliente.setTipoDocumento(TipoDocumento.CNPJ);
        cliente.setInscricaoEstadual("123456789");
        cliente.setIndicadorIE(IndicadorIE.contribuinteICMS);
        cliente.setRegimeTributario(RegimeTributario.regimeNormal);
        cliente.setDDD("19");
        cliente.setTelefone("34567890");
        
        // Criar endereco
        Endereco endereco = new Endereco();
        endereco.setRua("Rua das Flores");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCep("13170-000");
        endereco.setMunicipio("Sumare");
        endereco.setUf(UF.SP);
        endereco.setCodigoCidade("3552205");
        cliente.setEndereco(endereco);
        
        // Validar
        List<ValidacaoException> erros = cliente.validar();
        
        System.out.println("Erros encontrados: " + erros.size());
        if (!erros.isEmpty()) {
            System.out.println("ERRO! Cliente deveria estar valido:");
            for (ValidacaoException erro : erros) {
                System.out.println("  • " + erro.getMessage());
            }
        } else {
            System.out.println("✓ Cliente valido com sucesso!");
        }
        
        System.out.println("Cliente esta valido: " + cliente.estaValido());
    }
    
    /**
     * Testa cliente com dados invalidos
     */
    private static void testarClienteInvalido() {
        System.out.println("\n--- TESTE 3: Cliente Invalido ---");
        
        Cliente cliente = new Cliente();
        // Propositalmente deixar campos obrigatorios vazios
        cliente.setNome(""); // Nome vazio
        cliente.setDocumento("111.111.111-11"); // CPF invalido
        cliente.setTipoDocumento(TipoDocumento.CPF);
        cliente.setInscricaoEstadual("ISENTO");
        cliente.setIndicadorIE(IndicadorIE.contribuinteICMS); // Inconsistente
        cliente.setRegimeTributario(RegimeTributario.simplesNacional);
        cliente.setDDD("1"); // DDD invalido
        cliente.setTelefone("1234567"); // Telefone muito curto
        cliente.setInscricaoSuframa("12345678A"); // Deve ser numerico
        
        // Nao definir endereco (obrigatorio)
        
        // Validar
        List<ValidacaoException> erros = cliente.validar();
        
        System.out.println("Erros encontrados: " + erros.size());
        if (erros.isEmpty()) {
            System.out.println("ERRO! Cliente deveria estar invalido!");
        } else {
            System.out.println("✓ Erros detectados corretamente:");
            for (ValidacaoException erro : erros) {
                System.out.println("  • " + erro.getMessage());
            }
        }
        
        System.out.println("Cliente esta valido: " + cliente.estaValido());
        System.out.println("\nMensagem de erro formatada:");
        System.out.println(cliente.getErrosValidacao());
    }
    
    /**
     * Testa validacoes de regras de negocio
     */
    private static void testarRegrasNegocio() {
        System.out.println("\n--- TESTE 4: Regras de Negocio ---");
        
        // Teste ICMS vs ISSQN
        System.out.println("\n4.1. Teste ICMS vs ISSQN:");
        System.out.println("  ICMS e ISSQN ambos preenchidos -> " + 
            (RegrasNegocioValidator.validarICMSvsISSQN("icms", "issqn") ? "VALIDO" : "INVALIDO"));
        System.out.println("  Apenas ICMS -> " + 
            (RegrasNegocioValidator.validarICMSvsISSQN("icms", null) ? "VALIDO" : "INVALIDO"));
        System.out.println("  Apenas ISSQN -> " + 
            (RegrasNegocioValidator.validarICMSvsISSQN(null, "issqn") ? "VALIDO" : "INVALIDO"));
        
        // Teste Simples Nacional
        System.out.println("\n4.2. Teste Simples Nacional:");
        System.out.println("  Simples Nacional + CRT=1 -> " + 
            (RegrasNegocioValidator.validarSimplesNacional(RegimeTributario.simplesNacional, "1") ? "VALIDO" : "INVALIDO"));
        System.out.println("  Simples Nacional + CRT=3 -> " + 
            (RegrasNegocioValidator.validarSimplesNacional(RegimeTributario.simplesNacional, "3") ? "VALIDO" : "INVALIDO"));
        System.out.println("  Regime Normal + CRT=3 -> " + 
            (RegrasNegocioValidator.validarSimplesNacional(RegimeTributario.regimeNormal, "3") ? "VALIDO" : "INVALIDO"));
        
        // Teste Indicador IE
        System.out.println("\n4.3. Teste Indicador IE:");
        System.out.println("  Contribuinte ICMS + IE valida -> " + 
            (RegrasNegocioValidator.validarIndicadorIE(IndicadorIE.contribuinteICMS, "123456789") ? "VALIDO" : "INVALIDO"));
        System.out.println("  Contribuinte ICMS + ISENTO -> " + 
            (RegrasNegocioValidator.validarIndicadorIE(IndicadorIE.contribuinteICMS, "ISENTO") ? "VALIDO" : "INVALIDO"));
        System.out.println("  Contribuinte Isento + ISENTO -> " + 
            (RegrasNegocioValidator.validarIndicadorIE(IndicadorIE.contribuinteIsento, "ISENTO") ? "VALIDO" : "INVALIDO"));
        
        // Teste CFOP vs Operacao
        System.out.println("\n4.4. Teste CFOP vs Operacao:");
        System.out.println("  CFOP 5102 + Saida -> " + 
            (RegrasNegocioValidator.validarCFOPvsOperacao("5102", TipoOperacao.saida) ? "VALIDO" : "INVALIDO"));
        System.out.println("  CFOP 5102 + Entrada -> " + 
            (RegrasNegocioValidator.validarCFOPvsOperacao("5102", TipoOperacao.entrada) ? "VALIDO" : "INVALIDO"));
        System.out.println("  CFOP 1102 + Entrada -> " + 
            (RegrasNegocioValidator.validarCFOPvsOperacao("1102", TipoOperacao.entrada) ? "VALIDO" : "INVALIDO"));
        
        // Teste Local Destino vs UF
        System.out.println("\n4.5. Teste Local Destino vs UF:");
        System.out.println("  Interna SP-SP -> " + 
            (RegrasNegocioValidator.validarLocalDestinovsUF(IndicadorLocalDestinoOperacao.interna, "SP", "SP") ? "VALIDO" : "INVALIDO"));
        System.out.println("  Interna SP-RJ -> " + 
            (RegrasNegocioValidator.validarLocalDestinovsUF(IndicadorLocalDestinoOperacao.interna, "SP", "RJ") ? "VALIDO" : "INVALIDO"));
        System.out.println("  Interestadual SP-RJ -> " + 
            (RegrasNegocioValidator.validarLocalDestinovsUF(IndicadorLocalDestinoOperacao.interestadual, "SP", "RJ") ? "VALIDO" : "INVALIDO"));
        System.out.println("  Exterior SP-EX -> " + 
            (RegrasNegocioValidator.validarLocalDestinovsUF(IndicadorLocalDestinoOperacao.exterior, "SP", "EX") ? "VALIDO" : "INVALIDO"));
    }
} 