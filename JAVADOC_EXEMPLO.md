# 📖 JAVADOC - Exemplo de Documentação das Classes

Este arquivo mostra como as principais classes da biblioteca devem ser documentadas com JavaDoc completo para gerar documentação automática.

## 📚 **Como Gerar JavaDoc**

```bash
# Gerar documentação JavaDoc
javadoc -d javadoc -cp src -sourcepath src -subpackages o.famoso.nfe

# Ou usando o comando específico
javadoc -d docs/javadoc -private -author -version -use -windowtitle "Biblioteca NFe" -doctitle "O Famoso NFe - Biblioteca Completa" -cp src -sourcepath src o.famoso.nfe o.famoso.nfe.cliente o.famoso.nfe.item o.famoso.nfe.gerador o.famoso.nfe.validacao
```

---

## 1️⃣ **NFe.java - Classe Principal**

```java
package o.famoso.nfe;

/**
 * Classe principal da NFe (Nota Fiscal Eletrônica) conforme documentação TecnoSpeed.
 * 
 * <p>Esta classe representa uma nota fiscal eletrônica completa, implementando
 * todos os campos obrigatórios e opcionais conforme a legislação brasileira e
 * documentação da TecnoSpeed para geração de arquivos TX2.</p>
 * 
 * <h2>Funcionalidades Principais:</h2>
 * <ul>
 *   <li>Suporte a NFe (modelo 55) e NFC-e (modelo 65)</li>
 *   <li>Validação automática de campos obrigatórios</li>
 *   <li>Geração de arquivo TX2 para TecnoSpeed/NeverStop</li>
 *   <li>Cálculo automático de totais</li>
 *   <li>Suporte a todos os regimes tributários</li>
 * </ul>
 * 
 * <h2>Exemplo de Uso Básico:</h2>
 * <pre>{@code
 * // Criar nova NFe
 * NFe nfe = new NFe();
 * nfe.setTipoOperacao(TipoOperacao.saida);
 * nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);
 * 
 * // Configurar emitente
 * Cliente emitente = new Cliente();
 * emitente.setDocumento("12345678000195");
 * emitente.setNome("EMPRESA TESTE LTDA");
 * emitente.setRegimeTributario(RegimeTributario.simplesNacional);
 * nfe.setEmitente(emitente);
 * 
 * // Adicionar item
 * ItemNFe item = new ItemNFe();
 * item.setDescricaoItem("Produto Teste");
 * item.setValorTotalBruto(100.00);
 * nfe.adicionarItem(item);
 * 
 * // Gerar TX2
 * String tx2 = nfe.gerarTX2();
 * }</pre>
 * 
 * <h2>Campos Mapeados:</h2>
 * <p>A classe implementa mais de 200 campos conforme documentação TecnoSpeed:</p>
 * <ul>
 *   <li><strong>Identificação (A02-B29):</strong> Versão, chave, natureza da operação, etc.</li>
 *   <li><strong>Contingência (B28-B29):</strong> Data/hora e justificativa</li>
 *   <li><strong>Indicadores:</strong> Tipo operação, ambiente, consumidor final, etc.</li>
 *   <li><strong>Relacionamentos:</strong> Emitente, destinatário, itens, pagamentos</li>
 * </ul>
 * 
 * @author O Famoso - Rafael
 * @version 1.0
 * @since 1.0
 * 
 * @see Cliente
 * @see ItemNFe
 * @see Pagamento
 * @see GeradorTX2
 * @see <a href="https://tecnospeed.com.br">Documentação TecnoSpeed</a>
 */
public class NFe {
    
    /**
     * Versão do leiaute da NFe conforme documentação SEFAZ.
     * Campo A02 da documentação TecnoSpeed.
     * 
     * <p><strong>Obrigatório:</strong> Sim<br>
     * <strong>Tamanho:</strong> 1-4 caracteres<br>
     * <strong>Formato:</strong> Numérico com pontos (ex: "4.00")<br>
     * <strong>Valor padrão:</strong> "4.00"</p>
     */
    private String versaoLeiaut = "4.00";
    
    /**
     * Tipo da operação fiscal (entrada ou saída).
     * Campo B11 da documentação TecnoSpeed.
     * 
     * <p><strong>Obrigatório:</strong> Sim<br>
     * <strong>Valores:</strong></p>
     * <ul>
     *   <li>{@link TipoOperacao#entrada} - Entrada de mercadorias</li>
     *   <li>{@link TipoOperacao#saida} - Saída de mercadorias</li>
     * </ul>
     * 
     * @see TipoOperacao
     */
    private TipoOperacao tipoOperacao;
    
    /**
     * Cliente emitente da NFe.
     * 
     * <p>Representa a empresa ou pessoa que está emitindo a nota fiscal.
     * Deve conter todos os dados obrigatórios: documento (CNPJ/CPF), nome,
     * endereço completo e dados fiscais.</p>
     * 
     * <p><strong>Obrigatório:</strong> Sim<br>
     * <strong>Campos TX2:</strong> C02-C21</p>
     * 
     * @see Cliente
     */
    private Cliente emitente;
    
    /**
     * Cliente destinatário da NFe.
     * 
     * <p>Representa a empresa ou pessoa que está recebendo a mercadoria
     * ou serviço. Para consumidor final em NFC-e, pode ser simplificado.</p>
     * 
     * <p><strong>Obrigatório:</strong> Sim (pode ser "CONSUMIDOR NÃO IDENTIFICADO")<br>
     * <strong>Campos TX2:</strong> E02-E19</p>
     * 
     * @see Cliente
     */
    private Cliente destinatario;
    
    /**
     * Lista de produtos ou serviços da NFe.
     * 
     * <p>Cada item representa um produto ou serviço vendido, com suas
     * respectivas quantidades, valores e tributos.</p>
     * 
     * <p><strong>Obrigatório:</strong> Sim (mínimo 1 item)<br>
     * <strong>Máximo:</strong> 990 itens por NFe<br>
     * <strong>Campos TX2:</strong> H02-V01 (por item)</p>
     * 
     * @see ItemNFe
     */
    private List<ItemNFe> produtos;
    
    /**
     * Construtor padrão da NFe.
     * 
     * <p>Inicializa a NFe com valores padrão seguros:</p>
     * <ul>
     *   <li>Versão do leiaute: 4.00</li>
     *   <li>Modelo documento: 55 (NFe)</li>
     *   <li>Versão aplicativo: "O Famoso NFe 1.0"</li>
     * </ul>
     */
    public NFe() {
        // Inicialização com valores padrão
    }
    
    /**
     * Construtor com configuração básica da NFe.
     * 
     * @param ambiente Ambiente de emissão (produção ou homologação)
     * @param tipoOperacao Tipo da operação (entrada ou saída)
     * 
     * @throws IllegalArgumentException se algum parâmetro for nulo
     * 
     * @since 1.0
     */
    public NFe(IdentificacaoAmbiente ambiente, TipoOperacao tipoOperacao) {
        this();
        if (ambiente == null || tipoOperacao == null) {
            throw new IllegalArgumentException("Ambiente e tipo de operação são obrigatórios");
        }
        this.identificacaoAmbiente = ambiente;
        this.tipoOperacao = tipoOperacao;
    }
    
    /**
     * Adiciona um item (produto ou serviço) à NFe.
     * 
     * <p>O número sequencial do item é definido automaticamente
     * baseado na quantidade de itens já adicionados.</p>
     * 
     * @param item Item a ser adicionado
     * @throws IllegalArgumentException se o item for nulo
     * @throws IllegalStateException se já existem 990 itens (limite máximo)
     * 
     * @see ItemNFe
     * @since 1.0
     */
    public void adicionarItem(ItemNFe item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo");
        }
        
        if (produtos == null) {
            produtos = new ArrayList<>();
        }
        
        if (produtos.size() >= 990) {
            throw new IllegalStateException("Limite máximo de 990 itens por NFe");
        }
        
        // Definir número sequencial automaticamente
        item.setNItem(produtos.size() + 1);
        produtos.add(item);
    }
    
    /**
     * Gera o arquivo TX2 completo da NFe.
     * 
     * <p>Este é o método principal da biblioteca. Ele executa todas as
     * validações necessárias e gera o arquivo TX2 formatado conforme
     * documentação TecnoSpeed/NeverStop.</p>
     * 
     * <h3>Processo de Geração:</h3>
     * <ol>
     *   <li>Validação de campos obrigatórios</li>
     *   <li>Validação de regras de negócio</li>
     *   <li>Cálculo de totais automáticos</li>
     *   <li>Formatação TX2 na ordem correta</li>
     *   <li>Salvamento em arquivo</li>
     * </ol>
     * 
     * @return String contendo o TX2 completo, ou null se houver erros
     * 
     * @throws RuntimeException se houver erro interno durante a geração
     * 
     * @see #getErrosValidacao()
     * @see #temErros()
     * @see GeradorTX2
     * 
     * @since 1.0
     */
    public String gerarTX2() {
        GeradorTX2 gerador = new GeradorTX2(this);
        return gerador.gerarTX2Completo();
    }
    
    /**
     * Verifica se a NFe possui erros de validação.
     * 
     * <p>Este método executa todas as validações sem gerar o TX2,
     * permitindo verificar a integridade dos dados antes da geração.</p>
     * 
     * @return true se existem erros, false caso contrário
     * 
     * @see #getErrosValidacao()
     * @since 1.0
     */
    public boolean temErros() {
        GeradorTX2 gerador = new GeradorTX2(this);
        gerador.gerarTX2Completo();
        return gerador.temErros();
    }
    
    /**
     * Obtém a lista de erros de validação encontrados.
     * 
     * <p>Retorna uma lista detalhada de todos os erros encontrados
     * durante a validação da NFe, incluindo código do campo e
     * descrição do problema.</p>
     * 
     * @return Lista de strings com os erros encontrados (nunca null)
     * 
     * @see #temErros()
     * @since 1.0
     */
    public List<String> getErrosValidacao() {
        GeradorTX2 gerador = new GeradorTX2(this);
        gerador.gerarTX2Completo();
        return gerador.getErros();
    }
    
    /**
     * Verifica se a NFe está configurada como NFC-e.
     * 
     * @return true se for NFC-e (modelo 65), false se for NFe (modelo 55)
     * 
     * @see #isNFe()
     * @since 1.0
     */
    public boolean isNFCe() {
        return "65".equals(modeloDocumentoFiscal);
    }
    
    /**
     * Verifica se a NFe está configurada como NFe normal.
     * 
     * @return true se for NFe (modelo 55), false se for NFC-e (modelo 65)
     * 
     * @see #isNFCe()
     * @since 1.0
     */
    public boolean isNFe() {
        return "55".equals(modeloDocumentoFiscal);
    }
    
    /**
     * Verifica se a NFe está em contingência.
     * 
     * @return true se possui data/hora de contingência configurada
     * 
     * @see #getDataHoraContingencia()
     * @see #getJustificativaContingencia()
     * @since 1.0
     */
    public boolean isContingencia() {
        return dataHoraContingencia != null && !dataHoraContingencia.trim().isEmpty();
    }
    
    // ... outros métodos getters/setters com JavaDoc similar
}
```

---

## 2️⃣ **Cliente.java - Dados de Pessoas**

```java
package o.famoso.nfe.cliente;

/**
 * Representa os dados de uma pessoa física ou jurídica na NFe.
 * 
 * <p>Esta classe é utilizada tanto para o emitente quanto para o destinatário
 * da NFe, contendo todos os dados pessoais, fiscais e de endereçamento
 * necessários para a emissão da nota fiscal.</p>
 * 
 * <h2>Validações Automáticas:</h2>
 * <ul>
 *   <li>CPF/CNPJ com dígitos verificadores</li>
 *   <li>Tamanhos de campos conforme legislação</li>
 *   <li>Consistência entre indicador IE e inscrição estadual</li>
 *   <li>Regime tributário válido para emitente</li>
 * </ul>
 * 
 * <h2>Exemplo de Uso:</h2>
 * <pre>{@code
 * // Cliente pessoa jurídica
 * Cliente empresa = new Cliente();
 * empresa.setDocumento("12345678000195");
 * empresa.setNome("EMPRESA EXEMPLO LTDA");
 * empresa.setRegimeTributario(RegimeTributario.simplesNacional);
 * empresa.setIndicadorIE(IndicadorIE.contribuinteICMS);
 * empresa.setInscricaoEstadual("123456789");
 * 
 * // Cliente pessoa física
 * Cliente pessoa = new Cliente();
 * pessoa.setDocumento("12345678901");
 * pessoa.setNome("João da Silva");
 * pessoa.setIndicadorIE(IndicadorIE.naoContribuinte);
 * }</pre>
 * 
 * @author O Famoso - Rafael
 * @version 1.0
 * @since 1.0
 * 
 * @see Endereco
 * @see TipoDocumento
 * @see IndicadorIE
 * @see RegimeTributario
 */
public class Cliente {
    
    /**
     * Nome ou razão social do cliente.
     * Campo C03 (emitente) ou E04 (destinatário) da documentação TecnoSpeed.
     * 
     * <p><strong>Obrigatório:</strong> Sim<br>
     * <strong>Tamanho:</strong> 2-60 caracteres<br>
     * <strong>Formato:</strong> Texto livre</p>
     * 
     * <p>Para pessoa física: nome completo<br>
     * Para pessoa jurídica: razão social conforme CNPJ</p>
     */
    @Obrigatorio(codigo = "C03/E04", mensagem = "Nome/Razão Social é obrigatório")
    @Tamanho(min = 2, max = 60, codigo = "C03/E04")
    private String nome;
    
    /**
     * CPF ou CNPJ do cliente (apenas números).
     * Campo C02/C02a (emitente) ou E02/E03 (destinatário) da documentação TecnoSpeed.
     * 
     * <p><strong>Obrigatório:</strong> Sim<br>
     * <strong>Tamanho:</strong> 11 caracteres (CPF) ou 14 caracteres (CNPJ)<br>
     * <strong>Formato:</strong> Apenas números, sem formatação</p>
     * 
     * <p>O tipo de documento (CPF/CNPJ) é determinado automaticamente
     * pelo tamanho da string fornecida.</p>
     * 
     * @see TipoDocumento
     */
    @Obrigatorio(codigo = "C02/E02", mensagem = "CPF/CNPJ é obrigatório")
    private String documento;
    
    /**
     * Endereço completo do cliente.
     * Campos C06-C13 (emitente) ou E06-E13 (destinatário) da documentação TecnoSpeed.
     * 
     * <p><strong>Obrigatório:</strong> Sim<br>
     * <strong>Validações:</strong> Todos os campos obrigatórios do endereço</p>
     * 
     * @see Endereco
     */
    @Obrigatorio(codigo = "C06-C13/E06-E13", mensagem = "Endereço é obrigatório")
    private Endereco endereco;
    
    /**
     * Indicador da situação da Inscrição Estadual.
     * Campo E16a (destinatário) da documentação TecnoSpeed.
     * 
     * <p><strong>Obrigatório:</strong> Sim<br>
     * <strong>Valores:</strong></p>
     * <ul>
     *   <li>{@link IndicadorIE#contribuinteICMS} - Contribuinte ICMS</li>
     *   <li>{@link IndicadorIE#contribuinteIsento} - Contribuinte isento</li>
     *   <li>{@link IndicadorIE#naoContribuinte} - Não contribuinte</li>
     * </ul>
     * 
     * <p><strong>Regras de validação:</strong></p>
     * <ul>
     *   <li>Contribuinte ICMS: IE obrigatória</li>
     *   <li>Contribuinte isento: IE opcional</li>
     *   <li>Não contribuinte: IE não deve ser preenchida</li>
     * </ul>
     * 
     * @see IndicadorIE
     */
    private IndicadorIE indicadorIE;
    
    /**
     * Define o documento (CPF ou CNPJ) do cliente.
     * 
     * <p>O método automaticamente determina o tipo de documento baseado
     * no tamanho da string fornecida e executa validações de dígitos
     * verificadores.</p>
     * 
     * @param documento CPF (11 dígitos) ou CNPJ (14 dígitos) apenas números
     * @return true se o documento é válido, false caso contrário
     * 
     * @throws IllegalArgumentException se o documento for nulo ou vazio
     * 
     * @see #getDocumento()
     * @see #getTipoDocumento()
     * @since 1.0
     */
    public boolean setDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            throw new IllegalArgumentException("Documento não pode ser nulo ou vazio");
        }
        
        documento = Utils.apenasNumerosSemPontuacao(documento);
        
        if (documento.length() != 11 && documento.length() != 14) {
            return false; // Tamanho inválido
        }
        
        // Determinar tipo automaticamente
        if (documento.length() == 11) {
            tipoDocumento = TipoDocumento.CPF;
            if (!CPFValidator.validar(documento)) {
                return false;
            }
        } else {
            tipoDocumento = TipoDocumento.CNPJ;
            if (!CNPJValidator.validar(documento)) {
                return false;
            }
        }
        
        this.documento = documento;
        return true;
    }
    
    /**
     * Executa validação completa do cliente.
     * 
     * <p>Valida todos os campos obrigatórios, formatos e regras de negócio
     * específicas do cliente, incluindo:</p>
     * <ul>
     *   <li>Campos obrigatórios preenchidos</li>
     *   <li>CPF/CNPJ com dígitos verificadores válidos</li>
     *   <li>Consistência entre indicador IE e inscrição estadual</li>
     *   <li>Regime tributário válido (para emitente)</li>
     *   <li>Endereço completo e válido</li>
     * </ul>
     * 
     * @return Lista de erros encontrados (vazia se válido)
     * 
     * @see ValidacaoException
     * @see #estaValido()
     * @since 1.0
     */
    public List<ValidacaoException> validar() {
        List<ValidacaoException> erros = ValidadorCampos.validar(this);
        
        // Validações específicas de documento
        if (documento != null && !documento.trim().isEmpty()) {
            if (tipoDocumento == TipoDocumento.CNPJ) {
                if (!CNPJValidator.validar(documento)) {
                    erros.add(new ValidacaoException("CNPJ inválido", "C02/E02", "documento", documento));
                }
            } else if (tipoDocumento == TipoDocumento.CPF) {
                if (!CPFValidator.validar(documento)) {
                    erros.add(new ValidacaoException("CPF inválido", "C02a/E03", "documento", documento));
                }
            }
        }
        
        // Validar consistência do Indicador IE
        if (indicadorIE != null) {
            if (!RegrasNegocioValidator.validarIndicadorIE(indicadorIE, inscricaoEstadual)) {
                erros.add(new ValidacaoException("Indicador IE inconsistente com Inscrição Estadual", 
                    "C17/E17", "indicadorIE", indicadorIE));
            }
        }
        
        return erros;
    }
    
    /**
     * Verifica se o cliente está válido para uso.
     * 
     * @return true se todos os dados estão válidos, false caso contrário
     * 
     * @see #validar()
     * @since 1.0
     */
    public boolean estaValido() {
        return validar().isEmpty();
    }
    
    // ... outros métodos com JavaDoc similar
}
```

---

## 3️⃣ **ItemNFe.java - Produtos e Serviços**

```java
package o.famoso.nfe.item;

/**
 * Representa um item (produto ou serviço) da NFe.
 * 
 * <p>Esta classe implementa todos os campos necessários para descrever
 * um produto ou serviço na nota fiscal, incluindo dados comerciais,
 * tributários e específicos conforme a documentação TecnoSpeed.</p>
 * 
 * <h2>Campos Implementados:</h2>
 * <ul>
 *   <li><strong>Identificação (H02-I17b):</strong> Código, descrição, NCM, CFOP, valores</li>
 *   <li><strong>Rastreabilidade (I80-I85):</strong> Lotes, datas de fabricação/validade</li>
 *   <li><strong>Declaração Importação (I18-I35):</strong> DI, adições, dados aduaneiros</li>
 *   <li><strong>Exportação (I50-I52):</strong> RE, drawback, chave NFe</li>
 *   <li><strong>Específicos:</strong> Medicamentos, combustíveis, veículos</li>
 * </ul>
 * 
 * <h2>Exemplo de Uso:</h2>
 * <pre>{@code
 * ItemNFe item = new ItemNFe();
 * item.setNItem(1);
 * item.setCodigoProdutoServico("PROD001");
 * item.setDescricaoItem("Produto Teste");
 * item.setNCM("12345678");
 * item.setCFOP(5102);
 * item.setUnidadeComercial("UN");
 * item.setQuantidadeComercial(2.0);
 * item.setValorUnitarioComercial(25.50);
 * item.setValorTotalBruto(51.00);
 * 
 * // Configurar ICMS
 * item.configurarICMSSimplesNacional("102", 0.0);
 * }</pre>
 * 
 * @author O Famoso - Rafael
 * @version 1.0
 * @since 1.0
 * 
 * @see NFe
 * @see Imposto_ICMS
 * @see Imposto_PIS
 * @see Imposto_COFINS
 */
public class ItemNFe {
    
    /**
     * Número sequencial do item na NFe.
     * Campo H02 da documentação TecnoSpeed.
     * 
     * <p><strong>Obrigatório:</strong> Sim<br>
     * <strong>Faixa:</strong> 1-990<br>
     * <strong>Formato:</strong> Sequencial a partir de 1</p>
     * 
     * <p>Este número é definido automaticamente quando o item
     * é adicionado à NFe através do método {@link NFe#adicionarItem(ItemNFe)}.</p>
     */
    private int nItem = -1;
    
    /**
     * Código do produto ou serviço.
     * Campo I02 da documentação TecnoSpeed.
     * 
     * <p><strong>Obrigatório:</strong> Sim<br>
     * <strong>Tamanho:</strong> 1-60 caracteres<br>
     * <strong>Formato:</strong> Alfanumérico</p>
     * 
     * <p>Identificação única do produto no sistema da empresa.
     * Pode ser código de barras, SKU, código interno, etc.</p>
     */
    @Obrigatorio(codigo = "I02", mensagem = "Código do produto é obrigatório")
    @Tamanho(min = 1, max = 60, codigo = "I02")
    private String codigoProdutoServico;
    
    /**
     * Descrição detalhada do produto ou serviço.
     * Campo I04 da documentação TecnoSpeed.
     * 
     * <p><strong>Obrigatório:</strong> Sim<br>
     * <strong>Tamanho:</strong> 1-120 caracteres<br>
     * <strong>Formato:</strong> Texto livre</p>
     * 
     * <p>Descrição clara e objetiva que permita identificar
     * precisamente o produto ou serviço vendido.</p>
     */
    @Obrigatorio(codigo = "I04", mensagem = "Descrição é obrigatória")
    @Tamanho(min = 1, max = 120, codigo = "I04")
    private String descricaoItem;
    
    /**
     * Código NCM (Nomenclatura Comum do Mercosul).
     * Campo I05 da documentação TecnoSpeed.
     * 
     * <p><strong>Obrigatório:</strong> Sim<br>
     * <strong>Tamanho:</strong> 8 caracteres<br>
     * <strong>Formato:</strong> Numérico (ex: "12345678")</p>
     * 
     * <p><strong>Valores especiais:</strong></p>
     * <ul>
     *   <li>"00000000" - Para serviços</li>
     *   <li>Códigos NCM válidos conforme tabela IBGE</li>
     * </ul>
     * 
     * @see <a href="http://www.mdic.gov.br/comercio-exterior/negociacoes-internacionais/191-assuntos/categ-comercio-exterior/sgp-sistema-geral-de-preferencias/1799-sgp-nomenclatura-comum-do-mercosul">Tabela NCM Oficial</a>
     */
    @Obrigatorio(codigo = "I05", mensagem = "NCM é obrigatório")
    @Tamanho(valor = 8, codigo = "I05")
    @Formato(tipo = Formato.TipoFormato.NUMERICO, codigo = "I05")
    private String NCM;
    
    /**
     * CFOP (Código Fiscal de Operações e Prestações).
     * Campo I08 da documentação TecnoSpeed.
     * 
     * <p><strong>Obrigatório:</strong> Sim<br>
     * <strong>Tamanho:</strong> 4 dígitos<br>
     * <strong>Formato:</strong> Numérico (ex: 5102)</p>
     * 
     * <p><strong>Primeiro dígito indica o tipo:</strong></p>
     * <ul>
     *   <li>1xxx - Entrada estadual</li>
     *   <li>2xxx - Entrada interestadual</li>
     *   <li>3xxx - Entrada do exterior</li>
     *   <li>5xxx - Saída estadual</li>
     *   <li>6xxx - Saída interestadual</li>
     *   <li>7xxx - Saída para o exterior</li>
     * </ul>
     */
    @Obrigatorio(codigo = "I08", mensagem = "CFOP é obrigatório")
    private int CFOP = -1;
    
    /**
     * Valor total bruto do produto ou serviço.
     * Campo I11 da documentação TecnoSpeed.
     * 
     * <p><strong>Obrigatório:</strong> Sim<br>
     * <strong>Decimais:</strong> 2 casas<br>
     * <strong>Cálculo:</strong> quantidade × valor unitário</p>
     * 
     * <p>Este valor é usado como base para cálculo dos impostos
     * e compõe o valor total da NFe.</p>
     */
    @Obrigatorio(codigo = "I11", mensagem = "Valor total é obrigatório")
    private double valorTotalBruto = -1;
    
    /**
     * Configura ICMS para empresa do Simples Nacional.
     * 
     * <p>Método de conveniência para configurar o ICMS conforme
     * as regras do Simples Nacional, usando CSOSN.</p>
     * 
     * @param csosn Código de Situação da Operação Simples Nacional
     * @param percentualCredito Percentual de crédito (quando aplicável)
     * 
     * @throws IllegalArgumentException se CSOSN for inválido
     * 
     * @see CSOSN
     * @since 1.0
     */
    public void configurarICMSSimplesNacional(String csosn, double percentualCredito) {
        if (csosn == null || csosn.trim().isEmpty()) {
            throw new IllegalArgumentException("CSOSN é obrigatório");
        }
        
        // Validar CSOSN
        if (!Arrays.asList("101", "102", "103", "201", "202", "203", "300", "400", "500", "900")
                .contains(csosn)) {
            throw new IllegalArgumentException("CSOSN inválido: " + csosn);
        }
        
        // Configurar ICMS conforme CSOSN
        Imposto_ICMS icms = new Imposto_ICMS(TipoImposto.ICMS);
        
        switch (csosn) {
            case "101":
                CSOSN_101 csosn101 = new CSOSN_101();
                csosn101.setPercentualCredito(percentualCredito);
                csosn101.setValorCredito(valorTotalBruto * percentualCredito / 100);
                icms.setCsosn(csosn101);
                break;
                
            case "102":
            case "103":
            case "300":
            case "400":
                CSOSN_102 csosn102 = new CSOSN_102();
                icms.setCsosn(csosn102);
                break;
                
            // ... outros CSOSNs
        }
        
        this.icms = icms;
    }
    
    /**
     * Verifica se o item representa um produto físico.
     * 
     * @return true se NCM diferente de "00000000"
     * 
     * @see #isServico()
     * @since 1.0
     */
    public boolean isProduto() {
        return NCM != null && !"00000000".equals(NCM);
    }
    
    /**
     * Verifica se o item representa um serviço.
     * 
     * @return true se NCM igual a "00000000"
     * 
     * @see #isProduto()
     * @since 1.0
     */
    public boolean isServico() {
        return "00000000".equals(NCM);
    }
    
    // ... outros métodos com JavaDoc similar
}
```

---

## 4️⃣ **GeradorTX2.java - Geração de Arquivos**

```java
package o.famoso.nfe.gerador;

/**
 * Gerador centralizado de arquivos TX2 conforme documentação TecnoSpeed.
 * 
 * <p>Esta classe implementa o padrão Template Method para organizar
 * a geração do arquivo TX2 na ordem correta, executando todas as
 * validações necessárias e formatando os dados conforme especificação.</p>
 * 
 * <h2>Processo de Geração:</h2>
 * <ol>
 *   <li>Validação completa da NFe</li>
 *   <li>Geração do cabeçalho TX2</li>
 *   <li>Seções na ordem: Identificação → Emitente → Destinatário → Itens → Totais → Pagamentos → Informações</li>
 *   <li>Salvamento automático em arquivo</li>
 * </ol>
 * 
 * <h2>Validações Executadas:</h2>
 * <ul>
 *   <li>Campos obrigatórios preenchidos</li>
 *   <li>Formatos e tamanhos corretos</li>
 *   <li>Regras de negócio fiscais</li>
 *   <li>Consistência entre seções</li>
 *   <li>Cálculos automáticos</li>
 * </ul>
 * 
 * <h2>Exemplo de Uso:</h2>
 * <pre>{@code
 * NFe nfe = new NFe();
 * // ... configurar NFe
 * 
 * GeradorTX2 gerador = new GeradorTX2(nfe);
 * String tx2 = gerador.gerarTX2Completo();
 * 
 * if (tx2 != null) {
 *     System.out.println("TX2 gerado com sucesso!");
 * } else {
 *     List<String> erros = gerador.getErros();
 *     erros.forEach(System.out::println);
 * }
 * }</pre>
 * 
 * @author O Famoso - Rafael
 * @version 1.0
 * @since 1.0
 * 
 * @see NFe
 * @see ValidadorCampos
 */
public class GeradorTX2 {
    
    /**
     * NFe a ser processada.
     * Referência para a nota fiscal que será convertida em TX2.
     */
    private NFe nfe;
    
    /**
     * StringBuilder para construção do TX2.
     * Acumula todas as linhas do arquivo durante a geração.
     */
    private StringBuilder tx2;
    
    /**
     * Lista de erros encontrados durante a geração.
     * Coletados durante validações e formatação.
     */
    private List<String> erros;
    
    /**
     * Construtor do gerador TX2.
     * 
     * @param nfe NFe a ser processada
     * @throws IllegalArgumentException se NFe for nula
     */
    public GeradorTX2(NFe nfe) {
        if (nfe == null) {
            throw new IllegalArgumentException("NFe não pode ser nula");
        }
        this.nfe = nfe;
        this.tx2 = new StringBuilder();
        this.erros = new ArrayList<>();
    }
    
    /**
     * Gera o arquivo TX2 completo da NFe.
     * 
     * <p>Método principal que coordena todo o processo de geração,
     * seguindo a ordem exata da documentação TecnoSpeed.</p>
     * 
     * <h3>Ordem de Geração:</h3>
     * <ol>
     *   <li>Cabeçalho TX2 obrigatório</li>
     *   <li>Identificação (A02-B29)</li>
     *   <li>Emitente (C02-C21)</li>
     *   <li>Destinatário (E02-E19)</li>
     *   <li>Locais especiais (F02-F09, G02-G09)</li>
     *   <li>Itens (H02-V01)</li>
     *   <li>Totais (W03-W30)</li>
     *   <li>Transporte (X02-X34)</li>
     *   <li>Pagamentos (YA01-YB03)</li>
     *   <li>Informações finais (Z02-Z16, ZD02-ZD09)</li>
     * </ol>
     * 
     * @return String com TX2 completo, ou null se houver erros
     * 
     * @see #getErros()
     * @see #temErros()
     */
    public String gerarTX2Completo() {
        // Validar NFe antes de gerar
        if (!validarNFe()) {
            return null;
        }
        
        try {
            // Template Method - ordem conforme documentação
            adicionarCabecalhoTX2();
            gerarCabecalhoIdentificacao();   // A02-B29
            gerarEmitente();                 // C02-C21  
            gerarDestinatario();             // E02-E19
            gerarLocaisEspeciais();          // F02-F09, G02-G09
            gerarItens();                    // H02-V01
            gerarTotais();                   // W03-W30
            gerarTransporte();               // X02-X34
            gerarPagamentos();               // YA01-YB03
            gerarInformacoesFinais();        // Z02-Z16, ZD02-ZD09
            
            finalizarTX2();
            
            String tx2Resultado = tx2.toString();
            salvarTX2EmArquivo(tx2Resultado);
            
            return tx2Resultado;
        } catch (Exception e) {
            erros.add("Erro ao gerar TX2: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Verifica se existem erros de validação.
     * 
     * @return true se existem erros, false caso contrário
     */
    public boolean temErros() {
        return !erros.isEmpty();
    }
    
    /**
     * Obtém a lista de erros encontrados.
     * 
     * @return Lista de erros (nunca null)
     */
    public List<String> getErros() {
        return new ArrayList<>(erros);
    }
    
    /**
     * Valida a NFe antes da geração TX2.
     * 
     * <p>Executa todas as validações necessárias e coleta
     * os erros encontrados na lista interna.</p>
     * 
     * @return true se NFe está válida, false caso contrário
     */
    private boolean validarNFe() {
        // Validar estrutura básica
        if (nfe.getEmitente() == null) {
            erros.add("Emitente é obrigatório");
        }
        
        if (nfe.getDestinatario() == null) {
            erros.add("Destinatário é obrigatório");
        }
        
        if (nfe.getProdutos() == null || nfe.getProdutos().isEmpty()) {
            erros.add("Pelo menos um item é obrigatório");
        }
        
        if (nfe.getPagamentos() == null || nfe.getPagamentos().isEmpty()) {
            erros.add("Pelo menos uma forma de pagamento é obrigatória");
        }
        
        // Validar usando sistema de validação
        List<ValidacaoException> validacoes = ValidadorCampos.validar(nfe);
        for (ValidacaoException validacao : validacoes) {
            erros.add(validacao.getMessage());
        }
        
        return erros.isEmpty();
    }
    
    /**
     * Adiciona um campo ao TX2 com formatação padrão.
     * 
     * @param codigo Código do campo (ex: "versao_A02")
     * @param valor Valor do campo
     */
    private void adicionarCampo(String codigo, String valor) {
        if (valor != null && !valor.trim().isEmpty()) {
            tx2.append(codigo).append("=").append(valor);
        }
    }
    
    // ... outros métodos privados de geração
}
```

---

## 📄 **Comandos para Gerar JavaDoc**

### 🔧 **Script de Geração Completa**

```bash
#!/bin/bash
# gerar_javadoc.bat (Windows) ou gerar_javadoc.sh (Linux/Mac)

echo "Gerando JavaDoc da Biblioteca NFe..."

# Criar diretório de saída
mkdir -p docs/javadoc

# Gerar JavaDoc completo
javadoc \
  -d docs/javadoc \
  -cp src \
  -sourcepath src \
  -subpackages o.famoso.nfe \
  -private \
  -author \
  -version \
  -use \
  -splitindex \
  -windowtitle "Biblioteca NFe - O Famoso NFe" \
  -doctitle "O Famoso NFe - Biblioteca Completa para Geração de NFe" \
  -header "<b>O Famoso NFe v1.0</b>" \
  -footer "<b>O Famoso NFe v1.0</b>" \
  -bottom "Copyright © 2025 O Famoso - Rafael. Todos os direitos reservados." \
  -charset UTF-8 \
  -encoding UTF-8 \
  -docencoding UTF-8

echo "JavaDoc gerado em: docs/javadoc/index.html"
```

### 📋 **Estrutura de Saída**

```
docs/javadoc/
├── index.html                    # Página principal
├── allclasses-frame.html         # Lista de todas as classes
├── overview-summary.html         # Resumo geral
├── o/famoso/nfe/
│   ├── NFe.html                 # Documentação da classe NFe
│   ├── package-summary.html     # Resumo do package
│   ├── cliente/
│   │   ├── Cliente.html         # Documentação Cliente
│   │   ├── Endereco.html        # Documentação Endereco
│   │   └── package-summary.html
│   ├── item/
│   │   ├── ItemNFe.html         # Documentação ItemNFe
│   │   └── package-summary.html
│   └── gerador/
│       ├── GeradorTX2.html      # Documentação GeradorTX2
│       └── package-summary.html
├── stylesheet.css               # Estilos CSS
└── help-doc.html               # Ajuda do JavaDoc
```

---

## 📚 **Tags JavaDoc Utilizadas**

### 🏷️ **Tags Básicas**

| Tag | Uso | Exemplo |
|-----|-----|---------|
| `@author` | Autor da classe | `@author O Famoso - Rafael` |
| `@version` | Versão da classe | `@version 1.0` |
| `@since` | Versão de introdução | `@since 1.0` |
| `@param` | Parâmetro de método | `@param nfe NFe a ser processada` |
| `@return` | Valor de retorno | `@return true se válido` |
| `@throws` | Exceções lançadas | `@throws IllegalArgumentException se nulo` |
| `@see` | Referência cruzada | `@see Cliente` |
| `@deprecated` | Método obsoleto | `@deprecated Use novo método` |

### 🔗 **Tags de Referência**

| Tag | Uso | Exemplo |
|-----|-----|---------|
| `{@link}` | Link para classe/método | `{@link NFe#gerarTX2()}` |
| `{@code}` | Código inline | `{@code nfe.setEmitente(cliente)}` |
| `{@literal}` | Texto literal | `{@literal <xml>}` |
| `{@value}` | Valor de constante | `{@value #MAX_ITEMS}` |

### 📝 **Tags HTML Permitidas**

```html
<h2>Título de Seção</h2>
<h3>Subtítulo</h3>
<p>Parágrafo</p>
<ul><li>Lista</li></ul>
<ol><li>Lista numerada</li></ol>
<strong>Texto em negrito</strong>
<em>Texto em itálico</em>
<pre>Código pré-formatado</pre>
<code>Código inline</code>
<a href="url">Link externo</a>
```

---

## ✅ **Checklist de Documentação**

### 📋 **Para Cada Classe**

- [ ] Descrição geral da classe
- [ ] Funcionalidades principais
- [ ] Exemplo de uso básico
- [ ] Tags @author, @version, @since
- [ ] Referências para classes relacionadas

### 📋 **Para Cada Método Público**

- [ ] Descrição do que faz
- [ ] Parâmetros com @param
- [ ] Valor de retorno com @return
- [ ] Exceções com @throws
- [ ] Exemplo de uso (se complexo)
- [ ] Referências com @see

### 📋 **Para Cada Campo**

- [ ] Descrição do campo
- [ ] Código TX2 correspondente
- [ ] Se é obrigatório
- [ ] Tamanho e formato
- [ ] Valores válidos
- [ ] Regras de validação

---

**📖 JavaDoc completo para biblioteca profissional!**

Para gerar a documentação completa:
```bash
# Execute o script de geração
./gerar_javadoc.sh

# Abra no navegador
open docs/javadoc/index.html
``` 