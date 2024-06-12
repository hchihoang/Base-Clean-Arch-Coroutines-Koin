package com.inetkr.base.domain.usecase.base

interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(throwable: Throwable)
}
