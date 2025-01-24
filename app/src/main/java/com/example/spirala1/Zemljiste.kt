package com.example.spirala1

enum class Zemljiste(val naziv: String) {
    PJESKOVITO("Pjeskovito zemljište"),
    GLINENO("Glinеno zemljište"),
    ILOVACA("Ilovača"),
    CRNICA("Crnica"),
    SLJUNOVITO("Šljunovito zemljište"),
    KRECNJACKO("Krečnjačko zemljište");
    companion object {
        fun fromOpis(opis: String): Zemljiste? {
            return entries.find { it.naziv == opis }
        }
    }
}
