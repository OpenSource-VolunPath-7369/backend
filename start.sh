#!/bin/sh
# Script de inicio para Railway
# Busca el JAR ejecutable y lo ejecuta con el puerto correcto

PORT=${PORT:-8080}
JAR_FILE=$(find target -name "*.jar" -not -name "*-sources.jar" -not -name "*-javadoc.jar" | head -1)

if [ -z "$JAR_FILE" ]; then
  echo "Error: No se encontró el archivo JAR en target/"
  exit 1
fi

echo "Iniciando aplicación con puerto: $PORT"
echo "Archivo JAR: $JAR_FILE"

exec java -Dserver.port=$PORT -jar "$JAR_FILE"


