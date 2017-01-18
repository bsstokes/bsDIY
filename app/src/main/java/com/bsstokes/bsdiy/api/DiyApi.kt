package com.bsstokes.bsdiy.api

import okhttp3.HttpUrl
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface DiyApi {

    companion object {
        const val ENDPOINT = "https://api.diy.org/"
    }

    @GET("/skills?limit=1000&offset=0")
    fun getSkills(): Observable<Response<DiyResponse<List<Skill>>>>

    @GET("/skills/{skill_url}/challenges")
    fun getChallenges(@Path("skill_url") skillUrl: String): Observable<Response<DiyResponse<List<Challenge>>>>

    object Helper {
        fun normalizeUrl(url: String): String {
            if (url.isBlank()) {
                return url
            }

            val httpUrl: HttpUrl? = if (url.startsWith("http", ignoreCase = true)) {
                HttpUrl.parse(url)
            } else {
                HttpUrl.parse("https:" + url)
            }

            return httpUrl?.toString() ?: ""
        }
    }

    data class DiyResponse<T>(var head: Head?, var response: T?) {
        data class Head(var code: Int?, var status: String?, var collection: Collection?) {
            data class Collection(var limit: Int?, var offset: Int?)
        }
    }

    data class Skill(var id: Long,
                     var active: Boolean?,
                     var url: String?,
                     var title: String?,
                     var description: String?,
                     var icons: Icons?,
                     var images: Images?,
                     var color: String?) {
        data class Icons(var small: String?, var medium: String?)
        data class Images(var small: String?, var medium: String?, var large: String?)
    }

    data class Challenge(var id: Long,
                         var active: Boolean?,
                         var title: String?,
                         var description: String?,
                         var image: Image?) {
        data class Image(var ios_600: Asset?)
    }

    data class Asset(var url: String?, var mime: String?, var width: Int?, var height: Int?)
}