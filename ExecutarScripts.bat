@echo off

cd backend

echo Executando script para iniciar o Docker Compose e executar os testes...
call IniciarMavenTests.bat

echo.
echo Executando script para desligar o Docker Compose apos os testes...
call DesligarDockerComposeTest.bat

echo.
echo Todos os scripts foram executados com sucesso.