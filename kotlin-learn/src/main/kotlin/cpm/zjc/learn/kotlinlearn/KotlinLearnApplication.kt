package cpm.zjc.learn.kotlinlearn

import cpm.zjc.learn.kotlinlearn.bean.User
import cpm.zjc.learn.kotlinlearn.service.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@SpringBootApplication
class KotlinLearnApplication

fun main(args: Array<String>) {


    runApplication<KotlinLearnApplication>(*args)
}
