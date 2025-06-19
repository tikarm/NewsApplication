package com.tigran.applications.newsapplication.data.remote.exception

class ApiException(message: String, e: Exception? = null) : Exception(message, e)