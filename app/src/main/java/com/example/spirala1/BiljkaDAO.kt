package com.example.spirala1

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Dao
interface BiljkaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun ubaciBiljku(biljka: Biljka):Long

    @Query("SELECT * FROM Biljka WHERE onlineChecked = 0")
    suspend fun getBiljkeOnlineCheckedZero(): List<Biljka>

    @Update
    suspend fun updateBiljka(biljka: Biljka)

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    suspend fun ubaciSlikuPomocna(biljkaBit : BiljkaBitmap): Long

    @Query("SELECT * FROM Biljka")
    suspend fun getAllBiljkas(): List<Biljka>

    @Query("DELETE FROM Biljka")
    suspend fun clearData()

    @Query("SELECT bitmap FROM BiljkaBitmap WHERE idBiljke = :id")
    suspend fun dajBitmapuPoID(id: Long) : Bitmap

    @Transaction
    suspend fun saveBiljka(biljka: Biljka): Boolean {
        return ubaciBiljku(biljka) > 0
    }
    @Transaction
    suspend fun addImage(biljkaId: Long, biljkaBitmap: Bitmap): Boolean {
        return ubaciSlikuPomocna(BiljkaBitmap(null, biljkaId,biljkaBitmap)) > 0
    }
    @Transaction
    suspend fun fixOfflineBiljka():Int {
        var brojacZaVracanje : Int = 0
        var listaBiljakaFiltrirane = getBiljkeOnlineCheckedZero()
        for (objektBiljke in listaBiljakaFiltrirane) {
            var fixiranObjektBiljke = TrefleDAO().fixData(objektBiljke)
            fixiranObjektBiljke.id = objektBiljke.id
            fixiranObjektBiljke.onlineChecked = true;
            updateBiljka(fixiranObjektBiljke)
            if(imaRazlike(objektBiljke,fixiranObjektBiljke))
                brojacZaVracanje++
        }
        return brojacZaVracanje
    }
}