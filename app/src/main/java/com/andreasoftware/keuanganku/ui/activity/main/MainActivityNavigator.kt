package com.andreasoftware.keuanganku.ui.activity.main
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.data.model.WalletModel

object MainActivityNavigator {
    fun navigateTo(activity: FragmentActivity?, actionId: Int, bundle: Bundle? = null) {
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.root_nav_host_fragment) as? NavHostFragment
        val navController = navHostFragment?.navController
        navController?.navigate(actionId, bundle)
    }

    val ACTION_MAIN_TO_WALLET_FORM = R.id.action_main_fragment_to_walletForm
    val ACTION_MAIN_TO_EXPENSE_FORM = R.id.action_main_fragment_to_expenseForm
    val ACTION_MAIN_TO_INCOME_FORM = R.id.action_main_fragment_to_incomeForm
    val ACTION_MAIN_TO_DETAIL_WALLET = R.id.action_main_fragment_to_walletDetail
    val Action_MAIN_TO_EXPENSE_LIMITER_FORM = R.id.action_main_fragment_to_expenseLimiterForm

    fun navigateFromMainToWalletForm(activity: FragmentActivity?) {
        navigateTo(activity, ACTION_MAIN_TO_WALLET_FORM)
    }

    fun navigateFromMainToExpenseForm(activity: FragmentActivity?) {
        navigateTo(activity, ACTION_MAIN_TO_EXPENSE_FORM)
    }

    fun navigateFromMainToIncomeForm(activity: FragmentActivity?) {
        navigateTo(activity, ACTION_MAIN_TO_INCOME_FORM)
    }

    fun navigateFromMainToExpenseLimiterForm(activity: FragmentActivity?) {
        navigateTo(activity, Action_MAIN_TO_EXPENSE_LIMITER_FORM)
    }

    fun navigateFromMainToDetailWallet(activity: FragmentActivity?, wallet: WalletModel){
        val bundle = Bundle().apply {
            putParcelable("wallet", wallet) // Mengirim objek TransactionModel
        }
        navigateTo(activity, ACTION_MAIN_TO_DETAIL_WALLET, bundle)
    }
}