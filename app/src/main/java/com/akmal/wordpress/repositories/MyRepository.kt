package com.akmal.wordpress.repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.akmal.wordpress.model.ArtikelModel
import com.akmal.wordpress.utilities.POST_URL
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class MyRepository(val context: Context) {
    private val mutableLiveData = MutableLiveData<ApiResponse<ArrayList<ArtikelModel>>>()
    val liveData get() = mutableLiveData

    // Fungsi untuk mengambil data artikel dari API
    fun GetArtikel(page: String = "1"){
        liveData.value = ApiResponse.Loading()
        val requestQueue = Volley.newRequestQueue(context)
        val url = POST_URL + "page=$page"
        val stringRequest = object: StringRequest(Method.GET, url, {response->
            if (response.isNotEmpty()) {
                val tempList: ArrayList<ArtikelModel> = ArrayList()
                val mainJSONArray = JSONArray(response)

            for (p in 0 until mainJSONArray.length()) {
                val cardObjek = mainJSONArray.getJSONObject(p)
                cardObjek.apply {
                    val id = getInt("id")
                    val title = getJSONObject("title").getString("rendered")
                    val content = getJSONObject("content").getString("rendered")
                    val author = getInt("author")
                    val image = getJSONObject("featured_media").getString("source_url")
                    val model = ArtikelModel(
                        id = id,
                        judul = title,
                        isiKonten = content,
                        penulis = author.toString(),
                        gambar = image
                    )
                    tempList.add(model)
                }
            }
            liveData.value = ApiResponse.Okay(tempList)
        }
        },{
            error-> liveData.value = error.localizedMessage?.let { ApiResponse.Error(it) }

        }){

        }
        requestQueue.add(stringRequest)
    }
}


//                for (p in 0 until mainJSONArray.length()) {
//                    val cardObjek = mainJSONArray.getJSONObject(p)
//                    val id = cardObjek.getInt("id")
//                    val title = cardObjek.optString("title.rendered")
//                    val content = cardObjek.optString("content.rendered")
//                    val author = cardObjek.getInt("author")
//                    val image = cardObjek.optJSONObject("featured_media")
//                    // Tambahkan data ke dalam daftar ArtikelModel
//                }
//            }if (response.isNotEmpty()) {
//            val tempList: ArrayList<ArtikelModel> = ArrayList()
//            val mainJSONArray = JSONArray(response)