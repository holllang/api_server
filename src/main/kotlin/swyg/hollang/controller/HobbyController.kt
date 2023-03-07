package swyg.hollang.controller

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import swyg.hollang.dto.GetHobbiesRankResponse
import swyg.hollang.dto.common.SuccessResponse
import swyg.hollang.service.HobbyService
import swyg.hollang.utils.WebProperties

@RestController
@RequestMapping("/hobbies")
@CrossOrigin(origins = ["\${client.server.host}"])
class HobbyController(
    private val hobbyService: HobbyService
) {

    @GetMapping
    fun getHobbiesRank(@RequestParam(name = "page", defaultValue = "0") page: Int,
                       @RequestParam(name = "size", defaultValue = "20") size: Int,
                       @RequestParam(name = "sort", defaultValue = "recommendCount") sort: String)
        : ResponseEntity<SuccessResponse<GetHobbiesRankResponse>>{
        val pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort))
        val hobbies = hobbyService.getHobbies(pageRequest)

        return ResponseEntity.ok()
            .body(
                SuccessResponse(
                    HttpStatus.OK.name,
                    WebProperties.SUCCESS_RESPONSE_MESSAGE,
                    GetHobbiesRankResponse(hobbies.content)
                )
            )
    }
}