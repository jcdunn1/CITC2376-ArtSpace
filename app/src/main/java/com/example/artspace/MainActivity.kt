package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    val artPieces = listOf(
        ArtPiece(
            imageResource = R.drawable.art_piece_1,
            title = "Sagas",
            artist = "Equilibrium"
        ),
        ArtPiece(
            imageResource = R.drawable.art_piece_2,
            title = "Twilight of the Thunder God",
            artist = "Amon Amarth"
        ),
        ArtPiece(
            imageResource = R.drawable.art_piece_3,
            title = "Voimasta ja Kunniasta",
            artist = "Moonsorrow"
        )
    )
    var currentIndex by remember { mutableIntStateOf(0) }
    val currentArt = artPieces[currentIndex]

    // Main layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top: The image area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            ArtImageDisplay(
                modifier = Modifier.align(Alignment.Center),
                artPiece = currentArt
            )
        }

        // Bottom: Text surface + navigation row
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ArtInfoSurface(artPiece = currentArt)

            Spacer(modifier = Modifier.height(16.dp))

            ArtNavigationRow(
                onPrevious = { if (currentIndex > 0) currentIndex-- },
                onNext = { if (currentIndex < artPieces.size - 1) currentIndex++ },
                isPreviousEnabled = currentIndex > 0,
                isNextEnabled = currentIndex < artPieces.size - 1
            )
        }
    }
}

// --------------------------------------------------------
// Sub-Composables
// --------------------------------------------------------

/**
 * Shows the image in a raised surface.
 */
@Composable
fun ArtImageDisplay(
    modifier: Modifier = Modifier,
    artPiece: ArtPiece
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 8.dp
    ) {
        Image(
            painter = painterResource(id = artPiece.imageResource),
            contentDescription = artPiece.title,
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * A surface containing the title and artist in centered text.
 */
@Composable
fun ArtInfoSurface(
    modifier: Modifier = Modifier,
    artPiece: ArtPiece
) {
    Surface(
        modifier = modifier.padding(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = artPiece.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = artPiece.artist,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * A row of Previous/Next buttons with given onClick actions.
 */
@Composable
fun ArtNavigationRow(
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    isPreviousEnabled: Boolean,
    isNextEnabled: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onPrevious,
            enabled = isPreviousEnabled
        ) {
            Text("Previous")
        }
        Button(
            onClick = onNext,
            enabled = isNextEnabled
        ) {
            Text("Next")
        }
    }
}

// --------------------------------------------------------
// Previews
// --------------------------------------------------------

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ArtSpaceApp()
        }
    }
}
