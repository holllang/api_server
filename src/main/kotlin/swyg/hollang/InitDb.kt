package swyg.hollang

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.*
import java.io.File
import java.io.InputStream
import java.net.URI

@Component
@Profile(value = ["local", "dev"])
class InitDb(private val initService: InitService) {

    @PostConstruct
    fun init() {
        initService.initTestData(1)
        initService.initHobbyTypeData()
        initService.initHobbyData()
    }

    @Component
    @Transactional
    class InitService(
        @Value("\${spring.config.activate.on-profile}") private val activeProfile: String,
        @Autowired private val em: EntityManager) {

        private val s3BucketName: String = System.getenv("S3_BUCKET_NAME")

        fun initFile(): Workbook {
            if(activeProfile == "dev") {
                val s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(DefaultAWSCredentialsProviderChain())
                    .withRegion("ap-northeast-2")
                    .build()

                val s3Object = s3Client.getObject(s3BucketName, System.getenv("S3_INIT_DATA_KEY"))
                val excelInputStream = s3Object.objectContent

                return WorkbookFactory.create(excelInputStream)
            } else {
                val file = File(System.getenv("INIT_DATA_PATH"))
                val inputStream: InputStream = file.inputStream()

                return WorkbookFactory.create(inputStream)
            }
        }

        fun initTestData(testVersion: Long) {
            val workbook = initFile()

            val sheet = workbook.getSheet("test")
            val test = Test(testVersion)
            for (rowIndex in 1..sheet.lastRowNum) {
                val row = sheet.getRow(rowIndex)
                val question = Question(rowIndex.toLong(), test, row.getCell(0).stringCellValue,
                    "https://$s3BucketName.s3.ap-northeast-2.amazonaws.com/images/question/question${rowIndex}.png")
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

        fun initHobbyData() {
            val workbook = initFile()

            val sheet = workbook.getSheet("hobby")
            for (rowIndex in 1..sheet.lastRowNum) {
                val row = sheet.getRow(rowIndex)
                val hobby = Hobby(
                    mutableListOf(),
                    row.getCell(0).stringCellValue,
                    row.getCell(1).stringCellValue,
                    "https://$s3BucketName.s3.ap-northeast-2.amazonaws.com/images/hobby/${row.getCell(2).stringCellValue}.png"
                )
                em.persist(hobby)
            }
        }

        fun initHobbyTypeData() {
            val workbook = initFile()

            val sheet = workbook.getSheet("hobby_type")
            for (rowIndex in 1..sheet.lastRowNum) {
                val row = sheet.getRow(rowIndex)
                val hobbyType = HobbyType(
                    row.getCell(0).stringCellValue,
                    row.getCell(1).stringCellValue,
                    "https://$s3BucketName.s3.ap-northeast-2.amazonaws.com/images/hobby_type/${row.getCell(2).stringCellValue}.fbx",
                    "https://$s3BucketName.s3.ap-northeast-2.amazonaws.com/images/hobby_type/${row.getCell(3).stringCellValue}.png"
                )
                em.persist(hobbyType)
            }
        }

    }
}