package cl.rentalea.rentalapp.binding

import android.content.Intent
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.rentalea.rentalapp.ui.MainActivity
import cl.rentalea.rentalapp.ui.adapter.ReportListAdapter
import cl.rentalea.rentalapp.utils.Constants.DLOADING
import cl.rentalea.rentalapp.utils.alert

@BindingAdapter("alert")
fun bindAlert(view: View, txt: MutableLiveData<String>) {
    if (txt.value != null) {
        view.context.alert(0, txt.value!!).show()
    }
}

@BindingAdapter("loading")
fun bindLoading(view: View, status: MutableLiveData<Boolean>) {

    status.value.let {
        if (DLOADING != null) {
            if (it!!) DLOADING!!.show()
            else if (DLOADING!!.isShowing) DLOADING!!.dismiss()
        }
    }
}

@BindingAdapter("visible")
fun bindVisible(view: View, status: MutableLiveData<Boolean>) {
    view.visibility = if (status.value!!) View.VISIBLE else View.GONE
}

@BindingAdapter(value = ["adaptador"])
fun RecyclerView.bindAdapter(adaptador: RecyclerView.Adapter<*>?) {
    if (adaptador != null) {
        setHasFixedSize(true)
        layoutManager = when (adaptador) {
            is ReportListAdapter -> LinearLayoutManager(context)
            else -> null
        }

        adapter = adaptador
        adaptador.notifyDataSetChanged()
    }
}
