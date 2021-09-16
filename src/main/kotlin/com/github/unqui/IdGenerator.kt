package com.github.unqui

class IdGenerator() {
    private var userId = 0
    private var noteId = 0

    fun getUserId(): String {
        val id = userId
        userId += 1
        return "user_$id"
    }

    fun getNoteId(): String {
        val id = noteId
        noteId += 1
        return "note_$id"
    }

}