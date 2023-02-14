package swyg.hollang

import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Answer
import swyg.hollang.entity.Question
import swyg.hollang.entity.Test

@Component
@Profile(value = ["local", "dev"])
class InitDb(private val initService: InitService) {

    @PostConstruct
    fun init() {
        initService.testInit()
    }

    @Component
    @Transactional
    class InitService(@Autowired private val em: EntityManager) {

        fun testInit(){
            val test = Test(1)
            for(i in 1..12){
                val question = Question(i.toLong(), test, "질문 $i", "https://hollang-static.s3.ap-northeast-2.amazonaws.com/test.png")
                for(j in 1..2){
                    val answer = Answer(question, j.toLong(), "질문 $i 답변 $j")
                    question.answers.add(answer)
                }
                test.questions.add(question)
            }
            //cascade type을 all로 해놨으니 영속성이 전이돼서 부모 엔티티를 영속화시키면 자식 엔티티도 영속화된다.
            em.persist(test)
        }
    }
}