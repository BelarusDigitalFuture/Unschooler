package unschooler.code.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.a_auth.*
import unschooler.code.R
import unschooler.code.ui.main.MainActivity
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_auth)

        authGoogleButton.setOnClickListener {
            auth(
                AuthUI.IdpConfig.GoogleBuilder().build()
            )
        }

        viewModel.isAuth.observe(this, Observer { isAuth ->
            if (isAuth) {
                val intent = Intent(this@AuthActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }



    fun auth(provider: AuthUI.IdpConfig) {
        val providers = arrayListOf(
            provider
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            SIGN_IN_REQUEST_CODE
        )
    }

    companion object {
        const val SIGN_IN_REQUEST_CODE = 1
    }

}