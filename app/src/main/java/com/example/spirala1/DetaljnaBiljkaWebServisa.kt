package com.example.spirala1

import com.google.gson.annotations.SerializedName

data class DetaljnaBiljkaWebServisa (
    @SerializedName("id") val id: Int,
    @SerializedName("common_name") val commonName: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("scientific_name") val scientificName: String,
    @SerializedName("main_species_id") val mainSpeciesId: Int,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("year") val year: Int,
    @SerializedName("bibliography") val bibliography: String,
    @SerializedName("author") val author: String,
    @SerializedName("family_common_name") val familyCommonName: String?,
    @SerializedName("genus_id") val genusId: Int,
    @SerializedName("observations") val observations: String,
    @SerializedName("vegetable") val vegetable: Boolean,
    @SerializedName("links") val links: Links,
    @SerializedName("main_species") val mainSpecies: MainSpecies
)
data class MainSpecies(
    @SerializedName("id") val id: Int,
    @SerializedName("common_name") val commonName: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("scientific_name") val scientificName: String,
    @SerializedName("year") val year: Int,
    @SerializedName("bibliography") val bibliography: String,
    @SerializedName("author") val author: String,
    @SerializedName("status") val status: String,
    @SerializedName("rank") val rank: String,
    @SerializedName("family_common_name") val familyCommonName: String?,
    @SerializedName("genus_id") val genusId: Int,
    @SerializedName("observations") val observations: String,
    @SerializedName("vegetable") val vegetable: Boolean,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("genus") val genus: String,
    @SerializedName("family") val family: String,
    @SerializedName("duration") val duration: String?,
    @SerializedName("edible_part") val ediblePart: String?,
    @SerializedName("edible") val edible: Boolean,
    @SerializedName("specifications") val specifications: Specifications,
    @SerializedName("growth") val growth : Growth,
    @SerializedName("flower") val flower : Flower
)
data class Specifications(
    @SerializedName("ligneous_type") val ligneousType: String?,
    @SerializedName("growth_form") val growthForm: String?,
    @SerializedName("growth_habit") val growthHabit: String?,
    @SerializedName("growth_rate") val growthRate: String?,
    @SerializedName("nitrogen_fixation") val nitrogenFixation: String?,
    @SerializedName("shape_and_orientation") val shapeAndOrientation: String?,
    @SerializedName("toxicity") val toxicity: String?
)

data class Growth(
    @SerializedName("soil_texture") val soil_texture: Int?,
    @SerializedName("soil_humidity") val shapeAndOrientation: String?,
    @SerializedName("light") val light: Int?,
    @SerializedName("atmospheric_humidity") val atmospheric_humidity: Int?
)
data class Flower(
    @SerializedName("color") val color: List<String>?
)