package com.example.spirala1

fun dajTextIzmedjuZagrada(input: String): String {
    val regex = Regex("""\((.*?)\)""")
    val matchResult = regex.find(input)
    return matchResult?.groupValues?.getOrNull(1) ?: ""
}
fun provjeriNIJEJESTIVO(input: String): String {
    val notEdiblePhrase = "NIJE JESTIVO"
    val lowercasedInput = input.lowercase()
    if (!lowercasedInput.contains(notEdiblePhrase.lowercase())) {
        return "$input $notEdiblePhrase"
    }
    return input
}

fun provjeriToksicno(input: String): String {
    val notEdiblePhrase = "TOKSIČNO"
    val lowercasedInput = input.lowercase()
    if (!lowercasedInput.contains(notEdiblePhrase.lowercase())) {
        return "$input $notEdiblePhrase"
    }
    return input
}
fun imaRazlike(biljka1: Biljka, biljka2: Biljka): Boolean {
    return biljka1.naziv != biljka2.naziv ||
            biljka1.porodica != biljka2.porodica ||
            biljka1.medicinskoUpozorenje != biljka2.medicinskoUpozorenje ||
            !usporediListe(biljka1.medicinskeKoristi, biljka2.medicinskeKoristi) ||
            !jeIstiKao(biljka1.profilOkusa, biljka2.profilOkusa) ||
            !usporediListe(biljka1.jela, biljka2.jela) ||
            !usporediListe(biljka1.klimatskiTipovi, biljka2.klimatskiTipovi) ||
            !usporediListe(biljka1.zemljisniTipovi, biljka2.zemljisniTipovi)
}
fun <T:Comparable<T>> usporediListe(lista1: List<T>, lista2: List<T>): Boolean {
    if (lista1.size != lista2.size) return false
    var lista3= lista1.sorted()
    var lista4 = lista2.sorted()
    for(i in 0..<lista3.size) {
        if (lista3[i] != lista4[i]) {
            return false
        }
    }
    return true
}
fun jeIstiKao(prvi: ProfilOkusaBiljke, drugi: ProfilOkusaBiljke): Boolean {
    return prvi == drugi
}