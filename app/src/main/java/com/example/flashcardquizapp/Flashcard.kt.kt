
package com.example.flashlearn

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcards")
data class `Flashcard`(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val question: String,
    val answer: String
)
