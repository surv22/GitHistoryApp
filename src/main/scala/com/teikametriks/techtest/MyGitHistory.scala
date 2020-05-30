package com.teikametriks.techtest

import java.security.Principal

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@EnableOAuth2Sso
class MyGitHistory

object MyGitHistory extends App {
	SpringApplication.run(classOf[MyGitHistory])
}


