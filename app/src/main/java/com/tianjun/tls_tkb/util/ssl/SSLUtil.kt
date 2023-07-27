package com.tianjun.tls_tkb.util.ssl

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.ByteArrayInputStream
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.Base64
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLPeerUnverifiedException
import kotlin.jvm.Throws

object SSLUtil {

    class CanNotGetSSLCertificate : Exception("The SSL certificate cannot be retrieved: The website may not have an SSL certificate or for an unspecified reason.")

    @Throws(SSLPeerUnverifiedException::class,Exception::class)
    fun getSSLPublicKey(urlString: String): Certificate {
        val client = OkHttpClient.Builder().callTimeout(10, TimeUnit.SECONDS).build()
        val request = Request.Builder().url(urlString).build()
        val response = client.newCall(request).execute()
        val certificates = response.handshake?.peerCertificates

        if (!certificates.isNullOrEmpty()) {
            return certificates[0]
        }

        throw CanNotGetSSLCertificate()
    }

    fun formatCertificateAsString(certificate: Certificate): String {
        val base64Certificate = Base64.getEncoder().encodeToString(certificate.encoded)
        val formattedCertificate = StringBuilder()
        formattedCertificate.append("-----BEGIN CERTIFICATE-----\n")
        val chunkSize = 64
        var index = 0
        while (index < base64Certificate.length) {
            formattedCertificate.append(base64Certificate.substring(index, kotlin.math.min(index + chunkSize, base64Certificate.length)))
            formattedCertificate.append("\n")
            index += chunkSize
        }
        formattedCertificate.append("-----END CERTIFICATE-----")
        return formattedCertificate.toString()
    }

    fun isSSLCertificateValid(certificate: Certificate): Boolean {
        return try {
            val x509Certificate = certificate as X509Certificate
            val expirationDate = x509Certificate.notAfter
            val currentDate = Date()
            currentDate.before(expirationDate)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun convertCertificateStringToCertificate(certificateString: String): Certificate? {
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val certificateBytes = certificateString.toByteArray()
            val certificateInputStream = ByteArrayInputStream(certificateBytes)
            return certificateFactory.generateCertificate(certificateInputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


}