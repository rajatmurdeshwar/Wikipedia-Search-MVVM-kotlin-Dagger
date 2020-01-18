package com.test.rajat.wikisearch.model

data class ErrorEnvelope(val status_code: Int,
                         val status_message: String,
                         val success: Boolean)