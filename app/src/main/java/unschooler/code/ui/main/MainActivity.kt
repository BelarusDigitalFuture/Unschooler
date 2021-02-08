package unschooler.code.ui.main

import android.content.Intent
import android.os.Bundle
import unschooler.code.utils.OnBackPressable
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.a_main.*
import unschooler.code.R
import unschooler.code.ui.auth.AuthActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_main)
        navController = findNavController(R.id.nav_host_fragment)
        bottomNavigation.setupWithNavController(navController)

        viewModel.isAuth.observe(this, Observer { isAuth ->
            if (!isAuth) {
                val intent = Intent(this@MainActivity, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    override fun onBackPressed() {
        val fragment =
            (supportFragmentManager.fragments.getOrNull(0) as NavHostFragment)
                .childFragmentManager
                .fragments
                .getOrNull(0)
        if (fragment is OnBackPressable && fragment.onBackPressed())
            return
        super.onBackPressed()
    }
}