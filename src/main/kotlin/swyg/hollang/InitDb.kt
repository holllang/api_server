package swyg.hollang

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
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
    @Profile(value = ["local", "dev"])
    class InitService(
        @Value("\${spring.config.activate.on-profile}") private val activeProfile: String,
        @Autowired private val em: EntityManager) {

        @Value("\${aws.s3.bucket}")
        private lateinit var bucketName: String

        @Value("\${aws.s3.init-data-key}")
        private lateinit var initDataKey: String

        @Value("\${aws.cloudfront.host}")
        private lateinit var cloudfrontHost: String

        fun initFile(): Workbook {
            if(activeProfile == "dev") {
                val s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(DefaultAWSCredentialsProviderChain())
                    .withRegion("ap-northeast-2")
                    .build()

                val s3Object = s3Client.getObject(bucketName, initDataKey)
                val excelInputStream = s3Object.objectContent

                return WorkbookFactory.create(excelInputStream)
            } else {
                val file = File(initDataKey)
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

                val number = rowIndex.toLong()
                val content = row.getCell(0).stringCellValue
                val imageUrl =
                    "${cloudfrontHost}/images/question/question${rowIndex}.png"
                val question = Question(number, test, content, imageUrl)
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

                val categories = mutableListOf<Category>()
                val name = row.getCell(0).stringCellValue
                val description = row.getCell(1).stringCellValue
                val imageName = row.getCell(2).stringCellValue
                val imageUrl =
                    "${cloudfrontHost}/images/hobby/$imageName.png"
                val hobby = Hobby(categories, name, description, imageUrl)
                em.persist(hobby)
            }
        }

        fun initHobbyTypeData() {
            val workbook = initFile()

            val sheet = workbook.getSheet("hobby_type")
            for (rowIndex in 1..sheet.lastRowNum) {
                val row = sheet.getRow(rowIndex)

                val name = row.getCell(0).stringCellValue
                val description = row.getCell(1).stringCellValue
                val mbtiType = row.getCell(2).stringCellValue
                val threeDimensionImageUrl =
                    "${cloudfrontHost}/images/hobby_type/${mbtiType}.gif"
                val imageUrl = "${cloudfrontHost}/images/hobby_type/${mbtiType}.png"
                val fitHobbyTypes = mutableListOf(
                    row.getCell(3).stringCellValue,
                    row.getCell(4).stringCellValue,
                    row.getCell(5).stringCellValue
                )
                val hobbyType = HobbyType(
                    name,
                    description,
                    mbtiType,
                    threeDimensionImageUrl,
                    imageUrl,
                    fitHobbyTypes
                )
                em.persist(hobbyType)
            }
        }

    }
}