package com.github.unqui

import java.time.LocalDateTime

class NoteService() {

    private val idGenerator = IdGenerator()
    val users = mutableListOf<User>()

    fun login(email: String, password: String): User {
        return users.find { it.email == email && it.password == password } ?: throw UserException("Not found user")
    }

    fun register(userDraft: UserDraft): User {
        if (users.any { userDraft.email == it.email }) throw UserException("Email used")
        val user = User(idGenerator.getUserId(), userDraft.email, userDraft.image, userDraft.password, userDraft.displayName, mutableListOf())
        users.add(user)
        return user
    }

    fun getUser(userId: String): User {
        return users.firstOrNull { it.id == userId } ?: throw UserException("User not found")
    }

    fun addNote(userId: String, noteDraft: NoteDraft): Note {
        val user = getUser(userId)
        val note = Note(idGenerator.getNoteId(), noteDraft.title, noteDraft.description, LocalDateTime.now())
        user.notes.add(note)
        return note
    }

    fun getNote(userId: String, noteId: String): Note {
        val user = getUser(userId)
        return user.notes.firstOrNull { it.id == noteId } ?: throw UserException("Not found note")
    }

    fun editNote(userId: String, noteId: String, noteDraft: NoteDraft): Note {
        val note = getNote(userId, noteId)
        note.title = noteDraft.title
        note.description = noteDraft.description
        note.lastModifiedDate = LocalDateTime.now()
        return note
    }

    fun removeNote(userId: String, noteId: String): User {
        val user = getUser(userId)
        user.notes.removeIf { it.id == noteId }
        return user
    }

}