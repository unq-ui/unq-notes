package com.github.unqui

import java.time.LocalDateTime

class User(
    val id: String,
    val email: String,
    var image: String,
    var password: String,
    var displayName: String,
    val notes: MutableList<Note>
)

class Note(val id: String, var title: String, var description: String, var lastModifiedDate: LocalDateTime)

