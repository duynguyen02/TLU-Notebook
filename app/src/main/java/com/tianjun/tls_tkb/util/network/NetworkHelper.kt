package com.tianjun.tls_tkb.util.network

import android.content.Context
import android.net.ConnectivityManager
import java.net.MalformedURLException
import java.net.URISyntaxException
import java.net.URL


object NetworkHelper {
    class URLNeedStartWithHttpOrHttps : Exception("URLNeedStartWithHttpOrHttps")
    fun isValidURL(url : String) : Boolean{
        return try {
            if (!url.startsWith("http://") && !url.startsWith("https://")){
                throw URLNeedStartWithHttpOrHttps()
            }
            URL(url).toURI()
            true
        } catch (e: MalformedURLException) {
            false
        } catch (e: URISyntaxException) {
            false
        } catch (e: URLNeedStartWithHttpOrHttps){
            false
        }
    }
}