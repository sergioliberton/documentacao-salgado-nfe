import o.famoso.nfe.*;
import o.famoso.nfe.cliente.*;
import o.famoso.nfe.item.*;
import o.famoso.nfe.totais.*;
import o.famoso.nfe.informacoes.*;
import o.famoso.nfe.enums.*;
import o.famoso.nfe.tributos.*;
import o.famoso.nfe.tributos.cst.icms.*;
import o.famoso.nfe.tributos.extras.*;
import o.famoso.nfe.gerador.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Teste básico da geração TX2 - O Famoso NFe
 * 
 * @author Sergio Almeida
 */
public class TesteGeracaoTX2Completa {
    
    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("                   TESTE TX2 - O Famoso NFe");
        System.out.println("================================================================================");
        System.out.println("Testando geração TX2 com dados básicos...");
        System.out.println();
        
        try {
            // Criar NFe básica
            NFe nfe = criarNFeSimples();
            System.out.println("[1] NFe criada com sucesso");
            
            // Depuração - verificar validações
            System.out.println("\nVerificando dados da NFe:");
            System.out.println("   - Tem emitente: " + nfe.temEmitente());
            System.out.println("   - Tem destinatário: " + nfe.temDestinatario());
            System.out.println("   - Tem itens: " + nfe.temItens());
            System.out.println("   - Número NFe: " + nfe.getNumero());
            
            if (nfe.temEmitente()) {
                Cliente emitente = nfe.getEmitente();
                System.out.println("   - Emitente nome: " + emitente.getNome());
                System.out.println("   - Emitente documento: " + emitente.getDocumento());
                System.out.println("   - Emitente tem endereço: " + emitente.temEndereco());
            }
            
            if (nfe.temDestinatario()) {
                Cliente destinatario = nfe.getDestinatario();
                System.out.println("   - Destinatário nome: " + destinatario.getNome());
                System.out.println("   - Destinatário documento: " + destinatario.getDocumento());
                System.out.println("   - Destinatário tem endereço: " + destinatario.temEndereco());
            }
            
            if (nfe.temItens()) {
                System.out.println("   - Quantidade de itens: " + nfe.getItens().size());
            }
            
            // Gerar TX2
            GeradorTX2 gerador = new GeradorTX2(nfe);
            String tx2 = null;
            
            try {
                tx2 = gerador.gerarTX2Completo();
            } catch (Exception e) {
                System.out.println("\nERRO - Exception durante geracao TX2:");
                System.out.println("   Tipo: " + e.getClass().getSimpleName());
                System.out.println("   Mensagem: " + e.getMessage());
                if (e.getCause() != null) {
                    System.out.println("   Causa: " + e.getCause().getMessage());
                }
                e.printStackTrace();
            }
            
            if (tx2 != null && !tx2.trim().isEmpty()) {
                System.out.println("\n[2] TX2 gerado com sucesso!\n");
                
                // Mostrar estatísticas
                String[] linhas = tx2.split("\n");
                int numLinhas = linhas.length;
                int numCampos = 0;
                
                for (String linha : linhas) {
                    if (linha.contains("=")) {
                        numCampos++;
                    }
                }
                
                System.out.println("Estatisticas:");
                System.out.println("   Linhas: " + numLinhas);
                System.out.println("   Campos: " + numCampos);
                System.out.println("   Tamanho: " + tx2.length() + " caracteres");
                
                // Mostrar primeiras linhas
                System.out.println("\nPrimeiras linhas do TX2:");
                for (int i = 0; i < Math.min(5, linhas.length); i++) {
                    if (!linhas[i].trim().isEmpty()) {
                        System.out.println("   " + linhas[i]);
                    }
                }
                
                System.out.println("\nSUCESSO - TESTE CONCLUIDO COM SUCESSO!");
                
            } else {
                System.out.println("\nERRO - TX2 nao foi gerado!");
                
                // Mostrar erros de validação
                List<String> erros = gerador.getErros();
                if (erros != null && !erros.isEmpty()) {
                    System.out.println("\nERROS encontrados:");
                    for (String erro : erros) {
                        System.out.println("   - " + erro);
                    }
                } else {
                    System.out.println("   Nenhum erro específico retornado pelo gerador.");
                }
            }
            
        } catch (Exception e) {
            System.err.println("ERRO durante o teste:");
            e.printStackTrace();
        }
    }
    
    /**
     * Cria uma NFe completa conforme exemplo TX2 real
     */
    private static NFe criarNFeSimples() {
        NFe nfe = new NFe(IdentificacaoAmbiente.homologacao, TipoOperacao.saida);
        
        // Configurações básicas conforme exemplo
        nfe.setSerie(1);
        nfe.setNumero(123456);
        nfe.setNaturezaOperacao(NaturezaOperacao.VENDA);
        nfe.setModeloDanfe(ModeloDanfe.RETRATO); // Modelo 55
        nfe.setTipoEmissao(TipoEmissao.EMISSAO_NORMAL);
        nfe.setOperacaoConsumidorFinal(OperacaoComConsumidorFinal.normal);
        nfe.setIndicadorPresencaComprador(IndicadorPresencaComprador.OPERACAO_PRESENCIAL);
        
        // Emitente - Conforme exemplo "GABRIEL PINKE MATTOS - O FAMOSO"
        Cliente emitente = new Cliente();
        emitente.setNome("GABRIEL PINKE MATTOS");
        emitente.setNomeFantasia("O FAMOSO");
        emitente.setDocumento("00000000000000");
        emitente.setTipoDocumento(TipoDocumento.CNPJ);
        emitente.setInscricaoEstadual("671265002112");
        emitente.setIndicadorIE(IndicadorIE.contribuinteICMS);
        emitente.setRegimeTributario(RegimeTributario.simplesNacional); // CRT=1
        emitente.setDDD("19");
        emitente.setTelefone("971215217");
        
        Endereco enderecoEmitente = new Endereco();
        enderecoEmitente.setRua("RUA FRANCISCO MANOEL DE SOUZA");
        enderecoEmitente.setNumero("710");
        enderecoEmitente.setComplemento("");
        enderecoEmitente.setBairro("CHACARA BELA VISTA");
        enderecoEmitente.setMunicipio("SUMARE");
        enderecoEmitente.setCodigoCidade("3552403");
        enderecoEmitente.setUf(UF.SP);
        enderecoEmitente.setCep("13175500");
        enderecoEmitente.setTipoLogradouro(TipoLogradouro.RUA);
        
        emitente.setEndereco(enderecoEmitente);
        nfe.setEmitente(emitente);
        
        // Destinatário - Conforme exemplo "EMPORIO DOM LUIZ LTDA"
        Cliente destinatario = new Cliente();
        destinatario.setNome("EMPORIO DOM LUIZ LTDA");
        destinatario.setDocumento("46371190000154");
        destinatario.setTipoDocumento(TipoDocumento.CNPJ);
        destinatario.setInscricaoEstadual("671552093115");
        destinatario.setIndicadorIE(IndicadorIE.contribuinteICMS);
        destinatario.setDDD("19");
        destinatario.setTelefone("38282927");
        destinatario.setEmail("DOMLUIZXMLLOJA1@GMAIL.COM");
        
        Endereco enderecoDestinatario = new Endereco();
        enderecoDestinatario.setRua("RUA ANNA PEREIRA EICHEMBERGER");
        enderecoDestinatario.setNumero("415");
        enderecoDestinatario.setComplemento("LOJA 01");
        enderecoDestinatario.setBairro("PARQUE ROSA E SILVA");
        enderecoDestinatario.setMunicipio("SUMARE");
        enderecoDestinatario.setCodigoCidade("3552403");
        enderecoDestinatario.setUf(UF.SP);
        enderecoDestinatario.setCep("13173220");
        enderecoDestinatario.setTipoLogradouro(TipoLogradouro.RUA);
        
        destinatario.setEndereco(enderecoDestinatario);
        nfe.setDestinatario(destinatario);
        
        // Itens - Conforme exemplo com salgados
        List<ItemNFe> itens = new ArrayList<>();
        
        // Item 1 - COXINHA DE FRANGO
        ItemNFe item1 = new ItemNFe();
        item1.setNItem(1);
        item1.setCodigoProdutoServico("101");
        item1.setCodigoBarras("7448070132017");
        item1.setDescricaoItem("SALGADO FRITO CONGELADO - COXINHA DE FRANGO");
        item1.setNCM("19021900");
        item1.setCFOP(5401);
        item1.setUnidadeComercial("UN");
        item1.setQuantidadeComercial(15.0);
        item1.setValorUnitarioComercial(7.00);
        item1.setValorTotalBruto(105.00);
        item1.setUnidadeTributavel("UN");
        item1.setQuantidadeTributavel(15.0);
        item1.setValorUnitarioTributavel(7.00);
        item1.setIndicadorTotal("1");
        itens.add(item1);
        
        nfe.setItens(itens);
        
        // Totais - Simplificados
        TotaisNFe totais = new TotaisNFe();
        totais.setValorTotalProdutos(105.00);
        totais.setValorTotalNF(105.00);
        
        nfe.setTotais(totais);
        
        // Informações adicionais simplificadas
        InformacoesAdicionais info = new InformacoesAdicionais();
        info.setInformacoesComplementares("TX2 gerado com O Famoso NFe - Exemplo baseado em nota real");
        
        nfe.setInformacoesAdicionais(info);
        
        return nfe;
    }
} 