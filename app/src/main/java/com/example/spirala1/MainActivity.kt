package com.example.spirala1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var filtriraneBiljke: ArrayList<Biljka> = ArrayList<Biljka>()
    var sveBiljke : ArrayList<Biljka> = ArrayList<Biljka>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        sveBiljke.add(Biljka(
            naziv = "Bosiljak (Ocimum basilicum)",
            porodica = "Lamiaceae (usnate)",
            medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE),
            profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
            jela = listOf("Salata od paradajza", "Punjene tikvice"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA)
        ))
        sveBiljke.add(Biljka(
            naziv = "Nana (Mentha spicata)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA),
            profilOkusa = ProfilOkusaBiljke.MENTA,
            jela = listOf("Jogurt sa voćem", "Gulaš"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
            zemljisniTipovi = listOf(Zemljiste.GLINENO, Zemljiste.CRNICA)
        ))
        sveBiljke.add(Biljka(
            naziv = "Kamilica (Matricaria chamomilla)",
            porodica = "Asteraceae (glavočike)",
            medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Čaj od kamilice"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
        ))
        sveBiljke.add(Biljka(
            naziv = "Ružmarin (Rosmarinus officinalis)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPRITISKA),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Pečeno pile", "Grah","Gulaš"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
            zemljisniTipovi = listOf(Zemljiste.SLJUNOVITO, Zemljiste.KRECNJACKO)
        ))
        sveBiljke.add(Biljka(
            naziv = "Lavanda (Lavandula angustifolia)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine. Također, treba izbjegavati kontakt lavanda ulja sa očima.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PODRSKAIMUNITETU),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Jogurt sa voćem"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
        ))*/
        /*                               Promjenjive                             */
        /*------------------------Spinner-------------------------*/
        val spinner = findViewById<Spinner>(R.id.modSpinner)
        val spinnerZaBoju = findViewById<Spinner>(R.id.bojaSPIN)
        /*------------------------Button-------------------------*/
        var resetDugme: Button = findViewById<Button>(R.id.resetBtn);
        val dugmeDodajBiljku = findViewById<Button>(R.id.novaBiljkaBtn)
        val dugmeBrzaPretraga = findViewById<Button>(R.id.brzaPretraga)
        /*------------------------EditText-------------------------*/
        val unosTekstaZaBrzuPretragu = findViewById<EditText>(R.id.pretragaET)
        /*-----------------------String i View----------------------*/
        var selectedValue: String;
        var recycleViewForMod = findViewById<RecyclerView>(R.id.biljkeRV);
        recycleViewForMod.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        /*-------------------------Adapteri--------------------------*/
        var adapterZaMedicinskiPogled: MedicinskiAdapter = MedicinskiAdapter(filtriraneBiljke);
        var adapterZaKuharskiPogled: KuharskiAdapter = KuharskiAdapter(filtriraneBiljke);
        var adapterZaBotanickiPogled : BotanickiAdapter = BotanickiAdapter(filtriraneBiljke);
        /*-----------------------------------------------------------*/
        //Baza
        val baza = BiljkaDatabase.getInstance(application).biljkaDao()
        var trefleInstanca = TrefleDAO()
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            sveBiljke.addAll(baza.getAllBiljkas())
            napuniFiltriranuListu(sveBiljke);
            adapterZaKuharskiPogled.updateBiljke(filtriraneBiljke)
            adapterZaMedicinskiPogled.updateBiljke(filtriraneBiljke)
            adapterZaBotanickiPogled.updateBiljke(filtriraneBiljke)
        }
        /*---------------------------Listeneri----------------------------------*/
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                selectedValue = parent?.getItemAtPosition(position).toString()
                if(selectedValue == "Medicinski") {
                    recycleViewForMod.adapter = adapterZaMedicinskiPogled;
                    adapterZaMedicinskiPogled.updateBiljke(filtriraneBiljke);
                    dugmeBrzaPretraga.visibility = View.GONE
                    spinnerZaBoju.visibility = View.GONE
                    unosTekstaZaBrzuPretragu.visibility = View.GONE
                }else if(selectedValue == "Botanicki") {
                    recycleViewForMod.adapter = adapterZaBotanickiPogled;
                    adapterZaBotanickiPogled.updateBiljke(filtriraneBiljke);
                    dugmeBrzaPretraga.visibility = View.VISIBLE
                    spinnerZaBoju.visibility = View.VISIBLE
                    unosTekstaZaBrzuPretragu.visibility = View.VISIBLE
                }else if(selectedValue == "Kuharski") {
                    recycleViewForMod.adapter = adapterZaKuharskiPogled;
                    adapterZaKuharskiPogled.updateBiljke(filtriraneBiljke);
                    dugmeBrzaPretraga.visibility = View.GONE
                    spinnerZaBoju.visibility = View.GONE
                    unosTekstaZaBrzuPretragu.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        resetDugme.setOnClickListener {
            napuniFiltriranuListu(sveBiljke);
            adapterZaKuharskiPogled.updateBiljke(filtriraneBiljke);
            adapterZaBotanickiPogled.updateBiljke(filtriraneBiljke);
            adapterZaMedicinskiPogled.updateBiljke(filtriraneBiljke);
        }
        dugmeDodajBiljku.setOnClickListener {
            val intentZaPrelazak = Intent(this, NovaBiljkaActivity::class.java)
            startActivity(intentZaPrelazak);
        }
        dugmeBrzaPretraga.setOnClickListener {
            if(!unosTekstaZaBrzuPretragu.text.isEmpty() && !unosTekstaZaBrzuPretragu.text.isBlank()) {
                val scope = CoroutineScope(Job() + Dispatchers.Main)
                scope.launch {
                    val selectedItem = spinnerZaBoju.selectedItem.toString()
                    val t = TrefleDAO()
                    var listaZaPrikaz = t.getPlantsWithFlowerColor(selectedItem, unosTekstaZaBrzuPretragu.text.toString())
                    adapterZaBotanickiPogled.updateBiljke(listaZaPrikaz as ArrayList<Biljka>);
                }
            }
        }
        /*-------------------------------------------------------------------------------------*/

    }
    private fun napuniFiltriranuListu(listaP: ArrayList<Biljka>) {
        this.filtriraneBiljke.clear();
        this.filtriraneBiljke.addAll(listaP);
    }
}