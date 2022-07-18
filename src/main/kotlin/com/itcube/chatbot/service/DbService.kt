package com.itcube.chatbot.service

import com.itcube.chatbot.model.Collection
import com.itcube.chatbot.model.Node
import com.itcube.chatbot.model.Option

interface DbService {
    fun createCollection(name: String, description: String): Collection
    fun getCollections(): List<Collection>
    fun getCollection(collectionId: Int): Collection
    fun createNode(
        collectionId: Int,
        title: String,
        description: String,
        firstNode: Boolean,
        lastNode: Boolean,
        type: String,
        data: String?,
        options: List<Option>?,
        refOptIds: List<Int>?
    ): Node
    fun getNode(id: Int): Node
    fun getNodes(collectionId: Int): List<Node>
    fun removeNode(nodeId: Int)
    fun createOptionLink(optionId: Int, nodeId: Int)
    fun removeOptionLink(optionId: Int, nodeId: Int)
    fun addOptionToNode(option: Option, nodeId: Int): Option
    fun removeOptionToNode(optionId: Int)
    fun getNextNode(optionId: Int): Node?
    fun getFirstNode(collectionId: Int): Node
}