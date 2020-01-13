package hr.fer.drumre.rec.features.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import hr.fer.drumre.rec.App
import hr.fer.drumre.rec.R
import hr.fer.drumre.rec.commons.ui.base.BaseFragment
import hr.fer.drumre.rec.core.network.services.UserService
import hr.fer.drumre.rec.core.utils.ThemeUtils
import hr.fer.drumre.rec.databinding.FragmentLoginBinding
import hr.fer.drumre.rec.features.login.di.DaggerLoginComponent
import hr.fer.drumre.rec.features.login.di.LoginModule
import hr.fer.drumre.rec.features.login.menu.ToggleThemeCheckBox
import timber.log.Timber
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    layoutId = R.layout.fragment_login
) {

    @Inject
    lateinit var themeUtils: ThemeUtils
    @Inject
    lateinit var userService: UserService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        initializeFacebook()
        initializeGoogle()

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
        menu.findItem(R.id.menu_toggle_theme).apply {
            val actionView = this.actionView
            if (actionView is ToggleThemeCheckBox) {
                actionView.isChecked = themeUtils.isDarkTheme(requireContext())
                actionView.setOnCheckedChangeListener { _, isChecked ->
                    themeUtils.setNightMode(isChecked, DELAY_TO_APPLY_THEME)
                }
            }
        }
    }

    override fun onInitDependencyInjection() {
        DaggerLoginComponent
            .builder()
            .coreComponent(App.coreComponent(requireContext()))
            .loginModule(LoginModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    private fun initializeFacebook() {
        val callbackManager = CallbackManager.Factory.create()
        viewBinding.facebookLoginButton.setPermissions(*PERMISSIONS)
        viewBinding.facebookLoginButton.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    val accessToken = loginResult?.accessToken?.token
                    viewModel.loginWithFacebook(accessToken)
                }

                override fun onCancel() = Unit

                override fun onError(exception: FacebookException) = Unit
            })
    }

    private fun initializeGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(activity!!, gso)

        viewBinding.googleLoginButton.setSize(SignInButton.SIZE_STANDARD)
        viewBinding.googleLoginButton.setColorScheme(SignInButton.COLOR_LIGHT)
        viewBinding.googleLoginButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val accessToken = account?.idToken
            viewModel.loginWithGoogle(accessToken)
        } catch (e: ApiException) {
            Timber.e(e)
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
        private const val DELAY_TO_APPLY_THEME = 250L
        @JvmStatic
        private val PERMISSIONS = arrayOf("email", "user_likes")
    }
}
