package es.adriiiprieto.notesapp.presentation


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import es.adriiiprieto.notesapp.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val extendedFloatingActionButton = onView(allOf(withId(R.id.fragmentNotesListFab), withText("New note"), childAtPosition(childAtPosition(withId(R.id.nav_host_fragment), 0), 2), isDisplayed()))
        extendedFloatingActionButton.perform(click())

        Thread.sleep(500)

        val textInputEditText = onView(withId(R.id.fragmentNotesFormTextInputEditTextTitle))
        textInputEditText.perform(scrollTo(), replaceText("Sample of title"), closeSoftKeyboard())

        Thread.sleep(500)

        val textInputEditText2 = onView(withId(R.id.fragmentNotesFormTextInputEditTextBody))
        textInputEditText2.perform(scrollTo(), replaceText("Sample of description"), closeSoftKeyboard())

        Thread.sleep(500)

        val materialButton = onView(withId(R.id.fragmentNotesFormButtonSave))
        materialButton.perform(scrollTo(), click())

        Thread.sleep(500)

        val materialButton2 = onView(allOf(withId(android.R.id.button1), withText("OK"), childAtPosition(childAtPosition(withId(R.id.buttonPanel), 0), 3)))
        materialButton2.perform(scrollTo(), click())

        Thread.sleep(500)

        val materialButton3 = onView(allOf(withId(R.id.itemNoteButtonEdit), withText("Edit"), childAtPosition(childAtPosition(withId(R.id.itemNoteMaterialCardView), 0), 2), isDisplayed()))
        materialButton3.perform(click())

        Thread.sleep(500)

        val editText = onView(allOf(withId(R.id.fragmentNotesFormTextInputEditTextTitle), withText("Sample of title"), withParent(withParent(withId(R.id.fragmentNotesFormTextInputLayoutTitle))), isDisplayed()))
        editText.check(matches(withText("Sample of title")))

        Thread.sleep(500)

        val editText2 = onView(allOf(withId(R.id.fragmentNotesFormTextInputEditTextBody), withText("Sample of description"), withParent(withParent(withId(R.id.fragmentNotesFormTextInputLayoutBody))), isDisplayed()))
        editText2.check(matches(withText("Sample of description")))

        Thread.sleep(500)

        val textInputEditText3 = onView(allOf(withId(R.id.fragmentNotesFormTextInputEditTextBody), withText("Sample of description"), childAtPosition(childAtPosition(withId(R.id.fragmentNotesFormTextInputLayoutBody), 0), 1)))
        textInputEditText3.perform(scrollTo(), replaceText("Sample of description 2"))

        Thread.sleep(500)

        val textInputEditText4 = onView(allOf(withId(R.id.fragmentNotesFormTextInputEditTextBody), withText("Sample of description 2"), childAtPosition(childAtPosition(withId(R.id.fragmentNotesFormTextInputLayoutBody), 0), 1), isDisplayed()))
        textInputEditText4.perform(closeSoftKeyboard())

        Thread.sleep(500)

        val materialButton4 = onView(allOf(withId(R.id.fragmentNotesFormButtonSave), withText("Update"), childAtPosition(childAtPosition(withId(R.id.scrollView2), 0), 2)))
        materialButton4.perform(scrollTo(), click())

        Thread.sleep(500)

        val materialButton5 = onView(allOf(withId(android.R.id.button1), withText("OK"), childAtPosition(childAtPosition(withId(R.id.buttonPanel), 0), 3)))
        materialButton5.perform(scrollTo(), click())

        Thread.sleep(500)

        val materialButton6 = onView(allOf(withId(R.id.itemNoteButtonDelete), withText("Delete"), childAtPosition(childAtPosition(withId(R.id.itemNoteMaterialCardView), 0), 3), isDisplayed()))
        materialButton6.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
