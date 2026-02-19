package com.nativkod.schoolnoticeboard.core.security

interface TokenStore {
    fun getToken(): String?
    fun setToken(token: String)
    fun clear()
}
