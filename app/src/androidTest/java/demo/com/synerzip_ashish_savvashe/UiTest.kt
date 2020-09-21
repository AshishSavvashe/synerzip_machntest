package demo.com.synerzip_ashish_savvashe


import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import demo.com.synerzip_ashish_savvashe.views.HomeFragment
import demo.com.synerzip_ashish_savvashe.views.MainActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UiTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext =
            InstrumentationRegistry.getTargetContext()
        Assert.assertEquals(
            "com.journaldev.androidexpressobasics",
            appContext.packageName
        )
    }

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    /*Check Fragment Launching and View*/
    @Test
    fun testFragmentInit() {
        activityRule.activity.runOnUiThread { val resultFragment = startVoiceFragment() }
        Espresso.onView(withId(R.id.content_frame)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun startVoiceFragment(): HomeFragment {
        val activity = activityRule.activity as MainActivity
        val transaction = activity.supportFragmentManager.beginTransaction()
        val resultFragment = HomeFragment()
        transaction.add(resultFragment, "resultFragment")
        transaction.commitAllowingStateLoss()
        return resultFragment
    }

    @Test
    fun SearchClickedSuccess() {

        onView(withId(R.id.searchEditText)).perform(ViewActions.typeText("Pune"))
        onView(withId(R.id.btnSearch)).perform(click())
    }

    @Test
    fun shouldShowToastError() {
        onView(withId(R.id.searchEditText)).perform(ViewActions.typeText("Pune"))
        onView(withId(R.id.btnSearch)).perform(click())
    }

    @Test
    fun shouldShowToastSearchBoxEmpty() {
        onView(withId(R.id.searchEditText)).perform(ViewActions.typeText(""))
        onView(withId(R.id.btnSearch)).perform(click())
    }

    companion object {
    }
}