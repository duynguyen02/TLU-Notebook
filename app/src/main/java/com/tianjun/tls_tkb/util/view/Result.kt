package com.tianjun.tls_tkb.util.view

sealed class Result{
    class Success<T>(val t : T) : Result()
    class Loading(val progress : String?) : Result()
    class Error(val error : String) : Result()
    object Nothing : Result()
}
