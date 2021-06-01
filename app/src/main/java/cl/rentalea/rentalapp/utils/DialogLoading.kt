package cl.rentalea.rentalapp.utils

import android.app.Dialog
import android.content.Context
import android.view.Window
import cl.rentalea.rentalapp.R
import kotlinx.android.synthetic.main.dialog_loading.*

class DialogLoading(context: Context, msg: String): Dialog(context, R.style.loading_dialog)  {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_loading)
        setCancelable(false)
        setCanceledOnTouchOutside(false)

        title.text = msg
    }
}