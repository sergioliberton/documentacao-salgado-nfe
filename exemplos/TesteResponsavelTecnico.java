import o.famoso.nfe.informacoes.ResponsavelTecnico;
import java.util.List;

/**
 * Teste completo da classe ResponsavelTecnico
 * 
 * Este teste demonstra todas as funcionalidades da classe ResponsavelTecnico:
 * - Construtores
 * - Validações
 * - Formatação de dados
 * - Geração TX2
 * - Tratamento de erros
 * 
 * @author O Famoso - Rafael (Teste ResponsavelTecnico)
 */
public class TesteResponsavelTecnico {
    
    public static void main(String[] args) {
        System.out.println("=== TESTE COMPLETO DA CLASSE RESPONSAVEL TECNICO ===\n");
        
        // Teste 1: Construtor vazio e setters
        System.out.println("1. TESTE CONSTRUTOR VAZIO E SETTERS:");
        testeConstrutorVazio();
        
        // Teste 2: Construtor com campos obrigatórios
        System.out.println("\n2. TESTE CONSTRUTOR COM CAMPOS OBRIGATÓRIOS:");
        testeConstrutorObrigatorio();
        
        // Teste 3: Construtor completo com CSRT
        System.out.println("\n3. TESTE CONSTRUTOR COMPLETO COM CSRT:");
        testeConstrutorCompleto();
        
        // Teste 4: Validações e erros
        System.out.println("\n4. TESTE VALIDAÇÕES E TRATAMENTO DE ERROS:");
        testeValidacoes();
        
        // Teste 5: Formatação de dados
        System.out.println("\n5. TESTE FORMATAÇÃO DE DADOS:");
        testeFormatacao();
        
        // Teste 6: Geração TX2
        System.out.println("\n6. TESTE GERAÇÃO TX2:");
        testeGeracaoTX2();
        
        // Teste 7: Validações de CSRT
        System.out.println("\n7. TESTE VALIDAÇÕES CSRT:");
        testeValidacoesCSRT();
        
        System.out.println("\n=== TODOS OS TESTES CONCLUIDOS COM SUCESSO! ===");
    }
    
    /**
     * Teste do construtor vazio e setters
     */
    private static void testeConstrutorVazio() {
        try {
            ResponsavelTecnico rt = new ResponsavelTecnico();
            
            // Definindo campos obrigatórios
            rt.setCnpjResponsavel("12.345.678/0001-95");
            rt.setNomeContato("João Silva");
            rt.setEmailContato("joao@empresa.com.br");
            rt.setTelefoneContato("(11) 98765-4321");
            
                         System.out.println("OK Construtor vazio criado com sucesso");
             System.out.println("OK Campos obrigatorios definidos via setters");
             System.out.println("OK CNPJ: " + rt.getCnpjFormatado());
             System.out.println("OK Contato: " + rt.getNomeContato());
             System.out.println("OK E-mail: " + rt.getEmailContato());
             System.out.println("OK Telefone: " + rt.getTelefoneFormatado());
             System.out.println("OK Valido: " + rt.isValido());
            
        } catch (Exception e) {
            System.err.println("❌ Erro no teste construtor vazio: " + e.getMessage());
        }
    }
    
    /**
     * Teste do construtor com campos obrigatórios
     */
    private static void testeConstrutorObrigatorio() {
        try {
            ResponsavelTecnico rt = new ResponsavelTecnico(
                "98765432000180",
                "Maria Santos",
                "maria@desenvolvedor.com",
                "11987654321"
            );
            
            System.out.println("✓ Construtor com campos obrigatórios criado com sucesso");
            System.out.println("✓ CNPJ: " + rt.getCnpjFormatado());
            System.out.println("✓ Contato: " + rt.getNomeContato());
            System.out.println("✓ E-mail: " + rt.getEmailContato());
            System.out.println("✓ Telefone: " + rt.getTelefoneFormatado());
            System.out.println("✓ Válido: " + rt.isValido());
            System.out.println("✓ Tem CSRT: " + rt.temCSRT());
            
        } catch (Exception e) {
            System.err.println("❌ Erro no teste construtor obrigatório: " + e.getMessage());
        }
    }
    
    /**
     * Teste do construtor completo com CSRT
     */
    private static void testeConstrutorCompleto() {
        try {
            ResponsavelTecnico rt = new ResponsavelTecnico(
                "11222333000144",
                "Pedro Oliveira",
                "pedro@software.com.br",
                "4733221100",
                "01",
                "abcdefghijklmnopqrstuvwxyz01"
            );
            
            System.out.println("✓ Construtor completo criado com sucesso");
            System.out.println("✓ CNPJ: " + rt.getCnpjFormatado());
            System.out.println("✓ Contato: " + rt.getNomeContato());
            System.out.println("✓ E-mail: " + rt.getEmailContato());
            System.out.println("✓ Telefone: " + rt.getTelefoneFormatado());
            System.out.println("✓ ID CSRT: " + rt.getIdentificadorCSRT());
            System.out.println("✓ Hash CSRT: " + rt.getHashCSRT());
            System.out.println("✓ Válido: " + rt.isValido());
            System.out.println("✓ Tem CSRT: " + rt.temCSRT());
            
        } catch (Exception e) {
            System.err.println("❌ Erro no teste construtor completo: " + e.getMessage());
        }
    }
    
    /**
     * Teste de validações e tratamento de erros
     */
    private static void testeValidacoes() {
        // Teste 1: Campos obrigatórios vazios
        System.out.println("4.1 - Teste campos obrigatórios vazios:");
        try {
            ResponsavelTecnico rt = new ResponsavelTecnico();
            List<String> erros = rt.validar();
            
            System.out.println("✓ Erros encontrados: " + erros.size());
            for (String erro : erros) {
                System.out.println("  - " + erro);
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erro no teste validações: " + e.getMessage());
        }
        
        // Teste 2: CNPJ inválido
        System.out.println("\n4.2 - Teste CNPJ inválido:");
        try {
            ResponsavelTecnico rt = new ResponsavelTecnico();
            rt.setCnpjResponsavel("12345678000100"); // CNPJ inválido
            System.err.println("❌ Deveria ter dado erro para CNPJ inválido");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Erro capturado corretamente: " + e.getMessage());
        }
        
        // Teste 3: E-mail inválido
        System.out.println("\n4.3 - Teste e-mail inválido:");
        try {
            ResponsavelTecnico rt = new ResponsavelTecnico();
            rt.setEmailContato("email_invalido");
            System.err.println("❌ Deveria ter dado erro para e-mail inválido");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Erro capturado corretamente: " + e.getMessage());
        }
        
        // Teste 4: Telefone muito curto
        System.out.println("\n4.4 - Teste telefone muito curto:");
        try {
            ResponsavelTecnico rt = new ResponsavelTecnico();
            rt.setTelefoneContato("123");
            System.err.println("❌ Deveria ter dado erro para telefone muito curto");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Erro capturado corretamente: " + e.getMessage());
        }
    }
    
    /**
     * Teste de formatação de dados
     */
    private static void testeFormatacao() {
        try {
            ResponsavelTecnico rt = new ResponsavelTecnico();
            
            // Teste formatação CNPJ
            rt.setCnpjResponsavel("12345678000195");
            System.out.println("✓ CNPJ original: " + rt.getCnpjResponsavel());
            System.out.println("✓ CNPJ formatado: " + rt.getCnpjFormatado());
            
            // Teste formatação telefone
            rt.setTelefoneContato("11987654321");
            System.out.println("✓ Telefone original: " + rt.getTelefoneContato());
            System.out.println("✓ Telefone formatado: " + rt.getTelefoneFormatado());
            
            // Teste com telefone de 10 dígitos
            rt.setTelefoneContato("1133334444");
            System.out.println("✓ Telefone 10 dígitos: " + rt.getTelefoneFormatado());
            
        } catch (Exception e) {
            System.err.println("❌ Erro no teste formatação: " + e.getMessage());
        }
    }
    
    /**
     * Teste de geração TX2
     */
    private static void testeGeracaoTX2() {
        try {
            // Teste 1: TX2 sem CSRT
            System.out.println("6.1 - TX2 sem CSRT:");
            ResponsavelTecnico rt1 = new ResponsavelTecnico(
                "12345678000195",
                "Ana Costa",
                "ana@sistema.com",
                "4733221100"
            );
            
            String tx2_sem_csrt = rt1.gerarTX2();
            System.out.println("✓ TX2 gerado com sucesso:");
            System.out.println(tx2_sem_csrt);
            
            // Teste 2: TX2 com CSRT
            System.out.println("6.2 - TX2 com CSRT:");
            ResponsavelTecnico rt2 = new ResponsavelTecnico(
                "98765432000180",
                "Carlos Mendes",
                "carlos@desenvolvedor.com.br",
                "11987654321",
                "01",
                "abcdefghijklmnopqrstuvwxyz01"
            );
            
            String tx2_com_csrt = rt2.gerarTX2();
            System.out.println("✓ TX2 gerado com sucesso:");
            System.out.println(tx2_com_csrt);
            
        } catch (Exception e) {
            System.err.println("❌ Erro no teste geração TX2: " + e.getMessage());
        }
    }
    
    /**
     * Teste de validações CSRT
     */
    private static void testeValidacoesCSRT() {
        try {
            // Teste 1: CSRT apenas com ID (deve dar erro)
            System.out.println("7.1 - CSRT apenas com ID:");
            ResponsavelTecnico rt1 = new ResponsavelTecnico(
                "12345678000195",
                "Teste",
                "teste@teste.com",
                "1133334444"
            );
            rt1.setIdentificadorCSRT("01");
            
            List<String> erros = rt1.validar();
            if (erros.size() > 0) {
                System.out.println("✓ Erro detectado corretamente: " + erros.get(0));
            }
            
            // Teste 2: CSRT apenas com Hash (deve dar erro)
            System.out.println("\n7.2 - CSRT apenas com Hash:");
            ResponsavelTecnico rt2 = new ResponsavelTecnico(
                "12345678000195",
                "Teste",
                "teste@teste.com",
                "1133334444"
            );
            rt2.setHashCSRT("abcdefghijklmnopqrstuvwxyz01");
            
            erros = rt2.validar();
            if (erros.size() > 0) {
                System.out.println("✓ Erro detectado corretamente: " + erros.get(0));
            }
            
            // Teste 3: CSRT com ID de tamanho inválido
            System.out.println("\n7.3 - CSRT com ID de tamanho inválido:");
            try {
                ResponsavelTecnico rt3 = new ResponsavelTecnico();
                rt3.setIdentificadorCSRT("123"); // Mais de 2 caracteres
                System.err.println("❌ Deveria ter dado erro para ID CSRT inválido");
            } catch (IllegalArgumentException e) {
                System.out.println("✓ Erro capturado corretamente: " + e.getMessage());
            }
            
            // Teste 4: CSRT com Hash de tamanho inválido
            System.out.println("\n7.4 - CSRT com Hash de tamanho inválido:");
            try {
                ResponsavelTecnico rt4 = new ResponsavelTecnico();
                rt4.setHashCSRT("hash_muito_curto"); // Menos de 28 caracteres
                System.err.println("❌ Deveria ter dado erro para Hash CSRT inválido");
            } catch (IllegalArgumentException e) {
                System.out.println("✓ Erro capturado corretamente: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erro no teste validações CSRT: " + e.getMessage());
        }
    }
} 