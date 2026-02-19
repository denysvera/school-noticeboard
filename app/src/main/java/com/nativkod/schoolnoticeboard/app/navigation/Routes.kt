package com.nativkod.schoolnoticeboard.app.navigation

object Routes {
    const val FEED = "feed"
    const val DETAIL = "detail"
    const val ARG_ID = "id"

    fun detailRoute(id: String) = "$DETAIL/$id"
    const val detailPattern = "$DETAIL/{$ARG_ID}"
}