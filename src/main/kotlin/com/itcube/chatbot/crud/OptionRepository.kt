package com.itcube.chatbot.crud

import com.itcube.chatbot.model.Option
import org.springframework.data.repository.CrudRepository

interface OptionRepository: CrudRepository<Option, Int> {
}