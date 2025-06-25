package com.example.anonymousreview.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.anonymousreview.model.Review
import com.example.anonymousreview.model.dummyReviews

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        SearchBar()
        HeaderSection()
        InputPrompt()
        PromoBanner()
        ReviewList(reviews = dummyReviews)
    }
}

@Composable
fun SearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD6C3F2))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("bạn muốn tìm review về cái gì?") },
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(Icons.Default.FilterList, contentDescription = "Filter")
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("🔥 các tôi đang revi", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {}, colors = ButtonDefaults.outlinedButtonColors()) {
            Text("🔽 nổi bật")
        }
    }
}

@Composable
fun InputPrompt() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Tôi ơi review gì i nè...") },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    )
}

@Composable
fun PromoBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2A50A0))
            .padding(16.dp)
    ) {
        Text(
            "✨ bạn đã thử các gói nâng cao của tôi chưa...",
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ReviewList(reviews: List<Review>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(reviews) { review ->
            ReviewCard(review)
        }
    }
}

@Composable
fun ReviewCard(review: Review) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                review.tag,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.background(Color.Yellow).padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(review.title, fontWeight = FontWeight.Bold)
            Text(review.content, color = Color.Gray, maxLines = 3, overflow = TextOverflow.Ellipsis)
            if (review.images.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    review.images.forEach {
                        Image(painter = painterResource(id = it), contentDescription = null, modifier = Modifier.size(60.dp))
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                IconText("👍", review.likes.toString())
                IconText("💬", review.comments.toString())
                IconText("👁", review.views.toString())
            }
        }
    }
}

@Composable
fun IconText(icon: String, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(icon)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text)
    }
}