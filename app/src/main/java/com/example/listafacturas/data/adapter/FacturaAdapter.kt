package com.example.listafacturas.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listafacturas.R
import com.example.listafacturas.databinding.ItemFacturaBinding
import com.example.listafacturas.data.model.Factura
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FacturaAdapter(listener: OnManageFacturaListener): RecyclerView.Adapter<FacturaAdapter.ViewHolder>() {

    var facturas: ArrayList<Factura>
    var listener: OnManageFacturaListener = listener

    interface OnManageFacturaListener {
        fun onShowFactura(factura: Factura)
    }

    init {
        this.facturas = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_factura, parent, false))
    }

    override fun getItemCount(): Int {
        return facturas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(facturas.get(position), listener)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = ItemFacturaBinding.bind(itemView)

        fun bind(factura: Factura, listener: OnManageFacturaListener) {
            val formato1 = SimpleDateFormat("d", Locale("es", "ES"))
            val formato2 = SimpleDateFormat("MMM yyyy", Locale("es", "ES"))
            val date: Date = SimpleDateFormat("dd/MM/yyyy", Locale("en", "US")).parse(factura.fecha) as Date

            binding.tvFecha.text = formato1.format(date).plus(" ").plus(formato2.format(date).substring(0,1).uppercase()).plus(formato2.format(date).substring(1))

            val estado: String = factura.descEstado
            if (estado.equals("Pendiente de pago"))
                binding.tvEstado.text = estado

            binding.tvImporte.text = "${factura.importeOrdenacion} €"

            itemView.setOnClickListener(View.OnClickListener { listener.onShowFactura(factura) })
        }
    }

    fun update(data: List<Factura>?) {
        facturas.clear()
        facturas.addAll(data!!)

        notifyDataSetChanged()
    }
}