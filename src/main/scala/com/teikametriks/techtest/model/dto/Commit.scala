package com.teikametriks.techtest.model.dto

case class Commit(sha: String,
                  author: Author,
                  message: String,
                  distinct: Boolean,
                  url: String)


