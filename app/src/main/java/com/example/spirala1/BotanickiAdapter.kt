package com.example.spirala1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BotanickiAdapter (private var biljke: ArrayList<Biljka>) : RecyclerView.Adapter<BotanickiAdapter.BotanickiViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BotanickiAdapter.BotanickiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.botanicki_pogled, parent, false)
        return BotanickiViewHolder(view)
    }

    override fun onBindViewHolder(holder: BotanickiAdapter.BotanickiViewHolder, position: Int) {
        holder.naziv.text = biljke[position].naziv;
        holder.porodica.text = biljke[position].porodica;
        val context: Context = holder.slika.getContext()
        val t = TrefleDAO(context)
        val baza = BiljkaDatabase.getInstance(context).biljkaDao()
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            if(biljke[position].id != null) {
                var slikaIzBaze = baza.dajBitmapuPoID(biljke[position].id!!)
                if (slikaIzBaze != null) {
                    holder.slika.setImageBitmap(slikaIzBaze)
                } else {
                    var slikaServisa = t.getImage(biljke[position])
                    holder.slika.setImageBitmap(slikaServisa)
                    baza.addImage(biljke[position].id!!, slikaServisa)
                }
            }else {
                var slikaServisa = t.getImage(biljke[position])
                holder.slika.setImageBitmap(slikaServisa)
            }
        }
        if(biljke[position].klimatskiTipovi.isNotEmpty())
            holder.klimatski.text = biljke[position].klimatskiTipovi[0].opis;
        else
            holder.klimatski.text = ""
        if(biljke[position].zemljisniTipovi.isNotEmpty())
            holder.zemljisni.text = biljke[position].zemljisniTipovi[0].naziv;
        else
            holder.zemljisni.text = ""
    }

    override fun getItemCount(): Int = biljke.size;
    inner class BotanickiViewHolder(nacinPrikaza: View): RecyclerView.ViewHolder(nacinPrikaza){
        val naziv: TextView = nacinPrikaza.findViewById(R.id.nazivItem);
        val porodica: TextView = nacinPrikaza.findViewById(R.id.porodicaItem);
        val klimatski: TextView = nacinPrikaza.findViewById(R.id.klimatskiTipItem);
        val zemljisni: TextView = nacinPrikaza.findViewById(R.id.zemljisniTipItem);
        val slika: ImageView = nacinPrikaza.findViewById(R.id.slikaItem)

        init {
            // Postavljanje slušača klika na stavku
            nacinPrikaza.setOnClickListener {
                biljke.removeIf { biljka ->
                    biljka.porodica != biljke[position].porodica || // Uvjet za istu porodicu
                            biljka.klimatskiTipovi.intersect(biljke[position].klimatskiTipovi).isEmpty() || // Provjera klimatskih tipova
                            biljka.zemljisniTipovi.intersect(biljke[position].zemljisniTipovi).isEmpty() // Provjera zemljisnih tipova
                }
                updateBiljke(biljke)
            }
        }
    }
    fun updateBiljke(biljke: ArrayList<Biljka>) {
        this.biljke = biljke
        notifyDataSetChanged()
    }
}