package com.example.chatapp.dal.repository

import com.example.chatapp.dal.document.UserDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<UserDocument, String>