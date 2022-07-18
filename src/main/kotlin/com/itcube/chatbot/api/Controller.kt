package com.itcube.chatbot.api

import com.itcube.chatbot.model.Collection
import com.itcube.chatbot.model.Node
import com.itcube.chatbot.model.Option
import com.itcube.chatbot.service.DbService
import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import org.apache.tomcat.util.http.fileupload.FileUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


@RestController
@RequestMapping("api")
@CrossOrigin("*")
class Controller @Autowired constructor(val dbService: DbService) {

    @GetMapping("/ping")
    fun ping(): ResponseEntity<String> {
        return ResponseEntity.ok("ok")
    }

    @PostMapping("/createCollection")
    fun createCollection(@RequestBody coll: Collection): ResponseEntity<Collection> {
        return try {
            val col = dbService.createCollection(coll.name, coll.description)
            ResponseEntity.ok(col)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @GetMapping("/getCollections")
    fun getCollections(): ResponseEntity<List<Collection>> {
        val listOfColl = dbService.getCollections()

        return ResponseEntity.ok(listOfColl)
    }

    @PostMapping("/createNode")
    fun createNode(@RequestBody node: NodeDTO): ResponseEntity<Node> {
        return try {
            val nd = dbService.createNode(
                node.collectionId,
                node.title,
                node.description,
                node.firstNode,
                node.lastNode,
                node.type,
                node.data,
                node.options,
                node.refOptIds
            )
            ResponseEntity.ok(nd)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @GetMapping("/getNodes")
    fun getNodes(@RequestParam collectionId: Int): ResponseEntity<List<Node>> {
        return try {
            val nodeList = dbService.getNodes(collectionId )
            ResponseEntity.ok(nodeList)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @GetMapping("/getNode")
    fun getNode(@RequestParam nodeId: Int): ResponseEntity<Node> {
        return try {
            val nodeList = dbService.getNode(nodeId)
            ResponseEntity.ok(nodeList)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @PostMapping("/createOptionLink")
    fun createOptionLink(@RequestParam optionId: Int, @RequestParam nodeId: Int): ResponseEntity<String> {
        return try {
            dbService.createOptionLink(optionId, nodeId)
            ResponseEntity.ok("ok")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @DeleteMapping("/removeOptionLink")
    fun removeOptionLink(@RequestParam optionId: Int, @RequestParam nodeId: Int): ResponseEntity<String> {
        return try {
            dbService.removeOptionLink(optionId, nodeId)
            ResponseEntity.ok("ok")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @PostMapping("/addOption")
    fun addOption(@RequestBody addOpt: AddOptionDTO): ResponseEntity<Option> {
        return try {
            val option = dbService.addOptionToNode(addOpt.toOption(), addOpt.nodeId)
            ResponseEntity.ok(option)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @DeleteMapping("/removeOption")
    fun deleteOption(@RequestParam optionId: Int): ResponseEntity<String> {
        return try {
            dbService.removeOptionToNode(optionId)
            ResponseEntity.ok("ok")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @GetMapping("/getNextNode")
    fun getNextNode(@RequestParam optionId: Int): ResponseEntity<Node> {
        return try {
            val node = dbService.getNextNode(optionId)
            ResponseEntity.ok(node)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @GetMapping("/getFirstNode")
    fun getFirstNode(@RequestParam collectionId: Int): ResponseEntity<Node> {
        return try {
            val node = dbService.getFirstNode(collectionId)
            return ResponseEntity.ok(node)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @DeleteMapping("/removeNode")
    fun removeNode(@RequestParam nodeId: Int): ResponseEntity<String> {
        return try {
            dbService.removeNode(nodeId)
            ResponseEntity.ok("ok")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @PostMapping("/createPdfForm")
    fun createPdfForm(@RequestBody pdfForms: PdfFormDTO): ResponseEntity<ByteArray> {
        return try {
            val doc = Document()
            val writer = PdfWriter.getInstance(doc, FileOutputStream("TestPdf.pdf"))
            doc.open()
            pdfForms.formsList.forEach {
                doc.add(Paragraph(it))
            }
            doc.close()
            writer.close()

            val inFileBytes: ByteArray = Files.readAllBytes(Paths.get("TestPdf.pdf"))
            val encoded = Base64.getEncoder().encode(inFileBytes)

            ResponseEntity.ok().body(encoded)
        }    catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }


    class AddOptionDTO(
        val name: String,
        val description: String,
        val nodeId: Int
    ) {
        fun toOption(): Option {
            return Option(name, description)
        }
    }

    class PdfFormDTO(val formsList: List<String>)

    class NodeDTO(
        val collectionId: Int,
        val title: String,
        val description: String,
        val firstNode: Boolean,
        val lastNode: Boolean,
        var refOptIds: List<Int>?,
        var options: List<Option>,
        var type: String,
        var data: String?
    )

}