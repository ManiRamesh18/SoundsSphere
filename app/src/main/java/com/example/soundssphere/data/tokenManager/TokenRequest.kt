package com.example.soundssphere.data.tokenManager

data class TokenRequest(var grant_type : String ?= null, var client_id : String ?= null, var client_secret : String ?= null)