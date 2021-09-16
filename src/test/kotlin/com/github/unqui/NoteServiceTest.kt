package com.github.unqui

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NoteServiceTest {

    @Test
    fun registerTest() {
        val noteService = NoteService()
        assertEquals(noteService.users.size, 0)
        noteService.register(UserDraft("a@gmail.com", "image", "a", "Mr. A"))
        assertEquals(noteService.users.size, 1)
        val user = noteService.users.get(0)
        assertEquals(user.email, "a@gmail.com")
        assertEquals(user.image, "image")
        assertEquals(user.password, "a")
        assertEquals(user.displayName, "Mr. A")
    }

    @Test
    fun registerTwoTimesSameEmail() {
        val noteService = NoteService()
        assertEquals(noteService.users.size, 0)
        noteService.register(UserDraft("a@gmail.com", "image", "a", "Mr. A"))
        assertEquals(noteService.users.size, 1)
        assertFailsWith<UserException> {
            noteService.register(UserDraft("a@gmail.com", "image", "a", "Mr. A"))
        }
    }

    @Test
    fun loginTest() {
        val noteService = NoteService()
        assertEquals(noteService.users.size, 0)
        noteService.register(UserDraft("a@gmail.com", "image", "a", "Mr. A"))
        assertEquals(noteService.users.size, 1)

        val user = noteService.login("a@gmail.com", "a")
        assertEquals(user.email, "a@gmail.com")
        assertEquals(user.image, "image")
        assertEquals(user.password, "a")
        assertEquals(user.displayName, "Mr. A")
    }

    @Test
    fun loginWithoutUserTest() {
        val noteService = NoteService()
        assertFailsWith<UserException> {
            noteService.login("a@gmail.com", "a")
        }
    }

    @Test
    fun addNoteTest() {
        val noteService = NoteService()
        val user = noteService.register(UserDraft("a@gmail.com", "image", "a", "Mr. A"))
        assertEquals(user.notes.size, 0)

        noteService.addNote(user.id, NoteDraft("first note", "description"))
        assertEquals(user.notes.size, 1)

        val note = user.notes.get(0)
        assertEquals(note.title, "first note")
        assertEquals(note.description, "description")
    }

    @Test
    fun addNoteWithoutUserTest() {
        val noteService = NoteService()
        assertFailsWith<UserException> {
            noteService.addNote("user_1", NoteDraft("first note", "description"))
        }
    }

    @Test
    fun editNoteTest() {
        val noteService = NoteService()
        val user = noteService.register(UserDraft("a@gmail.com", "image", "a", "Mr. A"))
        val note = noteService.addNote(user.id, NoteDraft("first note", "description"))
        noteService.editNote(user.id, note.id, NoteDraft("new title", "new description"))
        assertEquals(note.title, "new title")
        assertEquals(note.description, "new description")
    }

    @Test
    fun editNoteWithWrongUserIdTest() {
        val noteService = NoteService()
        val user = noteService.register(UserDraft("a@gmail.com", "image", "a", "Mr. A"))
        val note = noteService.addNote(user.id, NoteDraft("first note", "description"))
        assertFailsWith<UserException> {
            noteService.editNote("user_2", note.id, NoteDraft("new title", "new description"))
        }
    }

    @Test
    fun editNoteWithAnotherUserIdTest() {
        val noteService = NoteService()
        val user = noteService.register(UserDraft("a@gmail.com", "image", "a", "Mr. A"))
        val user2 = noteService.register(UserDraft("b@gmail.com", "image", "b", "Mr. B"))
        val note = noteService.addNote(user.id, NoteDraft("first note", "description"))
        assertFailsWith<UserException> {
            noteService.editNote(user2.id, note.id, NoteDraft("new title", "new description"))
        }
    }

    @Test
    fun removeNote() {
        val noteService = NoteService()
        val user = noteService.register(UserDraft("a@gmail.com", "image", "a", "Mr. A"))
        val note = noteService.addNote(user.id, NoteDraft("first note", "description"))
        assertEquals(user.notes.size, 1)

        noteService.removeNote(user.id, note.id)
        assertEquals(user.notes.size, 0)
    }

    @Test
    fun getUser() {
        val noteService = NoteService()
        val user = noteService.register(UserDraft("a@gmail.com", "image", "a", "Mr. A"))

        val getUser = noteService.getUser(user.id)
        assertEquals(user, getUser)
    }

    @Test
    fun getNote() {
        val noteService = NoteService()
        val user = noteService.register(UserDraft("a@gmail.com", "image", "a", "Mr. A"))
        val note = noteService.addNote(user.id, NoteDraft("first note", "description"))

        val getNote = noteService.getNote(user.id, note.id)
        assertEquals(note, getNote)
    }

}
