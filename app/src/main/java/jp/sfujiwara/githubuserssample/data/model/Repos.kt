package jp.sfujiwara.githubuserssample.data.model

import com.google.gson.annotations.SerializedName


/**
 * Created by shn on 2021/02/27
 */
data class Repos(
    var id: Int = 0,
    @SerializedName("full_name") var fullName: String? = null,
    @SerializedName("html_url") var htmlUrl: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("stargazers_count") var stargazersCount: Int = 0,
    @SerializedName("watchers_count") var watchersCount: Int = 0,
    @SerializedName("forks_count") var forksCount: Int = 0,
    @SerializedName("language") var language: String? = null
)