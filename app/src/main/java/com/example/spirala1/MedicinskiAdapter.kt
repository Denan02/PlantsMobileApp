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

class MedicinskiAdapter (private var biljke: ArrayList<Biljka>) : RecyclerView.Adapter<MedicinskiAdapter.MedicinskiViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicinskiAdapter.MedicinskiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medicinski_pogled, parent, false)
        return MedicinskiViewHolder(view)
    }
    override fun onBindViewHolder(holder: MedicinskiAdapter.MedicinskiViewHolder, position: Int) {
        holder.naziv.text = biljke[position].naziv;
        holder.upozorenje.text = biljke[position].medicinskoUpozorenje;
        val prveTriKoristi = biljke[position].medicinskeKoristi.take(3);
        val context: Context = holder.slika.getContext()
        val t = TrefleDAO(context)
        val baza = BiljkaDatabase.getInstance(context).biljkaDao()
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            var slikaIzBaze = baza.dajBitmapuPoID(biljke[position].id!!)
            if(slikaIzBaze != null) {
                holder.slika.setImageBitmap(slikaIzBaze)
            }else {
                var slikaServisa = t.getImage(biljke[position])
                holder.slika.setImageBitmap(slikaServisa)
                baza.addImage(biljke[position].id!!, slikaServisa)
            }
        }
        if(prveTriKoristi.isNotEmpty()) {
            holder.korist1.text = prveTriKoristi[0].opis;
            holder.korist2.text = "";
            holder.korist3.text = "";
            if(prveTriKoristi.size >= 2) {
                holder.korist2.text = prveTriKoristi[1].opis;
                holder.korist3.text = "";
                if(prveTriKoristi.size >= 3)
                    holder.korist3.text = prveTriKoristi[2].opis;
            }
        }
    }
    override fun getItemCount(): Int = biljke.size;
    inner class MedicinskiViewHolder(nacinPrikaza: View): RecyclerView.ViewHolder(nacinPrikaza) {
        val naziv: TextView = nacinPrikaza.findViewById(R.id.nazivItem);
        val upozorenje: TextView = nacinPrikaza.findViewById(R.id.upozorenjeItem);
        val korist1: TextView = nacinPrikaza.findViewById(R.id.korist1Item);
        val korist2: TextView = nacinPrikaza.findViewById(R.id.korist2Item);
        val korist3: TextView = nacinPrikaza.findViewById(R.id.korist3Item);
        val slika:ImageView = nacinPrikaza.findViewById(R.id.slikaItem)
        init {
            // Postavljanje slušača klika na stavku
            nacinPrikaza.setOnClickListener {
                biljke.removeIf { biljka ->
                    !biljka.medicinskeKoristi.any { element -> biljke[position].medicinskeKoristi.contains(element) }
                }
                updateBiljke(biljke);
            }
        }
    }
    fun updateBiljke(biljkee: ArrayList<Biljka>) {
        this.biljke = biljkee
        this.notifyDataSetChanged()
    }

}