package es.adriiiprieto.notesapp.base

import java.io.Serializable

sealed class BaseState<S>(val data: S) : BaseViewState() {

    class Normal<S>(data: S) : BaseState<S>(data)

    class Loading<S>(data: S, val dataLoading: BaseExtraData? = null) : BaseState<S>(data)

    class Error<S>(data: S, val dataError: Throwable) : BaseState<S>(data)

}

data class BaseExtraData(val type: Int, val extraData: Any? = null) : Serializable
