package com.example.farfarawaylibrary.model.netcomponents

import retrofit2.Response

// Data class for manage the fail or success response and the exceptions
data class SafeCallResponse<T>(
    val status : Status,
    val data : Response<T>?,
    val error : Exception?
){
    sealed class Status {
        object Success : Status()
        object Failure : Status()
    }

    companion object {
        // For easy construction of a successful api call response
        fun <T> successCall(data: Response<T>) : SafeCallResponse<T>{
            return SafeCallResponse(
                status = Status.Success,
                data = data,
                error = null
            )
        }
        // For easy construction of a failed api call response
        fun <T> failureCall(e : Exception) : SafeCallResponse<T>{
            return SafeCallResponse(
                status = Status.Failure,
                data = null,
                error = e
            )
        }
    }

    val callFailed : Boolean
        get() = this.status == Status.Failure

    val callSuccessful : Boolean
        get() = this.status == Status.Success

    val body : T
        get() = this.data!!.body()!!
}