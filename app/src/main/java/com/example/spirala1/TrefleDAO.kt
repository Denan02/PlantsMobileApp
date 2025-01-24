package com.example.spirala1

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.FutureTarget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class TrefleDAO(private val context: Context ?= null) {
    private val myToken : String  = "Muyl-HFDdXCYCzDD_wK7HKGf9ArtqsSh9WDUvLwkBNo"
    suspend fun getImage(biljka: Biljka): Bitmap {
        return withContext(Dispatchers.IO) {
            val defaultBitmap:Bitmap = BitmapFactory.decodeResource(context!!.resources,R.drawable.errorimageload)
            try {
                var rez = BiljkeRepository.getBiljke(
                    "plants",
                    myToken,
                    dajTextIzmedjuZagrada(biljka.naziv)
                )
                var link: String = "";
                if (dajTextIzmedjuZagrada(biljka.naziv).isEmpty() || dajTextIzmedjuZagrada(biljka.naziv).isBlank()) {
                    return@withContext defaultBitmap
                }
                if (rez != null && rez.data.size > 0) {
                    link = rez.data[0].imageUrl
                } else {
                    return@withContext defaultBitmap
                }
                val futureTarget: FutureTarget<Bitmap> = Glide.with(context)
                    .asBitmap()
                    .load(link)
                    .submit()
                val bitmap = futureTarget.get()
                if (context != null) {
                    Glide.with(context).clear(futureTarget)
                }
                return@withContext bitmap
            }catch (e: IOException) {
                return@withContext defaultBitmap
            } catch (e: GlideException) {
                return@withContext defaultBitmap
            } catch (e: Exception) {
                return@withContext defaultBitmap
            }
        }
    }
    suspend fun fixData(biljka: Biljka):Biljka {
        return withContext(Dispatchers.IO) {
            try {
                var biljkaVrati: Biljka = Biljka(
                    naziv = biljka.naziv,
                    porodica = biljka.porodica,
                    medicinskoUpozorenje = biljka.medicinskoUpozorenje,
                    medicinskeKoristi = biljka.medicinskeKoristi,
                    profilOkusa = biljka.profilOkusa,
                    jela = biljka.jela,
                    klimatskiTipovi = biljka.klimatskiTipovi,
                    zemljisniTipovi = biljka.zemljisniTipovi
                )
                if (dajTextIzmedjuZagrada(biljka.naziv).isEmpty() || dajTextIzmedjuZagrada(biljka.naziv).isBlank()) {
                    return@withContext biljkaVrati;
                }
                var rez = BiljkeRepository.getBiljke(
                    "plants",
                    myToken,
                    dajTextIzmedjuZagrada(biljka.naziv)
                )
                if (rez != null) {
                    if (rez.meta.total > 0) {
                        //Provjera porodice
                        if (rez.data[0].family != null && biljka.porodica != rez.data[0].family) {
                            biljkaVrati.porodica = rez.data[0].family
                        }
                        //Provjera liste jela
                        var rez2 = BiljkeRepository.getBiljkePoID(rez.data[0].id, myToken)
                        if (rez2 != null) {
                            if (rez2.data.mainSpecies.edible == false) {
                                biljkaVrati.jela = listOf()
                                biljkaVrati.medicinskoUpozorenje =
                                    provjeriNIJEJESTIVO(biljkaVrati.medicinskoUpozorenje)
                            }
                            //provjera medicinskog upozorenja
                            if (rez2.data.mainSpecies.specifications.toxicity != null) {
                                biljkaVrati.medicinskoUpozorenje =
                                    provjeriToksicno(biljkaVrati.medicinskoUpozorenje)
                            }
                            //provjera zemljista
                            if (rez2.data.mainSpecies.growth.soil_texture != null) {
                                var listaZemlistaNova = ArrayList<Zemljiste>()
                                if (rez2.data.mainSpecies.growth.soil_texture == 9) {
                                    listaZemlistaNova.add(Zemljiste.SLJUNOVITO)
                                }
                                if (rez2.data.mainSpecies.growth.soil_texture == 10) {
                                    listaZemlistaNova.add(Zemljiste.KRECNJACKO)
                                }
                                if (rez2.data.mainSpecies.growth.soil_texture!! >= 1 && rez2.data.mainSpecies.growth.soil_texture!! <= 2) {
                                    listaZemlistaNova.add(Zemljiste.GLINENO)
                                }
                                if (rez2.data.mainSpecies.growth.soil_texture!! >= 3 && rez2.data.mainSpecies.growth.soil_texture!! <= 4) {
                                    listaZemlistaNova.add(Zemljiste.PJESKOVITO)
                                }
                                if (rez2.data.mainSpecies.growth.soil_texture!! >= 5 && rez2.data.mainSpecies.growth.soil_texture!! <= 6) {
                                    listaZemlistaNova.add(Zemljiste.ILOVACA)
                                }
                                if (rez2.data.mainSpecies.growth.soil_texture!! >= 7 && rez2.data.mainSpecies.growth.soil_texture!! <= 8) {
                                    listaZemlistaNova.add(Zemljiste.CRNICA)
                                }
                                biljkaVrati.zemljisniTipovi = listaZemlistaNova
                            }
                            //Provjera klimatski
                            if (rez2.data.mainSpecies.growth.light != null && rez2.data.mainSpecies.growth.atmospheric_humidity != null) {
                                var listaKlimaNova = ArrayList<KlimatskiTip>()
                                if ((rez2.data.mainSpecies.growth.light!! >= 6 && rez2.data.mainSpecies.growth.light!! <= 9) && (rez2.data.mainSpecies.growth.atmospheric_humidity!! >= 1 && rez2.data.mainSpecies.growth.atmospheric_humidity!! <= 5)) {
                                    listaKlimaNova.add(KlimatskiTip.SREDOZEMNA)
                                }
                                if ((rez2.data.mainSpecies.growth.light!! >= 8 && rez2.data.mainSpecies.growth.light!! <= 10) && (rez2.data.mainSpecies.growth.atmospheric_humidity!! >= 7 && rez2.data.mainSpecies.growth.atmospheric_humidity!! <= 10)) {
                                    listaKlimaNova.add(KlimatskiTip.TROPSKA)
                                }
                                if ((rez2.data.mainSpecies.growth.light!! >= 6 && rez2.data.mainSpecies.growth.light!! <= 9) && (rez2.data.mainSpecies.growth.atmospheric_humidity!! >= 5 && rez2.data.mainSpecies.growth.atmospheric_humidity!! <= 8)) {
                                    listaKlimaNova.add(KlimatskiTip.SUBTROPSKA)
                                }
                                if ((rez2.data.mainSpecies.growth.light!! >= 4 && rez2.data.mainSpecies.growth.light!! <= 7) && (rez2.data.mainSpecies.growth.atmospheric_humidity!! >= 3 && rez2.data.mainSpecies.growth.atmospheric_humidity!! <= 7)) {
                                    listaKlimaNova.add(KlimatskiTip.UMJERENA)
                                }
                                if ((rez2.data.mainSpecies.growth.light!! >= 7 && rez2.data.mainSpecies.growth.light!! <= 9) && (rez2.data.mainSpecies.growth.atmospheric_humidity!! >= 1 && rez2.data.mainSpecies.growth.atmospheric_humidity!! <= 2)) {
                                    listaKlimaNova.add(KlimatskiTip.SUHA)
                                }
                                if ((rez2.data.mainSpecies.growth.light!! >= 0 && rez2.data.mainSpecies.growth.light!! <= 5) && (rez2.data.mainSpecies.growth.atmospheric_humidity!! >= 3 && rez2.data.mainSpecies.growth.atmospheric_humidity!! <= 7)) {
                                    listaKlimaNova.add(KlimatskiTip.PLANINSKA)
                                }
                                biljkaVrati.klimatskiTipovi = listaKlimaNova;
                            }
                        }
                    }
                }
                return@withContext biljkaVrati;
            }catch (e: IOException) {
                return@withContext biljka
            } catch (e: GlideException) {
                return@withContext biljka
            } catch (e: Exception) {
                return@withContext biljka
            }
        }
    }
    suspend fun getPlantsWithFlowerColor(flower_color:String,substr:String):List<Biljka> {
        return withContext(Dispatchers.IO) {
            val listaVrati:ArrayList<Biljka> = ArrayList<Biljka>()
            try{
                var rez = BiljkeRepository.getBiljkeSubString(myToken, substr, flower_color)
                if (rez != null && rez.data.size > 0) {
                    for(i in 0 until rez.meta.total) {
                        var novaBiljka: Biljka = Biljka(
                            naziv = rez.data[i].commonName + "(" + rez.data[i].scientificName + ")",
                            porodica = "",
                            medicinskoUpozorenje = "",
                            medicinskeKoristi = emptyList(),
                            profilOkusa = ProfilOkusaBiljke.SLATKI,
                            jela = emptyList(),
                            klimatskiTipovi = emptyList(),
                            zemljisniTipovi = emptyList()
                        )
                        listaVrati.add(fixData(novaBiljka));
                    }
                }
                return@withContext listaVrati;
            }catch (e: IOException) {
                return@withContext listaVrati;
            } catch (e: GlideException) {
                return@withContext listaVrati;
            } catch (e: Exception) {
                return@withContext listaVrati;
            }
        }
    }
}