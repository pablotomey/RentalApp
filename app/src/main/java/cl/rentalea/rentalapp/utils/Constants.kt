package cl.rentalea.rentalapp.utils

import cl.rentalea.rentalapp.db.entity.CheckListItem
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.db.entity.User
import cl.rentalea.rentalapp.db.entity.Viaje

object Constants {

    val EMAIL = "mail@gmail.com"
    val PASSWORD = "123456"

    var DLOADING: DialogLoading? = null
    var REPORT: Report? = null
    var MATERIALES: MutableList<String>? = null
    var VIAJES: MutableList<Viaje>? = null
    var CHECKLIST_ITEM: CheckListItem? = null
    var CHECK_ITEMS_LIST: MutableList<CheckListItem>? = null

    // variable para cambiar la navegación en ReportListFragment
    var FOR_CHECKLIST_OR_SEND_REPORT: Int = 0
}