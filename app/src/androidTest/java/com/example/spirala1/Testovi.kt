package com.example.spirala1

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.anything
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Testovi {
    @get:Rule
    var activityRule: ActivityScenarioRule<NovaBiljkaActivity> = ActivityScenarioRule(NovaBiljkaActivity::class.java)

    /*--------------------Provjera za verifikaciju naziva----------------------*/
    @Test
    fun Test1() {
        onView(withId(R.id.nazivET)).perform(replaceText(""))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }
    @Test
    fun Test2() {
        onView(withId(R.id.nazivET)).perform(replaceText("A"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }
    @Test
    fun Test3() {
        onView(withId(R.id.nazivET)).perform(replaceText("AAAAAAAAAAAAAAAAAAAAA"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }
    /*--------------------Provjera za verifikaciju porodice----------------------*/
    @Test
    fun Test4() {
        onView(withId(R.id.porodicaET)).perform(replaceText(""))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }
    @Test
    fun Test5() {
        onView(withId(R.id.porodicaET)).perform(replaceText("A"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }
    @Test
    fun Test6() {
        onView(withId(R.id.porodicaET)).perform(replaceText("AAAAAAAAAAAAAAAAAAAAA"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }
    /*--------------------Provjera za verifikaciju medicinsko upozorenje----------------------*/
    @Test
    fun Test7() {
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText(""))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }
    @Test
    fun Test8() {
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("A"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }
    @Test
    fun Test9() {
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("AAAAAAAAAAAAAAAAAAAAA"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }
    /*--------------------Provjera za verifikaciju dodavanja jela----------------------*/
    @Test
    fun Test10() {
        onView(withId(R.id.jeloET)).perform(replaceText("Jelo"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jeloET)).perform(scrollTo())
        onView(withId(R.id.jeloET)).perform(replaceText("Jelo2"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jelaLV)).check(matches(ViewMatchers.hasChildCount(2)))
    }
    @Test
    fun Test11() {
        onView(withId(R.id.jeloET)).perform(replaceText("Jelo"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jeloET)).perform(scrollTo())
        onView(withId(R.id.jeloET)).perform(replaceText("Jelo"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jelaLV)).check(matches(ViewMatchers.hasChildCount(1)))
    }
    /*--------------------Provjera za verifikaciju listi----------------------*/
    @Test
    fun Test12() {
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.greskaJedan)).perform(scrollTo())
        onView(withId(R.id.greskaJedan)).check(matches(withText("Morate odabrati barem jednu medicinsku korist!")))
        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo())
        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0).perform(click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.medicinskaKoristLV)).perform(scrollTo())
        onView(withId(R.id.greskaJedan)).check(matches(withText("")))
    }
    @Test
    fun Test13() {
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.greskaDva)).perform(scrollTo())
        onView(withId(R.id.greskaDva)).check(matches(withText("Morate odabrati barem jedan klimatski tip!")))
        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.klimatskiTipLV)).perform(scrollTo())
        onView(withId(R.id.greskaDva)).check(matches(withText("")))
    }
    @Test
    fun Test14() {
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.greskaTri)).perform(scrollTo())
        onView(withId(R.id.greskaTri)).check(matches(withText("Morate odabrati barem jedan zemljisni tip!")))
        onView(withId(R.id.zemljisniTipLV)).perform(scrollTo())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.zemljisniTipLV)).perform(scrollTo())
        onView(withId(R.id.greskaTri)).check(matches(withText("")))
    }
    @Test
    fun Test15() {
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.greskaCetri)).perform(scrollTo())
        onView(withId(R.id.greskaCetri)).check(matches(withText("Morate odabrati profil okusa!")))
        onView(withId(R.id.profilOkusaLV)).perform(scrollTo())
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.profilOkusaLV)).perform(scrollTo())
        onView(withId(R.id.greskaCetri)).check(matches(withText("")))
    }
    @Test
    fun Test16() {
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.greskaPet)).perform(scrollTo())
        onView(withId(R.id.greskaPet)).check(matches(withText("Morate dodati barem jedno jelo!")))
        onView(withId(R.id.jeloET)).perform(scrollTo())
        onView(withId(R.id.jeloET)).perform(replaceText("Jelo"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.jelaLV)).perform(scrollTo())
        onView(withId(R.id.greskaPet)).check(matches(withText("")))
    }
    @Test
    fun Test17() {
        onView(withId(R.id.uslikajBiljkuBtn)).perform(scrollTo(),click())
        onView(withId(R.id.slikaIV)).check(matches(isDisplayed()))
    }
}