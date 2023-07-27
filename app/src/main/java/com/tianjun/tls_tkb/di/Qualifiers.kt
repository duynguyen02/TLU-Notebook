package com.tianjun.tls_tkb.di

import javax.inject.Qualifier

annotation class Qualifiers{
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RawResponseRetrofitConverter

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class JsonResponseRetrofitConverter
}

