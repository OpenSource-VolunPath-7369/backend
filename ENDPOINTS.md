# Endpoints de la API Volunpath

**Base URL:** `http://localhost:8080/api/v1`

---

## Autenticación

- `POST /authentication/sign-in` - Iniciar sesión
- `POST /authentication/sign-up` - Registro de usuario

---

## Usuarios

- `GET /users` - Obtener todos los usuarios
- `GET /users/{id}` - Obtener usuario por ID

---

## Voluntarios

- `GET /volunteers` - Obtener todos los voluntarios
- `GET /volunteers/{id}` - Obtener voluntario por ID
- `GET /volunteers/user/{userId}` - Obtener voluntario por User ID
- `POST /volunteers` - Crear voluntario
- `PUT /volunteers/{id}` - Actualizar voluntario
- `DELETE /volunteers/{id}` - Eliminar voluntario

---

## Organizaciones

- `GET /organizations` - Obtener todas las organizaciones
- `GET /organizations/{id}` - Obtener organización por ID
- `GET /organizations/user/{userId}` - Obtener organización por User ID
- `POST /organizations` - Crear organización
- `PUT /organizations/{id}` - Actualizar organización
- `DELETE /organizations/{id}` - Eliminar organización

---

## Publicaciones

- `GET /publications` - Obtener todas las publicaciones
- `GET /publications/{id}` - Obtener publicación por ID
- `GET /publications/organization/{organizationId}` - Obtener publicaciones por organización
- `POST /publications` - Crear publicación
- `PUT /publications/{id}/like` - Dar like a una publicación
- `DELETE /publications/{id}` - Eliminar publicación

---

## Mensajes

- `GET /messages/user/{userId}` - Obtener mensajes por User ID
- `GET /messages/{id}` - Obtener mensaje por ID
- `POST /messages` - Crear mensaje
- `PUT /messages/{id}/read` - Marcar mensaje como leído
- `DELETE /messages/{id}` - Eliminar mensaje

---

## Notificaciones

- `GET /notifications/user/{userId}` - Obtener notificaciones por User ID
- `GET /notifications/{id}` - Obtener notificación por ID
- `POST /notifications` - Crear notificación
- `PUT /notifications/{id}/read` - Marcar notificación como leída
- `PUT /notifications/user/{userId}/read-all` - Marcar todas las notificaciones como leídas
- `DELETE /notifications/{id}` - Eliminar notificación

---

**Total de Endpoints:** 33

