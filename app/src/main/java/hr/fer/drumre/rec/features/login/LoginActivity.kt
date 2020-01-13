package hr.fer.drumre.rec.features.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import hr.fer.drumre.rec.App
import hr.fer.drumre.rec.MainActivity
import hr.fer.drumre.rec.R
import hr.fer.drumre.rec.core.network.services.UserService
import hr.fer.drumre.rec.core.utils.ThemeUtils
import hr.fer.drumre.rec.databinding.ActivityLoginBinding
import hr.fer.drumre.rec.features.login.di.DaggerLoginComponent
import hr.fer.drumre.rec.features.login.di.LoginModule
import hr.fer.drumre.rec.features.login.menu.ToggleThemeCheckBox
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    private lateinit var callbackManager: CallbackManager
    @Inject
    lateinit var viewModel: LoginViewModel
    lateinit var binding: ActivityLoginBinding
    @Inject
    lateinit var themeUtils: ThemeUtils
    @Inject
    lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        DaggerLoginComponent
            .builder()
            .coreComponent(App.coreComponent(this))
            .loginModule(LoginModule(this))
            .build()
            .inject(this)

        initializeFacebook()
        initializeGoogle()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        menu.findItem(R.id.menu_toggle_theme).apply {
            val actionView = this.actionView
            if (actionView is ToggleThemeCheckBox) {
                actionView.isChecked = themeUtils.isDarkTheme(this@LoginActivity)
                actionView.setOnCheckedChangeListener { _, isChecked ->
                    themeUtils.setNightMode(isChecked, DELAY_TO_APPLY_THEME)
                }
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun initializeFacebook() {
        callbackManager = CallbackManager.Factory.create()

        binding.facebookLoginButton.setPermissions(*PERMISSIONS)
        binding.facebookLoginButton.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    val accessToken = loginResult?.accessToken?.token
                    viewModel.loginWithFacebook(accessToken)

                    Handler().postDelayed({
                        MainActivity.startWith(this@LoginActivity)
                    }, DELAY)
                }

                override fun onCancel() = Unit

                override fun onError(exception: FacebookException) = Unit
            })
    }

    private fun initializeGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestServerAuthCode(getString(R.string.server_client_id))
            .requestEmail()
            .requestProfile()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleLoginButton.setSize(SignInButton.SIZE_STANDARD)
        binding.googleLoginButton.setColorScheme(SignInButton.COLOR_AUTO)
        binding.googleLoginButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val accessToken = account?.idToken
            viewModel.loginWithGoogle(accessToken)
            Handler().postDelayed({
                MainActivity.startWith(this)
            }, DELAY)
        } catch (e: ApiException) {
            Timber.e(e)
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
        private const val DELAY = 1000L
        private const val DELAY_TO_APPLY_THEME = 250L
        @JvmStatic
        private val PERMISSIONS = arrayOf("email", "user_likes", "public_profile")

        fun startWith(context: Context) {
            val intent = Intent(context, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            context.startActivity(intent)
        }
    }
}
