package com.market.admin.network

import java.io.IOException
class NoConnectivityException : IOException() {
    override val message: String?
        get() = "No connectivity exception"
}
