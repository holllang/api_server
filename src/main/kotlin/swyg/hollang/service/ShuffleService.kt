package swyg.hollang.service

import swyg.hollang.entity.Question

class ShuffleService {

    fun shuffleElements(questions: MutableList<Question>) {
        questions.shuffle()
    }
}