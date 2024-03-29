package com.example.gc02.utils

import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Toast
import com.example.gc02.utils.CredentialCheck

class CredentialCheck {
    var fail: Boolean = false
    var msg: String = ""
    var error: CredentialError = CredentialError.PasswordError

    companion object {

        private val MINCHARS = 4

        private val checks = arrayOf(
            CredentialCheck().apply {
                fail = false
                msg = "Your credentials are OK"
                error = CredentialError.Success
            },
            CredentialCheck().apply {
                fail = true
                msg = "Invalid username"
                error = CredentialError.UsernameError
            },
            CredentialCheck().apply {
                fail = true
                msg = "Invalid password"
                error = CredentialError.PasswordError
            },
            CredentialCheck().apply {
                fail = true
                msg = "Passwords do not match"
                error = CredentialError.PasswordError
            }

        )

        fun login(username: String, password: String): CredentialCheck {
            return if (username.isBlank() || username.length < MINCHARS) checks[1]
            else if (password.isBlank() || password.length < MINCHARS) checks[2]
            else checks[0]
        }
        fun passwordOk(pass1: String, pass2: String): CredentialCheck {
            return if (pass1.isBlank() || pass1.length < MINCHARS) checks[2]
            else if (pass1 != pass2) checks[3]
            else checks[0]
        }
        fun join(username: String,email: String, password: String, repassword: String): CredentialCheck {
            return if (username.isBlank() || username.length < MINCHARS) checks[1]
            else if (password.isBlank() || password.length < MINCHARS) checks[2]
            else if (password != repassword) checks[3]
            else checks[0]
        }

        /**
        fun joinSeguro(username: String,email: String, password: String, repassword: String): CredentialCheck {
            return if (username.isBlank() || username.length < MINCHARS) checks[1]
            else if (password.isBlank() || password.length < MINCHARS) checks[2]
            else if (password != repassword) checks[3]
            else checks[0]
        }*/

        fun join_Modificar(username: String,email: String, password: String): CredentialCheck {
            return if (username.isBlank() || username.length < MINCHARS) checks[1]
            else if (password.isBlank() || password.length < MINCHARS) checks[2]
            else checks[0]
        }

        enum class CredentialError {
            PasswordError, UsernameError, Success
        }
    }
}