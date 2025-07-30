# 🔍 GUIA COMPLETO - Sistema de Validações NFe

Este documento apresenta **todas as validações automáticas** implementadas na biblioteca NFe, organizadas por tipo e categoria.

## 📊 **Visão Geral**

- **Total de Validações**: 200+ validações automáticas
- **Categorias**: Campo, Formato, Negócio, Fiscal, Sequência
- **Anotações**: @Obrigatorio, @Tamanho, @Formato, @ValidarCNPJ, etc.
- **Execução**: Automática na geração TX2

---

## 🏗️ **Arquitetura do Sistema**

```
ValidadorCampos (Principal)
├── 📋 Validações de Campo (@Obrigatorio, @Tamanho)
├── 🔤 Validações de Formato (@Formato, @ValidarCNPJ, @ValidarCPF)
├── 🏢 Validações de Negócio (RegrasNegocioValidator)
├── 📊 Validações Fiscais (Tributos, CST/CSOSN)
└── 🔗 Validações de Sequência (Dependências)
```

---

## 1️⃣ **Validações de Campo**

### 📋 **@Obrigatorio - Campos Obrigatórios**

**Localização**: Todas as classes principais

#### **NFe Principal**
```java
// Campos sempre obrigatórios
@Obrigatorio(codigo = "B08", mensagem = "Número da NFe é obrigatório")
private String numeroDocumentoFiscal;

@Obrigatorio(codigo = "B09", mensagem = "Data/hora emissão é obrigatória")
private String dataHoraEmissaoDocumentoFiscal;

@Obrigatorio(codigo = "B04", mensagem = "Natureza da operação é obrigatória")
private String naturezaOperacaoDescricao;
```

#### **Cliente (Emitente/Destinatário)**
```java
@Obrigatorio(codigo = "C03/E04", mensagem = "Nome/Razão Social é obrigatório")
private String nome;

@Obrigatorio(codigo = "C02/E02", mensagem = "CPF/CNPJ é obrigatório")
private String documento;

@Obrigatorio(codigo = "C06-C13/E06-E13", mensagem = "Endereço é obrigatório")
private Endereco endereco;

// Condicional - apenas para emitente
@Obrigatorio(codigo = "C21", mensagem = "Regime tributário é obrigatório", condicional = true)
private RegimeTributario regimeTributario;
```

#### **ItemNFe**
```java
@Obrigatorio(codigo = "I02", mensagem = "Código do produto é obrigatório")
private String codigoProdutoServico;

@Obrigatorio(codigo = "I04", mensagem = "Descrição é obrigatória")
private String descricaoItem;

@Obrigatorio(codigo = "I05", mensagem = "NCM é obrigatório")
private String NCM;

@Obrigatorio(codigo = "I08", mensagem = "CFOP é obrigatório")
private int CFOP;
```

### 📏 **@Tamanho - Validações de Tamanho**

#### **Tamanho Fixo**
```java
@Tamanho(valor = 2, codigo = "C16/E16")
private String DDD; // Exatamente 2 caracteres

@Tamanho(valor = 7, codigo = "C20")
private String CNAEFiscal; // Exatamente 7 caracteres

@Tamanho(valor = 8, codigo = "I05")
private String NCM; // Exatamente 8 caracteres
```

#### **Tamanho Variável**
```java
@Tamanho(min = 2, max = 60, codigo = "C03/E04")
private String nome; // Entre 2 e 60 caracteres

@Tamanho(min = 8, max = 9, codigo = "C16/E16")
private String telefone; // Entre 8 e 9 caracteres

@Tamanho(max = 60, codigo = "C04")
private String fantasia; // Até 60 caracteres
```

#### **Tamanhos Específicos por Campo**

| Campo | Tamanho | Código | Descrição |
|-------|---------|--------|-----------|
| `documento` (CPF) | 11 | C02a/E03 | CPF sem formatação |
| `documento` (CNPJ) | 14 | C02/E02 | CNPJ sem formatação |
| `inscricaoEstadual` | 2-14 | C17/E17 | Conforme UF |
| `inscricaoMunicipal` | 1-15 | C19/E18a | Conforme município |
| `inscricaoSuframa` | 9 | E18 | SUFRAMA |
| `CEP` | 8 | C13/E13 | CEP sem formatação |
| `codigoCidade` | 7 | C10/E10 | Código IBGE |
| `descricaoItem` | 1-120 | I04 | Descrição produto |
| `codigoProdutoServico` | 1-60 | I02 | Código produto |

---

## 2️⃣ **Validações de Formato**

### 🔤 **@Formato - Formatos Específicos**

#### **Formato Numérico**
```java
@Formato(tipo = Formato.TipoFormato.NUMERICO, codigo = "C16/E16")
private String DDD; // Apenas números

@Formato(tipo = Formato.TipoFormato.NUMERICO, codigo = "C20")
private String CNAEFiscal; // Apenas números

@Formato(tipo = Formato.TipoFormato.NUMERICO, codigo = "E18")
private String inscricaoSuframa; // Apenas números
```

#### **Formatos Específicos**
```java
@Formato(tipo = Formato.TipoFormato.CEP)
private String cep; // 99999999

@Formato(tipo = Formato.TipoFormato.EMAIL)
private String email; // formato@email.com

@Formato(tipo = Formato.TipoFormato.NCM)
private String NCM; // 99999999

@Formato(tipo = Formato.TipoFormato.CFOP)
private int CFOP; // 9999
```

#### **Formatos Customizados (Regex)**
```java
@Formato(regex = "^[0-9]{8}$", mensagem = "NCM deve ter 8 dígitos")
private String NCM;

@Formato(regex = "^(S|N)$", mensagem = "Deve ser S ou N")
private String indicadorEscala;
```

### 📄 **Validadores Específicos**

#### **@ValidarCNPJ**
```java
@ValidarCNPJ(codigo = "C02/E02", mensagem = "CNPJ inválido")
private String documentoCNPJ;
```

**Validações aplicadas:**
- ✅ 14 dígitos numéricos
- ✅ Dígitos verificadores corretos
- ✅ Não é sequência inválida (00000000000000, 11111111111111)
- ✅ Algoritmo oficial da Receita Federal

#### **@ValidarCPF**
```java
@ValidarCPF(codigo = "C02a/E03", mensagem = "CPF inválido")
private String documentoCPF;
```

**Validações aplicadas:**
- ✅ 11 dígitos numéricos
- ✅ Dígitos verificadores corretos
- ✅ Não é sequência inválida (00000000000, 11111111111)
- ✅ Algoritmo oficial da Receita Federal

#### **@ValidarCEP**
```java
@ValidarCEP(codigo = "C13/E13", mensagem = "CEP inválido")
private String cep;
```

**Validações aplicadas:**
- ✅ 8 dígitos numéricos
- ✅ Formato 99999999
- ✅ CEP existe na base de dados (opcional)
- ✅ Busca automática de endereço

---

## 3️⃣ **Validações de Negócio**

### 🏢 **RegrasNegocioValidator**

#### **Regime Tributário vs CRT**
```java
public static boolean validarSimplesNacional(RegimeTributario regime, String crt) {
    // Se é Simples Nacional, CRT deve ser 1
    if (regime == RegimeTributario.simplesNacional) {
        return "1".equals(crt);
    }
    // Se não é Simples Nacional, CRT deve ser 2 ou 3
    return "2".equals(crt) || "3".equals(crt);
}
```

**Regras:**
- ✅ Simples Nacional: CRT = 1
- ✅ Regime Normal: CRT = 2 ou 3
- ✅ Consistência entre regime e código

#### **Indicador IE vs Inscrição Estadual**
```java
public static boolean validarIndicadorIE(IndicadorIE indicador, String inscricaoEstadual) {
    switch (indicador) {
        case contribuinteICMS:
            return inscricaoEstadual != null && !inscricaoEstadual.trim().isEmpty();
        case contribuinteIsento:
            return true; // IE pode estar preenchida ou não
        case naoContribuinte:
            return inscricaoEstadual == null || inscricaoEstadual.trim().isEmpty();
        default:
            return false;
    }
}
```

**Regras:**
- ✅ Contribuinte ICMS: IE obrigatória
- ✅ Contribuinte Isento: IE opcional
- ✅ Não Contribuinte: IE não deve estar preenchida

#### **ICMS vs ISSQN (Mutuamente Exclusivos)**
```java
public static boolean validarICMSvsISSQN(Object icms, Object issqn) {
    // Se ambos estão preenchidos, é inválido
    if (icms != null && issqn != null) {
        return false;
    }
    // Se pelo menos um está preenchido, é válido
    return icms != null || issqn != null;
}
```

**Regras:**
- ✅ Item deve ter ICMS OU ISSQN
- ✅ Nunca ambos simultaneamente
- ✅ Nunca nenhum dos dois

### 📊 **Validações de Totais**

#### **Balanceamento de Valores**
```java
// Valor total produtos = soma dos itens
double somaItens = itens.stream()
    .mapToDouble(ItemNFe::getValorTotalBruto)
    .sum();

if (Math.abs(totais.getValorTotalProdutos() - somaItens) > 0.01) {
    erros.add("Valor total produtos não confere com soma dos itens");
}

// Valor total NFe = produtos + frete + seguro - desconto + outras despesas
double valorCalculado = totais.getValorTotalProdutos() 
    + totais.getValorTotalFrete()
    + totais.getValorTotalSeguro()
    - totais.getValorTotalDesconto()
    + totais.getValorOutrasDespesas();

if (Math.abs(totais.getValorTotalNF() - valorCalculado) > 0.01) {
    erros.add("Valor total NFe não confere com cálculo");
}
```

#### **Validações de Pagamento**
```java
// Valor total pagamentos = valor total NFe
double totalPagamentos = pagamentos.stream()
    .mapToDouble(Pagamento::getValorPagamento)
    .sum();

double valorNFe = totais.getValorTotalNF();

if (Math.abs(totalPagamentos - valorNFe) > 0.01) {
    erros.add("Total de pagamentos não confere com valor da NFe");
}
```

---

## 4️⃣ **Validações Fiscais**

### 🏛️ **CST/CSOSN por Regime**

#### **Regime Normal - CST ICMS**
```java
// CSTs válidos por situação
Map<String, List<String>> cstsValidos = Map.of(
    "TRIBUTADO", Arrays.asList("00", "10", "20", "70", "90"),
    "ISENTO", Arrays.asList("40", "41"),
    "NAO_TRIBUTADO", Arrays.asList("40", "41", "50"),
    "SUBSTITUICAO", Arrays.asList("10", "30", "60", "70", "90"),
    "DIFERIDO", Arrays.asList("51"),
    "OUTROS", Arrays.asList("90")
);
```

#### **Simples Nacional - CSOSN**
```java
// CSOSNs válidos
Map<String, String> csosnValidos = Map.of(
    "101", "Tributada com permissão de crédito",
    "102", "Sem permissão de crédito",
    "103", "Isenção para faixa de receita",
    "201", "Com crédito e ST",
    "202", "Sem crédito e ST",
    "203", "Isenção com ST",
    "300", "Imune",
    "400", "Não tributada pelo SN",
    "500", "ICMS cobrado por ST",
    "900", "Outros"
);
```

### 📋 **Validações por CST**

#### **CST 00 - Tributado Integralmente**
- ✅ Base de cálculo obrigatória e > 0
- ✅ Alíquota obrigatória e > 0
- ✅ Valor ICMS obrigatório e > 0
- ✅ Origem da mercadoria obrigatória

#### **CST 10 - Tributado com ST**
- ✅ Dados do CST 00 +
- ✅ Base de cálculo ST obrigatória
- ✅ Alíquota ST obrigatória
- ✅ Valor ICMS ST obrigatório

#### **CST 20 - Redução de Base**
- ✅ Dados do CST 00 +
- ✅ Percentual de redução obrigatório
- ✅ Base reduzida = base original × (1 - redução/100)

#### **CST 40/41/50 - Isentos/Não Tributados**
- ✅ Apenas origem da mercadoria
- ✅ Motivo da desoneração (quando aplicável)
- ✅ Valor ICMS desonerado (quando aplicável)

### 💸 **PIS/COFINS por CST**

#### **CSTs com Alíquota (01, 02)**
```java
// Validações obrigatórias
- Base de cálculo > 0
- Alíquota > 0 e <= 100
- Valor = base × alíquota / 100
- CST válido para o regime tributário
```

#### **CSTs com Quantidade (03)**
```java
// Validações obrigatórias
- Quantidade > 0
- Valor por unidade > 0
- Valor total = quantidade × valor unitário
```

#### **CSTs Não Tributados (04-09)**
```java
// Apenas CST obrigatório
- Sem campos adicionais
- CST condizente com a operação
```

### 🏢 **ISSQN - Validações Específicas**

```java
// Campos obrigatórios para ISSQN
- Base de cálculo > 0
- Alíquota entre 2% e 5% (conforme município)
- Valor ISSQN = base × alíquota / 100
- Código do serviço (LC 116/2003)
- Município do fato gerador
```

---

## 5️⃣ **Validações de Códigos Fiscais**

### 🗂️ **NCM (Nomenclatura Comum do Mercosul)**

```java
public static boolean validarNCM(String ncm) {
    if (ncm == null || ncm.length() != 8) {
        return false;
    }
    
    // Verificar se são apenas números
    if (!ncm.matches("\\d{8}")) {
        return false;
    }
    
    // NCM 00000000 é válido para serviços
    if ("00000000".equals(ncm)) {
        return true;
    }
    
    // Validar se NCM existe na tabela oficial
    return validarNCMNaTabelaOficial(ncm);
}
```

### 🔄 **CFOP (Código Fiscal de Operações e Prestações)**

```java
public static boolean validarCFOP(int cfop) {
    // CFOP deve ter 4 dígitos
    if (cfop < 1000 || cfop > 9999) {
        return false;
    }
    
    // Primeiro dígito indica tipo de operação
    int primeiroDigito = cfop / 1000;
    
    switch (primeiroDigito) {
        case 1: return true; // Entrada estadual
        case 2: return true; // Entrada interestadual
        case 3: return true; // Entrada exterior
        case 5: return true; // Saída estadual
        case 6: return true; // Saída interestadual
        case 7: return true; // Saída exterior
        default: return false;
    }
}
```

### 🏛️ **Códigos IBGE**

#### **Validação de Município**
```java
public static boolean validarCodigoMunicipio(String codigo, UF uf) {
    if (codigo == null || codigo.length() != 7) {
        return false;
    }
    
    // Primeiros 2 dígitos devem ser o código da UF
    String codigoUF = codigo.substring(0, 2);
    if (!String.valueOf(uf.getCodigo()).equals(codigoUF)) {
        return false;
    }
    
    // Verificar se município existe
    return validarMunicipioNaTabelaIBGE(codigo);
}
```

---

## 6️⃣ **Validações de Sequência XML**

### 🔗 **Campos Interdependentes**

#### **CEST e Escala**
```java
// Se informar indicadorEscala, CEST é obrigatório
if (indicadorEscala != null) {
    if (cest == null || cest.trim().isEmpty()) {
        erros.add("CEST é obrigatório quando indicadorEscala é informado");
    }
}

// Se escala não relevante, CNPJ fabricante é obrigatório
if ("N".equals(indicadorEscala)) {
    if (cnpjFabricante == null || cnpjFabricante.trim().isEmpty()) {
        erros.add("CNPJ fabricante obrigatório para escala não relevante");
    }
}
```

#### **Contingência**
```java
// Se em contingência, justificativa é obrigatória
if (dataHoraContingencia != null) {
    if (justificativaContingencia == null || justificativaContingencia.length() < 15) {
        erros.add("Justificativa de contingência deve ter pelo menos 15 caracteres");
    }
}
```

#### **Responsável Técnico**
```java
// Se informar CSRT, deve ter ID e Hash
if (identificadorCSRT != null || hashCSRT != null) {
    if (identificadorCSRT == null || hashCSRT == null) {
        erros.add("ID e Hash CSRT devem ser informados em conjunto");
    }
}
```

---

## 7️⃣ **Validações Customizadas**

### 🎯 **ValidadorCampos - Sistema Principal**

```java
/**
 * Executa todas as validações em um objeto
 */
public static List<ValidacaoException> validar(Object objeto) {
    List<ValidacaoException> erros = new ArrayList<>();
    
    // 1. Validações de anotação
    validarAnotacoes(objeto, erros);
    
    // 2. Validações de negócio
    validarRegrasNegocio(objeto, erros);
    
    // 3. Validações fiscais
    validarRegrasFiscais(objeto, erros);
    
    // 4. Validações de sequência
    validarSequenciasXML(objeto, erros);
    
    return erros;
}
```

### 🔧 **Validações Específicas por Classe**

#### **Cliente.validar()**
```java
public List<ValidacaoException> validar() {
    List<ValidacaoException> erros = ValidadorCampos.validar(this);
    
    // Validações específicas do Cliente
    if (documento != null) {
        if (tipoDocumento == TipoDocumento.CNPJ) {
            if (!CNPJValidator.validar(documento)) {
                erros.add(new ValidacaoException("CNPJ inválido"));
            }
        } else if (tipoDocumento == TipoDocumento.CPF) {
            if (!CPFValidator.validar(documento)) {
                erros.add(new ValidacaoException("CPF inválido"));
            }
        }
    }
    
    return erros;
}
```

#### **ItemNFe.validar()**
```java
public List<ValidacaoException> validar() {
    List<ValidacaoException> erros = ValidadorCampos.validar(this);
    
    // Validações específicas do Item
    
    // NCM para serviços
    if ("00000000".equals(NCM)) {
        if (CFOP < 5000 || CFOP >= 6000) {
            erros.add(new ValidacaoException("CFOP para serviços deve começar com 5"));
        }
    }
    
    // Unidades comercial e tributável
    if (unidadeComercial != null && unidadeTributavel != null) {
        if (!unidadeComercial.equals(unidadeTributavel)) {
            // Validar conversão
            validarConversaoUnidades(erros);
        }
    }
    
    return erros;
}
```

---

## 8️⃣ **Exceções de Validação**

### 🚨 **Hierarquia de Exceções**

```
ValidacaoException (Base)
├── CampoObrigatorioException
├── TamanhoInvalidoException
├── FormatoInvalidoException
├── DocumentoInvalidoException
├── RegraNegocioException
└── ValidacaoFiscalException
```

#### **ValidacaoException**
```java
public class ValidacaoException extends Exception {
    private String codigoCampo;    // Ex: "C02", "I04"
    private String nomeCampo;      // Ex: "documento", "descricaoItem"
    private Object valorInvalido;  // Valor que causou o erro
    
    @Override
    public String getMessage() {
        StringBuilder msg = new StringBuilder(super.getMessage());
        
        if (codigoCampo != null) {
            msg.append(" (Código: ").append(codigoCampo).append(")");
        }
        
        if (nomeCampo != null) {
            msg.append(" - Campo: ").append(nomeCampo);
        }
        
        if (valorInvalido != null) {
            msg.append(" - Valor: ").append(valorInvalido);
        }
        
        return msg.toString();
    }
}
```

#### **Exceções Específicas**
```java
// Campo obrigatório não preenchido
public class CampoObrigatorioException extends ValidacaoException {
    public CampoObrigatorioException(String mensagem, String codigo, String campo) {
        super(mensagem, codigo, campo, null);
    }
}

// Tamanho inválido
public class TamanhoInvalidoException extends ValidacaoException {
    private int tamanhoMinimo;
    private int tamanhoMaximo;
    private int tamanhoAtual;
}

// Formato inválido
public class FormatoInvalidoException extends ValidacaoException {
    private String formatoEsperado;
    private String regexPattern;
}
```

---

## 9️⃣ **Uso das Validações**

### ✅ **Validação Manual**

```java
// Validar NFe completa
NFe nfe = new NFe();
// ... configurar nfe

List<ValidacaoException> erros = ValidadorCampos.validar(nfe);

if (!erros.isEmpty()) {
    for (ValidacaoException erro : erros) {
        System.out.println("❌ " + erro.getMessage());
        System.out.println("   Campo: " + erro.getNomeCampo());
        System.out.println("   Código: " + erro.getCodigoCampo());
        System.out.println("   Valor: " + erro.getValorInvalido());
    }
} else {
    System.out.println("✅ NFe válida!");
}
```

### 🔄 **Validação Automática**

```java
// Validação automática na geração TX2
NFe nfe = new NFe();
// ... configurar nfe

String tx2 = nfe.gerarTX2();

if (tx2 == null) {
    // Houve erros de validação
    List<String> erros = nfe.getErrosValidacao();
    for (String erro : erros) {
        System.out.println("❌ " + erro);
    }
} else {
    System.out.println("✅ TX2 gerado com sucesso!");
}
```

### 🎯 **Validação Específica**

```java
// Validar apenas um cliente
Cliente cliente = new Cliente();
cliente.setDocumento("12345678000195");
cliente.setNome("Empresa Teste");

if (cliente.estaValido()) {
    System.out.println("✅ Cliente válido");
} else {
    System.out.println("❌ Erros no cliente:");
    System.out.println(cliente.getErrosValidacao());
}
```

---

## 🔟 **Performance e Otimização**

### ⚡ **Validações Otimizadas**

```java
// Cache de validações computacionalmente caras
private static final Map<String, Boolean> cacheValidacaoCNPJ = new HashMap<>();
private static final Map<String, Boolean> cacheValidacaoCPF = new HashMap<>();

public static boolean validarCNPJ(String cnpj) {
    if (cacheValidacaoCNPJ.containsKey(cnpj)) {
        return cacheValidacaoCNPJ.get(cnpj);
    }
    
    boolean valido = validarCNPJCompleto(cnpj);
    cacheValidacaoCNPJ.put(cnpj, valido);
    return valido;
}
```

### 📊 **Métricas de Validação**

```java
// Acompanhar performance das validações
public class MetricasValidacao {
    private static long tempoTotalValidacao = 0;
    private static int quantidadeValidacoes = 0;
    
    public static void registrarValidacao(long tempo) {
        tempoTotalValidacao += tempo;
        quantidadeValidacoes++;
    }
    
    public static double getTempoMedio() {
        return quantidadeValidacoes > 0 ? 
            (double) tempoTotalValidacao / quantidadeValidacoes : 0;
    }
}
```

---

## 📚 **Referência Rápida**

### 🔍 **Principais Validadores**

| Classe | Responsabilidade | Principais Métodos |
|--------|------------------|-------------------|
| `ValidadorCampos` | Validação geral por anotações | `validar(Object)` |
| `CNPJValidator` | Validação específica CNPJ | `validar(String)` |
| `CPFValidator` | Validação específica CPF | `validar(String)` |
| `CEPValidator` | Validação CEP + busca endereço | `validar(String)`, `buscarEndereco(String)` |
| `RegrasNegocioValidator` | Regras fiscais e de negócio | `validarSimplesNacional()`, `validarICMSvsISSQN()` |
| `CodigosFiscaisValidator` | NCM, CFOP, códigos IBGE | `validarNCM()`, `validarCFOP()` |

### 📋 **Checklist de Validação**

#### **Antes de Gerar TX2**
- [ ] NFe configurada com campos obrigatórios
- [ ] Emitente válido com endereço completo
- [ ] Destinatário válido com indicador IE correto
- [ ] Pelo menos 1 item com tributos configurados
- [ ] Pelo menos 1 forma de pagamento
- [ ] Regime tributário consistente com CST/CSOSN
- [ ] Totais balanceados

#### **Validações Críticas**
- [ ] CNPJ/CPF com dígitos verificadores válidos
- [ ] IE consistente com indicador
- [ ] NCM existente na tabela oficial
- [ ] CFOP compatível com tipo de operação
- [ ] CST/CSOSN válido para o regime tributário
- [ ] Alíquotas dentro dos limites legais
- [ ] Valores calculados corretamente

---

**🔍 Sistema de validação completo com 200+ verificações automáticas!**

Para uso prático, consulte:
- **[README.md](./README.md)** - Guia de uso
- **[GUIA_CAMPOS_NFE.md](./GUIA_CAMPOS_NFE.md)** - Mapeamento de campos
- **[EXEMPLOS_USO.md](./EXEMPLOS_USO.md)** - Exemplos práticos 