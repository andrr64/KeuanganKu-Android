package com.andreasoftware.keuanganku.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.andreasoftware.keuanganku.util.CurrencyFormatter
import java.util.Locale

class WalletItemAdapter(
    private var wallets: List<WalletModel>,
    private val locale: Locale,
    private val onItemClick: (WalletModel) -> Unit
): RecyclerView.Adapter<WalletItemAdapter.WalletItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WalletItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.component_wallet_item, parent, false)
        return WalletItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: WalletItemViewHolder,
        position: Int
    ) {
        val wallet = wallets[position]
        holder.name.text = wallet.name
        holder.balance.text = CurrencyFormatter.formatCurrency(wallet.balance, locale)
        holder.parent.setOnClickListener {
            onItemClick(wallet)
        }
    }

    override fun getItemCount(): Int = wallets.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateWallet(wallets: List<WalletModel>) {
        this.wallets = wallets
        notifyDataSetChanged()
    }

    class WalletItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.walletItemNameTextview)
        val balance: TextView = itemView.findViewById(R.id.walletItemBalanceTextview)
        val parent: CardView = itemView.findViewById(R.id.componentWalletItem)
    }
}