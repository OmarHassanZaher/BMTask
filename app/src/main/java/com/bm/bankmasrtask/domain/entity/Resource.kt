package com.bm.bankmasrtask.domain.entity

import androidx.annotation.NonNull
import androidx.annotation.Nullable

class Resource <T>(
    @field:NonNull @param:NonNull val status: Status,
    @field:Nullable @param:Nullable val data: T,
    @field:Nullable @param:Nullable val error_msg: String?,
    @field:Nullable @param:Nullable val responseCode: Int?,
    @field:Nullable @param:Nullable val throwable: Throwable?) {

    companion object{
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null, null, null)
        }
        fun <T> apiError(error_msg: String?, responseCode: Int?): Resource<T?> {
            return Resource(Status.API_ERROR, null, error_msg, responseCode, null)
        }
        fun <T> domainError(throwable: Throwable?): Resource<T?> {
            return Resource(Status.DOMAIN_ERROR, null, null, null, throwable)
        }
        fun <T> loading(): Resource<T?> {
            return Resource(Status.LOADING, null, null, null, null)
        }
    }

    enum class Status {
        SUCCESS, DOMAIN_ERROR, API_ERROR, LOADING
    }

}