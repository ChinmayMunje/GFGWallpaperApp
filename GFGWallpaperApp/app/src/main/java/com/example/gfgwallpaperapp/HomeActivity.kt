package com.example.gfgwallpaperapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeActivity : AppCompatActivity(), CategoryClickInterface {

    lateinit var wallpapersRV: RecyclerView
    lateinit var categoryRV: RecyclerView
    lateinit var categoryRVAdapter: CategoryRVAdapter
    lateinit var wallpaperRVAdapter: WallpaperRVAdapter
    lateinit var loadingPB: ProgressBar
    lateinit var searchEdt: EditText
    lateinit var searchIV: ImageView
    lateinit var wallpaperList: List<String>
    lateinit var categoryList: List<CategoryRVModal>
    lateinit var retrofitAPI: RetrofitAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        window.statusBarColor = getColor(R.color.black_shade_1)
        wallpapersRV = findViewById(R.id.idRVWallpapers)
        categoryRV = findViewById(R.id.idRVCategories)
        loadingPB = findViewById(R.id.idPBLoading)
        searchEdt = findViewById(R.id.idEdtSearch)
        searchIV = findViewById(R.id.idIVSearch)
        categoryList = ArrayList<CategoryRVModal>()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitAPI = retrofit.create(RetrofitAPI::class.java)

        searchIV.setOnClickListener {
            if (searchEdt.text.toString().isNotEmpty()) {
                getWallpapersByCategory(retrofitAPI, searchEdt.text.toString())
            }
        }
        getCategories()
        getWallpapersByCategory(retrofitAPI,"")
    }

    private fun getWallpapersByCategory(retrofitAPI: RetrofitAPI, category: String) {
        var call: Call<WallpaperRVModal>? = null
        if(category.isNotEmpty()){
           call = retrofitAPI.getWallpaperByCategory(category,30,1)
        }else{
            call = retrofitAPI.getWallpapers()
        }

        wallpaperList = ArrayList<String>()
        loadingPB.visibility = View.VISIBLE

        call!!.enqueue(object : Callback<WallpaperRVModal?> {
            override fun onResponse(
                call: Call<WallpaperRVModal?>,
                response: Response<WallpaperRVModal?>
            ) {
                if (response.isSuccessful) {

                    val md = response.body()
                    Log.e("TAGAPI", "Array list data is : ${md?.photos}")
                    Log.e("TAGAPI", "Response frpm API is : ${response.body()}")

                    loadingPB.visibility = View.GONE

                    val photoArrayList: ArrayList<Photos> = md!!.photos

                    try {
                        if (photoArrayList.isNotEmpty()) {
                            for (i in 0 until photoArrayList.size) {
                                val photoObj = photoArrayList.get(i)
                                val imgUrl: String = photoObj.src.portrait
                                wallpaperList = wallpaperList + imgUrl
                            }
                        } else {
                            Toast.makeText(
                                this@HomeActivity,
                                "No Wallpapers found",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        wallpaperRVAdapter = WallpaperRVAdapter(wallpaperList, applicationContext)
                        wallpapersRV.layoutManager = GridLayoutManager(applicationContext, 2)
                        wallpapersRV.adapter = wallpaperRVAdapter
                        wallpaperRVAdapter.notifyDataSetChanged()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Log.e("TAG", "JSON Exception is : ${e.message}")
                    }

                } else {
                    Log.e("TAGAPI", "Fail to get response : ${response}")
                }
            }

            override fun onFailure(call: Call<WallpaperRVModal?>, t: Throwable) {
                Log.e("TAGAPI", "Error is : ${t.message}")
            }
        })
    }

    private fun getCategories() {
        categoryList = categoryList + CategoryRVModal(
            "Nature",
            "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
        )

        categoryList = categoryList + CategoryRVModal(
            "Architecture",
            "https://images.pexels.com/photos/256150/pexels-photo-256150.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
        )
        categoryList = categoryList + CategoryRVModal(
            "Arts",
            "https://images.pexels.com/photos/1194420/pexels-photo-1194420.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
        )
        categoryList = categoryList + CategoryRVModal(
            "Music",
            "https://images.pexels.com/photos/4348093/pexels-photo-4348093.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
        )


        categoryList = categoryList + CategoryRVModal(
            "Abstract",
            "https://images.pexels.com/photos/2110951/pexels-photo-2110951.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
        )

        categoryList = categoryList + CategoryRVModal(
            "Cars",
            "https://images.pexels.com/photos/3802510/pexels-photo-3802510.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
        )

        categoryList = categoryList + CategoryRVModal(
            "Flowers",
            "https://images.pexels.com/photos/1086178/pexels-photo-1086178.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
        )

        categoryRVAdapter = CategoryRVAdapter(categoryList, this, this)
        categoryRV.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        categoryRV.adapter = categoryRVAdapter
        categoryRVAdapter.notifyDataSetChanged()
    }

    override fun onCategoryClick(position: Int) {
        val category: String = categoryList.get(position).categoryName
        getWallpapersByCategory(retrofitAPI, category)
    }

}