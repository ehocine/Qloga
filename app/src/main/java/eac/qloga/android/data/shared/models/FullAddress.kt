package eac.qloga.android.data.shared.models

/// Getaddress.io reply data

class FullAddress(
    val postcode: String,
    val latitude: Long,
    val longitude: Long,
    val formatted_address: List<String>,
    val thoroughfare: String,
    val building_name: String,
    val sub_building_name: String,
    val sub_building_number: String,
    val building_number: String,
    val line_1: String,
    val line_2: String,
    val line_3: String,
    val line_4: String,
    val locality: String,
    val town_or_city: String,
    val county: String,
    val district: String,
    val country: String,
    val residential: Boolean
)