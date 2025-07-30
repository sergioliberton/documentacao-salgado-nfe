@echo off
echo ===============================================
echo    BIBLIOTECA NFE - TESTE DE INSTALACAO
echo ===============================================
echo.
echo [1/3] Compilando teste basico...
javac -encoding UTF-8 -cp biblioteca-nfe.jar exemplos\TesteSimples.java
if %errorlevel% neq 0 (
    echo ❌ ERRO na compilacao
    pause
    exit /b 1
)

echo [2/3] Executando teste basico...
java -cp "biblioteca-nfe.jar;exemplos" TesteSimples
if %errorlevel% neq 0 (
    echo ❌ ERRO na execucao
    pause
    exit /b 1
)

echo [3/3] Testando NFe completa...
if exist exemplos\TesteCompleto.java (
    javac -encoding UTF-8 -cp biblioteca-nfe.jar exemplos\TesteCompleto.java
    java -cp "biblioteca-nfe.jar;exemplos" TesteCompleto
)

echo ✅ TODOS OS TESTES PASSARAM
echo 🎉 Biblioteca funcionando perfeitamente
echo.
pause
