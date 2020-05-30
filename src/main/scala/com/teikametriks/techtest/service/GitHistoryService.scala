package com.teikametriks.techtest.service

import java.text.SimpleDateFormat
import java.util.Date

import com.teikametriks.techtest.exception.{GitHistoryExceptionHandler, NoCommitFoundException}

import scala.math.min
import com.teikametriks.techtest.model.dao.GitHistoryDao
import com.teikametriks.techtest.model.dto.PushEvent
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpHeaders, HttpStatus, ResponseEntity}
import org.springframework.stereotype.Service

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

trait GitHistoryService {

  def getMostUsedKeyword(user: String):ResponseEntity[String]
  def getBusiestCommitHour(user: String): ResponseEntity[String]
}

@Service
class GitHistoryServiceImpl(@Autowired gitHistoryDao: GitHistoryDao) extends GitHistoryService {


  val dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
  val logger = LoggerFactory.getLogger(getClass)

  /**
   * this method calls git event api to return push events of a user
   * it extracts the commit messages from the commits in the event
   * and tries to find the most used keywords. For more relatable results,
   * only alphabetic keywords are returned. In case there are no commits, error msg is sent
   * @param user - extracted from Principal object of JavaSecurity(will contain name of authenticated user)
   * @return - upto 5 most frequent keywords in commit messages
   * */
  override def getMostUsedKeyword(user: String): ResponseEntity[String] = {
    Try{
      val events = gitHistoryDao.getUserEvents(user)
      getMostUsedKeywordsInCommits(events)
    } match {
      case Success(keywords) => {
        val response = "Your most used keywords are : " + keywords.mkString(", ")
        logger.info(s"found these frequent keywords in commit messages of user $user : $response")
        new ResponseEntity(response, new HttpHeaders, HttpStatus.OK)
      }
      case Failure(ex) => {
        logger.error(s"Some error occured when trying to identify frequent keywords for user $user : $ex")
        GitHistoryExceptionHandler.findMatchingException(ex)
      }
    }
  }

  /***
   * this method calls git event api to return push events of a user
   * it extracts the hour of the day from the created_at attribute of the result.
   * for user friendliness, the time is converted in 12 hour clock notation
   *
   * @param user - extracted from Principal object of JavaSecurity(will contain name of authenticated user)
   * @return - most frequent hour of commit in AM/PM format
   */
  override def getBusiestCommitHour(user: String): ResponseEntity[String] = {
    Try {
      val events = gitHistoryDao.getUserEvents(user)
      val commitTs = events.map(_.created_at).map(t => dateFormat.parse(t))
      if(commitTs.isEmpty) throw NoCommitFoundException()
      val commitHour = commitTs.map(_.getHours)
      val mostFrequentHour = commitHour.groupBy(i => i).mapValues(_.size).maxBy(_._2)._1
      val mostFreqHour = mostFrequentHour match {
        case am if (am <= 12) => am.toString + " AM"
        case pm if (pm > 12) => (pm - 12).toString + " PM"
      }
      mostFreqHour
    } match {
      case Success(mostFreqHour) => {
        logger.info(s"most frequent hour of commit for user $user is $mostFreqHour")
        new ResponseEntity("Most frequent hour of commit is "+mostFreqHour, new HttpHeaders, HttpStatus.OK)
      }
      case Failure(ex) => {
        logger.error(s"Some error occured when trying to identify frequent hour of commit for $user : $ex")
        GitHistoryExceptionHandler.findMatchingException(ex)
      }
    }

  }

  private def getMostUsedKeywordsInCommits(events: List[PushEvent]): List[String] = {
    val commitMsg = events.flatMap(event => event.payload.commits.map(c => (c.message, dateFormat.parse(event.created_at))))
    val lastTenCommits = commitMsg.sortBy(_._2)(Ordering[Date].reverse).take(10)
    val lastTenCommitMessage = lastTenCommits.map(_._1)
    if(lastTenCommitMessage.isEmpty) throw NoCommitFoundException()
    val words = lastTenCommitMessage.flatMap(_.split("[\\s@&.?$+-]+"))
    val alphabeticWords = words.filter(word => word.forall(_.isLetter)).map(_.toLowerCase()) //taking only alphabetic to avoid random characters, notation
    val countMap = alphabeticWords.foldLeft(mutable.TreeMap.empty[String, Int]) {
      (count, word) => count + (word -> (count.getOrElse(word, 0) + 1))
    }
   val sortedMap = countMap.toList.sortBy(-_._2)
    val mostUsedKeywods = sortedMap.take(min(5, sortedMap.size))
    mostUsedKeywods.map(_._1)
  }
}
