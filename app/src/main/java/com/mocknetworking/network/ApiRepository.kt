


import com.mocknetworking.model.LoginData
import com.mocknetworking.network.ApiClient
import io.reactivex.Observable

object ApiRepository {

    fun getLoginParams(params: LinkedHashMap<String, Any?>): Observable<LoginData> {
        return ApiClient.getApiService().callLoginApi(params)
    }

}