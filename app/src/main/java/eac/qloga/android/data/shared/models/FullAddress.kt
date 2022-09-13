package eac.qloga.android.data.shared.models

import com.fasterxml.jackson.annotation.JsonProperty

/// Getaddress.io reply data

data class FullAddress(
    @JsonProperty("building_name")
    val buildingName: String = "",
    @JsonProperty("building_number")
    val buildingNumber: String = "",
    @JsonProperty("country")
    val country: String = "",
    @JsonProperty("county")
    val county: String = "",
    @JsonProperty("district")
    val district: String = "",
    @JsonProperty("formatted_address")
    val formattedAddress: List<String> = listOf(),
    @JsonProperty("latitude")
    val latitude: Double = 0.0,
    @JsonProperty("line_1")
    val line1: String = "",
    @JsonProperty("line_2")
    val line2: String = "",
    @JsonProperty("line_3")
    val line3: String = "",
    @JsonProperty("line_4")
    val line4: String = "",
    @JsonProperty("locality")
    val locality: String = "",
    @JsonProperty("longitude")
    val longitude: Double = 0.0,
    @JsonProperty("postcode")
    val postcode: String = "",
    @JsonProperty("residential")
    val residential: Boolean = false,
    @JsonProperty("sub_building_name")
    val subBuildingName: String = "",
    @JsonProperty("sub_building_number")
    val subBuildingNumber: String = "",
    @JsonProperty("thoroughfare")
    val thoroughfare: String = "",
    @JsonProperty("town_or_city")
    val townOrCity: String = ""
)