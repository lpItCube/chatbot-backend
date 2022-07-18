package com.itcube.chatbot.service

import com.itcube.chatbot.crud.CollectionRepository
import com.itcube.chatbot.crud.NodeRepository
import com.itcube.chatbot.crud.OptionRepository
import com.itcube.chatbot.model.Collection
import com.itcube.chatbot.model.Node
import com.itcube.chatbot.model.Option
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DbServiceImpl @Autowired constructor(
    private val nodeRepo: NodeRepository,
    private val optRepo: OptionRepository,
    private val collRepo: CollectionRepository
): DbService {

    override fun createCollection(name: String, description: String): Collection {
        val col = Collection(name, description, false)
        return collRepo.save(col)
    }

    override fun getCollections(): List<Collection> {
        return collRepo.findAll()
    }

    override fun getCollection(collectionId: Int): Collection {
        val coll = collRepo.findById(collectionId)
        return coll.get()
    }

    override fun createNode(
        collectionId: Int,
        title: String,
        description: String,
        firstNode: Boolean,
        lastNode: Boolean,
        type: String,
        data: String?,
        options: List<Option>?,
        refOptIds: List<Int>?
    ): Node {
        val node = if(type == "input") {
            val defaultOpt = listOf(Option("submit", null))
            Node(title, description, firstNode, lastNode, type, data, defaultOpt)
        }
        else Node(title, description, firstNode, lastNode, type, data, options)

        val coll = getCollection(collectionId)
        coll.addNode(node)
        return nodeRepo.save(node)
    }

    override fun getNode(id: Int): Node {
        return nodeRepo.findById(id).get()
    }

    override fun getNodes(collectionId: Int): List<Node> {
        return nodeRepo.findAllByCollectionId(collectionId)
    }

    override fun removeNode(nodeId: Int) {
        val node = nodeRepo.findById(nodeId).get()
        val refOptions = node.refOpt
        refOptions.forEach {
            it.nextNode = null
            optRepo.save(it)
        }
        nodeRepo.save(node)
        return nodeRepo.deleteById(nodeId)
    }

    override fun createOptionLink(optionId: Int, nodeId: Int) {
        val node = nodeRepo.findById(nodeId).get()
        val option = optRepo.findById(optionId).get()
        node.addRefOpt(option)
        nodeRepo.save(node)
    }

    override fun addOptionToNode(option: Option, nodeId: Int): Option {
        val node = nodeRepo.findById(nodeId).get()
        option.addNode(node)
        return optRepo.save(option)
    }

    override fun removeOptionToNode(optionId: Int) {
        val option = optRepo.findById(optionId).get()
        optRepo.delete(option)
    }

    override fun getNextNode(optionId: Int): Node? {
        val option = optRepo.findById(optionId)
        if (option.isEmpty)
            return null
        return option.get().nextNode
    }

    override fun getFirstNode(collectionId: Int): Node {
        return nodeRepo.findByCollectionIdAndFirstNode(collectionId, true)
    }

    override fun removeOptionLink(optionId: Int, nodeId: Int) {
        val node = nodeRepo.findById(nodeId).get()
        val option = optRepo.findById(optionId).get()
        node.removeRefOpt(option)
        nodeRepo.save(node)
    }

}