package swyg.hollang

import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.*
import java.io.File
import java.io.InputStream

@Component
@Profile(value = ["local", "dev"])
class InitDb(private val initService: InitService) {

    private val filePath: String = System.getenv("INIT_DATA_FILE_PATH")

    @PostConstruct
    fun init() {
        initService.initTestData(filePath, 1)
        initService.initHobbyTypeData(filePath)
        initService.initHobbyData(filePath)
    }

    @Component
    @Transactional
    class InitService(@Autowired private val em: EntityManager) {

        private val s3BucketUrl: String = System.getenv("S3_BUCKET_URL")

        fun initTestData(filePath: String, testVersion: Long) {
            val file = File(filePath)
            val inputStream: InputStream = file.inputStream()
            val workbook = XSSFWorkbook(inputStream)

            val sheet = workbook.getSheet("test")
            val test = Test(testVersion)
            for (rowIndex in 1..sheet.lastRowNum) {
                val row = sheet.getRow(rowIndex)
                val question = Question(rowIndex.toLong(), test, row.getCell(0).stringCellValue,
                    "$s3BucketUrl/question${rowIndex}.png")
                for (cellIndex in row.firstCellNum + 1..row.firstCellNum + 2) {
                    val cell = row.getCell(cellIndex)
                    val cellValue = cell?.stringCellValue
                    val answer = Answer(question, cellIndex.toLong(), cellValue!!)
                    question.answers.add(answer)
                }
                test.questions.add(question)
            }
            //cascade type을 all로 해놨으니 영속성이 전이돼서 부모 엔티티를 영속화시키면 자식 엔티티도 영속화된다.
            em.persist(test)
        }

        fun initHobbyData(filePath: String) {
            val file = File(filePath)
            val inputStream: InputStream = file.inputStream()
            val workbook = XSSFWorkbook(inputStream)

            val sheet = workbook.getSheet("hobby")
            for (rowIndex in 1..sheet.lastRowNum) {
                val row = sheet.getRow(rowIndex)
                val hobby = Hobby(
                    mutableListOf(),
                    row.getCell(0).stringCellValue,
                    row.getCell(1).stringCellValue,
                    "$s3BucketUrl/${row.getCell(2).stringCellValue}.png"
                )
                em.persist(hobby)
            }
        }

        fun initHobbyTypeData(filePath: String) {
            val file = File(filePath)
            val inputStream: InputStream = file.inputStream()
            val workbook = XSSFWorkbook(inputStream)

            val sheet = workbook.getSheet("hobby_type")
            for (rowIndex in 1..sheet.lastRowNum) {
                val row = sheet.getRow(rowIndex)
                val hobbyType = HobbyType(
                    row.getCell(0).stringCellValue,
                    row.getCell(1).stringCellValue,
                    "$s3BucketUrl/${row.getCell(2).stringCellValue}.fbx",
                    "$s3BucketUrl/${row.getCell(3).stringCellValue}.png"
                )
                em.persist(hobbyType)
            }
        }

    }
}