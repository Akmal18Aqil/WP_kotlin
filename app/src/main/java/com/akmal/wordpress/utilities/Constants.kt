package com.akmal.wordpress.utilities

const val POST_PER_PAGE = 7
const val WEB_URL = "https://annur2.net/"
const val BASE_URL = "$WEB_URL/wp-json/wp/v2"
const val POST_URL = "$BASE_URL/posts?_embed&per_page=$POST_PER_PAGE&"