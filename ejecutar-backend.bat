@echo off
echo ========================================
echo   Ejecutando Backend Volunpath
echo ========================================
echo.

REM Verificar que estamos en el directorio correcto
if not exist "pom.xml" (
    echo ERROR: No se encontro pom.xml
    echo Asegurate de ejecutar este script desde el directorio del proyecto
    pause
    exit /b 1
)

REM Verificar Java
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java no esta instalado o no esta en el PATH
    echo Por favor instala Java JDK 17 o superior
    pause
    exit /b 1
)

echo [1/3] Verificando Java...
java -version
echo.

echo [2/3] Compilando proyecto...
call mvnw.cmd clean compile
if errorlevel 1 (
    echo ERROR: Fallo la compilacion
    pause
    exit /b 1
)
echo.

echo [3/3] Iniciando servidor...
echo El backend estara disponible en: http://localhost:8080
echo Swagger UI: http://localhost:8080/swagger-ui.html
echo.
call mvnw.cmd spring-boot:run

pause

