package com.mertoenjosh.notekeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

class NoteActivity : AppCompatActivity() {
    private lateinit var spinnerCourses: Spinner
    private lateinit var noteTitle: TextView
    private lateinit var noteText: TextView
    val noteGetTogetherHelper = NoteGetTogetherHelper(this, lifecycle)
    private var notePosition = POSITION_NOT_SET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        spinnerCourses = findViewById(R.id.spinnerCourses)
        noteTitle = findViewById(R.id.editTextNoteTitle)
        noteText = findViewById(R.id.editTextNoteText)

        // Populating a spinner

        // 1: create an instance of the adapter
        val spinnerCoursesAdapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item,
            DataManager.courses.values.toList())

        // 2: set a dropdown resource
        spinnerCoursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // 3: associate the spinner with the adapter
        spinnerCourses.adapter = spinnerCoursesAdapter

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
            intent.getIntExtra(NOTE_POSITION, notePosition)

        if (notePosition != POSITION_NOT_SET) {
            displayNote()
        } else {
            DataManager.notes.add(NoteInfo())
            notePosition = DataManager.notes.lastIndex
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(notePosition >= DataManager.notes.lastIndex) {
            val menuItem = menu?.findItem(R.id.action_next)
            if (menuItem != null) {
                menuItem.icon = getDrawable(R.drawable.ic_baseline_block_24)
                menuItem.isEnabled = false
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                moveNext()
                true
            }
            R.id.action_get_together -> {
                noteGetTogetherHelper.sendMessage(DataManager.loadNote(notePosition))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        noteTitle.text = note.title
        noteText.text = note.text
        val coursePosition = DataManager.courses.values.indexOf(note.course)
        spinnerCourses.setSelection(coursePosition)
    }

    private fun moveNext() { // increment note position to move next
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        note.title = noteTitle.text.toString()
        note.text = noteText.text.toString()
        note.course = spinnerCourses.selectedItem as CourseInfo
    }
    
    companion object {
        private const val TAG = "NoteActivityTAG"
    }
}