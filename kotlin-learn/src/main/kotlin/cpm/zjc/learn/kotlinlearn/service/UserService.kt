package cpm.zjc.learn.kotlinlearn.service

import cpm.zjc.learn.kotlinlearn.bean.User
import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
@Transactional
@Slf4j
class UserServiceImp(val userRepository: UserRepository):UserService {
    override fun checkLogin(userName: String, password: String): Boolean {
        val all = userRepository.findAll()


        return userRepository.exists(Example.of(User(id = null,
                name = "", loginName = userName, password = password, age = null)))
    }

//    @Transactional(Transactional.TxType.SUPPORTS)
    private fun makeException(s: String) {
        throw RuntimeException(s)
    }

    override fun saveUsers(users: List<User>) {
        userRepository.saveAll(users)
        val all = userRepository.findAll()
        println("----------first search $all")
        this.makeException("start exception--------")
        println("after exception-------------")
    }

    override fun findAll(): List<User> {
        return userRepository.findAll()
    }
}

interface UserService{

    fun checkLogin(userName: String, password: String) :Boolean

    fun findAll() :List<User>

    fun saveUsers(users: List<User>)
}