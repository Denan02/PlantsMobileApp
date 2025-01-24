package com.example.spirala1

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Biljka::class,
            parentColumns = ["id"],
            childColumns = ["idBiljke"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["idBiljke"], unique = true)]
)
data class BiljkaBitmap(
    @PrimaryKey(autoGenerate = true) val id: Long ?= 0,
    @ColumnInfo(name = "idBiljke") val biljkaId: Long,
    @ColumnInfo(name = "bitmap") val bitmap: Bitmap
)