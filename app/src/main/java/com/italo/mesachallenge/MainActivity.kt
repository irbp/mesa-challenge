package com.italo.mesachallenge

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.italo.mesachallenge.fragment.FilterDialogFragment
import com.italo.mesachallenge.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), FilterDialogFragment.OnDialogSelectorListener {

    private var currentOption = 0
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)

        // Bottom navigation setup
        bottom_nav.setupWithNavController(navController)

        // Action bar setup
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.filter_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_filter) {
            val filterDialog = FilterDialogFragment(this, currentOption)
            filterDialog.show(supportFragmentManager, FILTER_DIALOG_TAG)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDialogSelectedOption(option: Int) {
        mainViewModel.changeCategory(option)
        currentOption = option
    }

    companion object {
        private const val FILTER_DIALOG_TAG = "filterDialog"
    }
}
