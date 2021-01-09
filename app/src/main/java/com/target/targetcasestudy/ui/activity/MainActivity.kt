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
import com.target.targetcasestudy.ui.payment.PaymentDialogFragment
import com.target.targetcasestudy.util.gone
import com.target.targetcasestudy.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ActionListener {

    private val dealListFragment = DealListFragment()
    private val dealItemFragment = DealItemFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showDealListFragment()
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
        }
    }

    private fun showDealListFragment() {
        back.gone()
        showFragment(dealListFragment)
    }

    private fun showDealItemFragment(data: Any?) {
        back.show()
        dealItemFragment.arguments = Bundle().apply { putInt(KEY_DEAL_DATA, data as Int) }
        showFragment(dealItemFragment)
    }

    /**
     * Standard mechanism for displaying a fragment
     */
    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    companion object {
        const val KEY_DEAL_DATA = "deal_data"
    }
}
