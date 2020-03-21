package com.example.kotlinretrofit2rxjava.Retrofit

import com.example.kotlinretrofit2rxjava.Model.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface IMyAPI {
    @get:GET("posts")
    val posts: Observable<List<Post>>
}