package com.example.gc02.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gc02.R
import com.example.gc02.databinding.ActivityHomeBinding
import com.example.gc02.model.Article
import com.example.gc02.model.User

class HomeActivity : AppCompatActivity(), ConsultarArticuloFragment.OnShopClickListener, TabLayoutFragment.OnShopClickListener, UserProvider{
    private lateinit var user: User
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    companion object {
        const val USER_INFO = "USER_INFO"
        fun start(
            context: Context,
            user: User,
        ) {
            val intent = Intent(context, HomeActivity::class.java).apply {
                putExtra(USER_INFO, user)
            }
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getSerializableExtra(USER_INFO) as User

        setupNavegacion()
        setUpUI(user)
    }

    private fun setupNavegacion() {
        val bottomNavigationItemView = binding.bottomNavigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(
            bottomNavigationItemView,
            navHostFragment.navController
        )
    }

    fun setUpUI(user: User) {
        binding.bottomNavigation.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.page_perfil,
                R.id.page_home,
                R.id.page_articulos
            )
        )
       setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
         //Hide toolbar and bottom navigation when in detail fragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.consultarDetallesArticuloFragment)
             {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_home, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        // Configure the search info and add any event listeners.

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            // User chooses the "Settings" item. Show the app settings UI.
            val action = SettingFragmentDirections.actionHomeToSettingsFragment()
            navController.navigate(action)
            true
        }
        else -> {
            // The user's action isn't recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onShopClick(shop: Article) {
        val action = ConsultarArticuloFragmentDirections.actionPageArticulosToConsultarDetallesArticuloFragment(shop)
        navController.navigate(action)
    }

    override fun onShopClickProductosPerfil(shop: Article) {
        val action = TabLayoutFragmentDirections.actionProductosPerilToConsultarDetallesArticuloFragment(shop)
        navController.navigate(action)
    }

    override fun getUser() = user
}