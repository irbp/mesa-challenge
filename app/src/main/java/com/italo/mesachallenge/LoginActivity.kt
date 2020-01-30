package com.italo.mesachallenge

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.facebook.*
import com.facebook.login.LoginResult
import com.italo.mesachallenge.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val TAG = LoginActivity::class.java.simpleName
    private lateinit var callbackManager: CallbackManager
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        callbackManager = CallbackManager.Factory.create()

        btn_login_fb.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                val accessToken = result?.accessToken
                getLoginInformation(accessToken!!)
            }

            override fun onCancel() {
                Log.d(TAG, "Login canceled")
            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(this@LoginActivity, getString(R.string.login_error), Toast.LENGTH_LONG).show()
            }

        })

        // Waiting 1 second to check access token
        // This is necessary because facebook sdk takes some time to initialize
        Handler().postDelayed({ loginViewModel.checkLogin() }, 1000)

        loginViewModel.isSuccess.observe(this, Observer {
            if (it == true) {
                startMainActivity()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getLoginInformation(accessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(
            accessToken
        ) { jsonObject, _ ->
            try {
                val name = jsonObject.getString("name")
                val picUrl =
                    jsonObject.getJSONObject("picture").getJSONObject("data").getString("url")
                loginViewModel.saveCurrentUser(name, picUrl) {}
            } catch (e: JSONException) {
                Log.e(TAG, e.message.toString())
            }
        }

        val parameters = Bundle()
        parameters.putString("fields", "id,name,email,picture.width(200)")
        request.parameters = parameters
        request.executeAsync()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
