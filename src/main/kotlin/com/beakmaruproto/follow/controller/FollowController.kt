package com.beakmaruproto.follow.controller

import com.beakmaruproto.follow.service.FollowService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/follow")
@Controller
class FollowController @Autowired constructor(
    private val followService: FollowService,
) {
}