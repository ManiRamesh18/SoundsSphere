package com.example.soundssphere.data.util

sealed class FetchedResource<ResourceType, FailureType> {

    data class Success<ResourceType, FailureType>(val data: ResourceType) : FetchedResource<ResourceType, FailureType>()

    data class Failure<ResourceType, FailureType>(
        val cause: FailureType,
        val data: ResourceType? = null
    ) : FetchedResource<ResourceType, FailureType>()
}