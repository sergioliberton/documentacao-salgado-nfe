# 📦 Biblioteca NFe "O Famoso NFe" - v1.0

## 🚀 Instalação Rápida

### Requisitos
- Java 8 ou superior
- Sistema compatível: Windows, Linux, macOS

### Passos de Instalação
1. **Baixe** o arquivo `biblioteca-nfe.jar`
2. **Adicione** ao classpath do seu projeto:
   ```bash
   java -cp "biblioteca-nfe.jar;seu-projeto.jar" SuaClasse
   ```
3. **Importe** as classes em seu código:
   ```java
   import o.famoso.nfe.*;
   import o.famoso.nfe.cliente.*;
   import o.famoso.nfe.enums.*;
   ```

### Exemplo Básico de Uso
```java
import o.famoso.nfe.*;
import o.famoso.nfe.enums.*;

public class MeuExemplo {
    public static void main(String[] args) {
        NFe nfe = new NFe();
        nfe.setIdentificacaoAmbiente(IdentificacaoAmbiente.homologacao);
        nfe.setTipoOperacao(TipoOperacao.saida);
ECHO est� desativado.
        // Configure emitente, destinatário, itens...
ECHO est� desativado.
        String tx2 = nfe.gerarTX2();
        System.out.println("TX2 gerado: " + tx2);
    }
}
```

## 📚 Documentação Completa

- **README.md** - Guia básico de uso
- **GUIA_AMBIENTES.md** - Configuração de homologação/produção
- **EXEMPLOS_USO.md** - 10 cenários práticos completos
- **GUIA_CAMPOS_NFE.md** - Mapeamento de todos os campos NFe
- **GUIA_VALIDACOES.md** - Sistema de validações
- **JAVADOC_EXEMPLO.md** - Documentação das classes

## 🧪 Testando a Instalação

1. Compile um exemplo:
   ```bash
   javac -cp biblioteca-nfe.jar exemplos\TesteSimples.java
   ```

2. Execute o teste:
   ```bash
   java -cp "biblioteca-nfe.jar;." TesteSimples
   ```

## 📞 Suporte

- **Versão**: 1.0 - Janeiro 2025
- **Autor**: O Famoso - Rafael  
- **Licença**: MIT
- **Repositório**: [Em desenvolvimento]

### Problemas Comuns

**Erro de ClassPath:**
```bash
# Certifique-se de incluir o JAR no classpath
java -cp "biblioteca-nfe.jar;." SuaClasse
```

**Erro de Encoding:**
```bash
# Compile com encoding UTF-8
javac -encoding UTF-8 -cp biblioteca-nfe.jar SeuArquivo.java
```

**🎉 Biblioteca pronta para uso**
