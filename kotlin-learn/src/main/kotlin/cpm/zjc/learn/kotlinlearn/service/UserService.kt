package cpm.zjc.learn.kotlinlearn.service

import cpm.zjc.learn.kotlinlearn.bean.User
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service


@Service
class UserServiceImp(val userRepository: UserRepository) {
    fun checkLogin(userName: String, password: String): Boolean {
        val all = userRepository.findAll()


        return userRepository.exists(Example.of(User(id = null,
                name = "", loginName = userName, password = password, age = null)))
    }

    fun saveUsers(users: List<User>) {
        userRepository.saveAll(users)
    }
}