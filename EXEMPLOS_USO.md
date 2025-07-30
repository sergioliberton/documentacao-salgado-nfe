# 💡 GUIA DE EXEMPLOS - Casos de Uso Práticos

Este documento apresenta **exemplos completos e práticos** de uso da biblioteca NFe para diferentes cenários reais de negócio.

## 📊 **Índice de Cenários**

1. [🛒 NFe B2C - Venda para Consumidor Final](#1-nfe-b2c---venda-para-consumidor-final)
2. [🏢 NFe B2B - Venda Empresarial](#2-nfe-b2b---venda-empresarial)  
3. [🍔 NFC-e - Cupom Fiscal Eletrônico](#3-nfc-e---cupom-fiscal-eletrônico)
4. [🏪 Simples Nacional - Varejo](#4-simples-nacional---varejo)
5. [🏭 Regime Normal - Indústria](#5-regime-normal---indústria)
6. [🛠️ Prestação de Serviços (ISSQN)](#6-prestação-de-serviços-issqn)
7. [🚚 NFe com Transporte](#7-nfe-com-transporte)
8. [🔄 NFe de Devolução](#8-nfe-de-devolução)
9. [🌍 NFe de Exportação](#9-nfe-de-exportação)
10. [🚨 NFe em Contingência](#10-nfe-em-contingência)

---

## 1️⃣ **NFe B2C - Venda para Consumidor Final**

### 📋 **Cenário**
Loja física vendendo para pessoa física com CPF informado.

### 🏭 **Configuração de Ambiente**

#### **🔧 Para Testes (Homologação)**
```java
// Use sempre homologação para desenvolvimento
nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);

// Dados de teste permitidos
emitente.setDocumento("99999999000191"); // CNPJ de teste
destinatario.setDocumento("11111111111"); // CPF de teste
```

#### **🚀 Para Produção (Emissão Real)**
```java
// Apenas após homologação aprovada
nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.producao);

// Dados reais obrigatórios
emitente.setDocumento("12345678000195"); // CNPJ real
destinatario.setDocumento("12345678901"); // CPF real
```

### 💻 **Código Completo**

```java
import o.famoso.nfe.*;
import o.famoso.nfe.cliente.*;
import o.famoso.nfe.item.*;
import o.famoso.nfe.enums.*;
import o.famoso.nfe.pagamento.*;

public class ExemploNFeB2C {
    public static void main(String[] args) {
        try {
            // 1. Criar NFe para consumidor final
            NFe nfe = new NFe();
            nfe.setTipoOperacao(TipoOperacao.saida);
            
            // 🔧 ESCOLHA O AMBIENTE:
            // Para testes (recomendado para desenvolvimento)
            nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);
            
            // Para produção (apenas após homologação aprovada)
            // nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.producao);
            
            nfe.setFinalidadeOperacao(FinalidadeOperacao.normal);
            nfe.setNaturezaOperacaoDescricao("Venda de mercadorias");
            nfe.setConsumidorFinal(OperacaoComConsumidorFinal.sim);
            nfe.setIndicadorPresencaComprador(IndicadorPresencaComprador.operacaoPresencial);
            
            // 2. Configurar Emitente (Loja)
            Cliente emitente = new Cliente();
            emitente.setDocumento("12345678000195");
            emitente.setNome("LOJA DO JOÃO LTDA");
            emitente.setNomeFantasia("Loja do João");
            emitente.setRegimeTributario(RegimeTributario.simplesNacional);
            emitente.setIndicadorIE(IndicadorIE.contribuinteICMS);
            emitente.setInscricaoEstadual("123456789");
            
            // Endereço da loja
            Endereco enderecoEmitente = new Endereco();
            enderecoEmitente.setCep("13170000");
            enderecoEmitente.setRua("Rua das Flores");
            enderecoEmitente.setNumero("100");
            enderecoEmitente.setBairro("Centro");
            enderecoEmitente.setMunicipio("Sumaré");
            enderecoEmitente.setUf(UF.SP);
            enderecoEmitente.setCodigoCidade("3552205");
            emitente.setEndereco(enderecoEmitente);
            
            nfe.setEmitente(emitente);
            
            // 3. Configurar Destinatário (Cliente com CPF)
            Cliente destinatario = new Cliente();
            destinatario.setDocumento("12345678901");
            destinatario.setNome("Maria da Silva");
            destinatario.setIndicadorIE(IndicadorIE.naoContribuinte);
            
            // Endereço do cliente
            Endereco enderecoDestinatario = new Endereco();
            enderecoDestinatario.setCep("01310100");
            enderecoDestinatario.setRua("Avenida Paulista");
            enderecoDestinatario.setNumero("1000");
            enderecoDestinatario.setBairro("Bela Vista");
            enderecoDestinatario.setMunicipio("São Paulo");
            enderecoDestinatario.setUf(UF.SP);
            enderecoDestinatario.setCodigoCidade("3550308");
            destinatario.setEndereco(enderecoDestinatario);
            
            nfe.setDestinatario(destinatario);
            
            // 4. Adicionar Itens
            List<ItemNFe> itens = new ArrayList<>();
            
            // Item 1 - Produto físico
            ItemNFe item1 = new ItemNFe();
            item1.setNItem(1);
            item1.setCodigoProdutoServico("CAMISETA-001");
            item1.setDescricaoItem("Camiseta Azul Tamanho M");
            item1.setNCM("61091000");
            item1.setCFOP(5102); // Venda dentro do estado
            item1.setUnidadeComercial("UN");
            item1.setQuantidadeComercial(2.0);
            item1.setValorUnitarioComercial(35.90);
            item1.setValorTotalBruto(71.80);
            item1.setUnidadeTributavel("UN");
            item1.setQuantidadeTributavel(2.0);
            item1.setValorUnitarioTributavel(35.90);
            item1.setIndicadorTotal("1");
            
            // ICMS Simples Nacional
            item1.configurarICMSSimplesNacional("102", 0.0);
            
            itens.add(item1);
            
            // Item 2 - Outro produto
            ItemNFe item2 = new ItemNFe();
            item2.setNItem(2);
            item2.setCodigoProdutoServico("BERMUDA-001");
            item2.setDescricaoItem("Bermuda Jeans Azul");
            item2.setNCM("62034200");
            item2.setCFOP(5102);
            item2.setUnidadeComercial("UN");
            item2.setQuantidadeComercial(1.0);
            item2.setValorUnitarioComercial(89.90);
            item2.setValorTotalBruto(89.90);
            item2.setUnidadeTributavel("UN");
            item2.setQuantidadeTributavel(1.0);
            item2.setValorUnitarioTributavel(89.90);
            item2.setIndicadorTotal("1");
            
            // ICMS Simples Nacional
            item2.configurarICMSSimplesNacional("102", 0.0);
            
            itens.add(item2);
            
            nfe.setProdutos(itens);
            
            // 5. Configurar Pagamentos
            List<Pagamento> pagamentos = new ArrayList<>();
            
            // Pagamento em dinheiro
            Pagamento pagamento1 = new Pagamento();
            pagamento1.setFormaPagamento(FormaPagamento.dinheiro);
            pagamento1.setValorPagamento(100.00);
            pagamentos.add(pagamento1);
            
            // Pagamento no cartão de débito
            Pagamento pagamento2 = new Pagamento();
            pagamento2.setFormaPagamento(FormaPagamento.cartaoDebito);
            pagamento2.setValorPagamento(61.70);
            pagamentos.add(pagamento2);
            
            nfe.setPagamentos(pagamentos);
            
            // 6. Informações adicionais
            InformacoesAdicionais info = new InformacoesAdicionais();
            info.setInformacoesComplementares("Venda realizada em loja física");
            nfe.setInformacoesAdicionais(info);
            
            // 7. Gerar TX2
            String arquivoTX2 = nfe.gerarTX2();
            
            if (arquivoTX2 != null) {
                System.out.println("✅ NFe B2C gerada com sucesso!");
                System.out.println("💰 Valor total: R$ " + 
                    String.format("%.2f", nfe.getValorTotalPagamentos()));
                System.out.println("📁 Arquivo salvo em: output/");
            } else {
                System.out.println("❌ Erro ao gerar NFe B2C:");
                nfe.getErrosValidacao().forEach(erro -> 
                    System.out.println("  • " + erro));
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

### 📊 **Características do Exemplo B2C**
- ✅ Consumidor final = Sim
- ✅ Presença do comprador = Presencial
- ✅ Destinatário pessoa física (CPF)
- ✅ Indicador IE = Não contribuinte
- ✅ ICMS Simples Nacional (CSOSN 102)
- ✅ Pagamento misto (dinheiro + cartão)

---

## 2️⃣ **NFe B2B - Venda Empresarial**

### 📋 **Cenário**
Empresa vendendo para outra empresa com ICMS normal.

```java
public class ExemploNFeB2B {
    public static void main(String[] args) {
        try {
            // 1. Criar NFe empresarial
            NFe nfe = new NFe();
            nfe.setTipoOperacao(TipoOperacao.saida);
            nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);
            nfe.setFinalidadeOperacao(FinalidadeOperacao.normal);
            nfe.setNaturezaOperacaoDescricao("Venda de produtos para revenda");
            nfe.setConsumidorFinal(OperacaoComConsumidorFinal.nao);
            nfe.setIndicadorPresencaComprador(IndicadorPresencaComprador.naoSeAplica);
            
            // 2. Emitente (Distribuidora)
            Cliente emitente = new Cliente();
            emitente.setDocumento("11222333000144");
            emitente.setNome("DISTRIBUIDORA ABC LTDA");
            emitente.setNomeFantasia("Distribuidora ABC");
            emitente.setRegimeTributario(RegimeTributario.regimeNormal);
            emitente.setIndicadorIE(IndicadorIE.contribuinteICMS);
            emitente.setInscricaoEstadual("111222333");
            
            Endereco enderecoEmitente = new Endereco();
            enderecoEmitente.setCep("13170000");
            enderecoEmitente.setRua("Rua Industrial");
            enderecoEmitente.setNumero("500");
            enderecoEmitente.setBairro("Distrito Industrial");
            enderecoEmitente.setMunicipio("Sumaré");
            enderecoEmitente.setUf(UF.SP);
            enderecoEmitente.setCodigoCidade("3552205");
            emitente.setEndereco(enderecoEmitente);
            
            nfe.setEmitente(emitente);
            
            // 3. Destinatário (Empresa compradora)
            Cliente destinatario = new Cliente();
            destinatario.setDocumento("44555666000177");
            destinatario.setNome("COMERCIAL XYZ LTDA");
            destinatario.setIndicadorIE(IndicadorIE.contribuinteICMS);
            destinatario.setInscricaoEstadual("444555666");
            
            Endereco enderecoDestinatario = new Endereco();
            enderecoDestinatario.setCep("20040020");
            enderecoDestinatario.setRua("Rua da Quitanda");
            enderecoDestinatario.setNumero("100");
            enderecoDestinatario.setBairro("Centro");
            enderecoDestinatario.setMunicipio("Rio de Janeiro");
            enderecoDestinatario.setUf(UF.RJ);
            enderecoDestinatario.setCodigoCidade("3304557");
            destinatario.setEndereco(enderecoDestinatario);
            
            nfe.setDestinatario(destinatario);
            
            // 4. Item com ICMS interestadual
            List<ItemNFe> itens = new ArrayList<>();
            
            ItemNFe item = new ItemNFe();
            item.setNItem(1);
            item.setCodigoProdutoServico("PROD-DIST-001");
            item.setDescricaoItem("Produto para Revenda");
            item.setNCM("12345678");
            item.setCFOP(6102); // Venda interestadual
            item.setUnidadeComercial("CX");
            item.setQuantidadeComercial(50.0);
            item.setValorUnitarioComercial(25.00);
            item.setValorTotalBruto(1250.00);
            item.setUnidadeTributavel("CX");
            item.setQuantidadeTributavel(50.0);
            item.setValorUnitarioTributavel(25.00);
            item.setIndicadorTotal("1");
            
            // ICMS regime normal CST 00
            item.configurarICMS("00", 12.0, 1250.00); // 12% alíquota interestadual
            
            // PIS e COFINS
            item.configurarPIS("01", 1.65, 1250.00); // 1,65%
            item.configurarCOFINS("01", 7.6, 1250.00); // 7,6%
            
            itens.add(item);
            nfe.setProdutos(itens);
            
            // 5. Pagamento empresarial
            List<Pagamento> pagamentos = new ArrayList<>();
            
            Pagamento pagamento = new Pagamento();
            pagamento.setFormaPagamento(FormaPagamento.boletoBancario);
            pagamento.setValorPagamento(1250.00);
            pagamentos.add(pagamento);
            
            nfe.setPagamentos(pagamentos);
            
            // 6. Transportador
            Transportador transportador = new Transportador();
            transportador.setModalidadeFrete(ModalidadeFrete.porContaDoRemetente);
            transportador.setCpfCnpjTransportador("99888777000166");
            transportador.setNomeTransportador("TRANSPORTES RÁPIDOS LTDA");
            transportador.setInscricaoEstadualTransportador("999888777");
            nfe.setTransportador(transportador);
            
            // 7. Informações da empresa
            InformacoesAdicionais info = new InformacoesAdicionais();
            info.setInformacoesComplementares("Pedido nº 12345 - Venda B2B");
            nfe.setInformacoesAdicionais(info);
            
            // 8. Gerar TX2
            String arquivoTX2 = nfe.gerarTX2();
            
            if (arquivoTX2 != null) {
                System.out.println("✅ NFe B2B gerada com sucesso!");
                System.out.println("🏢 Operação empresarial");
                System.out.println("💰 Valor: R$ 1.250,00");
                System.out.println("🚚 Com transportador");
            } else {
                System.out.println("❌ Erro ao gerar NFe B2B:");
                nfe.getErrosValidacao().forEach(System.out::println);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 📊 **Características do Exemplo B2B**
- ✅ Consumidor final = Não
- ✅ Destinatário pessoa jurídica (CNPJ)
- ✅ Ambos contribuintes ICMS
- ✅ ICMS regime normal (CST 00)
- ✅ PIS/COFINS com alíquotas
- ✅ Operação interestadual (SP → RJ)
- ✅ Transporte incluso

---

## 3️⃣ **NFC-e - Cupom Fiscal Eletrônico**

### 📋 **Cenário**
Lanchonete emitindo cupom fiscal para venda no balcão.

```java
public class ExemploNFCe {
    public static void main(String[] args) {
        try {
            // 1. Criar NFC-e
            NFe nfce = new NFe();
            nfce.setModeloDocumentoFiscal("65"); // NFC-e
            nfce.setTipoOperacao(TipoOperacao.saida);
            nfce.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);
            nfce.setFinalidadeOperacao(FinalidadeOperacao.normal);
            nfce.setNaturezaOperacaoDescricao("Venda ao consumidor");
            nfce.setConsumidorFinal(OperacaoComConsumidorFinal.sim);
            nfce.setIndicadorPresencaComprador(IndicadorPresencaComprador.operacaoPresencial);
            
            // 2. Emitente (Lanchonete)
            Cliente emitente = new Cliente();
            emitente.setDocumento("12345678000195");
            emitente.setNome("LANCHONETE BOM SABOR LTDA");
            emitente.setNomeFantasia("Lanchonete Bom Sabor");
            emitente.setRegimeTributario(RegimeTributario.simplesNacional);
            emitente.setIndicadorIE(IndicadorIE.contribuinteICMS);
            emitente.setInscricaoEstadual("123456789");
            
            Endereco enderecoEmitente = new Endereco();
            enderecoEmitente.setCep("13170000");
            enderecoEmitente.setRua("Rua do Comércio");
            enderecoEmitente.setNumero("123");
            enderecoEmitente.setBairro("Centro");
            enderecoEmitente.setMunicipio("Sumaré");
            enderecoEmitente.setUf(UF.SP);
            enderecoEmitente.setCodigoCidade("3552205");
            emitente.setEndereco(enderecoEmitente);
            
            nfce.setEmitente(emitente);
            
            // 3. Destinatário não identificado (consumidor)
            Cliente destinatario = new Cliente();
            destinatario.setNome("CONSUMIDOR NAO IDENTIFICADO");
            destinatario.setIndicadorIE(IndicadorIE.naoContribuinte);
            // Para NFC-e, pode não ter endereço completo
            nfce.setDestinatario(destinatario);
            
            // 4. Itens da lanchonete
            List<ItemNFe> itens = new ArrayList<>();
            
            // Item 1 - Lanche
            ItemNFe item1 = new ItemNFe();
            item1.setNItem(1);
            item1.setCodigoProdutoServico("LANCHE-001");
            item1.setDescricaoItem("X-Burger com Batata");
            item1.setNCM("21069090"); // Preparações alimentícias
            item1.setCFOP(5102);
            item1.setUnidadeComercial("UN");
            item1.setQuantidadeComercial(1.0);
            item1.setValorUnitarioComercial(15.90);
            item1.setValorTotalBruto(15.90);
            item1.setUnidadeTributavel("UN");
            item1.setQuantidadeTributavel(1.0);
            item1.setValorUnitarioTributavel(15.90);
            item1.setIndicadorTotal("1");
            
            // ICMS Simples Nacional
            item1.configurarICMSSimplesNacional("102", 0.0);
            
            itens.add(item1);
            
            // Item 2 - Bebida
            ItemNFe item2 = new ItemNFe();
            item2.setNItem(2);
            item2.setCodigoProdutoServico("BEBIDA-001");
            item2.setDescricaoItem("Refrigerante Lata 350ml");
            item2.setNCM("22021000");
            item2.setCFOP(5102);
            item2.setUnidadeComercial("UN");
            item2.setQuantidadeComercial(2.0);
            item2.setValorUnitarioComercial(4.50);
            item2.setValorTotalBruto(9.00);
            item2.setUnidadeTributavel("UN");
            item2.setQuantidadeTributavel(2.0);
            item2.setValorUnitarioTributavel(4.50);
            item2.setIndicadorTotal("1");
            
            // ICMS Simples Nacional
            item2.configurarICMSSimplesNacional("102", 0.0);
            
            itens.add(item2);
            
            nfce.setProdutos(itens);
            
            // 5. Pagamento à vista
            List<Pagamento> pagamentos = new ArrayList<>();
            
            Pagamento pagamento = new Pagamento();
            pagamento.setFormaPagamento(FormaPagamento.dinheiro);
            pagamento.setValorPagamento(24.90);
            pagamentos.add(pagamento);
            
            nfce.setPagamentos(pagamentos);
            
            // 6. Informações do cupom
            InformacoesAdicionais info = new InformacoesAdicionais();
            info.setInformacoesComplementares("Obrigado pela preferência!");
            nfce.setInformacoesAdicionais(info);
            
            // 7. Gerar TX2
            String arquivoTX2 = nfce.gerarTX2();
            
            if (arquivoTX2 != null) {
                System.out.println("✅ NFC-e gerada com sucesso!");
                System.out.println("🍔 Cupom de lanchonete");
                System.out.println("💰 Total: R$ 24,90");
                System.out.println("📱 Pronta para imprimir");
            } else {
                System.out.println("❌ Erro ao gerar NFC-e:");
                nfce.getErrosValidacao().forEach(System.out::println);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 📊 **Características do Exemplo NFC-e**
- ✅ Modelo = 65 (NFC-e)
- ✅ Consumidor não identificado
- ✅ Operação presencial
- ✅ Itens típicos de lanchonete
- ✅ Pagamento à vista
- ✅ Simples Nacional

---

## 4️⃣ **Simples Nacional - Varejo**

### 📋 **Cenário**
Loja enquadrada no Simples Nacional com diferentes CSOSNs.

```java
public class ExemploSimplesNacional {
    public static void main(String[] args) {
        try {
            // 1. NFe Simples Nacional
            NFe nfe = new NFe();
            nfe.setTipoOperacao(TipoOperacao.saida);
            nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);
            nfe.setFinalidadeOperacao(FinalidadeOperacao.normal);
            nfe.setNaturezaOperacaoDescricao("Venda de mercadorias - Simples Nacional");
            nfe.setConsumidorFinal(OperacaoComConsumidorFinal.sim);
            
            // 2. Emitente Simples Nacional
            Cliente emitente = new Cliente();
            emitente.setDocumento("12345678000195");
            emitente.setNome("VAREJO POPULAR LTDA");
            emitente.setRegimeTributario(RegimeTributario.simplesNacional);
            emitente.setOptanteSimplesNacional(OptanteSimplesNacional.optante);
            emitente.setIndicadorIE(IndicadorIE.contribuinteICMS);
            emitente.setInscricaoEstadual("123456789");
            
            Endereco enderecoEmitente = new Endereco();
            enderecoEmitente.setCep("13170000");
            enderecoEmitente.setRua("Rua do Varejo");
            enderecoEmitente.setNumero("200");
            enderecoEmitente.setBairro("Centro");
            enderecoEmitente.setMunicipio("Sumaré");
            enderecoEmitente.setUf(UF.SP);
            enderecoEmitente.setCodigoCidade("3552205");
            emitente.setEndereco(enderecoEmitente);
            
            nfe.setEmitente(emitente);
            
            // 3. Destinatário
            Cliente destinatario = new Cliente();
            destinatario.setDocumento("12345678901");
            destinatario.setNome("Cliente Simples");
            destinatario.setIndicadorIE(IndicadorIE.naoContribuinte);
            
            Endereco enderecoDestinatario = new Endereco();
            enderecoDestinatario.setCep("13170001");
            enderecoDestinatario.setRua("Rua do Cliente");
            enderecoDestinatario.setNumero("100");
            enderecoDestinatario.setBairro("Residencial");
            enderecoDestinatario.setMunicipio("Sumaré");
            enderecoDestinatario.setUf(UF.SP);
            enderecoDestinatario.setCodigoCidade("3552205");
            destinatario.setEndereco(enderecoDestinatario);
            
            nfe.setDestinatario(destinatario);
            
            // 4. Itens com diferentes CSOSNs
            List<ItemNFe> itens = new ArrayList<>();
            
            // Item 1 - CSOSN 101 (com permissão de crédito)
            ItemNFe item1 = new ItemNFe();
            item1.setNItem(1);
            item1.setCodigoProdutoServico("PROD-SN-001");
            item1.setDescricaoItem("Produto com Crédito SN");
            item1.setNCM("12345678");
            item1.setCFOP(5102);
            item1.setUnidadeComercial("UN");
            item1.setQuantidadeComercial(1.0);
            item1.setValorUnitarioComercial(100.00);
            item1.setValorTotalBruto(100.00);
            item1.setUnidadeTributavel("UN");
            item1.setQuantidadeTributavel(1.0);
            item1.setValorUnitarioTributavel(100.00);
            item1.setIndicadorTotal("1");
            
            // CSOSN 101 - Tributada com permissão de crédito
            item1.configurarICMSSimplesNacional("101", 2.5); // 2,5% crédito
            
            itens.add(item1);
            
            // Item 2 - CSOSN 102 (sem permissão de crédito)
            ItemNFe item2 = new ItemNFe();
            item2.setNItem(2);
            item2.setCodigoProdutoServico("PROD-SN-002");
            item2.setDescricaoItem("Produto sem Crédito SN");
            item2.setNCM("87654321");
            item2.setCFOP(5102);
            item2.setUnidadeComercial("UN");
            item2.setQuantidadeComercial(2.0);
            item2.setValorUnitarioComercial(50.00);
            item2.setValorTotalBruto(100.00);
            item2.setUnidadeTributavel("UN");
            item2.setQuantidadeTributavel(2.0);
            item2.setValorUnitarioTributavel(50.00);
            item2.setIndicadorTotal("1");
            
            // CSOSN 102 - Sem permissão de crédito
            item2.configurarICMSSimplesNacional("102", 0.0);
            
            itens.add(item2);
            
            // Item 3 - CSOSN 500 (ICMS ST)
            ItemNFe item3 = new ItemNFe();
            item3.setNItem(3);
            item3.setCodigoProdutoServico("PROD-ST-001");
            item3.setDescricaoItem("Produto com Substituição Tributária");
            item3.setNCM("11111111");
            item3.setCFOP(5102);
            item3.setUnidadeComercial("UN");
            item3.setQuantidadeComercial(1.0);
            item3.setValorUnitarioComercial(75.00);
            item3.setValorTotalBruto(75.00);
            item3.setUnidadeTributavel("UN");
            item3.setQuantidadeTributavel(1.0);
            item3.setValorUnitarioTributavel(75.00);
            item3.setIndicadorTotal("1");
            
            // CSOSN 500 - ICMS cobrado por ST
            item3.configurarICMSSimplesNacional("500", 0.0);
            
            itens.add(item3);
            
            nfe.setProdutos(itens);
            
            // 5. Pagamento
            List<Pagamento> pagamentos = new ArrayList<>();
            
            Pagamento pagamento = new Pagamento();
            pagamento.setFormaPagamento(FormaPagamento.cartaoCredito);
            pagamento.setValorPagamento(275.00);
            pagamentos.add(pagamento);
            
            nfe.setPagamentos(pagamentos);
            
            // 6. Informações do Simples Nacional
            InformacoesAdicionais info = new InformacoesAdicionais();
            info.setInformacoesComplementares(
                "Empresa optante pelo Simples Nacional\n" +
                "Diferentes CSOSNs conforme legislação"
            );
            nfe.setInformacoesAdicionais(info);
            
            // 7. Gerar TX2
            String arquivoTX2 = nfe.gerarTX2();
            
            if (arquivoTX2 != null) {
                System.out.println("✅ NFe Simples Nacional gerada!");
                System.out.println("🏪 Varejo com CSOSNs variados");
                System.out.println("💰 Total: R$ 275,00");
                System.out.println("📋 CSOSNs: 101, 102, 500");
            } else {
                System.out.println("❌ Erro ao gerar NFe:");
                nfe.getErrosValidacao().forEach(System.out::println);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 📊 **Características do Simples Nacional**
- ✅ CRT = 1 (Simples Nacional)
- ✅ CSOSN 101: Com crédito 2,5%
- ✅ CSOSN 102: Sem crédito
- ✅ CSOSN 500: Substituição tributária
- ✅ Optante pelo Simples Nacional

---

## 5️⃣ **Regime Normal - Indústria**

### 📋 **Cenário**
Indústria no regime normal com ICMS, IPI, PIS/COFINS.

```java
public class ExemploRegimeNormal {
    public static void main(String[] args) {
        try {
            // 1. NFe Regime Normal
            NFe nfe = new NFe();
            nfe.setTipoOperacao(TipoOperacao.saida);
            nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);
            nfe.setFinalidadeOperacao(FinalidadeOperacao.normal);
            nfe.setNaturezaOperacaoDescricao("Venda de produtos industrializados");
            nfe.setConsumidorFinal(OperacaoComConsumidorFinal.nao);
            
            // 2. Emitente Industrial
            Cliente emitente = new Cliente();
            emitente.setDocumento("11222333000144");
            emitente.setNome("INDUSTRIA EXEMPLO S.A.");
            emitente.setNomeFantasia("Indústria Exemplo");
            emitente.setRegimeTributario(RegimeTributario.regimeNormal);
            emitente.setIndicadorIE(IndicadorIE.contribuinteICMS);
            emitente.setInscricaoEstadual("111222333");
            
            Endereco enderecoEmitente = new Endereco();
            enderecoEmitente.setCep("13170000");
            enderecoEmitente.setRua("Rodovia Industrial");
            enderecoEmitente.setNumero("1000");
            enderecoEmitente.setBairro("Distrito Industrial");
            enderecoEmitente.setMunicipio("Sumaré");
            enderecoEmitente.setUf(UF.SP);
            enderecoEmitente.setCodigoCidade("3552205");
            emitente.setEndereco(enderecoEmitente);
            
            nfe.setEmitente(emitente);
            
            // 3. Destinatário Distribuidor
            Cliente destinatario = new Cliente();
            destinatario.setDocumento("44555666000177");
            destinatario.setNome("DISTRIBUIDORA PARCEIRA LTDA");
            destinatario.setIndicadorIE(IndicadorIE.contribuinteICMS);
            destinatario.setInscricaoEstadual("444555666");
            
            Endereco enderecoDestinatario = new Endereco();
            enderecoDestinatario.setCep("01310100");
            enderecoDestinatario.setRua("Avenida Comercial");
            enderecoDestinatario.setNumero("500");
            enderecoDestinatario.setBairro("Centro");
            enderecoDestinatario.setMunicipio("São Paulo");
            enderecoDestinatario.setUf(UF.SP);
            enderecoDestinatario.setCodigoCidade("3550308");
            destinatario.setEndereco(enderecoDestinatario);
            
            nfe.setDestinatario(destinatario);
            
            // 4. Produto industrializado
            List<ItemNFe> itens = new ArrayList<>();
            
            ItemNFe item = new ItemNFe();
            item.setNItem(1);
            item.setCodigoProdutoServico("PROD-IND-001");
            item.setDescricaoItem("Produto Industrializado XYZ");
            item.setNCM("84212300");
            item.setCFOP(5102); // Venda dentro do estado
            item.setUnidadeComercial("UN");
            item.setQuantidadeComercial(100.0);
            item.setValorUnitarioComercial(50.00);
            item.setValorTotalBruto(5000.00);
            item.setUnidadeTributavel("UN");
            item.setQuantidadeTributavel(100.0);
            item.setValorUnitarioTributavel(50.00);
            item.setIndicadorTotal("1");
            
            // ICMS CST 00 - Tributado integralmente
            item.configurarICMS("00", 18.0, 5000.00); // 18% ICMS SP
            
            // IPI - Produto industrializado
            item.configurarIPI("50", 10.0, 5000.00); // 10% IPI
            
            // PIS/COFINS - Regime normal
            item.configurarPIS("01", 1.65, 5000.00); // 1,65% PIS
            item.configurarCOFINS("01", 7.6, 5000.00); // 7,6% COFINS
            
            itens.add(item);
            
            nfe.setProdutos(itens);
            
            // 5. Pagamento a prazo
            List<Pagamento> pagamentos = new ArrayList<>();
            
            Pagamento pagamento = new Pagamento();
            pagamento.setFormaPagamento(FormaPagamento.outros);
            pagamento.setValorPagamento(5000.00);
            pagamentos.add(pagamento);
            
            nfe.setPagamentos(pagamentos);
            
            // 6. Cobrança (fatura + parcelas)
            Fatura fatura = new Fatura();
            fatura.setNumeroFatura("FAT-001/2025");
            fatura.setValorOriginalFatura(5000.00);
            nfe.setFatura(fatura);
            
            List<Parcela> parcelas = new ArrayList<>();
            
            // 3 parcelas mensais
            for (int i = 1; i <= 3; i++) {
                Parcela parcela = new Parcela();
                parcela.setNumeroParcela("00" + i);
                parcela.setDataVencimento("2025-0" + (i + 1) + "-15");
                parcela.setValorParcela(1666.67);
                parcelas.add(parcela);
            }
            
            nfe.setParcelas(parcelas);
            
            // 7. Transporte
            Transportador transportador = new Transportador();
            transportador.setModalidadeFrete(ModalidadeFrete.porContaDoRemetente);
            transportador.setCpfCnpjTransportador("99888777000166");
            transportador.setNomeTransportador("LOGÍSTICA INDUSTRIAL LTDA");
            nfe.setTransportador(transportador);
            
            // 8. Informações industriais
            InformacoesAdicionais info = new InformacoesAdicionais();
            info.setInformacoesComplementares(
                "Venda de produtos industrializados\n" +
                "Regime Normal - ICMS 18%, IPI 10%\n" +
                "PIS 1,65% e COFINS 7,6%\n" +
                "Pagamento: 3x sem juros"
            );
            nfe.setInformacoesAdicionais(info);
            
            // 9. Gerar TX2
            String arquivoTX2 = nfe.gerarTX2();
            
            if (arquivoTX2 != null) {
                System.out.println("✅ NFe Industrial gerada!");
                System.out.println("🏭 Regime Normal com todos os impostos");
                System.out.println("💰 Total: R$ 5.000,00");
                System.out.println("📊 ICMS: R$ 900,00 (18%)");
                System.out.println("📊 IPI: R$ 500,00 (10%)");
                System.out.println("📊 PIS: R$ 82,50 (1,65%)");
                System.out.println("📊 COFINS: R$ 380,00 (7,6%)");
                System.out.println("💳 Parcelado em 3x");
            } else {
                System.out.println("❌ Erro ao gerar NFe Industrial:");
                nfe.getErrosValidacao().forEach(System.out::println);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 📊 **Características do Regime Normal**
- ✅ CRT = 2 (Regime Normal)
- ✅ ICMS CST 00 - 18%
- ✅ IPI - 10%
- ✅ PIS - 1,65%
- ✅ COFINS - 7,6%
- ✅ Faturamento parcelado
- ✅ Transporte incluso

---

## 6️⃣ **Prestação de Serviços (ISSQN)**

### 📋 **Cenário**
Empresa de consultoria prestando serviços com ISSQN.

```java
public class ExemploServicoISSQN {
    public static void main(String[] args) {
        try {
            // 1. NFe de Serviço
            NFe nfe = new NFe();
            nfe.setTipoOperacao(TipoOperacao.saida);
            nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);
            nfe.setFinalidadeOperacao(FinalidadeOperacao.normal);
            nfe.setNaturezaOperacaoDescricao("Prestação de serviços de consultoria");
            nfe.setConsumidorFinal(OperacaoComConsumidorFinal.nao);
            
            // 2. Prestador de Serviço
            Cliente emitente = new Cliente();
            emitente.setDocumento("12345678000195");
            emitente.setNome("CONSULTORIA ESTRATÉGICA LTDA");
            emitente.setNomeFantasia("CE Consultoria");
            emitente.setRegimeTributario(RegimeTributario.regimeNormal);
            emitente.setIndicadorIE(IndicadorIE.contribuinteICMS);
            emitente.setInscricaoEstadual("123456789");
            emitente.setInscricaoMunicipal("87654321");
            
            Endereco enderecoEmitente = new Endereco();
            enderecoEmitente.setCep("13170000");
            enderecoEmitente.setRua("Rua dos Consultores");
            enderecoEmitente.setNumero("100");
            enderecoEmitente.setBairro("Centro Empresarial");
            enderecoEmitente.setMunicipio("Sumaré");
            enderecoEmitente.setUf(UF.SP);
            enderecoEmitente.setCodigoCidade("3552205");
            emitente.setEndereco(enderecoEmitente);
            
            nfe.setEmitente(emitente);
            
            // 3. Tomador do Serviço
            Cliente destinatario = new Cliente();
            destinatario.setDocumento("11222333000144");
            destinatario.setNome("EMPRESA CLIENTE LTDA");
            destinatario.setIndicadorIE(IndicadorIE.contribuinteICMS);
            destinatario.setInscricaoEstadual("111222333");
            
            Endereco enderecoDestinatario = new Endereco();
            enderecoDestinatario.setCep("01310100");
            enderecoDestinatario.setRua("Avenida Empresarial");
            enderecoDestinatario.setNumero("200");
            enderecoDestinatario.setBairro("Centro");
            enderecoDestinatario.setMunicipio("São Paulo");
            enderecoDestinatario.setUf(UF.SP);
            enderecoDestinatario.setCodigoCidade("3550308");
            destinatario.setEndereco(enderecoDestinatario);
            
            nfe.setDestinatario(destinatario);
            
            // 4. Item de Serviço
            List<ItemNFe> itens = new ArrayList<>();
            
            ItemNFe item = new ItemNFe();
            item.setNItem(1);
            item.setCodigoProdutoServico("SERV-CONS-001");
            item.setDescricaoItem("Consultoria Estratégica - 40 horas");
            item.setNCM("00000000"); // Serviços usam NCM 00000000
            item.setCFOP(5933); // Prestação de serviços sujeitos ao ISSQN
            item.setUnidadeComercial("HR");
            item.setQuantidadeComercial(40.0);
            item.setValorUnitarioComercial(150.00);
            item.setValorTotalBruto(6000.00);
            item.setUnidadeTributavel("HR");
            item.setQuantidadeTributavel(40.0);
            item.setValorUnitarioTributavel(150.00);
            item.setIndicadorTotal("1");
            
            // ISSQN - 5% sobre serviços
            item.configurarISSQN(5.0, "1401", "3552205"); // 5% ISSQN, código serviço 14.01
            
            // PIS/COFINS sobre serviços
            item.configurarPIS("01", 1.65, 6000.00);
            item.configurarCOFINS("01", 7.6, 6000.00);
            
            // ICMS não se aplica a serviços de ISSQN
            item.configurarICMS("40", 0.0, 0.0); // CST 40 - Não tributado
            
            itens.add(item);
            
            nfe.setProdutos(itens);
            
            // 5. Pagamento
            List<Pagamento> pagamentos = new ArrayList<>();
            
            Pagamento pagamento = new Pagamento();
            pagamento.setFormaPagamento(FormaPagamento.boletoBancario);
            pagamento.setValorPagamento(6000.00);
            pagamentos.add(pagamento);
            
            nfe.setPagamentos(pagamentos);
            
            // 6. Informações do serviço
            InformacoesAdicionais info = new InformacoesAdicionais();
            info.setInformacoesComplementares(
                "Prestação de serviços de consultoria estratégica\n" +
                "ISSQN 5% - Município de Sumaré/SP\n" +
                "Código de serviço: 14.01 - Consultoria\n" +
                "Período: Janeiro/2025 - 40 horas"
            );
            nfe.setInformacoesAdicionais(info);
            
            // 7. Responsável técnico (obrigatório para alguns serviços)
            ResponsavelTecnico resp = new ResponsavelTecnico();
            resp.setCnpjResponsavel("12345678000195");
            resp.setNomeContato("João da Silva");
            resp.setEmailContato("joao@ceconsultoria.com.br");
            resp.setTelefoneContato("1912345678");
            nfe.setResponsavelTecnico(resp);
            
            // 8. Gerar TX2
            String arquivoTX2 = nfe.gerarTX2();
            
            if (arquivoTX2 != null) {
                System.out.println("✅ NFe de Serviços gerada!");
                System.out.println("🛠️ Consultoria com ISSQN");
                System.out.println("💰 Valor: R$ 6.000,00");
                System.out.println("📊 ISSQN 5%: R$ 300,00");
                System.out.println("📊 PIS: R$ 99,00");
                System.out.println("📊 COFINS: R$ 456,00");
                System.out.println("👨‍💼 Com responsável técnico");
            } else {
                System.out.println("❌ Erro ao gerar NFe de Serviços:");
                nfe.getErrosValidacao().forEach(System.out::println);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 📊 **Características dos Serviços**
- ✅ NCM = 00000000 (serviços)
- ✅ CFOP 5933 (serviços ISSQN)
- ✅ ISSQN 5% do valor
- ✅ ICMS CST 40 (não tributado)
- ✅ PIS/COFINS normais
- ✅ Código de serviço LC 116/2003
- ✅ Responsável técnico

---

## 7️⃣ **NFe com Transporte**

### 📋 **Cenário**
Venda com transporte detalhado, volumes e rastreamento.

```java
public class ExemploNFeTransporte {
    public static void main(String[] args) {
        try {
            // 1. NFe com transporte completo
            NFe nfe = new NFe();
            nfe.setTipoOperacao(TipoOperacao.saida);
            nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);
            nfe.setFinalidadeOperacao(FinalidadeOperacao.normal);
            nfe.setNaturezaOperacaoDescricao("Venda com frete");
            nfe.setConsumidorFinal(OperacaoComConsumidorFinal.nao);
            
            // 2. Emitente
            Cliente emitente = criarEmitentePadrao();
            nfe.setEmitente(emitente);
            
            // 3. Destinatário em outro estado
            Cliente destinatario = new Cliente();
            destinatario.setDocumento("44555666000177");
            destinatario.setNome("CLIENTE INTERESTADUAL LTDA");
            destinatario.setIndicadorIE(IndicadorIE.contribuinteICMS);
            destinatario.setInscricaoEstadual("444555666");
            
            Endereco enderecoDestinatario = new Endereco();
            enderecoDestinatario.setCep("20040020");
            enderecoDestinatario.setRua("Rua de Destino");
            enderecoDestinatario.setNumero("100");
            enderecoDestinatario.setBairro("Centro");
            enderecoDestinatario.setMunicipio("Rio de Janeiro");
            enderecoDestinatario.setUf(UF.RJ);
            enderecoDestinatario.setCodigoCidade("3304557");
            destinatario.setEndereco(enderecoDestinatario);
            
            nfe.setDestinatario(destinatario);
            
            // 4. Item com frete
            List<ItemNFe> itens = new ArrayList<>();
            
            ItemNFe item = new ItemNFe();
            item.setNItem(1);
            item.setCodigoProdutoServico("PROD-FRETE-001");
            item.setDescricaoItem("Produto com Frete Incluso");
            item.setNCM("12345678");
            item.setCFOP(6102); // Interestadual
            item.setUnidadeComercial("UN");
            item.setQuantidadeComercial(10.0);
            item.setValorUnitarioComercial(100.00);
            item.setValorTotalBruto(1000.00);
            item.setValorFrete(150.00); // Frete rateado no item
            item.setUnidadeTributavel("UN");
            item.setQuantidadeTributavel(10.0);
            item.setValorUnitarioTributavel(100.00);
            item.setIndicadorTotal("1");
            
            // ICMS interestadual
            item.configurarICMS("00", 12.0, 1000.00);
            
            itens.add(item);
            nfe.setProdutos(itens);
            
            // 5. Transportador completo
            Transportador transportador = new Transportador();
            transportador.setModalidadeFrete(ModalidadeFrete.porContaDoRemetente);
            transportador.setCpfCnpjTransportador("99888777000166");
            transportador.setNomeTransportador("TRANSPORTES BRASIL LTDA");
            transportador.setInscricaoEstadualTransportador("999888777");
            transportador.setEnderecoCompleto("Rua dos Transportes, 500");
            transportador.setNomeMunicipio("São Paulo");
            transportador.setUf(UF.SP);
            
            nfe.setTransportador(transportador);
            
            // 6. Veículo de transporte
            VeiculoTransporte veiculo = new VeiculoTransporte();
            veiculo.setPlaca("ABC1D23");
            veiculo.setUf(UF.SP);
            veiculo.setRntc("12345678");
            nfe.setVeiculoTransporte(veiculo);
            
            // 7. Volumes transportados
            List<Volume> volumes = new ArrayList<>();
            
            Volume volume1 = new Volume();
            volume1.setQuantidade(5);
            volume1.setEspecie("Caixas");
            volume1.setMarca("Marca ABC");
            volume1.setNumeracao("001-005");
            volume1.setPesoBruto(50.500);
            volume1.setPesoLiquido(45.000);
            volumes.add(volume1);
            
            Volume volume2 = new Volume();
            volume2.setQuantidade(3);
            volume2.setEspecie("Embalagens");
            volume2.setMarca("Marca XYZ");
            volume2.setNumeracao("006-008");
            volume2.setPesoBruto(30.250);
            volume2.setPesoLiquido(27.000);
            volumes.add(volume2);
            
            nfe.setVolumes(volumes);
            
            // 8. Totais incluindo frete
            TotaisNFe totais = new TotaisNFe();
            totais.setValorTotalProdutos(1000.00);
            totais.setValorTotalFrete(150.00);
            totais.setValorBaseCalculoICMS(1000.00);
            totais.setValorTotalICMS(120.00);
            totais.setValorTotalNF(1150.00);
            nfe.setTotais(totais);
            
            // 9. Pagamento
            List<Pagamento> pagamentos = new ArrayList<>();
            
            Pagamento pagamento = new Pagamento();
            pagamento.setFormaPagamento(FormaPagamento.boletoBancario);
            pagamento.setValorPagamento(1150.00);
            pagamentos.add(pagamento);
            
            nfe.setPagamentos(pagamentos);
            
            // 10. Informações de transporte
            InformacoesAdicionais info = new InformacoesAdicionais();
            info.setInformacoesComplementares(
                "Transporte por conta do remetente\n" +
                "Frete: R$ 150,00\n" +
                "Volumes: 8 unidades\n" +
                "Peso total: 80,75 Kg\n" +
                "Transportadora: TRANSPORTES BRASIL LTDA\n" +
                "Veículo: ABC1D23 (SP)"
            );
            nfe.setInformacoesAdicionais(info);
            
            // 11. Gerar TX2
            String arquivoTX2 = nfe.gerarTX2();
            
            if (arquivoTX2 != null) {
                System.out.println("✅ NFe com Transporte gerada!");
                System.out.println("🚚 Frete por conta do remetente");
                System.out.println("💰 Produtos: R$ 1.000,00");
                System.out.println("💰 Frete: R$ 150,00");
                System.out.println("💰 Total: R$ 1.150,00");
                System.out.println("📦 8 volumes - 80,75 Kg");
                System.out.println("🚛 Veículo: ABC1D23");
            } else {
                System.out.println("❌ Erro ao gerar NFe com Transporte:");
                nfe.getErrosValidacao().forEach(System.out::println);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static Cliente criarEmitentePadrao() {
        Cliente emitente = new Cliente();
        emitente.setDocumento("12345678000195");
        emitente.setNome("EMPRESA REMETENTE LTDA");
        emitente.setRegimeTributario(RegimeTributario.regimeNormal);
        emitente.setIndicadorIE(IndicadorIE.contribuinteICMS);
        emitente.setInscricaoEstadual("123456789");
        
        Endereco endereco = new Endereco();
        endereco.setCep("13170000");
        endereco.setRua("Rua da Empresa");
        endereco.setNumero("100");
        endereco.setBairro("Centro");
        endereco.setMunicipio("Sumaré");
        endereco.setUf(UF.SP);
        endereco.setCodigoCidade("3552205");
        emitente.setEndereco(endereco);
        
        return emitente;
    }
}
```

### 📊 **Características do Transporte**
- ✅ Modalidade: Por conta do remetente
- ✅ Transportador com CNPJ/IE
- ✅ Veículo com placa e RNTC
- ✅ Volumes detalhados
- ✅ Peso bruto e líquido
- ✅ Frete incluso no total
- ✅ Operação interestadual

---

## 8️⃣ **NFe de Devolução**

### 📋 **Cenário**
Devolução de mercadorias com referência à NFe original.

```java
public class ExemploNFeDevolucao {
    public static void main(String[] args) {
        try {
            // 1. NFe de Devolução
            NFe nfe = new NFe();
            nfe.setTipoOperacao(TipoOperacao.entrada); // Entrada por devolução
            nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);
            nfe.setFinalidadeOperacao(FinalidadeOperacao.devolucao);
            nfe.setNaturezaOperacaoDescricao("Devolução de mercadorias");
            nfe.setConsumidorFinal(OperacaoComConsumidorFinal.nao);
            
            // 2. Emitente (quem está devolvendo - antes era destinatário)
            Cliente emitente = new Cliente();
            emitente.setDocumento("44555666000177");
            emitente.setNome("CLIENTE QUE DEVOLVE LTDA");
            emitente.setRegimeTributario(RegimeTributario.regimeNormal);
            emitente.setIndicadorIE(IndicadorIE.contribuinteICMS);
            emitente.setInscricaoEstadual("444555666");
            
            Endereco enderecoEmitente = new Endereco();
            enderecoEmitente.setCep("01310100");
            enderecoEmitente.setRua("Rua Cliente");
            enderecoEmitente.setNumero("200");
            enderecoEmitente.setBairro("Centro");
            enderecoEmitente.setMunicipio("São Paulo");
            enderecoEmitente.setUf(UF.SP);
            enderecoEmitente.setCodigoCidade("3550308");
            emitente.setEndereco(enderecoEmitente);
            
            nfe.setEmitente(emitente);
            
            // 3. Destinatário (quem recebe a devolução - vendedor original)
            Cliente destinatario = new Cliente();
            destinatario.setDocumento("12345678000195");
            destinatario.setNome("VENDEDOR ORIGINAL LTDA");
            destinatario.setIndicadorIE(IndicadorIE.contribuinteICMS);
            destinatario.setInscricaoEstadual("123456789");
            
            Endereco enderecoDestinatario = new Endereco();
            enderecoDestinatario.setCep("13170000");
            enderecoDestinatario.setRua("Rua da Empresa");
            enderecoDestinatario.setNumero("100");
            enderecoDestinatario.setBairro("Centro");
            enderecoDestinatario.setMunicipio("Sumaré");
            enderecoDestinatario.setUf(UF.SP);
            enderecoDestinatario.setCodigoCidade("3552205");
            destinatario.setEndereco(enderecoDestinatario);
            
            nfe.setDestinatario(destinatario);
            
            // 4. Documento referenciado (NFe original)
            List<DocumentoReferenciado> docsRef = new ArrayList<>();
            
            DocRefNFe docOriginal = new DocRefNFe();
            docOriginal.setChaveAcesso("35250112345678000195550010000012341234567890");
            docsRef.add(docOriginal);
            
            nfe.setDocumentosRefenciados(docsRef);
            
            // 5. Item devolvido
            List<ItemNFe> itens = new ArrayList<>();
            
            ItemNFe item = new ItemNFe();
            item.setNItem(1);
            item.setCodigoProdutoServico("PROD-DEVOL-001");
            item.setDescricaoItem("Produto Devolvido por Defeito");
            item.setNCM("12345678");
            item.setCFOP(1202); // Devolução entrada
            item.setUnidadeComercial("UN");
            item.setQuantidadeComercial(1.0);
            item.setValorUnitarioComercial(100.00);
            item.setValorTotalBruto(100.00);
            item.setUnidadeTributavel("UN");
            item.setQuantidadeTributavel(1.0);
            item.setValorUnitarioTributavel(100.00);
            item.setIndicadorTotal("1");
            
            // ICMS na devolução - mesmo CST da saída original
            item.configurarICMS("00", 18.0, 100.00);
            
            itens.add(item);
            nfe.setProdutos(itens);
            
            // 6. Pagamento - devolução geralmente sem pagamento
            List<Pagamento> pagamentos = new ArrayList<>();
            
            Pagamento pagamento = new Pagamento();
            pagamento.setFormaPagamento(FormaPagamento.semPagamento);
            pagamento.setValorPagamento(0.00);
            pagamentos.add(pagamento);
            
            nfe.setPagamentos(pagamentos);
            
            // 7. Informações da devolução
            InformacoesAdicionais info = new InformacoesAdicionais();
            info.setInformacoesComplementares(
                "DEVOLUÇÃO DE MERCADORIAS\n" +
                "NFe Original: 1234\n" +
                "Motivo: Produto com defeito\n" +
                "Autorização: DEV-001/2025\n" +
                "Estorno de valores conforme NFe referenciada"
            );
            nfe.setInformacoesAdicionais(info);
            
            // 8. Gerar TX2
            String arquivoTX2 = nfe.gerarTX2();
            
            if (arquivoTX2 != null) {
                System.out.println("✅ NFe de Devolução gerada!");
                System.out.println("🔄 Tipo: Entrada por devolução");
                System.out.println("📋 Finalidade: Devolução");
                System.out.println("🔗 Referencia NFe original");
                System.out.println("💰 Valor: R$ 100,00");
                System.out.println("❌ Motivo: Produto defeituoso");
            } else {
                System.out.println("❌ Erro ao gerar NFe de Devolução:");
                nfe.getErrosValidacao().forEach(System.out::println);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 📊 **Características da Devolução**
- ✅ Tipo = Entrada (quem devolve emite)
- ✅ Finalidade = Devolução
- ✅ CFOP 1202 (devolução entrada)
- ✅ Documento referenciado obrigatório
- ✅ Sem pagamento (estorno)
- ✅ Mesma tributação da operação original

---

## 9️⃣ **NFe de Exportação**

### 📋 **Cenário**
Exportação para o exterior com drawback e registro de exportação.

```java
public class ExemploNFeExportacao {
    public static void main(String[] args) {
        try {
            // 1. NFe de Exportação
            NFe nfe = new NFe();
            nfe.setTipoOperacao(TipoOperacao.saida);
            nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);
            nfe.setFinalidadeOperacao(FinalidadeOperacao.normal);
            nfe.setNaturezaOperacaoDescricao("Exportação de produtos");
            nfe.setIndicadorLocalDestino(IndicadorLocalDestinoOperacao.exterior);
            nfe.setConsumidorFinal(OperacaoComConsumidorFinal.nao);
            
            // 2. Exportador
            Cliente emitente = new Cliente();
            emitente.setDocumento("12345678000195");
            emitente.setNome("EXPORTADORA BRASIL LTDA");
            emitente.setRegimeTributario(RegimeTributario.regimeNormal);
            emitente.setIndicadorIE(IndicadorIE.contribuinteICMS);
            emitente.setInscricaoEstadual("123456789");
            
            Endereco enderecoEmitente = new Endereco();
            enderecoEmitente.setCep("13170000");
            enderecoEmitente.setRua("Porto Industrial");
            enderecoEmitente.setNumero("1000");
            enderecoEmitente.setBairro("Zona Portuária");
            enderecoEmitente.setMunicipio("Santos");
            enderecoEmitente.setUf(UF.SP);
            enderecoEmitente.setCodigoCidade("3548500");
            emitente.setEndereco(enderecoEmitente);
            
            nfe.setEmitente(emitente);
            
            // 3. Destinatário no Exterior
            Cliente destinatario = new Cliente();
            destinatario.setNome("INTERNATIONAL COMPANY LLC");
            destinatario.setIndicadorIE(IndicadorIE.naoContribuinte);
            // Para exterior, não é obrigatório CPF/CNPJ brasileiro
            
            Endereco enderecoDestinatario = new Endereco();
            enderecoDestinatario.setRua("123 International Street");
            enderecoDestinatario.setBairro("Business District");
            enderecoDestinatario.setMunicipio("New York");
            enderecoDestinatario.setUf(UF.EX); // Exterior
            enderecoDestinatario.setCodigoCidade("9999999"); // Código para exterior
            enderecoDestinatario.setCep("00000000");
            destinatario.setEndereco(enderecoDestinatario);
            
            nfe.setDestinatario(destinatario);
            
            // 4. Produto para exportação
            List<ItemNFe> itens = new ArrayList<>();
            
            ItemNFe item = new ItemNFe();
            item.setNItem(1);
            item.setCodigoProdutoServico("EXPORT-001");
            item.setDescricaoItem("Produto Brasileiro para Exportação");
            item.setNCM("12345678");
            item.setCFOP(7101); // Venda para o exterior
            item.setUnidadeComercial("UN");
            item.setQuantidadeComercial(1000.0);
            item.setValorUnitarioComercial(5.00);
            item.setValorTotalBruto(5000.00);
            item.setUnidadeTributavel("UN");
            item.setQuantidadeTributavel(1000.0);
            item.setValorUnitarioTributavel(5.00);
            item.setIndicadorTotal("1");
            
            // Dados de exportação
            item.setNumeroRegistroExportacao("RE123456789");
            item.setNumeroAtoConcessorioDrawback("DRB987654321");
            
            // ICMS para exportação - isento
            item.configurarICMS("41", 0.0, 0.0); // CST 41 - Não tributado
            
            // PIS/COFINS - exportação isenta
            item.configurarPIS("06", 0.0, 0.0); // CST 06 - Alíquota zero
            item.configurarCOFINS("06", 0.0, 0.0); // CST 06 - Alíquota zero
            
            itens.add(item);
            nfe.setProdutos(itens);
            
            // 5. Pagamento em moeda estrangeira
            List<Pagamento> pagamentos = new ArrayList<>();
            
            Pagamento pagamento = new Pagamento();
            pagamento.setFormaPagamento(FormaPagamento.outros);
            pagamento.setValorPagamento(5000.00); // Valor convertido em reais
            pagamentos.add(pagamento);
            
            nfe.setPagamentos(pagamentos);
            
            // 6. Informações de exportação
            Exportacao exportacao = new Exportacao();
            exportacao.setUfSaida(UF.SP);
            exportacao.setLocalExportacao("Porto de Santos - SP");
            exportacao.setLocalDespacho("Receita Federal - Santos");
            nfe.setExportacao(exportacao);
            
            // 7. Transporte internacional
            Transportador transportador = new Transportador();
            transportador.setModalidadeFrete(ModalidadeFrete.semTransporte);
            nfe.setTransportador(transportador);
            
            // 8. Informações da exportação
            InformacoesAdicionais info = new InformacoesAdicionais();
            info.setInformacoesComplementares(
                "EXPORTAÇÃO PARA O EXTERIOR\n" +
                "Destinatário: INTERNATIONAL COMPANY LLC\n" +
                "País de destino: Estados Unidos\n" +
                "Porto de saída: Santos/SP\n" +
                "Registro de Exportação: RE123456789\n" +
                "Drawback: DRB987654321\n" +
                "Valor FOB: USD 1,000.00\n" +
                "Taxa de câmbio: R$ 5,00\n" +
                "ICMS, PIS e COFINS: Isentos (exportação)"
            );
            nfe.setInformacoesAdicionais(info);
            
            // 9. Gerar TX2
            String arquivoTX2 = nfe.gerarTX2();
            
            if (arquivoTX2 != null) {
                System.out.println("✅ NFe de Exportação gerada!");
                System.out.println("🌍 Destino: Exterior (EUA)");
                System.out.println("🚢 Porto: Santos/SP");
                System.out.println("💰 Valor: R$ 5.000,00 (USD 1,000.00)");
                System.out.println("📋 RE: RE123456789");
                System.out.println("📋 Drawback: DRB987654321");
                System.out.println("💸 Impostos: Isentos");
            } else {
                System.out.println("❌ Erro ao gerar NFe de Exportação:");
                nfe.getErrosValidacao().forEach(System.out::println);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 📊 **Características da Exportação**
- ✅ Local destino = Exterior
- ✅ UF = EX (Exterior)
- ✅ CFOP 7101 (exportação)
- ✅ ICMS CST 41 (não tributado)
- ✅ PIS/COFINS CST 06 (alíquota zero)
- ✅ Registro de Exportação (RE)
- ✅ Drawback
- ✅ Informações do porto de saída

---

## 🔟 **NFe em Contingência**

### 📋 **Cenário**
NFe emitida em contingência por problemas de conectividade.

```java
public class ExemploNFeContingencia {
    public static void main(String[] args) {
        try {
            // 1. NFe em Contingência
            NFe nfe = new NFe();
            nfe.setTipoOperacao(TipoOperacao.saida);
            nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);
            nfe.setFinalidadeOperacao(FinalidadeOperacao.normal);
            nfe.setNaturezaOperacaoDescricao("Venda em contingência");
            nfe.setConsumidorFinal(OperacaoComConsumidorFinal.sim);
            
            // Configurar contingência
            nfe.setTipoEmissao(TipoEmissao.contingenciaFSIA); // FS-IA
            nfe.setDataHoraContingencia("2025-01-20T14:30:00-03:00");
            nfe.setJustificativaContingencia("Problemas de conectividade com a internet impossibilitaram a transmissão normal da NFe");
            
            // 2. Emitente
            Cliente emitente = new Cliente();
            emitente.setDocumento("12345678000195");
            emitente.setNome("LOJA EM CONTINGENCIA LTDA");
            emitente.setRegimeTributario(RegimeTributario.simplesNacional);
            emitente.setIndicadorIE(IndicadorIE.contribuinteICMS);
            emitente.setInscricaoEstadual("123456789");
            
            Endereco enderecoEmitente = new Endereco();
            enderecoEmitente.setCep("13170000");
            enderecoEmitente.setRua("Rua da Contingência");
            enderecoEmitente.setNumero("100");
            enderecoEmitente.setBairro("Centro");
            enderecoEmitente.setMunicipio("Sumaré");
            enderecoEmitente.setUf(UF.SP);
            enderecoEmitente.setCodigoCidade("3552205");
            emitente.setEndereco(enderecoEmitente);
            
            nfe.setEmitente(emitente);
            
            // 3. Destinatário
            Cliente destinatario = new Cliente();
            destinatario.setDocumento("12345678901");
            destinatario.setNome("Cliente Contingência");
            destinatario.setIndicadorIE(IndicadorIE.naoContribuinte);
            
            Endereco enderecoDestinatario = new Endereco();
            enderecoDestinatario.setCep("13170001");
            enderecoDestinatario.setRua("Rua do Cliente");
            enderecoDestinatario.setNumero("200");
            enderecoDestinatario.setBairro("Residencial");
            enderecoDestinatario.setMunicipio("Sumaré");
            enderecoDestinatario.setUf(UF.SP);
            enderecoDestinatario.setCodigoCidade("3552205");
            destinatario.setEndereco(enderecoDestinatario);
            
            nfe.setDestinatario(destinatario);
            
            // 4. Item vendido em contingência
            List<ItemNFe> itens = new ArrayList<>();
            
            ItemNFe item = new ItemNFe();
            item.setNItem(1);
            item.setCodigoProdutoServico("CONT-001");
            item.setDescricaoItem("Produto Vendido em Contingência");
            item.setNCM("12345678");
            item.setCFOP(5102);
            item.setUnidadeComercial("UN");
            item.setQuantidadeComercial(1.0);
            item.setValorUnitarioComercial(50.00);
            item.setValorTotalBruto(50.00);
            item.setUnidadeTributavel("UN");
            item.setQuantidadeTributavel(1.0);
            item.setValorUnitarioTributavel(50.00);
            item.setIndicadorTotal("1");
            
            // ICMS Simples Nacional
            item.configurarICMSSimplesNacional("102", 0.0);
            
            itens.add(item);
            nfe.setProdutos(itens);
            
            // 5. Pagamento
            List<Pagamento> pagamentos = new ArrayList<>();
            
            Pagamento pagamento = new Pagamento();
            pagamento.setFormaPagamento(FormaPagamento.dinheiro);
            pagamento.setValorPagamento(50.00);
            pagamentos.add(pagamento);
            
            nfe.setPagamentos(pagamentos);
            
            // 6. Informações da contingência
            InformacoesAdicionais info = new InformacoesAdicionais();
            info.setInformacoesComplementares(
                "NFe EMITIDA EM CONTINGÊNCIA - FS-IA\n" +
                "Data/Hora entrada contingência: 20/01/2025 14:30:00\n" +
                "Motivo: Problemas de conectividade\n" +
                "Esta NFe deverá ser transmitida assim que possível\n" +
                "Regime de contingência conforme legislação vigente"
            );
            nfe.setInformacoesAdicionais(info);
            
            // 7. Responsável técnico (obrigatório em contingência)
            ResponsavelTecnico resp = new ResponsavelTecnico();
            resp.setCnpjResponsavel("12345678000195");
            resp.setNomeContato("Técnico Responsável");
            resp.setEmailContato("tecnico@loja.com.br");
            resp.setTelefoneContato("1912345678");
            nfe.setResponsavelTecnico(resp);
            
            // 8. Gerar TX2
            String arquivoTX2 = nfe.gerarTX2();
            
            if (arquivoTX2 != null) {
                System.out.println("✅ NFe em Contingência gerada!");
                System.out.println("🚨 Tipo emissão: FS-IA");
                System.out.println("⏰ Entrada contingência: 20/01/2025 14:30");
                System.out.println("❌ Motivo: Problemas conectividade");
                System.out.println("💰 Valor: R$ 50,00");
                System.out.println("⚠️ Transmitir assim que possível");
                
                // Verificar se há avisos específicos de contingência
                if (nfe.isContingencia()) {
                    System.out.println("📋 Justificativa válida: " + 
                        (nfe.getJustificativaContingencia().length() >= 15 ? "✅" : "❌"));
                }
            } else {
                System.out.println("❌ Erro ao gerar NFe em Contingência:");
                nfe.getErrosValidacao().forEach(System.out::println);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 📊 **Características da Contingência**
- ✅ Tipo emissão = FS-IA
- ✅ Data/hora entrada contingência obrigatória
- ✅ Justificativa mínimo 15 caracteres
- ✅ Responsável técnico obrigatório
- ✅ Transmissão posterior obrigatória
- ✅ Informações específicas na observação

---

## 📚 **Utilitários e Helpers**

### 🛠️ **Métodos Auxiliares Comuns**

```java
public class NFeUtils {
    
    /**
     * Cria cliente padrão para testes
     */
    public static Cliente criarClientePadrao(String documento, String nome) {
        Cliente cliente = new Cliente();
        cliente.setDocumento(documento);
        cliente.setNome(nome);
        cliente.setIndicadorIE(IndicadorIE.naoContribuinte);
        
        Endereco endereco = new Endereco();
        endereco.setCep("13170000");
        endereco.setRua("Rua Teste");
        endereco.setNumero("100");
        endereco.setBairro("Centro");
        endereco.setMunicipio("Sumaré");
        endereco.setUf(UF.SP);
        endereco.setCodigoCidade("3552205");
        cliente.setEndereco(endereco);
        
        return cliente;
    }
    
    /**
     * Cria item básico para testes
     */
    public static ItemNFe criarItemBasico(int numero, String codigo, String descricao, double valor) {
        ItemNFe item = new ItemNFe();
        item.setNItem(numero);
        item.setCodigoProdutoServico(codigo);
        item.setDescricaoItem(descricao);
        item.setNCM("12345678");
        item.setCFOP(5102);
        item.setUnidadeComercial("UN");
        item.setQuantidadeComercial(1.0);
        item.setValorUnitarioComercial(valor);
        item.setValorTotalBruto(valor);
        item.setUnidadeTributavel("UN");
        item.setQuantidadeTributavel(1.0);
        item.setValorUnitarioTributavel(valor);
        item.setIndicadorTotal("1");
        
        return item;
    }
    
    /**
     * Valida NFe e exibe erros
     */
    public static boolean validarEExibirErros(NFe nfe) {
        if (nfe.temErros()) {
            System.out.println("❌ Erros encontrados:");
            nfe.getErrosValidacao().forEach(erro -> 
                System.out.println("  • " + erro));
            return false;
        }
        return true;
    }
}
```

---

## 📋 **Checklist de Implementação**

### ✅ **Antes de Gerar NFe**

1. **Dados Básicos**
   - [ ] Tipo operação definido
   - [ ] Ambiente (produção/homologação)
   - [ ] Finalidade da operação
   - [ ] Natureza da operação

2. **Emitente**
   - [ ] CNPJ válido
   - [ ] Nome/Razão social
   - [ ] Endereço completo
   - [ ] Regime tributário
   - [ ] Inscrição estadual

3. **Destinatário**
   - [ ] CPF/CNPJ válido
   - [ ] Nome completo
   - [ ] Endereço (se obrigatório)
   - [ ] Indicador IE correto

4. **Itens**
   - [ ] Pelo menos 1 item
   - [ ] Códigos NCM válidos
   - [ ] CFOP correto
   - [ ] Valores balanceados
   - [ ] Tributos configurados

5. **Pagamentos**
   - [ ] Pelo menos 1 forma
   - [ ] Valor total = valor NFe
   - [ ] Tipo correto

6. **Validações**
   - [ ] Executar validação completa
   - [ ] Corrigir erros encontrados
   - [ ] Verificar consistência

---

## 🏭 **Guia Completo de Ambientes**

### 🔧 **Ambiente de Homologação (Desenvolvimento)**

**Quando usar:**
- ✅ Desenvolvimento e testes
- ✅ Validação de código
- ✅ Demonstrações
- ✅ Treinamento

**Configuração:**
```java
// Sempre use homologação para desenvolvimento
nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);

// Dados de teste são permitidos
emitente.setDocumento("99999999000191"); // CNPJ de teste
destinatario.setDocumento("11111111111"); // CPF de teste
```

**Características:**
- ✅ **CNPJ/CPF de teste** aceitos
- ✅ **Validações menos rigorosas**
- ✅ **Não gera NFe real** na SEFAZ
- ✅ **Ideal para desenvolvimento**

### 🚀 **Ambiente de Produção (Emissão Real)**

**Quando usar:**
- ⚠️ Apenas após homologação aprovada
- ⚠️ Emissão real de NFe
- ⚠️ Sistema em produção

**Configuração:**
```java
// Apenas após homologação aprovada
nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.producao);

// Dados reais obrigatórios
emitente.setDocumento("12345678000195"); // CNPJ real
destinatario.setDocumento("12345678901"); // CPF real
```

**Requisitos:**
- ⚠️ **Certificado digital** válido
- ⚠️ **CNPJ/CPF reais** (não de teste)
- ⚠️ **Homologação aprovada** na SEFAZ
- ⚠️ **Configuração correta** do NeverStop/TecnoSpeed

### 📋 **Checklist de Migração para Produção**

1. **Certificado Digital**
   - [ ] Instalado no sistema
   - [ ] Válido e não expirado
   - [ ] Configurado no NeverStop/TecnoSpeed

2. **Dados da Empresa**
   - [ ] CNPJ real (não de teste)
   - [ ] Inscrição estadual válida
   - [ ] Endereço completo e correto

3. **Configuração do Sistema**
   - [ ] NeverStop/TecnoSpeed configurado
   - [ ] Certificado digital selecionado
   - [ ] Ambiente de produção ativo

4. **Testes Obrigatórios**
   - [ ] Teste em homologação aprovado
   - [ ] Validações passando
   - [ ] TX2 gerado corretamente

5. **Migração**
   - [ ] Alterar código para `IdentificacaoAmbiente.producao`
   - [ ] Usar dados reais
   - [ ] Testar primeira NFe

### ⚠️ **Avisos Importantes**

**Nunca use produção para:**
- ❌ Desenvolvimento
- ❌ Testes
- ❌ Demonstrações
- ❌ Treinamento

**Sempre teste primeiro em:**
- ✅ Homologação
- ✅ Dados de teste
- ✅ Validações completas

---

**💡 Biblioteca completa com 10 cenários práticos implementados!**

Para mais informações:
- **[README.md](./README.md)** - Guia básico
- **[GUIA_CAMPOS_NFE.md](./GUIA_CAMPOS_NFE.md)** - Mapeamento completo
- **[GUIA_VALIDACOES.md](./GUIA_VALIDACOES.md)** - Sistema de validações 