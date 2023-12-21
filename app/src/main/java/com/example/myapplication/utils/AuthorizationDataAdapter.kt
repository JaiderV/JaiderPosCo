package com.example.myapplication.utils


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.database.entity.AuthorizationDataEntity

class AuthorizationDataAdapter(private var dataList: List<AuthorizationDataEntity>) :
    RecyclerView.Adapter<AuthorizationDataAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Referencias desde el fragment_authorization_form_item.xml
        val TextViewId: TextView = itemView.findViewById(R.id.textViewId)
        val TextViewCommerceCode: TextView = itemView.findViewById(R.id.textViewCommerceCode)
        val TextViewTerminalCode: TextView = itemView.findViewById(R.id.textViewTerminalCode)
        val TextViewAmount: TextView = itemView.findViewById(R.id.textViewAmount)
        val TextViewCardNumber: TextView = itemView.findViewById(R.id.textViewCardNumber)
        val TextViewReceiptId: TextView = itemView.findViewById(R.id.textViewReceiptId)
        val TextViewRrn: TextView = itemView.findViewById(R.id.textViewRrn)

        fun reverseFormatAmount(amount: String): String {
            val formattedAmount = amount.replace("[.,]".toRegex(), "")
            val parsedAmount = formattedAmount.toDoubleOrNull() ?: 0.0
            return String.format("$%,.0f", parsedAmount)
        }

        // vincula los datos de un objeto AuthorizationData a las vistas
        fun bind(authorizationDataEntity: AuthorizationDataEntity) {
            TextViewId.text = "ID: ${authorizationDataEntity.id}"
            TextViewCommerceCode.text =
                "Código de Comercio: ${authorizationDataEntity.commerceCode}"
            TextViewTerminalCode.text = "Terminal: ${authorizationDataEntity.terminalCode}"
            TextViewAmount.text = "Monto: ${reverseFormatAmount(authorizationDataEntity.amount)}"
            TextViewCardNumber.text = "Número de Tarjeta: ${authorizationDataEntity.card}"
            TextViewReceiptId.text = "Número de Recibo: ${authorizationDataEntity.receiptId}"
            TextViewRrn.text = "RRN: ${authorizationDataEntity.rrn}"
        }
    }

    // Infla el diseño del elemento de la lista desde fragment_transaction_list_item.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_transaction_list_item, parent, false)
        return ViewHolder(itemView)
    }

    //Este método devuelve la cantidad total de elementos en la lista de datos (dataList).
    override fun getItemCount(): Int {
        return dataList.size
    }

    /*Vincula los datos de una vista en una posicion especifica utilizando ViewHolder
     y llama al método bind con los datos de la posición */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val authorizationData = dataList[position]
        holder.bind(authorizationData)
    }

    //Actualiza la lista de datos
    fun updateData(newDataList: List<AuthorizationDataEntity>) {
        dataList = newDataList
        //Notifica al adaptador el cambio para actualizar el recycler
        notifyDataSetChanged()
    }
}