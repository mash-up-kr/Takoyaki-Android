package org.mashup.takoyaki.data.repository

import org.mashup.takoyaki.data.remote.api.ApiService

open class BaseRepository(protected val apiService: ApiService)