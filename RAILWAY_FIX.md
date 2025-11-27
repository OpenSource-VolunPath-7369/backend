# Solución para el Error de Railway

## Problemas Identificados

1. **Perfil Maven inexistente**: Railway está intentando usar `-Pproduction` que no existe en el `pom.xml`
2. **Versión de Java incorrecta**: Railway detecta Java 21 pero el proyecto usa Java 17
3. **Tiempo de compilación agotado**: El build está tomando más de 10 minutos

## Soluciones Implementadas

### 1. Archivo `nixpacks.toml`
Este archivo configura Nixpacks (el builder de Railway) para:
- Usar Java 17 en lugar de Java 21
- Ejecutar el comando de build correcto (sin el flag `-Pproduction`)
- Optimizar el proceso de compilación

### 2. Archivo `railway.json`
Configuración alternativa para Railway que especifica:
- Comando de build sin el perfil inexistente
- Comando de inicio correcto

## Pasos para Aplicar la Solución

### Opción 1: Usar Nixpacks (Recomendado)
1. Asegúrate de que el archivo `nixpacks.toml` esté en la raíz del proyecto
2. En Railway, ve a la configuración del servicio
3. Verifica que el "Build Command" esté vacío o sea: `./mvnw clean package -DskipTests -B`
4. Verifica que el "Start Command" sea: `java -Dserver.port=$PORT $JAVA_OPTS -jar target/*.jar`

### Opción 2: Configurar Manualmente en Railway
1. Ve a tu proyecto en Railway
2. Selecciona el servicio "backend"
3. Ve a "Settings" → "Build & Deploy"
4. En "Build Command", cambia a:
   ```
   chmod +x mvnw && ./mvnw clean package -DskipTests -B
   ```
5. En "Start Command", asegúrate de que sea:
   ```
   java -Dserver.port=$PORT $JAVA_OPTS -jar target/*.jar
   ```
6. En "Nixpacks Config File Path", deja vacío o pon: `nixpacks.toml`

### Opción 3: Usar Dockerfile (Más rápido)
Si el build sigue siendo lento, puedes usar el Dockerfile existente:
1. En Railway, ve a "Settings" → "Build & Deploy"
2. Cambia "Build Command" a: (deja vacío)
3. Railway detectará automáticamente el Dockerfile y lo usará

## Optimizaciones Adicionales

### Para acelerar el build:

1. **Usar Dockerfile** (ya existe en el proyecto):
   - El Dockerfile usa multi-stage build
   - Cachea las dependencias de Maven
   - Reduce el tiempo de compilación

2. **Variables de entorno en Railway**:
   - Agrega `MAVEN_OPTS=-Xmx1024m -XX:MaxPermSize=256m` para limitar memoria
   - Esto puede ayudar a evitar timeouts

3. **Verificar dependencias**:
   - Asegúrate de que todas las dependencias en `pom.xml` sean necesarias
   - Las dependencias opcionales como `spring-boot-devtools` no afectan el build

## Verificación

Después de aplicar los cambios:
1. Haz commit y push de los archivos nuevos (`nixpacks.toml`, `railway.json`)
2. Railway debería detectar automáticamente los cambios
3. El build debería:
   - Usar Java 17
   - No intentar usar el perfil `-Pproduction`
   - Completarse en menos de 10 minutos

## Si el problema persiste

1. **Revisa los logs de Railway** para ver exactamente qué comando está ejecutando
2. **Verifica la versión de Java** en los logs (debe ser 17, no 21)
3. **Contacta soporte de Railway** si el timeout persiste después de optimizar

## Comandos de Build Correctos

✅ **Correcto:**
```bash
./mvnw clean package -DskipTests -B
```

❌ **Incorrecto (lo que Railway estaba usando):**
```bash
./mvnw clean dependency:list install -Pproduction
```

El flag `-Pproduction` no existe en tu `pom.xml`, por eso fallaba.

