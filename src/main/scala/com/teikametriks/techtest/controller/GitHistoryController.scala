package com.teikametriks.techtest.controller

import java.security.Principal

import com.teikametriks.techtest.service.GitHistoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpHeaders, HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{GetMapping, RestController}

@RestController
class GitHistoryController(@Autowired gitHistoryService: GitHistoryService) {

  @GetMapping(path = Array("/"))
  def getWelcomeResponse(principal: Principal): String = {
    return "Hi " + principal.getName + " welcome to MyGitHistory"
  }

  @GetMapping(path = Array("/keywords"))
  def getMostUsedKeyword(principal: Principal): ResponseEntity[String] = {
    gitHistoryService.getMostUsedKeyword(principal.getName)
  }

  @GetMapping(path = Array("/BusiestCommitHour"))
  def getBusiestCommitHour(principal: Principal): ResponseEntity[String] = {
    gitHistoryService.getBusiestCommitHour(principal.getName)
  }

}
