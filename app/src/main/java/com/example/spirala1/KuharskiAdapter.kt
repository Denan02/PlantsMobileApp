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

class KuharskiAdapter (private var biljke: ArrayList<Biljka>) : RecyclerView.Adapter<KuharskiAdapter.KuharskiViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KuharskiAdapter.KuharskiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kuharski_pogled, parent, false)
        return KuharskiViewHolder(view)
    }

    override fun onBindViewHolder(holder: KuharskiAdapter.KuharskiViewHolder, position: Int) {
        holder.naziv.text = biljke[position].naziv;
        holder.okus.text = biljke[position].profilOkusa.opis;
        val prvaTriJela = biljke[position].jela.take(3);
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
        holder.jelo1.text = "";
        holder.jelo2.text = "";
        holder.jelo3.text = "";
        if(prvaTriJela.isNotEmpty()) {
            holder.jelo1.text = prvaTriJela[0];
            holder.jelo2.text = "";
            holder.jelo3.text = "";
            if (prvaTriJela.size >= 2) {
                holder.jelo2.text = prvaTriJela[1];
                holder.jelo3.text = "";
                if (prvaTriJela.size >= 3)
                    holder.jelo3.text = prvaTriJela[2];
            }
        }
    }

    override fun getItemCount(): Int = biljke.size;
    inner class KuharskiViewHolder(nacinPrikaza: View): RecyclerView.ViewHolder(nacinPrikaza) {
        val naziv: TextView = nacinPrikaza.findViewById(R.id.nazivItem);
        val okus: TextView = nacinPrikaza.findViewById(R.id.profilOkusaItem);
        val jelo1: TextView = nacinPrikaza.findViewById(R.id.jelo1Item);
        val jelo2: TextView = nacinPrikaza.findViewById(R.id.jelo2Item);
        val jelo3: TextView = nacinPrikaza.findViewById(R.id.jelo3Item);
        val slika: ImageView = nacinPrikaza.findViewById(R.id.slikaItem)
        init {
            // Postavljanje slušača klika na stavku
            nacinPrikaza.setOnClickListener {
                biljke.removeIf { biljka ->
                    biljka.profilOkusa != biljke[position].profilOkusa && // Dodatni uvjet za provjeru naziva
                            !biljka.jela.any { element -> biljke[position].jela.contains(element) }
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