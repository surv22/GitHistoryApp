package com.teikametriks.techtest.exception

import org.springframework.http.{HttpHeaders, HttpStatus, ResponseEntity}

class GitHistoryException extends Exception

case class GitApiTimeOutException(errorMessage: String = "Git Api is taking too long to respond") extends GitHistoryException
case class GitApiException(errorMessage: String = "cannot fetch git event data.Unable to get response from git vendor") extends GitHistoryException
case class MyGitHistoryException(errorMessage: String = "oops! something is wrong. we are looking into it") extends GitHistoryException
case class NoCommitFoundException(errorMessage: String = "Looks like you don't have any commit on your github account") extends GitHistoryException

object GitHistoryExceptionHandler {
  def findMatchingException(ex: Throwable): ResponseEntity[String] = {
    ex match {
      case gitApiTimeOutEx: GitApiTimeOutException => new ResponseEntity(gitApiTimeOutEx.errorMessage, new HttpHeaders(), HttpStatus.GATEWAY_TIMEOUT)
      case gitApiException: GitApiException => new ResponseEntity(gitApiException.errorMessage, new HttpHeaders, HttpStatus.EXPECTATION_FAILED)
      case noCommitFoundException: NoCommitFoundException => new ResponseEntity(noCommitFoundException.errorMessage, new HttpHeaders, HttpStatus.OK)
      case _ => new ResponseEntity(MyGitHistoryException().errorMessage, new HttpHeaders, HttpStatus.INTERNAL_SERVER_ERROR)
    }
  }
}




