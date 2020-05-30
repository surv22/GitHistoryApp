package com.teikametriks.techtest.util

import com.google.common.base.Stopwatch
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.{DefaultHttpClient, HttpClientBuilder}


@Component
class GitClientUtil {

  val logger = LoggerFactory.getLogger(getClass)

  def getDataFromGitApi(request: HttpGet): HttpResponse = {
    val client = HttpClientBuilder.create().build()
    val stopWatch = Stopwatch.createStarted()
    val res = client.execute(request)
    res
  }
}

