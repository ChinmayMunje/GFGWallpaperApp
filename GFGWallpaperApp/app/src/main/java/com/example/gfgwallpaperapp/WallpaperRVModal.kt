package com.example.gfgwallpaperapp

data class WallpaperRVModal(
    var page: Int,
    var per_page: Int,
    var total_results: Int,
    var next_page: String,
    var photos: ArrayList<Photos>,

    )