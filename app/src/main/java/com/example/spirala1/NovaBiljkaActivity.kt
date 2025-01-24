package com.example.spirala1

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.forEach
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class NovaBiljkaActivity : AppCompatActivity() {
    /*                       Na nivou klase Promjenjive                          */
    /* -----------------------------List-------------------------------- */
    private lateinit var medicinskaKoristLista : ListView
    private lateinit var klimatskiTipLista : ListView
    private lateinit var zemljisniTipLista : ListView
    private lateinit var profilOkusaLista : ListView
    private lateinit var jelaLista : ListView
    /* -----------------------------EditText-------------------------------- */
    private lateinit var nazivTekst :EditText
    private lateinit var porodicaTekst :EditText
    private lateinit var medicinskoUpozorenjeTekst :EditText
    private lateinit var jeloTekst :EditText
    private lateinit var listaJelaZaPrikaz :EditText
    /* -----------------------------Button-------------------------------- */
    private lateinit var dugmeZaDOdavanjeBiljke:Button
    /* -----------------------------String-------------------------------- */
    private lateinit var pozicijaZaIzmjenu : String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_biljka)
        postaviSveListViewNaEkranu();
        /*                       Lokalne Promjenjive                          */
        /* -----------------------------List-------------------------------- */
        medicinskaKoristLista = findViewById<ListView>(R.id.medicinskaKoristLV);
        klimatskiTipLista = findViewById<ListView>(R.id.klimatskiTipLV);
        zemljisniTipLista = findViewById<ListView>(R.id.zemljisniTipLV);
        profilOkusaLista = findViewById<ListView>(R.id.profilOkusaLV);
        jelaLista = findViewById<ListView>(R.id.jelaLV);
        /* -----------------------------EditText-------------------------------- */
        nazivTekst = findViewById<EditText>(R.id.nazivET)
        porodicaTekst = findViewById<EditText>(R.id.porodicaET)
        medicinskoUpozorenjeTekst = findViewById<EditText>(R.id.medicinskoUpozorenjeET)
        jeloTekst = findViewById<EditText>(R.id.jeloET)
        var listaJelaZaPrikaz = ArrayList<String>();
        /* -----------------------------Button-------------------------------- */
        val dugmeZaSlikanjeBiljke = findViewById<Button>(R.id.uslikajBiljkuBtn)
        val dugmeZaDodavanjeJela = findViewById<Button>(R.id.dodajJeloBtn)
        dugmeZaDOdavanjeBiljke = findViewById<Button>(R.id.dodajBiljkuBtn)
        //Adapteri
        val adapterZaJela = ArrayAdapter(this,android.R.layout.simple_list_item_1, listaJelaZaPrikaz)
        val baza = BiljkaDatabase.getInstance(application).biljkaDao()
        /* -----------------------------Postavljanje adaptera-------------------------------- */
        jelaLista.adapter = adapterZaJela;
        /* ----------------------------------------------------------------------- */
        /*--------------------------------INT---------------------------------------*/
        /* -----------------------------Listeneri-------------------------------- */
        dugmeZaSlikanjeBiljke.setOnClickListener {
            val intentSlike = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intentSlike.resolveActivity(packageManager) != null) {
                startActivityForResult(intentSlike, 1)
            }
        }
        dugmeZaDodavanjeJela.setOnClickListener {
            if(dugmeZaDodavanjeJela.text == "Dodaj jelo"){
                if(verificirajJelo(jeloTekst.text.toString())) {
                    listaJelaZaPrikaz.add(jeloTekst.text.toString())
                    adapterZaJela.notifyDataSetChanged()
                    jeloTekst.setText("")
                }
            }else {
                if(jeloTekst.text.toString().isEmpty()) {
                    for (i in 0 until adapterZaJela.count) {
                        val item = adapterZaJela.getItem(i)
                        if (item.toString() == pozicijaZaIzmjenu) {
                            adapterZaJela.remove(item)
                            adapterZaJela.notifyDataSetChanged()
                            break;
                        }
                    }
                    dugmeZaDodavanjeJela.text = "Dodaj jelo"
                }else {
                    if(verificirajJelo(jeloTekst.text.toString())) {
                        for (i in 0 until adapterZaJela.count) {
                            val item = adapterZaJela.getItem(i)
                            if (item == pozicijaZaIzmjenu) {
                                adapterZaJela.remove(item)
                                adapterZaJela.insert(jeloTekst.text.toString(), i)
                            }
                        }
                        adapterZaJela.notifyDataSetChanged()
                        jeloTekst.setText("")
                        dugmeZaDodavanjeJela.text = "Dodaj jelo"
                    }
                }
            }
        }

        dugmeZaDOdavanjeBiljke.setOnClickListener {
            if(validirajUnosKorisnika()) {
                var trefleInstanca = TrefleDAO()
                val scope = CoroutineScope(Job() + Dispatchers.Main)
                scope.launch {
                    var selektiraniIndexi = klimatskiTipLista.checkedItemPositions
                    val listaKlimatskihTipova = mutableListOf<KlimatskiTip>();
                    val listaMedicinskeKoristi = mutableListOf<MedicinskaKorist>();
                    val listaZemljisnihTipova = mutableListOf<Zemljiste>();
                    val listaJelaIzabranih = mutableListOf<String>();
                    lateinit var izabraniOkus: ProfilOkusaBiljke;

                    for (i in 0 until selektiraniIndexi.size()) {
                        val position = selektiraniIndexi.keyAt(i)
                        if (selektiraniIndexi.valueAt(i)) {
                            val selectedItem = klimatskiTipLista.adapter.getItem(position) as String
                            KlimatskiTip.fromOpis(selectedItem)?.let { it1 -> listaKlimatskihTipova.add(it1) }
                        }
                    }
                    selektiraniIndexi = medicinskaKoristLista.checkedItemPositions
                    for (i in 0 until selektiraniIndexi.size()) {
                        val position = selektiraniIndexi.keyAt(i)
                        if (selektiraniIndexi.valueAt(i)) {
                            val selectedItem = medicinskaKoristLista.adapter.getItem(position) as String
                            MedicinskaKorist.fromOpis(selectedItem)?.let { it1 -> listaMedicinskeKoristi.add(it1) }
                        }
                    }
                    selektiraniIndexi = profilOkusaLista.checkedItemPositions
                    for (i in 0 until selektiraniIndexi.size()) {
                        val position = selektiraniIndexi.keyAt(i)
                        if (selektiraniIndexi.valueAt(i)) {
                            val selectedItem = profilOkusaLista.adapter.getItem(position) as String
                            izabraniOkus = ProfilOkusaBiljke.fromOpis(selectedItem)!!
                        }
                    }
                    selektiraniIndexi = zemljisniTipLista.checkedItemPositions
                    for (i in 0 until selektiraniIndexi.size()) {
                        val position = selektiraniIndexi.keyAt(i)
                        if (selektiraniIndexi.valueAt(i)) {
                            val selectedItem = zemljisniTipLista.adapter.getItem(position) as String
                            Zemljiste.fromOpis(selectedItem)?.let { it1 -> listaZemljisnihTipova.add(it1) }
                        }
                    }

                    for (i in 0 until jelaLista.count) {
                        val item = jelaLista.getItemAtPosition(i)
                        listaJelaIzabranih.add(item.toString())
                    }
                    var biljkaDodaj = trefleInstanca.fixData(Biljka(
                        naziv = nazivTekst.text.toString(),
                        porodica = porodicaTekst.text.toString(),
                        medicinskoUpozorenje = medicinskoUpozorenjeTekst.text.toString(),
                        medicinskeKoristi = listaMedicinskeKoristi,
                        profilOkusa = izabraniOkus,
                        jela = listaJelaIzabranih,
                        klimatskiTipovi = listaKlimatskihTipova,
                        zemljisniTipovi = listaZemljisnihTipova
                    ))
                    baza.saveBiljka(trefleInstanca.fixData(biljkaDodaj))
                    val intentZaPovratakIDodavanje = Intent(this@NovaBiljkaActivity, MainActivity::class.java)
                    startActivity(intentZaPovratakIDodavanje)
                }
            }
        }
        jelaLista.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position)
            jeloTekst.setText(selectedItem.toString())
            pozicijaZaIzmjenu = selectedItem.toString()
            dugmeZaDodavanjeJela.text = "Izmijeni jelo"
        }
    }
    /* ----------------------------------------------------------------------- */

    private fun validirajUnosKorisnika(): Boolean {
        val g = findViewById<TextView>(R.id.greskaJedan)
        val g2 = findViewById<TextView>(R.id.greskaDva)
        val g3 = findViewById<TextView>(R.id.greskaTri)
        val g4 = findViewById<TextView>(R.id.greskaCetri)
        val g5 = findViewById<TextView>(R.id.greskaPet)
        g.text = ""
        g2.text = ""
        g3.text = ""
        g4.text = ""
        g5.text = ""
        var vrati : Boolean = true;
        if(nazivTekst.text.length < 2 || nazivTekst.text.length > 40) {
            nazivTekst.setError("Duzina texta mora biti izmedju 2 i 40!")
            vrati = false;
        }
        if(porodicaTekst.text.length < 2 || porodicaTekst.text.length > 20) {
            porodicaTekst.setError("Duzina texta mora biti izmedju 2 i 20!")
            vrati = false;
        }
        if(medicinskoUpozorenjeTekst.text.length < 2 || medicinskoUpozorenjeTekst.text.length > 20) {
            medicinskoUpozorenjeTekst.setError("Duzina texta mora biti izmedju 2 i 20!")
            vrati = false;
        }
        if(medicinskaKoristLista.checkedItemCount < 1) {
            g.text ="Morate odabrati barem jednu medicinsku korist!"
            vrati = false;
        }
        if(klimatskiTipLista.checkedItemCount < 1){
            g2.text ="Morate odabrati barem jedan klimatski tip!"
            vrati = false;
        }
        if(zemljisniTipLista.checkedItemCount < 1){
            g3.text ="Morate odabrati barem jedan zemljisni tip!"
            vrati = false;
        }
        if(profilOkusaLista.checkedItemCount < 1){
            g4.text ="Morate odabrati profil okusa!"
            vrati = false;
        }
        if(jelaLista.count < 1){
            g5.text ="Morate dodati barem jedno jelo!"
            vrati = false;
        }
        return vrati;
    }
    private fun verificirajJelo(text: String): Boolean {
        if(jeloTekst.text.length < 2 || jeloTekst.text.length > 20) {
            jeloTekst.setError("Duzina texta mora biti izmedju 2 i 20!")
            return false
        }
        for (i in 0 until jelaLista.count) {
            if (jelaLista.adapter.getItem(i).toString().toUpperCase() == text.toUpperCase()) {
                jeloTekst.setError("Jelo vec postoji!")
                return false
            }
        }
        return true;
    }
    private fun postaviSveListViewNaEkranu() {
        val medicinskaKoristLista = findViewById<ListView>(R.id.medicinskaKoristLV);
        val klimatskiTipLista = findViewById<ListView>(R.id.klimatskiTipLV);
        val zemljisniTipLista = findViewById<ListView>(R.id.zemljisniTipLV);
        val profilOkusaLista = findViewById<ListView>(R.id.profilOkusaLV);

        val listaMedicinskihKoristi: ArrayList<String> = enumValues<MedicinskaKorist>().map { it.opis } as ArrayList<String>
        medicinskaKoristLista.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, listaMedicinskihKoristi)
        medicinskaKoristLista.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        val listaKlimatskiTip: ArrayList<String> = enumValues<KlimatskiTip>().map { it.opis } as ArrayList<String>
        klimatskiTipLista.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, listaKlimatskiTip)
        klimatskiTipLista.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        val listaZemljisniTip: ArrayList<String> = enumValues<Zemljiste>().map { it.naziv } as ArrayList<String>
        zemljisniTipLista.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, listaZemljisniTip)
        zemljisniTipLista.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        val listaProfilOkusa: ArrayList<String> = enumValues<ProfilOkusaBiljke>().map { it.opis } as ArrayList<String>
        profilOkusaLista.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, listaProfilOkusa)
        profilOkusaLista.choiceMode = ListView.CHOICE_MODE_SINGLE

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val REQUEST_IMAGE_CAPTURE = 1;
        val prikazSlike = findViewById<ImageView>(R.id.slikaIV)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            prikazSlike.setImageBitmap(imageBitmap)
        }
    }
}