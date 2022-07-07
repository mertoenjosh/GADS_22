package com.mertoenjosh.notekeeper

import android.os.Bundle
import androidx.lifecycle.ViewModel

class NotesListActivityViewModel: ViewModel() {
    var isNewlyCreated = true
    private val navDrawerDisplaySelectionName = "com.mertoenjosh.notekeeper.NotesListActivityViewModel.navDrawerDisplaySelection"

    var navDrawerDisplaySelection = R.id.nav_notes
    private var recentlyViewedNotesIds = "com.mertoenjosh.notekeeper.NotesListActivityViewModel.recentlyViewedNotesIds"

    private val maxRecent = 5
    val recentlyViewed = ArrayList<NoteInfo>(maxRecent)

    fun addToRecentlyViewedNotes(note: NoteInfo) {
        // Check if selection is already in the list
        val existingIndex = recentlyViewed.indexOf(note)
        if (existingIndex == -1) {
            // it isn't in the list...
            // Add new one to beginning of list and remove any beyond max we want to keep
            recentlyViewed.add(0, note)
            for (index in recentlyViewed.lastIndex downTo maxRecent)
                recentlyViewed.removeAt(index)
        } else {
            // it is in the list...
            // Shift the ones above down the list and make it first member of the list
            for (index in (existingIndex - 1) downTo 0)
                recentlyViewed[index + 1] = recentlyViewed[index]
            recentlyViewed[0] = note
        }
    }

    fun saveState(outState: Bundle) {
        outState.putInt(navDrawerDisplaySelectionName, navDrawerDisplaySelection)
        val noteIds = DataManager.noteIdsAsIntArray(recentlyViewed)
        outState.putIntArray(recentlyViewedNotesIds, noteIds)
    }

    fun restoreState(savedInstanceState: Bundle) {
        navDrawerDisplaySelection = savedInstanceState.getInt(navDrawerDisplaySelectionName)
        val noteIds = savedInstanceState.getIntArray(recentlyViewedNotesIds)
        if (noteIds != null) {
            val noteList = DataManager.loadNotes(*noteIds)
            recentlyViewed.addAll(noteList)
        }
    }


}