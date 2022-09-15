package eac.qloga.android.features.p4p.provider.shared.utils



sealed class CustomerVerificationOptions(val label: String){
    object Id: CustomerVerificationOptions("ID")
    object Address: CustomerVerificationOptions("Address")
    object Avatar: CustomerVerificationOptions("Avatar")

    companion object{
        val itemList by lazy { listOf(Id, Address, Avatar) }
    }
}