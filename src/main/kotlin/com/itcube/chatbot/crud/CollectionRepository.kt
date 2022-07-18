package com.itcube.chatbot.crud

import org.springframework.data.repository.CrudRepository
import com.itcube.chatbot.model.Collection

interface CollectionRepository: CrudRepository<Collection, Int> {
        override fun findAll(): List<Collection>
}