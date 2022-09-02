package eac.qloga.android.features.p4p.showroom.shared.utils

import eac.qloga.android.R

sealed class ServiceCategory(
    val label: String,
    val iconId: Int
) {
    object Cleaning: ServiceCategory(iconId = R.drawable.ic_cleaning,label = "Cleaning")
    object Pets: ServiceCategory(iconId = R.drawable.ic_pets,label = "Pets")
    object Care: ServiceCategory(iconId = R.drawable.ic_care,label = "Care")
    object Handyman: ServiceCategory(iconId = R.drawable.ic_handyman,label = "Handyman")
    object Plumbing: ServiceCategory(iconId = R.drawable.ic_plumbing,label = "Plumbing")
    object Electrical: ServiceCategory(iconId = R.drawable.ic_electrical,label = "Electrical")
    object Cargo: ServiceCategory(iconId = R.drawable.ic_cargo,label = "Cargo")
    object Hair: ServiceCategory(iconId = R.drawable.ic_hair,label = "Hair")
    object Nails: ServiceCategory(iconId = R.drawable.ic_nails,label = "Nails")
    object Gas: ServiceCategory(iconId = R.drawable.ic_gas,label = "Gas")
    object Computing: ServiceCategory(iconId = R.drawable.ic_computing,label = "Computing")
    object Gardening: ServiceCategory(iconId = R.drawable.ic_gardening,label = "Gardening")

    companion object{
        val listOfItems by lazy {
            listOf(
                Cleaning, Pets, Care, Handyman, Plumbing, Electrical, Cargo, Hair, Nails, Gas, Computing, Gardening
            )
        }
    }
}