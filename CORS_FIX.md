# Solución al Problema de CORS

## Problema Identificado

El frontend en `https://volunpath.netlify.app` no puede comunicarse con el backend en Railway debido a un error de CORS (Cross-Origin Resource Sharing).

**Error en consola:**
```
Access to XMLHttpRequest at 'https://backend-production-edc9.up.railway.app/api/v1/authentication/sign-up' 
from origin 'https://volunpath.netlify.app' has been blocked by CORS policy: 
Response to preflight request doesn't pass access control check: 
No 'Access-Control-Allow-Origin' header is present on the requested resource.
```

## Causa

El dominio `volunpath.netlify.app` **NO estaba** en la lista de orígenes permitidos en la configuración de CORS del backend.

## Solución Aplicada

Se agregó `https://volunpath.netlify.app` a la lista de orígenes permitidos en `application.properties`:

```properties
cors.allowed-origins=http://localhost:4200,https://volunpath.netlify.app,https://691e8e8e9eb1a676c7dbd9c5--movesys-front.netlify.app,https://movesys-front.netlify.app
```

## Verificación en Railway

Después de hacer push de los cambios, verifica que Railway tenga las variables de entorno correctas:

1. Ve a Railway → Tu proyecto → Variables
2. Verifica que NO haya una variable `CORS_ALLOWED_ORIGINS` que esté sobrescribiendo la configuración
3. Si existe, actualízala para incluir `https://volunpath.netlify.app`

## Orígenes Actualmente Permitidos

- `http://localhost:4200` - Desarrollo local
- `https://volunpath.netlify.app` - Producción (Netlify)
- `https://691e8e8e9eb1a676c7dbd9c5--movesys-front.netlify.app` - Preview de Netlify
- `https://movesys-front.netlify.app` - Otro dominio de Netlify

## Nota Importante

**Esto NO es un problema de base de datos.** La base de datos está funcionando correctamente. El problema es que el navegador bloquea las peticiones HTTP entre diferentes dominios por seguridad, y el backend debe explícitamente permitir estos orígenes mediante CORS.

## Próximos Pasos

1. Hacer commit y push de los cambios
2. Esperar a que Railway despliegue la nueva versión
3. Probar nuevamente el registro desde `volunpath.netlify.app`
4. Verificar que las peticiones funcionen correctamente

