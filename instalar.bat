@echo off
echo ===============================================
echo    BIBLIOTECA NFE - INSTALACAO
echo ===============================================
echo.
echo Esta biblioteca permite gerar NFe via arquivos TX2
echo compatíveis com o programa NeverStop da TecnoSpeed.
echo.
echo Para usar a biblioteca:
echo.
echo 1. Adicione biblioteca-nfe.jar ao classpath do seu projeto
echo 2. Importe as classes: import o.famoso.nfe.*;
echo 3. Veja os exemplos na pasta exemplos/
echo 4. Leia a documentacao nos arquivos .md
echo.
echo Exemplo de uso:
echo    javac -cp biblioteca-nfe.jar MeuCodigo.java
echo    java -cp "biblioteca-nfe.jar;." MeuCodigo
echo.
echo ✅ Biblioteca instalada e pronta para uso
echo.
echo 📚 Documentacao disponivel:
dir *.md /b
echo.
echo 🧪 Exemplos disponveis:
dir exemplos\*.java /b
echo.
pause
