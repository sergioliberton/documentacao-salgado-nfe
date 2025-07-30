# 🏭 GUIA COMPLETO DE AMBIENTES - Biblioteca NFe

Este documento explica como configurar e usar os ambientes de **homologação** e **produção** na biblioteca NFe "O Famoso NFe".

## 📋 **Índice**

1. [🔧 Ambiente de Homologação](#-ambiente-de-homologação)
2. [🚀 Ambiente de Produção](#-ambiente-de-produção)
3. [⚙️ Configuração de Ambientes](#️-configuração-de-ambientes)
4. [🔄 Migração entre Ambientes](#-migração-entre-ambientes)
5. [⚠️ Troubleshooting](#️-troubleshooting)
6. [📋 Checklist de Produção](#-checklist-de-produção)

---

## 🔧 **Ambiente de Homologação**

### 🎯 **Quando Usar**

- ✅ **Desenvolvimento** de código
- ✅ **Testes** de funcionalidades
- ✅ **Demonstrações** para clientes
- ✅ **Treinamento** de usuários
- ✅ **Validação** de regras de negócio

### ⚙️ **Configuração**

```java
import o.famoso.nfe.enums.IdentificacaoAmbiente;

// Configurar para homologação
NFe nfe = new NFe();
nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);
```

### 📝 **Dados Permitidos**

```java
// CNPJ de teste (aceito em homologação)
emitente.setDocumento("99999999000191");

// CPF de teste (aceito em homologação)
destinatario.setDocumento("11111111111");

// Outros dados de teste
emitente.setInscricaoEstadual("123456789");
```

### ✨ **Características**

- ✅ **CNPJ/CPF de teste** são aceitos
- ✅ **Validações menos rigorosas**
- ✅ **Não gera NFe real** na SEFAZ
- ✅ **Ideal para desenvolvimento**
- ✅ **Sem necessidade de certificado digital**

---

## 🚀 **Ambiente de Produção**

### 🎯 **Quando Usar**

- ⚠️ **Apenas após homologação aprovada**
- ⚠️ **Emissão real de NFe**
- ⚠️ **Sistema em produção**
- ⚠️ **Vendas reais**

### ⚙️ **Configuração**

```java
import o.famoso.nfe.enums.IdentificacaoAmbiente;

// Configurar para produção (APENAS após homologação)
NFe nfe = new NFe();
nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.producao);
```

### 📝 **Dados Obrigatórios**

```java
// CNPJ real (obrigatório em produção)
emitente.setDocumento("12345678000195");

// CPF/CNPJ real (obrigatório em produção)
destinatario.setDocumento("12345678901");

// Dados reais obrigatórios
emitente.setInscricaoEstadual("123456789");
```

### ⚠️ **Requisitos**

- ⚠️ **Certificado digital** válido
- ⚠️ **CNPJ/CPF reais** (não de teste)
- ⚠️ **Homologação aprovada** na SEFAZ
- ⚠️ **Configuração correta** do NeverStop/TecnoSpeed

---

## ⚙️ **Configuração de Ambientes**

### 🔧 **Exemplo Completo - Homologação**

```java
public class ExemploHomologacao {
    public static void main(String[] args) {
        try {
            // 1. Criar NFe para testes
            NFe nfe = new NFe();
            nfe.setTipoOperacao(TipoOperacao.saida);
            nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);
            nfe.setFinalidadeOperacao(FinalidadeOperacao.normal);
            nfe.setNaturezaOperacaoDescricao("Venda de produtos");
            
            // 2. Emitente com dados de teste
            Cliente emitente = new Cliente();
            emitente.setDocumento("99999999000191"); // CNPJ de teste
            emitente.setNome("EMPRESA TESTE LTDA");
            emitente.setRegimeTributario(RegimeTributario.simplesNacional);
            emitente.setIndicadorIE(IndicadorIE.contribuinteICMS);
            emitente.setInscricaoEstadual("123456789");
            
            // Endereço do emitente
            Endereco enderecoEmitente = new Endereco();
            enderecoEmitente.setCep("13170-000");
            enderecoEmitente.setRua("Rua de Teste");
            enderecoEmitente.setNumero("100");
            enderecoEmitente.setBairro("Centro");
            enderecoEmitente.setMunicipio("Sumaré");
            enderecoEmitente.setUf(UF.SP);
            enderecoEmitente.setCodigoCidade("3552205");
            emitente.setEndereco(enderecoEmitente);
            
            nfe.setEmitente(emitente);
            
            // 3. Destinatário com dados de teste
            Cliente destinatario = new Cliente();
            destinatario.setDocumento("11111111111"); // CPF de teste
            destinatario.setNome("João da Silva");
            destinatario.setIndicadorIE(IndicadorIE.naoContribuinte);
            
            Endereco enderecoDestinatario = new Endereco();
            enderecoDestinatario.setCep("01310-100");
            enderecoDestinatario.setRua("Avenida Paulista");
            enderecoDestinatario.setNumero("1000");
            enderecoDestinatario.setBairro("Bela Vista");
            enderecoDestinatario.setMunicipio("São Paulo");
            enderecoDestinatario.setUf(UF.SP);
            enderecoDestinatario.setCodigoCidade("3550308");
            destinatario.setEndereco(enderecoDestinatario);
            
            nfe.setDestinatario(destinatario);
            
            // 4. Item de teste
            ItemNFe item = new ItemNFe();
            item.setNItem(1);
            item.setCodigoProdutoServico("PROD001");
            item.setDescricaoItem("Produto de Teste");
            item.setNCM("12345678");
            item.setCFOP(5102);
            item.setUnidadeComercial("UN");
            item.setQuantidadeComercial(2.0);
            item.setValorUnitarioComercial(25.50);
            item.setValorTotalBruto(51.00);
            
            // Configurar ICMS Simples Nacional
            item.configurarICMSSimplesNacional("102", 0.0);
            
            List<ItemNFe> itens = new ArrayList<>();
            itens.add(item);
            nfe.setProdutos(itens);
            
            // 5. Pagamento
            Pagamento pagamento = new Pagamento();
            pagamento.setFormaPagamento(FormaPagamento.dinheiro);
            pagamento.setValorPagamento(51.00);
            
            List<Pagamento> pagamentos = new ArrayList<>();
            pagamentos.add(pagamento);
            nfe.setPagamentos(pagamentos);
            
            // 6. Gerar TX2
            String arquivoTX2 = nfe.gerarTX2();
            
            if (arquivoTX2 != null) {
                System.out.println("✅ NFe de teste gerada com sucesso!");
                System.out.println("📁 Arquivo TX2 salvo em: output/");
            } else {
                System.out.println("❌ Erro ao gerar NFe de teste");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }
}
```

### 🚀 **Exemplo Completo - Produção**

```java
public class ExemploProducao {
    public static void main(String[] args) {
        try {
            // ⚠️ ATENÇÃO: Use apenas após homologação aprovada!
            
            // 1. Criar NFe para produção
            NFe nfe = new NFe();
            nfe.setTipoOperacao(TipoOperacao.saida);
            nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.producao);
            nfe.setFinalidadeOperacao(FinalidadeOperacao.normal);
            nfe.setNaturezaOperacaoDescricao("Venda de produtos");
            
            // 2. Emitente com dados reais
            Cliente emitente = new Cliente();
            emitente.setDocumento("12345678000195"); // CNPJ real
            emitente.setNome("EMPRESA REAL LTDA");
            emitente.setRegimeTributario(RegimeTributario.simplesNacional);
            emitente.setIndicadorIE(IndicadorIE.contribuinteICMS);
            emitente.setInscricaoEstadual("123456789");
            
            // Endereço real do emitente
            Endereco enderecoEmitente = new Endereco();
            enderecoEmitente.setCep("13170-000");
            enderecoEmitente.setRua("Rua Real");
            enderecoEmitente.setNumero("100");
            enderecoEmitente.setBairro("Centro");
            enderecoEmitente.setMunicipio("Sumaré");
            enderecoEmitente.setUf(UF.SP);
            enderecoEmitente.setCodigoCidade("3552205");
            emitente.setEndereco(enderecoEmitente);
            
            nfe.setEmitente(emitente);
            
            // 3. Destinatário com dados reais
            Cliente destinatario = new Cliente();
            destinatario.setDocumento("12345678901"); // CPF real
            destinatario.setNome("João da Silva");
            destinatario.setIndicadorIE(IndicadorIE.naoContribuinte);
            
            Endereco enderecoDestinatario = new Endereco();
            enderecoDestinatario.setCep("01310-100");
            enderecoDestinatario.setRua("Avenida Paulista");
            enderecoDestinatario.setNumero("1000");
            enderecoDestinatario.setBairro("Bela Vista");
            enderecoDestinatario.setMunicipio("São Paulo");
            enderecoDestinatario.setUf(UF.SP);
            enderecoDestinatario.setCodigoCidade("3550308");
            destinatario.setEndereco(enderecoDestinatario);
            
            nfe.setDestinatario(destinatario);
            
            // 4. Item real
            ItemNFe item = new ItemNFe();
            item.setNItem(1);
            item.setCodigoProdutoServico("PROD001");
            item.setDescricaoItem("Produto Real");
            item.setNCM("12345678");
            item.setCFOP(5102);
            item.setUnidadeComercial("UN");
            item.setQuantidadeComercial(2.0);
            item.setValorUnitarioComercial(25.50);
            item.setValorTotalBruto(51.00);
            
            // Configurar ICMS Simples Nacional
            item.configurarICMSSimplesNacional("102", 0.0);
            
            List<ItemNFe> itens = new ArrayList<>();
            itens.add(item);
            nfe.setProdutos(itens);
            
            // 5. Pagamento
            Pagamento pagamento = new Pagamento();
            pagamento.setFormaPagamento(FormaPagamento.dinheiro);
            pagamento.setValorPagamento(51.00);
            
            List<Pagamento> pagamentos = new ArrayList<>();
            pagamentos.add(pagamento);
            nfe.setPagamentos(pagamentos);
            
            // 6. Validar antes de gerar
            if (!nfe.estaValido()) {
                System.out.println("❌ NFe inválida para produção:");
                nfe.getErrosValidacao().forEach(erro -> 
                    System.out.println("  • " + erro));
                return;
            }
            
            // 7. Gerar TX2
            String arquivoTX2 = nfe.gerarTX2();
            
            if (arquivoTX2 != null) {
                System.out.println("✅ NFe de produção gerada com sucesso!");
                System.out.println("📁 Arquivo TX2 salvo em: output/");
                System.out.println("⚠️ Certifique-se de que o certificado digital está configurado!");
            } else {
                System.out.println("❌ Erro ao gerar NFe de produção");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }
}
```

---

## 🔄 **Migração entre Ambientes**

### 🔧 **De Homologação para Produção**

```java
// 1. Teste completo em homologação
public void testarEmHomologacao() {
    NFe nfe = new NFe();
    nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);
    
    // Configurar com dados de teste
    emitente.setDocumento("99999999000191");
    destinatario.setDocumento("11111111111");
    
    // Testar geração
    String tx2 = nfe.gerarTX2();
    assert tx2 != null : "TX2 deve ser gerado";
}

// 2. Migrar para produção
public void migrarParaProducao() {
    NFe nfe = new NFe();
    nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.producao);
    
    // Usar dados reais
    emitente.setDocumento("12345678000195");
    destinatario.setDocumento("12345678901");
    
    // Validar rigorosamente
    if (!nfe.estaValido()) {
        throw new RuntimeException("NFe inválida para produção");
    }
    
    // Gerar TX2
    String tx2 = nfe.gerarTX2();
}
```

### 📋 **Checklist de Migração**

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

---

## ⚠️ **Troubleshooting**

### ❌ **Erro: "Ambiente de produção não permitido"**

**Causa:** Tentativa de usar produção sem requisitos.

**Solução:**
```java
// Use sempre homologação para desenvolvimento
nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);

// Para produção, certifique-se de ter:
// 1. Certificado digital válido
// 2. CNPJ/CPF reais (não de teste)
// 3. Homologação aprovada na SEFAZ
```

### ❌ **Erro: "Certificado digital não encontrado"**

**Causa:** Certificado não configurado para produção.

**Solução:**
```java
// Em produção, configure o certificado:
// 1. Instale o certificado no sistema
// 2. Configure o caminho no NeverStop/TecnoSpeed
// 3. Teste primeiro em homologação
```

### ❌ **Erro: "CNPJ de teste não permitido em produção"**

**Causa:** Usando dados de teste em produção.

**Solução:**
```java
// Em homologação (permitido)
emitente.setDocumento("99999999000191"); // CNPJ de teste

// Em produção (obrigatório)
emitente.setDocumento("12345678000195"); // CNPJ real
```

### ❌ **Erro: "Validação falhou em produção"**

**Causa:** Validações mais rigorosas em produção.

**Solução:**
```java
// Sempre valide antes de gerar em produção
if (!nfe.estaValido()) {
    System.out.println("❌ NFe inválida:");
    nfe.getErrosValidacao().forEach(erro -> 
        System.out.println("  • " + erro));
    return;
}

// Só então gere o TX2
String tx2 = nfe.gerarTX2();
```

---

## 📋 **Checklist de Produção**

### ✅ **Antes de Migrar para Produção**

1. **Certificado Digital**
   - [ ] Instalado no sistema
   - [ ] Válido e não expirado
   - [ ] Configurado no NeverStop/TecnoSpeed
   - [ ] Testado em homologação

2. **Dados da Empresa**
   - [ ] CNPJ real (não de teste)
   - [ ] Inscrição estadual válida
   - [ ] Endereço completo e correto
   - [ ] Regime tributário correto

3. **Configuração do Sistema**
   - [ ] NeverStop/TecnoSpeed configurado
   - [ ] Certificado digital selecionado
   - [ ] Ambiente de produção ativo
   - [ ] Conexão com SEFAZ testada

4. **Testes Obrigatórios**
   - [ ] Teste em homologação aprovado
   - [ ] Validações passando
   - [ ] TX2 gerado corretamente
   - [ ] Primeira NFe de produção testada

5. **Código**
   - [ ] Alterado para `IdentificacaoAmbiente.producao`
   - [ ] Dados reais configurados
   - [ ] Validações implementadas
   - [ ] Tratamento de erros

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

## 🎯 **Resumo**

### 🔧 **Homologação**
- ✅ **Para desenvolvimento e testes**
- ✅ **Dados de teste permitidos**
- ✅ **Validações menos rigorosas**
- ✅ **Não gera NFe real**

### 🚀 **Produção**
- ⚠️ **Apenas após homologação aprovada**
- ⚠️ **Dados reais obrigatórios**
- ⚠️ **Certificado digital necessário**
- ⚠️ **Gera NFe real na SEFAZ**

---

**💡 Configure corretamente os ambientes para evitar problemas!**

Para mais informações:
- **[README.md](./README.md)** - Guia básico
- **[EXEMPLOS_USO.md](./EXEMPLOS_USO.md)** - Exemplos práticos
- **[GUIA_VALIDACOES.md](./GUIA_VALIDACOES.md)** - Sistema de validações 