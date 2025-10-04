package gladun.vlad.testme.data.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Base interceptor class with headers shared across auth and unauth requests.
 */
open class CommonInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        addHeaders(requestBuilder)

        val modifiedRequest = requestBuilder.build()
        val response = chain.proceed(modifiedRequest)
        return response
    }

    protected open fun addHeaders(requestBuilder: Request.Builder) {
        requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded")
    }
}