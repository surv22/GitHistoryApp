//package com.teikametriks.techtest.exception
//
//import org.junit.runner.RunWith
//import org.scalatest.junit.JUnitRunner
//import org.springframework.http.HttpStatus
//import org.scalatest.{Matchers, WordSpec}
//import org.scalatest.mockito.MockitoSugar
//
//import scala.concurrent.TimeoutException
//
//@RunWith(classOf[JUnitRunner])
//class GitExceptionHandlerSpec extends WordSpec with Matchers with MockitoSugar{
//
//  trait Context {
//    val timoutException = new TimeoutException
//    val gitApiException = new GitApiException
//    val gitApiTimeOutException = new GitApiTimeOutException
//    val nullPointerException = new NullPointerException
//    val noCommitFoundException = new NoCommitFoundException
//
//    val objectUnderTest = GitHistoryExceptionHandler
//  }
//
//  "GitHistory Exception Handler " should {
//    "return GATEWAY_TIMEOUT response if GitApiTimeoutException is thrown " in new Context {
//      val response = objectUnderTest.findMatchingException(gitApiTimeOutException)
//      response.getStatusCode shouldBe HttpStatus.GATEWAY_TIMEOUT
//    }
//
//    "return EXPECTATION_FAILED response if GitApiException is thrown " in new Context {
//      val response = objectUnderTest.findMatchingException(gitApiException)
//      response.getStatusCode shouldBe HttpStatus.EXPECTATION_FAILED
//    }
//
//    "return INTERNAL_SERVICE_EXCEPTION when exception occurs on service side " in new Context {
//      val response = objectUnderTest.findMatchingException(nullPointerException)
//      response.getStatusCode shouldBe HttpStatus.INTERNAL_SERVER_ERROR
//    }
//
//    "return OK status with no commit found message if no commits for that user is found " in new Context {
//      val response = objectUnderTest.findMatchingException(noCommitFoundException)
//      response.getStatusCode shouldBe HttpStatus.OK
//    }
//  }
//}
