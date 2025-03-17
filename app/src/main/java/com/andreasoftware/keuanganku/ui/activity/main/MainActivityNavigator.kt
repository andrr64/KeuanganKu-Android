package com.andreasoftware.keuanganku.ui.activity.main
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import com.andreasoftware.keuanganku.R // Ganti dengan package aplikasi anda

object MainActivityNavigator {
    fun navigateTo(activity: FragmentActivity?, actionId: Int) {
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.root_nav_host_fragment) as? NavHostFragment
        val navController = navHostFragment?.navController
        navController?.navigate(actionId)
    }

    val ACTION_MAIN_TO_WALLET_FORM = R.id.action_main_fragment_to_walletForm
    val ACTION_MAIN_TO_EXPENSE_FORM = R.id.action_main_fragment_to_expenseForm
    val ACTION_MAIN_TO_INCOME_FORM = R.id.action_main_fragment_to_incomeForm

    fun navigateFromMainToWalletForm(activity: FragmentActivity?) {
        navigateTo(activity, ACTION_MAIN_TO_WALLET_FORM)
    }

    fun navigateFromMainToExpenseForm(activity: FragmentActivity?) {
        navigateTo(activity, ACTION_MAIN_TO_EXPENSE_FORM)
    }

    fun navigateFromMainToIncomeForm(activity: FragmentActivity?) {
        navigateTo(activity, ACTION_MAIN_TO_INCOME_FORM)
    }
}