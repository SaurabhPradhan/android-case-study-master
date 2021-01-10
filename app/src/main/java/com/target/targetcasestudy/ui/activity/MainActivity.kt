package com.target.targetcasestudy.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.target.targetcasestudy.R
import com.target.targetcasestudy.ui.base.ActionListener
import com.target.targetcasestudy.ui.deals.DealItemFragment
import com.target.targetcasestudy.ui.deals.DealListFragment
import com.target.targetcasestudy.ui.deals.DealListFragment.Companion.ACTION_START_DEAL_ITEM_VIEW
import com.target.targetcasestudy.ui.dialog.LoadingDialogFragment
import com.target.targetcasestudy.ui.payment.PaymentDialogFragment
import com.target.targetcasestudy.util.gone
import com.target.targetcasestudy.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ActionListener {

    private val dealListFragment = DealListFragment()
    private val dealItemFragment = DealItemFragment()
    private var isDealItemFragmentVisible = false
    private var lastDealItemData = -1
    private lateinit var loadingDialog: LoadingDialogFragment
    private val loadingDialogTag = "LoadingDialog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null && savedInstanceState.getBoolean(ACTION_START_DEAL_ITEM_VIEW)) {
            showDealItemFragment(savedInstanceState.getInt(KEY_DEAL_DATA))
        }else{
            showDealListFragment()
        }
        setSupportActionBar(toolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.creditCard -> {
                PaymentDialogFragment().show(supportFragmentManager, "CreditCardValidation")
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu, this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onAction(action: String, data: Any?) {
        when (action) {
            ACTION_START_DEAL_ITEM_VIEW -> showDealItemFragment(data)
            DealItemFragment.ACTION_BACK -> showDealListFragment()
            ACTION_HIDE_PROGRESS_BAR -> hideProgress()
            ACTION_LOAD_PROGRESS_BAR -> showProgress()
        }
    }

    private fun showDealListFragment() {
        isDealItemFragmentVisible = false
        back.gone()
        showFragment(dealListFragment)
    }

    private fun showDealItemFragment(data: Any?) {
        isDealItemFragmentVisible = true
        lastDealItemData = data as Int
        back.show()
        dealItemFragment.arguments = Bundle().apply { putInt(KEY_DEAL_DATA, data) }
        showFragment(dealItemFragment)
    }

    /**
     * Standard mechanism for displaying a fragment
     */
    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    private fun initLoadingDialog() {
        if (!::loadingDialog.isInitialized) {
            loadingDialog = LoadingDialogFragment()
        }
    }

    private fun showProgress() {
        initLoadingDialog()
        if (loadingDialog.dialog == null || loadingDialog.dialog?.isShowing == false) {
            loadingDialog.show(supportFragmentManager, loadingDialogTag)
        }
    }

    private fun hideProgress() {
        if (::loadingDialog.isInitialized && loadingDialog.dialog?.isShowing == true) {
            loadingDialog.dismissAllowingStateLoss()
        }
    }

    override fun onBackPressed() {
        if (dealItemFragment.isVisible) {
            showDealListFragment()
        } else if (dealListFragment.isVisible) {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_DEAL_DATA, lastDealItemData)
        outState.putBoolean(ACTION_START_DEAL_ITEM_VIEW, isDealItemFragmentVisible)
    }

    companion object {
        const val KEY_DEAL_DATA = "deal_data"
        const val ACTION_LOAD_PROGRESS_BAR = "show_progress"
        const val ACTION_HIDE_PROGRESS_BAR = "hide_progress"
    }
}
