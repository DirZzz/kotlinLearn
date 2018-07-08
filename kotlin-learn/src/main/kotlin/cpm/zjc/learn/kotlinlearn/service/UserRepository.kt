package cpm.zjc.learn.kotlinlearn.service

import cpm.zjc.learn.kotlinlearn.bean.User
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository: JpaRepository<User, Int>{
}
