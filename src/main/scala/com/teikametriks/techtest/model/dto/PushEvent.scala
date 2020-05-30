package com.teikametriks.techtest.model.dto

import java.util.Date

case class PushEvent(id: String,
                     actor: Actor,
                     repo: Repo,
                     payload: Payload,
                     public: Boolean,
                     created_at: String
                    )
