package com.example.chatapp.dal.repository

import com.example.chatapp.dal.document.TopicDocument
import com.example.chatapp.dal.document.UserDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface TopicRepository : MongoRepository<TopicDocument, String>
