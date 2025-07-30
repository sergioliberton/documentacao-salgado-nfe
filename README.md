# 📄 Biblioteca NFe "O Famoso NFe"
Criei essa documentação para melhor entedimento da biblioteca que fiz, assim toda modificação que eu for fazer futuramente, eu posso modificar essa documentação.

Não liga para algumas formalidades, que sempre costumei criar documentação assim.

Uma biblioteca Java completa para geração de NFe (Nota Fiscal Eletrônica) através de arquivos TX2 compatíveis com o programa NeverStop da TecnoSpeed.

## 🎯 **Visão Geral**

Esta biblioteca permite criar programaticamente notas fiscais eletrônicas (NFe e NFC-e) com **validação automática**, **cálculos fiscais** e **geração de arquivos TX2** prontos para transmissão via NeverStop/TecnoSpeed.

### ✨ **Principais Funcionalidades**

- ✅ **NFe e NFC-e**: Suporte completo aos dois modelos
- ✅ **Validação automática**: Mais de 200+ validações fiscais
- ✅ **TX2 completo**: Geração do arquivo conforme documentação TecnoSpeed  
- ✅ **Tributos**: ICMS, ICMS-ST, PIS, COFINS, ISSQN, IPI
- ✅ **Regimes**: Simples Nacional e Regime Normal
- ✅ **Campos mapeados**: 200+ campos da documentação oficial
- ✅ **Thread-safe**: Uso seguro em aplicações concorrentes
- ✅ **Zero dependências**: Apenas Java SE 8+

---

## 🚀 **Instalação**

(No seu caso achoque a melhor opção é o .jar)

### Requisitos
- Java 8 ou superior
- Sistema compatível: Windows, Linux, macOS

### Opção 1: JAR Compilado
```bash
# 1. Baixe o arquivo biblioteca-nfe.jar
# 2. Adicione ao classpath do seu projeto
java -cp "biblioteca-nfe.jar:meu-projeto.jar" MinhaClasse
```

### Opção 2: Compilar do Código
```bash
# 1. Clone o repositório
git clone [url-do-repositorio]

# 2. Compile usando o script fornecido
./compilar.bat  # Windows
# ou
javac -d bin -cp src src/o/famoso/nfe/*.java src/o/famoso/nfe/*/*.java

# 3. Gere o JAR
jar -cf biblioteca-nfe.jar -C bin .
```

### Opção 3: Integração em Projeto Java
```java
// Adicione os arquivos da pasta src/o/famoso/nfe ao seu projeto
// Mantenha a estrutura de pacotes: o.famoso.nfe.*
```

---

## 💡 **Uso Básico**

### Exemplo Simples - NFe de Venda

```java
import o.famoso.nfe.*;
import o.famoso.nfe.cliente.*;
import o.famoso.nfe.item.*;
import o.famoso.nfe.enums.*;
import o.famoso.nfe.pagamento.*;

public class ExemploNFe {
    public static void main(String[] args) {
        try {
            // 1. Criar NFe
            NFe nfe = new NFe();
            nfe.setTipoOperacao(TipoOperacao.saida);
            nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);
            nfe.setFinalidadeOperacao(FinalidadeOperacao.normal);
            nfe.setNaturezaOperacaoDescricao("Venda de produtos");
            
            // 2. Configurar Emitente
            Cliente emitente = new Cliente();
            emitente.setDocumento("12345678000195"); // CNPJ
            emitente.setNome("EMPRESA TESTE LTDA");
            emitente.setRegimeTributario(RegimeTributario.simplesNacional);
            emitente.setIndicadorIE(IndicadorIE.contribuinteICMS);
            emitente.setInscricaoEstadual("123456789");
            
            // Endereço do emitente
            Endereco enderecoEmitente = new Endereco();
            enderecoEmitente.setCep("13170-000");
            enderecoEmitente.buscarCEP(true);  // Busca automática
            enderecoEmitente.setNumero("100");
            emitente.setEndereco(enderecoEmitente);
            
            nfe.setEmitente(emitente);
            
            // 3. Configurar Destinatário
            Cliente destinatario = new Cliente();
            destinatario.setDocumento("12345678901");  // CPF
            destinatario.setNome("João da Silva");
            destinatario.setIndicadorIE(IndicadorIE.naoContribuinte);
            
            Endereco enderecoDestinatario = new Endereco();
            enderecoDestinatario.setCep("01310-100");
            enderecoDestinatario.buscarCEP(true);
            enderecoDestinatario.setNumero("200");
            destinatario.setEndereco(enderecoDestinatario);
            
            nfe.setDestinatario(destinatario);
            
            // 4. Adicionar Item
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
            
            // Adicionar item à NFe
            List<ItemNFe> itens = new ArrayList<>();
            itens.add(item);
            nfe.setProdutos(itens);
            
            // 5. Configurar Pagamento
            Pagamento pagamento = new Pagamento();
            pagamento.setFormaPagamento(FormaPagamento.dinheiro);
            pagamento.setValorPagamento(51.00);
            
            List<Pagamento> pagamentos = new ArrayList<>();
            pagamentos.add(pagamento);
            nfe.setPagamentos(pagamentos);
            
            // 6. Gerar TX2
            String arquivoTX2 = nfe.gerarTX2();
            
            if (arquivoTX2 != null) {
                System.out.println("✅ NFe gerada com sucesso!");
                System.out.println("📁 Arquivo TX2 salvo em: output/");
                
                // Verificar se tem erros
                if (nfe.temErros()) {
                    System.out.println("⚠️ Avisos encontrados:");
                    for (String erro : nfe.getErrosValidacao()) {
                        System.out.println("  • " + erro);
                    }
                }
            } else {
                System.out.println("❌ Erro ao gerar NFe:");
                for (String erro : nfe.getErrosValidacao()) {
                    System.out.println("  • " + erro);
                }
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

---

## 📋 **Estrutura da Biblioteca**

### Principais Classes

| Classe | Descrição | Campos Principais |
|--------|-----------|-------------------|
| **NFe** | Classe principal da nota fiscal | Emitente, destinatário, itens, pagamentos |
| **Cliente** | Dados de emitente e destinatário | CNPJ/CPF, nome, endereço, IE |
| **ItemNFe** | Produtos e serviços da NFe | Descrição, NCM, CFOP, valores, tributos |
| **Pagamento** | Formas de pagamento | Tipo, valor, dados do cartão |
| **GeradorTX2** | Geração do arquivo TX2 | Validações e formatação TX2 |

### Packages Organizados

```
o.famoso.nfe/
├── cliente/          # Cliente, Endereco, TipoDocumento
├── enums/           # Todos os enums (UF, FormaPagamento, etc.)
├── gerador/         # GeradorTX2
├── item/            # ItemNFe e relacionados
├── pagamento/       # Pagamento, FormaPagamento
├── totais/          # TotaisNFe
├── tributos/        # ICMS, PIS, COFINS, ISSQN, CSTs
├── validacao/       # Sistema de validações
└── utils/           # Utilitários (formatação, etc.)
```

---

## 🎯 **Cenários de Uso**

### 1. NFe B2B (Empresa para Empresa)
```java
// NFe com ICMS, destinatário PJ com IE
nfe.setFinalidadeOperacao(FinalidadeOperacao.normal);
destinatario.setIndicadorIE(IndicadorIE.contribuinteICMS);
item.configurarICMS("00", 18.0, 100.0); // CST 00, 18% alíquota
```

### 2. NFC-e Varejo (Consumidor Final)
```java
// NFC-e com consumidor final
nfe.setModeloDocumentoFiscal("65"); // NFC-e
nfe.setConsumidorFinal(OperacaoComConsumidorFinal.sim);
nfe.setIndicadorPresencaComprador(IndicadorPresencaComprador.operacaoPresencial);
```

### 3. Simples Nacional
```java
// Configurar emitente no Simples Nacional
emitente.setRegimeTributario(RegimeTributario.simplesNacional);
item.configurarICMSSimplesNacional("102", 0.0); // CSOSN 102
```

### 4. Operação com ISSQN (Serviços)
```java
// Item de serviço com ISSQN
item.setNCM("00000000"); // Serviços usam NCM 00
item.configurarISSQN(5.0, "1401", "3550308"); // 5% alíquota
```

---

## ⚙️ **Configurações Avançadas**

### Validações Customizadas
```java
// Verificar se NFe está válida antes de gerar
if (!nfe.estaValido()) {
    List<String> erros = nfe.getErrosValidacao();
    // Tratar erros...
}
```

### Campos Opcionais
```java
// Responsável técnico (obrigatório para algumas empresas)
ResponsavelTecnico resp = new ResponsavelTecnico();
resp.setCnpjResponsavel("11222333000144");
resp.setNomeContato("José da Silva");
resp.setEmailContato("tecnico@empresa.com");
nfe.setResponsavelTecnico(resp);

// Informações adicionais
InformacoesAdicionais info = new InformacoesAdicionais();
info.setInformacoesComplementares("Observações da NFe");
nfe.setInformacoesAdicionais(info);
```

### Personalizar Geração TX2
```java
// Acessar gerador diretamente para configurações especiais
GeradorTX2 gerador = new GeradorTX2(nfe);
String tx2 = gerador.gerarTX2Completo();

// Verificar erros específicos
if (gerador.temErros()) {
    List<String> erros = gerador.getErros();
}
```

---

## 🔍 **Validações Implementadas**

A biblioteca implementa mais de **200 validações automáticas**:

### Validações de Campo
- ✅ Campos obrigatórios
- ✅ Tamanhos mínimos e máximos  
- ✅ Formatos (CNPJ, CPF, CEP, etc.)
- ✅ Valores numéricos válidos

### Validações de Negócio
- ✅ Regras do Simples Nacional
- ✅ ICMS vs ISSQN (mutuamente exclusivos)
- ✅ Indicadores consistentes
- ✅ Totais balanceados
- ✅ Sequências XML obrigatórias

### Validações Fiscais
- ✅ CST/CSOSN válidos por regime
- ✅ Alíquotas por UF
- ✅ Códigos IBGE válidos
- ✅ NCM e CFOP compatíveis

---

## 📚 **Documentação Adicional**

- **[GUIA_CAMPOS_NFE.md](./GUIA_CAMPOS_NFE.md)** - Mapa completo de todos os campos
- **[GUIA_VALIDACOES.md](./GUIA_VALIDACOES.md)** - Documentação de validações
- **[EXEMPLOS_USO.md](./EXEMPLOS_USO.md)** - Exemplos para diferentes cenários  
- **[JavaDoc](./javadoc/)** - Documentação das classes

---

## 🧪 **Testes**

### Executar Testes
```bash
# Testes básicos
java -cp biblioteca-nfe.jar TesteSimples

# Teste completo
java -cp biblioteca-nfe.jar TesteCompleto

# Teste de geração TX2
java -cp biblioteca-nfe.jar TesteTX2
```

### Exemplos de Teste
```java
// Teste de validação
NFe nfe = new NFe();
// ... configurar nfe
assert nfe.estaValido() : "NFe deve estar válida";

// Teste de geração
String tx2 = nfe.gerarTX2();
assert tx2 != null : "TX2 deve ser gerado";
assert tx2.contains("versao_A02=4.00") : "Deve ter versão correta";
```

---

## 🤝 **Suporte e Contribuição**

### Problemas Comuns

**Erro: "CNPJ inválido"**
```java
// Verificar se CNPJ tem 14 dígitos numéricos
cliente.setDocumento("12345678000195"); // ✅ Correto
cliente.setDocumento("12.345.678/0001-95"); // ❌ Com formatação
```

**Erro: "Campo obrigatório não preenchido"**
```java
// Verificar campos obrigatórios mínimos
nfe.setEmitente(emitente);     // ✅ Obrigatório
nfe.setDestinatario(destinatario); // ✅ Obrigatório  
nfe.setProdutos(itens);        // ✅ Pelo menos 1 item
nfe.setPagamentos(pagamentos); // ✅ Pelo menos 1 pagamento
```

### Contato
- **Autor**: Sergio Almeida
- **Versão**: 1.0 - 2025
- **Licença**: MIT

---

## 🔄 **Changelog**

### Versão 1.0 (2025)
- ✅ Estrutura completa da NFe (200+ campos)
- ✅ Sistema de validações automáticas
- ✅ Geração TX2 completa
- ✅ Suporte a ICMS, PIS, COFINS, ISSQN
- ✅ Simples Nacional e Regime Normal
- ✅ Documentação completa

---

**🎉 Biblioteca pronta para produção! Gere suas NFes com confiança!** 