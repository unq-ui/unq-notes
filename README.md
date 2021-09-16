# UNQ » UIs » TP Notes

[![](https://jitpack.io/v/unq-ui/unq-notes.svg)](https://jitpack.io/#unq-ui/unq-notes)


Notes es una plataforma donde los usuarios pueden agregar/modificar/notas notas.

## Especificación de Dominio

### Dependencia

Agregar el repositorio:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Agregar la dependencia:

```xml
<dependency>
    <groupId>com.github.unq-ui</groupId>
    <artifactId>unq-notes</artifactId>
    <version>v1.0.0</version>
</dependency>
```

Toda interacción con el dominio se hace a través de la clase `NoteService`. La programación del dominio ya es proveído por la cátedra.

Se tiene que instanciar el sistema de la siguiente forma:

```kotlin
val spotifyService = getNoteService()
```

### NoteService

```kotlin

// @Throw UserException si el user no existe
fun login(email: String, password: String): User {
    return users.find { it.email == email && it.password == password } ?: throw UserException("Not found user")
}

// @Throw UserException si el email esta en uso
fun register(userDraft: UserDraft): User

// @Throw UserException si el userId no existe
fun getUser(userId: String): User

// @Throw UserException si el userId no existe
fun addNote(userId: String, noteDraft: NoteDraft): Note

// @Throw UserException si el userId o el noteId no existen
fun getNote(userId: String, noteId: String): Note

// @Throw UserException si el userId o el noteId no existen
fun editNote(userId: String, noteId: String, noteDraft: NoteDraft): Note

// @Throw UserException si el userId no existe
fun removeNote(userId: String, noteId: String): User

```

### Note

```kotlin
class Note(
    val id: String,
    var title: String,
    var description: String,
    var lastModifiedDate: LocalDateTime
)
```

### User

```kotlin
class User(
    val id: String,
    val email: String,
    var image: String,
    var password: String,
    var displayName: String,
    val notes: MutableList<Note>
)
```

### NoteDraft

Es la representación de una nota antes de ser guardada por el sistema

```kotlin
class NoteDraft(
    var title: String,
    var description: String
)
```

### UserDraft

Es la representación de un usuario antes de ser guardado por el sistema

```kotlin
class UserDraft(
    var email: String,
    var image: String,
    var password: String,
    var displayName: String
)
```
