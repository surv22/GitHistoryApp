package com.teikametriks.techtest.model.dao

import scala.language.postfixOps
import com.teikametriks.techtest.exception.{GitApiException, GitApiTimeOutException}
import com.teikametriks.techtest.model.dto.PushEvent
import com.teikametriks.techtest.util.GitClientUtil
import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization._
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import scala.io.Source

trait GitHistoryDao {
  def getUserEvents(user: String): List[PushEvent]
}

@Repository
class GitHistoryDaoImpl(@Autowired clientUtil: GitClientUtil) extends GitHistoryDao {

  val logger = LoggerFactory.getLogger(getClass)
  implicit val format = DefaultFormats

  def getUserEvents(user: String): List[PushEvent] = {
    try {
      val uri = "https://api.github.com/users/" + user + "/events"
      val gitGet = new HttpGet(uri)

      val response = clientUtil.getDataFromGitApi(gitGet)
     response.getStatusLine.getStatusCode match {
        case HttpStatus.SC_OK => logger.info(s"received response: ${response.toString}")
        case HttpStatus.SC_REQUEST_TIMEOUT => throw GitApiTimeOutException()
        case _ => throw GitApiException()
      }

      if (Option(response.getEntity) isEmpty) {
        logger.error("response is empty!")
      }

      val inputStream = response.getEntity.getContent

      val responseStr = Source.fromInputStream(inputStream).mkString
      val event = read[List[PushEvent]](responseStr)
      event.filter(_.payload.commits.size > 0)
    } catch {
      case ex: Exception => throw ex
    }
  }
}

