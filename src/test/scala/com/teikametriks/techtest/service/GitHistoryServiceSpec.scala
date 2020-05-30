//package com.teikametriks.techtest.service
//
//import com.teikametriks.techtest.exception.NoCommitFoundException
//import com.teikametriks.techtest.model.dao.GitHistoryDao
//import com.teikametriks.techtest.model.dto.{Commit, Payload, PushEvent}
//import org.junit.runner.RunWith
//import org.scalatest.{Matchers, WordSpec}
//import org.scalatest.junit.JUnitRunner
//import org.scalatest.mockito.MockitoSugar
//import org.mockito.Mockito.when
//import org.mockito.Matchers.any
//import org.springframework.http.HttpStatus
//
//@RunWith(classOf[JUnitRunner])
//class GitHistoryServiceSpec extends WordSpec with Matchers with MockitoSugar{
//
//  trait Context {
//    val commit1 = mock[Commit]
//    when(commit1.message).thenReturn("Adding vendor information")
//    val commit2 = mock[Commit]
//    when(commit2.message).thenReturn("Minor Fixes to cover nullPointerException case, adding comments")
//    val commit3 = mock[Commit]
//    when(commit3.message).thenReturn("adding minor fixes for payment information")
//
//    val commit4 = mock[Commit]
//    when(commit4.message).thenReturn("very short message")
//
//    val payload1 = Payload(commits = List(commit1, commit2, commit3))
//    val payload2 = Payload(commits = Nil)
//    val payload3 = Payload(commits = List(commit4))
//
//    val pushEvent1 = mock[PushEvent]
//    when(pushEvent1.payload).thenReturn(payload1)
//    when(pushEvent1.created_at).thenReturn("2020-05-27T08:44:53Z")
//
//    val pushEvent2 = mock[PushEvent]
//    when(pushEvent2.payload).thenReturn(payload2)
//    when(pushEvent2.created_at).thenReturn("2020-05-27T15:44:53Z")
//
//    val pushEvent3 = mock[PushEvent]
//    when(pushEvent3.payload).thenReturn(payload3)
//    when(pushEvent3.created_at).thenReturn("2020-05-27T15:44:53Z")
//
//    val mockGitHistoryDao = mock[GitHistoryDao]
//
//    val objectUnderTest = new GitHistoryServiceImpl(mockGitHistoryDao)
//  }
//
//  "GitHistoryService " should {
//    "return noCommitFound response if no commits are found " in new Context {
//      when(mockGitHistoryDao.getUserEvents(any())).thenReturn(List(pushEvent2))
//      val response = objectUnderTest.getMostUsedKeyword("User1")
//      response.getStatusCode shouldBe HttpStatus.OK
//      response.getBody shouldBe NoCommitFoundException().errorMessage
//    }
//
//    "return unique keywords ignoring case " in new Context {
//      when(mockGitHistoryDao.getUserEvents(any())).thenReturn(List(pushEvent1))
//      val response = objectUnderTest.getMostUsedKeyword("User1")
//      response.getBody shouldBe "Your most used keywords are : adding, fixes, information, minor, case"
//    }
//
//    "return all keywords if less than 5 keywords found " in new Context {
//      when(mockGitHistoryDao.getUserEvents(any())).thenReturn(List(pushEvent3))
//      val response = objectUnderTest.getMostUsedKeyword("user1")
//      response.getBody shouldBe "Your most used keywords are : message, short, very"
//    }
//
//    "should return 12 hour clock timing instead of military timing " in new Context {
//      when(mockGitHistoryDao.getUserEvents(any())).thenReturn(List(pushEvent1, pushEvent2, pushEvent3))
//      val response = objectUnderTest.getBusiestCommitHour("User1")
//      response.getBody shouldBe "Most frequent hour of commit is 3 PM"
//    }
//  }
//}
