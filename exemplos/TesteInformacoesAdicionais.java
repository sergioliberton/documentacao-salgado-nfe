import o.famoso.nfe.informacoes.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Teste completo das classes de Informacoes Adicionais
 * 
 * Este teste demonstra todas as funcionalidades das classes:
 * - InformacoesAdicionais (Z02-Z03)
 * - CampoLivre (Z05-Z06, Z08-Z09)
 * - ProcessoReferenciado (Z11-Z12)
 * - Exportacao (ZA02-ZA04)
 * 
 * @author O Famoso - Rafael (Teste InformacoesAdicionais)
 */
public class TesteInformacoesAdicionais {
    
    public static void main(String[] args) {
        System.out.println("=== TESTE COMPLETO INFORMACOES ADICIONAIS ===\n");
        
        // Teste 1: InformacoesAdicionais
        testeInformacoesAdicionais();
        
        // Teste 2: CampoLivre
        testeCampoLivre();
        
        // Teste 3: ProcessoReferenciado
        testeProcessoReferenciado();
        
        // Teste 4: Exportacao
        testeExportacao();
        
        // Teste 5: Integracao completa
        testeIntegracaoCompleta();
        
        System.out.println("\n=== TODOS OS TESTES CONCLUIDOS ===");
    }
    
    private static void testeInformacoesAdicionais() {
        System.out.println("1. TESTE INFORMACOES ADICIONAIS:");
        try {
            // Teste construtor vazio
            InformacoesAdicionais info1 = new InformacoesAdicionais();
            System.out.println("OK Construtor vazio criado");
            
            // Teste com informacoes do fisco
            InformacoesAdicionais info2 = InformacoesAdicionais.comInformacoesFisco(
                "Cliente optante pelo Simples Nacional, conforme Lei Complementar 123/2006"
            );
            System.out.println("OK Informacoes do fisco: " + info2.temInformacoesFisco());
            
            // Teste com informacoes complementares
            InformacoesAdicionais info3 = InformacoesAdicionais.comInformacoesComplementares(
                "Vendedor: Joao Silva\nEntrega prevista: 5 dias uteis\nGarantia: 12 meses"
            );
            System.out.println("OK Informacoes complementares: " + info3.temInformacoesComplementares());
            
            // Teste completo
            InformacoesAdicionais info4 = InformacoesAdicionais.completas(
                "Regime tributario: Simples Nacional",
                "Observacoes: Produto nacional, sem similar importado"
            );
            System.out.println("OK Informacoes completas: " + info4.temTodasInformacoes());
            
            // Teste adicionar informacoes
            info4.adicionarInformacoesComplementares("Desconto especial para cliente fidelizado", "; ");
            System.out.println("OK Informacoes adicionadas");
            
            // Teste TX2
            String tx2 = info4.gerarTX2();
            System.out.println("OK TX2 gerado:");
            System.out.println(tx2);
            
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }
    
    private static void testeCampoLivre() {
        System.out.println("\n2. TESTE CAMPO LIVRE:");
        try {
            // Teste campos do contribuinte
            CampoLivre campo1 = CampoLivre.contribuinte("PEDIDO", "12345");
            CampoLivre campo2 = CampoLivre.contribuinte("VENDEDOR", "Maria Silva");
            CampoLivre campo3 = CampoLivre.contribuinte("OBSERVACAO", "Cliente especial");
            
            System.out.println("OK Campos contribuinte criados: " + campo1.isContribuinte());
            
            // Teste campos do fisco
            CampoLivre campo4 = CampoLivre.fisco("REGIME", "Simples Nacional");
            CampoLivre campo5 = CampoLivre.fisco("ISENCAO", "Lei 123/2006");
            
            System.out.println("OK Campos fisco criados: " + campo4.isFisco());
            
            // Lista de campos
            List<CampoLivre> campos = new ArrayList<>();
            campos.add(campo1);
            campos.add(campo2);
            campos.add(campo3);
            campos.add(campo4);
            campos.add(campo5);
            
            // Validacao da lista
            List<String> erros = CampoLivre.validarLista(campos);
            System.out.println("OK Validacao lista: " + erros.size() + " erros");
            
            // TX2 da lista
            String tx2Lista = CampoLivre.gerarTX2Lista(campos);
            System.out.println("OK TX2 lista gerado:");
            System.out.println(tx2Lista);
            
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }
    
    private static void testeProcessoReferenciado() {
        System.out.println("\n3. TESTE PROCESSO REFERENCIADO:");
        try {
            // Teste factory methods
            ProcessoReferenciado proc1 = ProcessoReferenciado.sefaz("123456789");
            ProcessoReferenciado proc2 = ProcessoReferenciado.justicaFederal("987654321");
            ProcessoReferenciado proc3 = ProcessoReferenciado.confaz("PROTOCOLO2024001");
            
            System.out.println("OK Processos criados:");
            System.out.println("  - SEFAZ: " + proc1.isSEFAZ());
            System.out.println("  - Justica Federal: " + proc2.isJusticaFederal());
            System.out.println("  - CONFAZ: " + proc3.isCONFAZ());
            
            // Teste com codigo
            ProcessoReferenciado proc4 = new ProcessoReferenciado("PROCESSO2024", "9");
            System.out.println("OK Processo outros: " + proc4.isOutros());
            
            // Lista de processos
            List<ProcessoReferenciado> processos = new ArrayList<>();
            processos.add(proc1);
            processos.add(proc2);
            processos.add(proc3);
            processos.add(proc4);
            
            // Validacao
            List<String> erros = ProcessoReferenciado.validarLista(processos);
            System.out.println("OK Validacao processos: " + erros.size() + " erros");
            
            // TX2
            String tx2Processos = ProcessoReferenciado.gerarTX2Lista(processos);
            System.out.println("OK TX2 processos gerado:");
            System.out.println(tx2Processos);
            
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }
    
    private static void testeExportacao() {
        System.out.println("\n4. TESTE EXPORTACAO:");
        try {
            // Teste factory methods
            Exportacao exp1 = Exportacao.viaPorto("SP", "SANTOS");
            System.out.println("OK Exportacao porto: " + exp1.getLocalExportacao());
            
            Exportacao exp2 = Exportacao.viaAeroporto("RJ", "GALEAO");
            System.out.println("OK Exportacao aeroporto: " + exp2.getLocalExportacao());
            
            Exportacao exp3 = Exportacao.viaFronteira("RS", "URUGUAIANA");
            System.out.println("OK Exportacao fronteira: " + exp3.getLocalExportacao());
            
            // Exportacao com recinto alfandegado
            Exportacao exp4 = Exportacao.comRecintoAlfandegado(
                "SP", "PORTO DE SANTOS", "RECINTO ALFANDEGADO SANTOS"
            );
            System.out.println("OK Exportacao com recinto: " + exp4.temLocalDespacho());
            
            // Teste TX2
            String tx2Export = exp4.gerarTX2();
            System.out.println("OK TX2 exportacao gerado:");
            System.out.println(tx2Export);
            
            // Teste validacao UF invalida
            try {
                Exportacao expInvalida = new Exportacao("EX", "EXTERIOR");
                System.err.println("ERRO: Deveria ter rejeitado UF EX");
            } catch (IllegalArgumentException e) {
                System.out.println("OK Erro detectado para UF EX: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }
    
    private static void testeIntegracaoCompleta() {
        System.out.println("\n5. TESTE INTEGRACAO COMPLETA:");
        try {
            // Cenario: NFe de exportacao com todas as informacoes adicionais
            
            // 1. Informacoes adicionais
            InformacoesAdicionais informacoes = InformacoesAdicionais.completas(
                "Operacao de exportacao conforme legislacao vigente",
                "Produto nacional para mercado exterior\nBeneficio fiscal: Lei 123/2006"
            );
            
            // 2. Campos livres
            List<CampoLivre> campos = new ArrayList<>();
            campos.add(CampoLivre.contribuinte("LOTE", "EXP2024001"));
            campos.add(CampoLivre.contribuinte("DESTINO", "ARGENTINA"));
            campos.add(CampoLivre.fisco("DRAWBACK", "SIM"));
            
            // 3. Processos referenciados
            List<ProcessoReferenciado> processos = new ArrayList<>();
            processos.add(ProcessoReferenciado.secexRFB("DRAWBACK2024001"));
            processos.add(ProcessoReferenciado.confaz("CONVENIO123"));
            
            // 4. Exportacao
            Exportacao exportacao = Exportacao.comRecintoAlfandegado(
                "SP", "PORTO DE SANTOS", "RECINTO ALFANDEGADO SANTOS"
            );
            
            // Gerar TX2 completo
            StringBuilder tx2Completo = new StringBuilder();
            tx2Completo.append("=== NFe EXPORTACAO - INFORMACOES ADICIONAIS ===\n");
            tx2Completo.append(informacoes.gerarTX2());
            tx2Completo.append(CampoLivre.gerarTX2Lista(campos));
            tx2Completo.append(ProcessoReferenciado.gerarTX2Lista(processos));
            tx2Completo.append(exportacao.gerarTX2());
            
            System.out.println("OK Integracao completa - TX2 final:");
            System.out.println(tx2Completo.toString());
            
            // Estatisticas
            System.out.println("\nESTATISTICAS:");
            System.out.println("- Informacoes: " + informacoes.getTamanhoTotal() + " caracteres");
            System.out.println("- Campos livres: " + campos.size() + " campos");
            System.out.println("- Processos: " + processos.size() + " processos");
            System.out.println("- Exportacao: " + (exportacao.temLocalDespacho() ? "com" : "sem") + " recinto");
            
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 