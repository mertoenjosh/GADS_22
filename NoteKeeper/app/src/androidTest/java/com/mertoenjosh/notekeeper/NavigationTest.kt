package com.mertoenjosh.notekeeper

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.core.StringContains.containsString
import org.junit.Test


@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @Rule @JvmField
    val notesListActivity = ActivityScenarioRule(NotesListActivity::class.java)

    @Test
    fun selectNoteAfterNavigationDrawerChange() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_courses))

        val coursePos = 0
        onView(withId(R.id.rvNoteList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CourseRecyclerAdapter.ViewHolder>(coursePos, click()))

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_notes))

        val notePos = 0
        onView(withId(R.id.rvNoteList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<NoteRecyclerAdapter.ViewHolder>(notePos, click()))

        val note = DataManager.notes[notePos]
        onView(withId(R.id.spinnerCourses))
            .check(matches(withSpinnerText(containsString(note.course?.title))))
        onView(withId(R.id.editTextNoteTitle))
            .check(matches(withText(containsString(note.title))))
        onView(withId(R.id.editTextNoteText))
            .check(matches(withText(containsString(note.text))))
    }
}