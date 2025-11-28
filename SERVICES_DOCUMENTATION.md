# 5.2.2.6. Services Documentation Evidence for Sprint Review

Esta sección recopila los endpoints desarrollados y validados durante el Sprint, enfocados en la gestión de autenticación, usuarios, voluntarios, organizaciones, publicaciones, mensajería y notificaciones en la plataforma Volunpath. Se documenta el uso de los métodos HTTP correspondientes (GET, POST, PUT, DELETE) junto con ejemplos de llamadas, respuestas y validaciones funcionales. La documentación se ha generado mediante OpenAPI, permitiendo una visualización clara y organizada de los servicios.

**Base URL:** `http://localhost:8080/api/v1`

**Formato de Respuesta:** `application/json`

---

## 1. Autenticación (Authentication)

### 1.1. Iniciar Sesión
**Endpoint:** `POST /authentication/sign-in`

**Descripción:** Autentica un usuario en el sistema y devuelve un token JWT junto con la información del usuario autenticado.

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "username": "usuario123",
  "password": "password123"
}
```

**Response 200 OK:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "id": 1,
  "username": "usuario123",
  "email": "usuario@example.com",
  "name": "Usuario Ejemplo",
  "avatar": "data:image/png;base64,iVBORw0KGgo...",
  "roles": ["ROLE_VOLUNTEER"]
}
```

**Response 401 Unauthorized:**
```json
{
  "error": "Invalid credentials"
}
```

---

### 1.2. Registro de Usuario
**Endpoint:** `POST /authentication/sign-up`

**Descripción:** Registra un nuevo usuario en el sistema.

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "username": "nuevo_usuario",
  "email": "nuevo@example.com",
  "password": "password123",
  "name": "Nuevo Usuario",
  "avatar": "data:image/png;base64,iVBORw0KGgo..."
}
```

**Response 201 Created:**
```json
{
  "id": 2,
  "username": "nuevo_usuario",
  "email": "nuevo@example.com",
  "name": "Nuevo Usuario",
  "avatar": "data:image/png;base64,iVBORw0KGgo...",
  "roles": ["ROLE_USER"]
}
```

**Response 400 Bad Request:**
```json
{
  "error": "Username already exists"
}
```

---

## 2. Usuarios (Users)

### 2.1. Obtener Todos los Usuarios
**Endpoint:** `GET /users`

**Descripción:** Obtiene la lista de todos los usuarios registrados en el sistema.

**Headers:**
```
Authorization: Bearer {token}
```

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "username": "usuario123",
    "email": "usuario@example.com",
    "name": "Usuario Ejemplo",
    "avatar": "data:image/png;base64,iVBORw0KGgo...",
    "roles": ["ROLE_VOLUNTEER"]
  },
  {
    "id": 2,
    "username": "org_admin",
    "email": "admin@org.com",
    "name": "Admin Organización",
    "avatar": null,
    "roles": ["ROLE_ORGANIZATION_ADMIN"]
  }
]
```

---

### 2.2. Obtener Usuario por ID
**Endpoint:** `GET /users/{id}`

**Descripción:** Obtiene la información de un usuario específico por su ID.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `id` (Long): ID del usuario

**Response 200 OK:**
```json
{
  "id": 1,
  "username": "usuario123",
  "email": "usuario@example.com",
  "name": "Usuario Ejemplo",
  "avatar": "data:image/png;base64,iVBORw0KGgo...",
  "roles": ["ROLE_VOLUNTEER"]
}
```

**Response 404 Not Found:**
```json
{
  "error": "User not found"
}
```

---

## 3. Voluntarios (Volunteers)

### 3.1. Obtener Todos los Voluntarios
**Endpoint:** `GET /volunteers`

**Descripción:** Obtiene la lista de todos los voluntarios registrados.

**Headers:**
```
Authorization: Bearer {token}
```

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "userId": 1,
    "name": "Juan Pérez",
    "email": "juan@example.com",
    "phone": "+1234567890",
    "avatar": "data:image/png;base64,iVBORw0KGgo...",
    "bio": "Voluntario apasionado por el medio ambiente",
    "skills": ["Comunicación", "Liderazgo"],
    "interests": ["Medio Ambiente", "Educación"],
    "location": "Ciudad, País",
    "createdAt": "2024-01-15T10:30:00Z"
  }
]
```

---

### 3.2. Obtener Voluntario por ID
**Endpoint:** `GET /volunteers/{id}`

**Descripción:** Obtiene la información de un voluntario específico.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `id` (Long): ID del voluntario

**Response 200 OK:**
```json
{
  "id": 1,
  "userId": 1,
  "name": "Juan Pérez",
  "email": "juan@example.com",
  "phone": "+1234567890",
  "avatar": "data:image/png;base64,iVBORw0KGgo...",
  "bio": "Voluntario apasionado por el medio ambiente",
  "skills": ["Comunicación", "Liderazgo"],
  "interests": ["Medio Ambiente", "Educación"],
  "location": "Ciudad, País",
  "createdAt": "2024-01-15T10:30:00Z"
}
```

---

### 3.3. Obtener Voluntario por User ID
**Endpoint:** `GET /volunteers/user/{userId}`

**Descripción:** Obtiene el perfil de voluntario asociado a un usuario específico.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `userId` (Long): ID del usuario

**Response 200 OK:**
```json
{
  "id": 1,
  "userId": 1,
  "name": "Juan Pérez",
  "email": "juan@example.com",
  "phone": "+1234567890",
  "avatar": "data:image/png;base64,iVBORw0KGgo...",
  "bio": "Voluntario apasionado por el medio ambiente",
  "skills": ["Comunicación", "Liderazgo"],
  "interests": ["Medio Ambiente", "Educación"],
  "location": "Ciudad, País",
  "createdAt": "2024-01-15T10:30:00Z"
}
```

---

### 3.4. Crear Voluntario
**Endpoint:** `POST /volunteers`

**Descripción:** Crea un nuevo perfil de voluntario.

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "userId": 1,
  "name": "Juan Pérez",
  "email": "juan@example.com",
  "phone": "+1234567890",
  "avatar": "data:image/png;base64,iVBORw0KGgo...",
  "bio": "Voluntario apasionado por el medio ambiente",
  "skills": ["Comunicación", "Liderazgo"],
  "interests": ["Medio Ambiente", "Educación"],
  "location": "Ciudad, País"
}
```

**Response 201 Created:**
```json
{
  "id": 1,
  "userId": 1,
  "name": "Juan Pérez",
  "email": "juan@example.com",
  "phone": "+1234567890",
  "avatar": "data:image/png;base64,iVBORw0KGgo...",
  "bio": "Voluntario apasionado por el medio ambiente",
  "skills": ["Comunicación", "Liderazgo"],
  "interests": ["Medio Ambiente", "Educación"],
  "location": "Ciudad, País",
  "createdAt": "2024-01-15T10:30:00Z"
}
```

---

### 3.5. Actualizar Voluntario
**Endpoint:** `PUT /volunteers/{id}`

**Descripción:** Actualiza la información de un voluntario existente.

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Path Parameters:**
- `id` (Long): ID del voluntario

**Request Body:**
```json
{
  "name": "Juan Pérez Actualizado",
  "email": "juan.actualizado@example.com",
  "phone": "+1234567890",
  "avatar": "data:image/png;base64,iVBORw0KGgo...",
  "bio": "Bio actualizada",
  "skills": ["Comunicación", "Liderazgo", "Nueva Habilidad"],
  "interests": ["Medio Ambiente", "Educación", "Tecnología"],
  "location": "Nueva Ciudad, País"
}
```

**Response 200 OK:**
```json
{
  "id": 1,
  "userId": 1,
  "name": "Juan Pérez Actualizado",
  "email": "juan.actualizado@example.com",
  "phone": "+1234567890",
  "avatar": "data:image/png;base64,iVBORw0KGgo...",
  "bio": "Bio actualizada",
  "skills": ["Comunicación", "Liderazgo", "Nueva Habilidad"],
  "interests": ["Medio Ambiente", "Educación", "Tecnología"],
  "location": "Nueva Ciudad, País",
  "createdAt": "2024-01-15T10:30:00Z"
}
```

---

### 3.6. Eliminar Voluntario
**Endpoint:** `DELETE /volunteers/{id}`

**Descripción:** Elimina un perfil de voluntario.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `id` (Long): ID del voluntario

**Response 204 No Content**

**Response 404 Not Found:**
```json
{
  "error": "Volunteer not found"
}
```

---

## 4. Organizaciones (Organizations)

### 4.1. Obtener Todas las Organizaciones
**Endpoint:** `GET /organizations`

**Descripción:** Obtiene la lista de todas las organizaciones registradas.

**Headers:**
```
Authorization: Bearer {token}
```

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "userId": 2,
    "name": "Organización Verde",
    "email": "contacto@orgverde.com",
    "phone": "+1234567890",
    "logo": "data:image/png;base64,iVBORw0KGgo...",
    "description": "Organización dedicada al cuidado del medio ambiente",
    "website": "https://www.orgverde.com",
    "address": "Calle Principal 123, Ciudad",
    "foundedYear": 2020,
    "volunteerCount": 50,
    "rating": 4.5,
    "categories": ["Medio Ambiente", "Conservación"],
    "isVerified": true,
    "socialMedia": {
      "facebook": "https://facebook.com/orgverde",
      "instagram": "https://instagram.com/orgverde",
      "twitter": "https://twitter.com/orgverde"
    }
  }
]
```

---

### 4.2. Obtener Organización por ID
**Endpoint:** `GET /organizations/{id}`

**Descripción:** Obtiene la información de una organización específica.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `id` (Long): ID de la organización

**Response 200 OK:**
```json
{
  "id": 1,
  "userId": 2,
  "name": "Organización Verde",
  "email": "contacto@orgverde.com",
  "phone": "+1234567890",
  "logo": "data:image/png;base64,iVBORw0KGgo...",
  "description": "Organización dedicada al cuidado del medio ambiente",
  "website": "https://www.orgverde.com",
  "address": "Calle Principal 123, Ciudad",
  "foundedYear": 2020,
  "volunteerCount": 50,
  "rating": 4.5,
  "categories": ["Medio Ambiente", "Conservación"],
  "isVerified": true,
  "socialMedia": {
    "facebook": "https://facebook.com/orgverde",
    "instagram": "https://instagram.com/orgverde",
    "twitter": "https://twitter.com/orgverde"
  }
}
```

---

### 4.3. Obtener Organización por User ID
**Endpoint:** `GET /organizations/user/{userId}`

**Descripción:** Obtiene el perfil de organización asociado a un usuario específico.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `userId` (Long): ID del usuario

**Response 200 OK:**
```json
{
  "id": 1,
  "userId": 2,
  "name": "Organización Verde",
  "email": "contacto@orgverde.com",
  "phone": "+1234567890",
  "logo": "data:image/png;base64,iVBORw0KGgo...",
  "description": "Organización dedicada al cuidado del medio ambiente",
  "website": "https://www.orgverde.com",
  "address": "Calle Principal 123, Ciudad",
  "foundedYear": 2020,
  "volunteerCount": 50,
  "rating": 4.5,
  "categories": ["Medio Ambiente", "Conservación"],
  "isVerified": true,
  "socialMedia": {
    "facebook": "https://facebook.com/orgverde",
    "instagram": "https://instagram.com/orgverde",
    "twitter": "https://twitter.com/orgverde"
  }
}
```

---

### 4.4. Crear Organización
**Endpoint:** `POST /organizations`

**Descripción:** Crea un nuevo perfil de organización.

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "userId": 2,
  "name": "Organización Verde",
  "email": "contacto@orgverde.com",
  "phone": "+1234567890",
  "logo": "data:image/png;base64,iVBORw0KGgo...",
  "description": "Organización dedicada al cuidado del medio ambiente",
  "website": "https://www.orgverde.com",
  "address": "Calle Principal 123, Ciudad",
  "foundedYear": 2020,
  "categories": ["Medio Ambiente", "Conservación"],
  "socialMedia": {
    "facebook": "https://facebook.com/orgverde",
    "instagram": "https://instagram.com/orgverde",
    "twitter": "https://twitter.com/orgverde"
  }
}
```

**Response 201 Created:**
```json
{
  "id": 1,
  "userId": 2,
  "name": "Organización Verde",
  "email": "contacto@orgverde.com",
  "phone": "+1234567890",
  "logo": "data:image/png;base64,iVBORw0KGgo...",
  "description": "Organización dedicada al cuidado del medio ambiente",
  "website": "https://www.orgverde.com",
  "address": "Calle Principal 123, Ciudad",
  "foundedYear": 2020,
  "volunteerCount": 0,
  "rating": 0.0,
  "categories": ["Medio Ambiente", "Conservación"],
  "isVerified": false,
  "socialMedia": {
    "facebook": "https://facebook.com/orgverde",
    "instagram": "https://instagram.com/orgverde",
    "twitter": "https://twitter.com/orgverde"
  }
}
```

---

### 4.5. Actualizar Organización
**Endpoint:** `PUT /organizations/{id}`

**Descripción:** Actualiza la información de una organización existente.

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Path Parameters:**
- `id` (Long): ID de la organización

**Request Body:**
```json
{
  "name": "Organización Verde Actualizada",
  "email": "nuevo@orgverde.com",
  "phone": "+1234567890",
  "logo": "data:image/png;base64,iVBORw0KGgo...",
  "description": "Descripción actualizada",
  "website": "https://www.orgverde-nuevo.com",
  "address": "Nueva Dirección 456, Ciudad",
  "foundedYear": 2021,
  "categories": ["Medio Ambiente", "Conservación", "Educación"],
  "socialMedia": {
    "facebook": "https://facebook.com/orgverde-nuevo",
    "instagram": "https://instagram.com/orgverde-nuevo",
    "twitter": "https://twitter.com/orgverde-nuevo"
  }
}
```

**Response 200 OK:**
```json
{
  "id": 1,
  "userId": 2,
  "name": "Organización Verde Actualizada",
  "email": "nuevo@orgverde.com",
  "phone": "+1234567890",
  "logo": "data:image/png;base64,iVBORw0KGgo...",
  "description": "Descripción actualizada",
  "website": "https://www.orgverde-nuevo.com",
  "address": "Nueva Dirección 456, Ciudad",
  "foundedYear": 2021,
  "volunteerCount": 50,
  "rating": 4.5,
  "categories": ["Medio Ambiente", "Conservación", "Educación"],
  "isVerified": true,
  "socialMedia": {
    "facebook": "https://facebook.com/orgverde-nuevo",
    "instagram": "https://instagram.com/orgverde-nuevo",
    "twitter": "https://twitter.com/orgverde-nuevo"
  }
}
```

---

### 4.6. Eliminar Organización
**Endpoint:** `DELETE /organizations/{id}`

**Descripción:** Elimina un perfil de organización.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `id` (Long): ID de la organización

**Response 204 No Content**

**Response 404 Not Found:**
```json
{
  "error": "Organization not found"
}
```

---

## 5. Publicaciones (Publications)

### 5.1. Obtener Todas las Publicaciones
**Endpoint:** `GET /publications`

**Descripción:** Obtiene la lista de todas las publicaciones.

**Headers:**
```
Authorization: Bearer {token}
```

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "organizationId": 1,
    "title": "Campaña de Limpieza de Playas",
    "description": "Únete a nuestra campaña de limpieza de playas este sábado",
    "image": "data:image/png;base64,iVBORw0KGgo...",
    "status": "published",
    "likes": 25,
    "createdAt": "2024-01-15T10:30:00Z"
  }
]
```

---

### 5.2. Obtener Publicación por ID
**Endpoint:** `GET /publications/{id}`

**Descripción:** Obtiene la información de una publicación específica.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `id` (Long): ID de la publicación

**Response 200 OK:**
```json
{
  "id": 1,
  "organizationId": 1,
  "title": "Campaña de Limpieza de Playas",
  "description": "Únete a nuestra campaña de limpieza de playas este sábado",
  "image": "data:image/png;base64,iVBORw0KGgo...",
  "status": "published",
  "likes": 25,
  "createdAt": "2024-01-15T10:30:00Z"
}
```

---

### 5.3. Obtener Publicaciones por Organización
**Endpoint:** `GET /publications/organization/{organizationId}`

**Descripción:** Obtiene todas las publicaciones de una organización específica.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `organizationId` (Long): ID de la organización

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "organizationId": 1,
    "title": "Campaña de Limpieza de Playas",
    "description": "Únete a nuestra campaña de limpieza de playas este sábado",
    "image": "data:image/png;base64,iVBORw0KGgo...",
    "status": "published",
    "likes": 25,
    "createdAt": "2024-01-15T10:30:00Z"
  },
  {
    "id": 2,
    "organizationId": 1,
    "title": "Taller de Reciclaje",
    "description": "Aprende técnicas de reciclaje en nuestro taller gratuito",
    "image": "data:image/png;base64,iVBORw0KGgo...",
    "status": "published",
    "likes": 15,
    "createdAt": "2024-01-20T14:00:00Z"
  }
]
```

---

### 5.4. Crear Publicación
**Endpoint:** `POST /publications`

**Descripción:** Crea una nueva publicación.

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "organizationId": 1,
  "title": "Nueva Campaña de Voluntariado",
  "description": "Descripción de la nueva campaña",
  "image": "data:image/png;base64,iVBORw0KGgo...",
  "status": "draft"
}
```

**Response 201 Created:**
```json
{
  "id": 3,
  "organizationId": 1,
  "title": "Nueva Campaña de Voluntariado",
  "description": "Descripción de la nueva campaña",
  "image": "data:image/png;base64,iVBORw0KGgo...",
  "status": "draft",
  "likes": 0,
  "createdAt": "2024-01-25T09:00:00Z"
}
```

---

### 5.5. Dar Like a una Publicación
**Endpoint:** `PUT /publications/{id}/like`

**Descripción:** Incrementa el contador de likes de una publicación.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `id` (Long): ID de la publicación

**Response 200 OK:**
```json
{
  "id": 1,
  "organizationId": 1,
  "title": "Campaña de Limpieza de Playas",
  "description": "Únete a nuestra campaña de limpieza de playas este sábado",
  "image": "data:image/png;base64,iVBORw0KGgo...",
  "status": "published",
  "likes": 26,
  "createdAt": "2024-01-15T10:30:00Z"
}
```

---

### 5.6. Eliminar Publicación
**Endpoint:** `DELETE /publications/{id}`

**Descripción:** Elimina una publicación.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `id` (Long): ID de la publicación

**Response 204 No Content**

**Response 404 Not Found:**
```json
{
  "error": "Publication not found"
}
```

---

## 6. Mensajes (Messages)

### 6.1. Obtener Mensajes por User ID
**Endpoint:** `GET /messages/user/{userId}`

**Descripción:** Obtiene todos los mensajes de un usuario específico (tanto enviados como recibidos).

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `userId` (Long): ID del usuario

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "senderId": 1,
    "senderName": "Juan Pérez",
    "senderIcon": "data:image/png;base64,iVBORw0KGgo...",
    "recipientId": 2,
    "content": "Hola, me interesa participar en tu campaña",
    "type": "general",
    "isRead": false,
    "timestamp": "2024-01-15T10:30:00Z"
  },
  {
    "id": 2,
    "senderId": 2,
    "senderName": "Organización Verde",
    "senderIcon": "data:image/png;base64,iVBORw0KGgo...",
    "recipientId": 1,
    "content": "¡Gracias por tu interés! Te contactaremos pronto",
    "type": "general",
    "isRead": true,
    "timestamp": "2024-01-15T11:00:00Z"
  }
]
```

---

### 6.2. Obtener Mensaje por ID
**Endpoint:** `GET /messages/{id}`

**Descripción:** Obtiene la información de un mensaje específico.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `id` (Long): ID del mensaje

**Response 200 OK:**
```json
{
  "id": 1,
  "senderId": 1,
  "senderName": "Juan Pérez",
  "senderIcon": "data:image/png;base64,iVBORw0KGgo...",
  "recipientId": 2,
  "content": "Hola, me interesa participar en tu campaña",
  "type": "general",
  "isRead": false,
  "timestamp": "2024-01-15T10:30:00Z"
}
```

---

### 6.3. Crear Mensaje
**Endpoint:** `POST /messages`

**Descripción:** Crea un nuevo mensaje.

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "senderId": 1,
  "senderName": "Juan Pérez",
  "senderIcon": "data:image/png;base64,iVBORw0KGgo...",
  "recipientId": 2,
  "content": "Hola, me interesa participar en tu campaña",
  "type": "general"
}
```

**Response 201 Created:**
```json
{
  "id": 3,
  "senderId": 1,
  "senderName": "Juan Pérez",
  "senderIcon": "data:image/png;base64,iVBORw0KGgo...",
  "recipientId": 2,
  "content": "Hola, me interesa participar en tu campaña",
  "type": "general",
  "isRead": false,
  "timestamp": "2024-01-25T12:00:00Z"
}
```

---

### 6.4. Marcar Mensaje como Leído
**Endpoint:** `PUT /messages/{id}/read`

**Descripción:** Marca un mensaje como leído.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `id` (Long): ID del mensaje

**Response 200 OK:**
```json
{
  "id": 1,
  "senderId": 1,
  "senderName": "Juan Pérez",
  "senderIcon": "data:image/png;base64,iVBORw0KGgo...",
  "recipientId": 2,
  "content": "Hola, me interesa participar en tu campaña",
  "type": "general",
  "isRead": true,
  "timestamp": "2024-01-15T10:30:00Z"
}
```

---

### 6.5. Eliminar Mensaje
**Endpoint:** `DELETE /messages/{id}`

**Descripción:** Elimina un mensaje.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `id` (Long): ID del mensaje

**Response 204 No Content**

**Response 404 Not Found:**
```json
{
  "error": "Message not found"
}
```

---

## 7. Notificaciones (Notifications)

### 7.1. Obtener Notificaciones por User ID
**Endpoint:** `GET /notifications/user/{userId}`

**Descripción:** Obtiene todas las notificaciones de un usuario específico.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `userId` (Long): ID del usuario

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "userId": 1,
    "title": "Nuevo mensaje",
    "message": "Organización Verde te envió un mensaje: Campaña de Limpieza",
    "type": "new_message",
    "isRead": false,
    "createdAt": "2024-01-15T10:30:00Z",
    "actionUrl": "/mensajes"
  },
  {
    "id": 2,
    "userId": 1,
    "title": "Nueva actividad",
    "message": "Se ha publicado una nueva actividad: Taller de Reciclaje",
    "type": "new_activity",
    "isRead": true,
    "createdAt": "2024-01-14T09:00:00Z",
    "actionUrl": "/publicaciones"
  }
]
```

---

### 7.2. Obtener Notificación por ID
**Endpoint:** `GET /notifications/{id}`

**Descripción:** Obtiene la información de una notificación específica.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `id` (Long): ID de la notificación

**Response 200 OK:**
```json
{
  "id": 1,
  "userId": 1,
  "title": "Nuevo mensaje",
  "message": "Organización Verde te envió un mensaje: Campaña de Limpieza",
  "type": "new_message",
  "isRead": false,
  "createdAt": "2024-01-15T10:30:00Z",
  "actionUrl": "/mensajes"
}
```

---

### 7.3. Crear Notificación
**Endpoint:** `POST /notifications`

**Descripción:** Crea una nueva notificación.

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "userId": 1,
  "title": "Nuevo mensaje",
  "message": "Organización Verde te envió un mensaje: Campaña de Limpieza",
  "type": "new_message",
  "actionUrl": "/mensajes"
}
```

**Response 201 Created:**
```json
{
  "id": 3,
  "userId": 1,
  "title": "Nuevo mensaje",
  "message": "Organización Verde te envió un mensaje: Campaña de Limpieza",
  "type": "new_message",
  "isRead": false,
  "createdAt": "2024-01-25T12:00:00Z",
  "actionUrl": "/mensajes"
}
```

---

### 7.4. Marcar Notificación como Leída
**Endpoint:** `PUT /notifications/{id}/read`

**Descripción:** Marca una notificación como leída.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `id` (Long): ID de la notificación

**Response 200 OK:**
```json
{
  "id": 1,
  "userId": 1,
  "title": "Nuevo mensaje",
  "message": "Organización Verde te envió un mensaje: Campaña de Limpieza",
  "type": "new_message",
  "isRead": true,
  "createdAt": "2024-01-15T10:30:00Z",
  "actionUrl": "/mensajes"
}
```

---

### 7.5. Marcar Todas las Notificaciones como Leídas
**Endpoint:** `PUT /notifications/user/{userId}/read-all`

**Descripción:** Marca todas las notificaciones de un usuario como leídas.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `userId` (Long): ID del usuario

**Response 200 OK**

---

### 7.6. Eliminar Notificación
**Endpoint:** `DELETE /notifications/{id}`

**Descripción:** Elimina una notificación.

**Headers:**
```
Authorization: Bearer {token}
```

**Path Parameters:**
- `id` (Long): ID de la notificación

**Response 204 No Content**

**Response 404 Not Found:**
```json
{
  "error": "Notification not found"
}
```

---

## Validaciones y Códigos de Estado HTTP

### Códigos de Estado Comunes

- **200 OK**: La solicitud fue exitosa
- **201 Created**: Recurso creado exitosamente
- **204 No Content**: Operación exitosa sin contenido de respuesta
- **400 Bad Request**: Solicitud inválida (validación fallida)
- **401 Unauthorized**: No autenticado o token inválido
- **403 Forbidden**: No tiene permisos para realizar la acción
- **404 Not Found**: Recurso no encontrado
- **500 Internal Server Error**: Error interno del servidor

### Validaciones Implementadas

1. **Autenticación:**
   - Username: mínimo 3 caracteres, único
   - Email: formato válido, único
   - Password: mínimo 6 caracteres

2. **Voluntarios:**
   - Email: formato válido
   - Phone: formato opcional
   - Avatar: máximo 100KB (base64)

3. **Organizaciones:**
   - Email: formato válido, único
   - Name: máximo 200 caracteres
   - Description: máximo 2000 caracteres
   - Logo: máximo 100KB (base64)

4. **Publicaciones:**
   - Title: requerido
   - Status: debe ser uno de: `draft`, `published`, `archived`

5. **Mensajes:**
   - Content: requerido
   - Type: debe ser uno de: `volunteer_inquiry`, `activity_details`, `general`, `system`

6. **Notificaciones:**
   - Title: requerido
   - Message: requerido
   - Type: debe ser uno de: `new_activity`, `new_message`, `activity_confirmed`, `activity_cancelled`, `general`

---

## Documentación OpenAPI/Swagger

La documentación interactiva de la API está disponible en:

**URL:** `http://localhost:8080/swagger-ui.html`

Esta documentación permite:
- Visualizar todos los endpoints disponibles
- Probar los endpoints directamente desde el navegador
- Ver los esquemas de request y response
- Validar los modelos de datos

---

## Notas de Implementación

1. **Autenticación JWT:**
   - Todos los endpoints (excepto `/authentication/sign-in` y `/authentication/sign-up`) requieren un token JWT en el header `Authorization: Bearer {token}`
   - El token expira después de un período configurado (ver `application.properties`)

2. **CORS:**
   - Configurado para permitir solicitudes desde `http://localhost:4200` (frontend Angular)
   - Métodos permitidos: GET, POST, PUT, DELETE, OPTIONS
   - Headers permitidos: Authorization, Content-Type

3. **Base de Datos:**
   - MySQL como base de datos principal
   - JPA/Hibernate para el mapeo objeto-relacional
   - `ddl-auto=update` para actualización automática del esquema

4. **Imágenes:**
   - Las imágenes se almacenan como strings base64 en campos `LONGTEXT`
   - Límite máximo: 100KB por imagen (validado en el backend)

---

## Evidencia de Validación Funcional

Durante el Sprint se validaron los siguientes escenarios:

✅ **Autenticación:**
- Registro de usuarios (voluntarios y organizaciones)
- Inicio de sesión con generación de token JWT
- Validación de credenciales incorrectas

✅ **Gestión de Perfiles:**
- Creación de perfiles de voluntarios y organizaciones
- Actualización de información de perfiles
- Consulta de perfiles por ID y por User ID

✅ **Publicaciones:**
- Creación de publicaciones por organizaciones
- Listado de publicaciones por organización
- Sistema de likes en publicaciones

✅ **Mensajería:**
- Envío de mensajes entre usuarios
- Recepción de mensajes (voluntarios y organizaciones)
- Marcado de mensajes como leídos

✅ **Notificaciones:**
- Creación automática de notificaciones al recibir mensajes
- Consulta de notificaciones por usuario
- Marcado de notificaciones como leídas

---

**Fecha de Documentación:** Enero 2024  
**Versión de la API:** 1.0  
**Sprint:** Sprint Review - Volunpath Platform

