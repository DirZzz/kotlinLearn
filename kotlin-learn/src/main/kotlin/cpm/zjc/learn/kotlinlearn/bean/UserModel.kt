package cpm.zjc.learn.kotlinlearn.bean

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id


@Entity
data class User(@Id @GeneratedValue val id :Int?,
                val name: String,
                val loginName: String,
                val password: String,
                val age: Int?)