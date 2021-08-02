package cl.rentalea.rentalapp.utils

import cl.rentalea.rentalapp.db.entity.CheckListItem
import cl.rentalea.rentalapp.db.entity.Report
import cl.rentalea.rentalapp.db.entity.User
import cl.rentalea.rentalapp.db.entity.Viaje

object Constants {

    var DLOADING: DialogLoading? = null
    var REPORT: Report? = null
    var USER: User? = null
    var MATERIALES: MutableList<String>? = null
    var VIAJES: MutableList<Viaje>? = null
    var CHECKLIST_ITEM: CheckListItem? = null
    var CHECK_ITEMS_LIST: MutableList<CheckListItem>? = null
}