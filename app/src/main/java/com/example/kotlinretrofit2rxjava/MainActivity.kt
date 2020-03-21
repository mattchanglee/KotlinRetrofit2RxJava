package com.example.kotlinretrofit2rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinretrofit2rxjava.Adapter.PostAdapter
import com.example.kotlinretrofit2rxjava.Model.Post
import com.example.kotlinretrofit2rxjava.Retrofit.IMyAPI
import com.example.kotlinretrofit2rxjava.Retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal lateinit var jsonApi:IMyAPI
    internal lateinit var compositeDisposable : CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Init API
        val retrofit = RetrofitClient.instance
        jsonApi = retrofit.create(IMyAPI::class.java)

        //view
        recycler_posts.setHasFixedSize (true)
        recycler_posts.layoutManager = LinearLayoutManager(this)
        fetchData();
    }

    private fun fetchData() {
        compositeDisposable = CompositeDisposable();
        compositeDisposable.add(jsonApi.posts
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{posts->displayData(posts)}
        )
    }

    private fun displayData(posts: List<Post>?) {
        val adapter = PostAdapter(this,posts!!)
        recycler_posts.adapter = adapter
    }
}
