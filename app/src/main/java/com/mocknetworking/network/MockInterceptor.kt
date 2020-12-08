import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import org.json.JSONObject

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        if (uri.endsWith(suffix = "login")) {

            val buffer = Buffer()
            chain.request().body?.writeTo(buffer);
            val params = JSONObject(buffer.readUtf8())

            val jsonObject = JSONObject()
            jsonObject.put("isSuccessful", true)
            jsonObject.put("username", params["username"])

            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(jsonObject.toString())
                .body(
                    ResponseBody.create(
                        "application/json".toMediaTypeOrNull(),
                        jsonObject.toString().toByteArray()
                    )
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }

}

