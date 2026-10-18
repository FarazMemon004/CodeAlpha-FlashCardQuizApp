
package com.example.flashlearn

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

private val Ink = Color(0xFF18233F)
private val Indigo = Color(0xFF5146E5)
private val Canvas = Color(0xFFF5F6FC)
private val Muted = Color(0xFF737B91)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val database = FlashcardDatabase.getDatabase(applicationContext)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Canvas
                ) {
                    FlashLearnApp(database = database)
                }
            }
        }
    }
}

@Composable
fun FlashLearnApp(database: FlashcardDatabase) {
    val dao = database.flashcardDao()
    val cards by dao.getAllFlashcards().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    var currentIndex by rememberSaveable { mutableStateOf(0) }
    var revealed by rememberSaveable { mutableStateOf(false) }

    var showEditor by remember { mutableStateOf(false) }
    var editingCard by remember { mutableStateOf<Flashcard?>(null) }
    var questionInput by remember { mutableStateOf("") }
    var answerInput by remember { mutableStateOf("") }

    val safeIndex = if (cards.isEmpty()) {
        0
    } else {
        currentIndex.coerceIn(0, cards.lastIndex)
    }

    val currentCard = cards.getOrNull(safeIndex)

    LaunchedEffect(cards.size) {
        if (cards.isEmpty()) {
            currentIndex = 0
        } else if (currentIndex >= cards.size) {
            currentIndex = cards.lastIndex
        }
        revealed = false
    }

    fun addCard() {
        editingCard = null
        questionInput = ""
        answerInput = ""
        showEditor = true
    }

    fun editCard(card: Flashcard) {
        editingCard = card
        questionInput = card.question
        answerInput = card.answer
        showEditor = true
    }

    Scaffold(
        containerColor = Canvas
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Indigo,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("F", color = Color.White,
                                fontSize = 27.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(Modifier.width(12.dp))

                    Column {
                        Text(
                            "FlashWise",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Ink
                        )
                        Text(
                            "Learn something every day",
                            color = Muted,
                            fontSize = 12.sp
                        )
                    }
                }

                Text(
                    "${cards.size} cards",
                    color = Indigo,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }

            // Intro
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    "YOUR STUDY SPACE",
                    color = Indigo,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                Text(
                    "Ready to learn?",
                    color = Ink,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Review your cards and strengthen your memory.",
                    color = Muted,
                    fontSize = 14.sp
                )
            }

            // Flashcard
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(285.dp),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            if (revealed) "ANSWER" else "QUESTION",
                            color = Indigo,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            letterSpacing = 1.4.sp
                        )
                        Text(
                            if (cards.isEmpty()) "0 / 0"
                            else "${safeIndex + 1} / ${cards.size}",
                            color = Muted,
                            fontSize = 12.sp
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (currentCard == null) {
                            Text("✦", color = Indigo, fontSize = 35.sp)
                            Spacer(Modifier.height(12.dp))
                            Text(
                                "Your first flashcard",
                                color = Ink,
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "Add a question and answer to begin studying.",
                                color = Muted,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        } else {
                            Text(
                                if (revealed) currentCard.answer
                                else currentCard.question,
                                color = Ink,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                            if (revealed) {
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    "Answer revealed",
                                    color = Muted,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    Button(
                        onClick = { revealed = !revealed },
                        enabled = currentCard != null,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Indigo
                        )
                    ) {
                        Text(
                            if (revealed) "Show Question" else "Reveal Answer",
                            modifier = Modifier.padding(vertical = 5.dp),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        if (cards.isNotEmpty()) {
                            currentIndex = (safeIndex - 1)
                                .coerceAtLeast(0)
                            revealed = false
                        }
                    },
                    enabled = cards.isNotEmpty() && safeIndex > 0,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(13.dp)
                ) {
                    Text("← Previous")
                }

                Button(
                    onClick = {
                        if (cards.isNotEmpty()) {
                            currentIndex = (safeIndex + 1)
                                .coerceAtMost(cards.lastIndex)
                            revealed = false
                        }
                    },
                    enabled = cards.isNotEmpty() && safeIndex < cards.lastIndex,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(13.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Indigo
                    )
                ) {
                    Text("Next →")
                }
            }

            // Card library header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Your flashcards",
                        color = Ink,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Manage your study collection",
                        color = Muted,
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = { addCard() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Indigo
                    )
                ) {
                    Text("+ Add")
                }
            }

            if (cards.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📚", fontSize = 30.sp)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "No cards yet",
                            color = Ink,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Tap + Add to create your first card.",
                            color = Muted,
                            fontSize = 13.sp
                        )
                    }
                }
            } else {
                cards.forEachIndexed { index, card ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(17.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(15.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.Top
                            ) {
                                Surface(
                                    color = Color(0xFFEFEDFF),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            "${index + 1}",
                                            color = Indigo,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Spacer(Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        card.question,
                                        color = Ink,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 15.sp
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        card.answer,
                                        color = Muted,
                                        fontSize = 13.sp
                                    )
                                }
                            }

                            HorizontalDivider(color = Color(0xFFEEF0F6))

                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextButton(
                                    onClick = { editCard(card) }
                                ) {
                                    Text("Edit", color = Indigo)
                                }
                                TextButton(
                                    onClick = {
                                        scope.launch {
                                            dao.deleteFlashcard(card)
                                        }
                                    }
                                ) {
                                    Text("Delete", color = Color(0xFFCF3D54))
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                "FlashLearn • Your cards are saved on this device",
                color = Muted,
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    // Add / Edit dialog
    if (showEditor) {
        AlertDialog(
            onDismissRequest = { showEditor = false },
            title = {
                Text(
                    if (editingCard == null) "Create flashcard"
                    else "Edit flashcard",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = questionInput,
                        onValueChange = { questionInput = it },
                        label = { Text("Question") },
                        placeholder = { Text("Enter your question") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )

                    OutlinedTextField(
                        value = answerInput,
                        onValueChange = { answerInput = it },
                        label = { Text("Answer") },
                        placeholder = { Text("Enter the answer") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val question = questionInput.trim()
                        val answer = answerInput.trim()

                        if (question.isNotEmpty() && answer.isNotEmpty()) {
                            scope.launch {
                                val oldCard = editingCard
                                if (oldCard == null) {
                                    dao.insertFlashcard(
                                        Flashcard(
                                            question = question,
                                            answer = answer
                                        )
                                    )
                                } else {
                                    dao.updateFlashcard(
                                        oldCard.copy(
                                            question = question,
                                            answer = answer
                                        )
                                    )
                                }
                            }

                            showEditor = false
                            revealed = false
                        }
                    },
                    enabled = questionInput.isNotBlank() &&
                            answerInput.isNotBlank()
                ) {
                    Text("Save", color = Indigo)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showEditor = false }
                ) {
                    Text("Cancel")
                }
            },
            shape = RoundedCornerShape(22.dp)
        )
    }
}
