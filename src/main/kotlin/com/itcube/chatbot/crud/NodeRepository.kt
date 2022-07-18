package com.itcube.chatbot.crud

import com.itcube.chatbot.model.Node
import org.springframework.data.repository.CrudRepository

interface NodeRepository: CrudRepository<Node, Int> {
    fun findAllByCollectionId(collectionId: Int): List<Node>
    fun findByCollectionIdAndFirstNode(collectionId: Int, firstNode: Boolean): Node
}