package com.mocknetworking.ui


import com.mocknetworking.base.BaseViewModel
import com.mocknetworking.base.Status
import com.mocknetworking.model.LoginData
import io.reactivex.Observable

class UserViewModel : BaseViewModel<LoginData>() {

    fun getLoginData(username: String?, password: String?) {
        val params = LinkedHashMap<String, Any?>()
        params["username"] = username
        params["password"] = password
        createRequest(ApiRepository.getLoginParams(params))
    }

    private var welcomeMsg = "Welcome Back "

    fun setUserName(username: String) {
        welcomeMsg += username
    }

    fun getUserName() = welcomeMsg

    private fun createRequest(request: Observable<LoginData>) {
        compositeDisposable.add(
            request.doOnSubscribe { status.postValue(Status.LOADING) }
                .subscribe({
                    data.postValue(it)
                    status.postValue(Status.SUCCESS)
                }, {
                    error.postValue(it)
                    status.postValue(Status.ERROR)
                })
        )
    }
}