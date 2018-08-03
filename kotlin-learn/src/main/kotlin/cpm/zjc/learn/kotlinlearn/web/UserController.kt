package cpm.zjc.learn.kotlinlearn.web

import cpm.zjc.learn.kotlinlearn.bean.User
import cpm.zjc.learn.kotlinlearn.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.PostConstruct

@RestController
@RequestMapping("user")
class UserController constructor(val userService: UserService) {

    @PostConstruct
    fun initDate() {
        val tomi = User(id = null, name = "tomi", loginName = "test", password = "123", age = 18)
        val mocel = User(id = null, name = "mocel", loginName = "root", password = "root", age = null)
        try {
            userService.saveUsers(listOf(tomi, mocel))
        }catch (e:Exception){
            println(e.message)
        }
    }


    @GetMapping("login")
    fun checkLogin(userName: String,
                   password: String)
            : String {
        val checkLogin = userService.checkLogin(userName, password)
        println("welcome login, $userName")
        return if (checkLogin) {
            "welcome login, $userName!!!"
        } else {
            "please check username and password again!"
        }
    }


}