package gladun.vlad.testme.data.network

import gladun.vlad.testme.BuildConfig
import okhttp3.Request

/**
 * In the real life app a session object or provider will be injected here with a way to extract securely stored key and secret obtained in the auth/login flow
 */
class AuthInterceptor : CommonInterceptor() {
    override fun addHeaders(requestBuilder: Request.Builder) {
        super.addHeaders(requestBuilder)
        requestBuilder.addHeader("Authorization", "OAuth oauth_consumer_key=${BuildConfig.CONSUMER_KEY}," +
                "oauth_signature_method=PLAINTEXT,oauth_signature=${BuildConfig.CONSUMER_SECRET}&")
    }
}