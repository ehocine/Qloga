package eac.qloga.android.core.shared.components

import eac.qloga.android.R

data class PortfolioImage(
    val id: Int,
    val link: Int = R.drawable.home_background,
    val description: String? = null
)