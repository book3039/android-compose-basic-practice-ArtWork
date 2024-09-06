package com.example.artspace

import android.R.id
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme
import com.example.tiptime.ui.theme.md_theme_light_inverseSurface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    var artWorkSequence by remember { mutableStateOf(ArtWorkSequence.FIRST) }

    val artWork = when (artWorkSequence) {
        ArtWorkSequence.FIRST -> ArtWork(
                artistResource = R.string.vincent_van_gogh,
                titleResource = R.string.starry_night,
                paintResource = R.drawable.starry_night,
                yearOfCreationResource = R.string.starry_night_year_of_creation
            )

        ArtWorkSequence.SECOND -> ArtWork(
            artistResource = R.string.claude_monet,
            titleResource = R.string.woman_with_a_parasol,
            paintResource = R.drawable.woman_with_a_parasol,
            yearOfCreationResource = R.string.woman_with_a_parasol_year_of_creation
        )

        ArtWorkSequence.THIRD -> ArtWork(
            artistResource = R.string.lee_jungseop,
            titleResource = R.string.white_ox,
            paintResource = R.drawable.white_ox,
            yearOfCreationResource = R.string.white_ox_year_of_creation
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .systemBarsPadding(),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .wrapContentSize()
        ) {
            ArtWorkWall(
                imageResource = artWork.paintResource,
            )
        }

        ArtWorkTitle(
            titleResource = artWork.titleResource,
            artistResource = artWork.artistResource,
            yearOfCreationResource = artWork.yearOfCreationResource,
        )

        Spacer(Modifier.height(24.dp))

        DisplayController(
            onPreviousClick = {
                when (artWorkSequence) {
                    ArtWorkSequence.FIRST -> {}
                    ArtWorkSequence.SECOND -> artWorkSequence = ArtWorkSequence.FIRST
                    ArtWorkSequence.THIRD -> artWorkSequence = ArtWorkSequence.SECOND
                }
            },
            onNextClick = {
                when (artWorkSequence) {
                    ArtWorkSequence.FIRST -> artWorkSequence = ArtWorkSequence.SECOND
                    ArtWorkSequence.SECOND -> artWorkSequence = ArtWorkSequence.THIRD
                    ArtWorkSequence.THIRD -> {}
                }
            }
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun ArtWorkWall(
    @DrawableRes imageResource: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shadowElevation = 10.dp,
        modifier = modifier.padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = null,
            modifier = modifier.padding(24.dp)
        )
    }
}

@Composable
fun ArtWorkTitle(
    @StringRes titleResource: Int,
    @StringRes artistResource: Int,
    @StringRes yearOfCreationResource: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.LightGray)
    ) {
        Text(
            text = stringResource(titleResource),
            lineHeight = 40.sp,
            fontSize = 36.sp,
            fontWeight = FontWeight.Thin,
            modifier = modifier.padding(start = 8.dp, top = 8.dp)
        )
        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(fontWeight = FontWeight.SemiBold)
                ) {
                    append(stringResource(id = artistResource))
                }
                append(" ")
                append(stringResource(id = yearOfCreationResource))
            },
            fontSize = 18.sp,
            fontWeight = FontWeight.Thin,
            modifier = modifier.padding(start = 10.dp, bottom = 8.dp)
        )
    }
}

@Composable
fun DisplayController(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        Button(
            onClick = onPreviousClick,
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier.width(dimensionResource(id = R.dimen.button_width))
        ) {
            Text(
                text = stringResource(R.string.previous),
            )
        }

        Button(
            onClick = onNextClick,
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier.width(dimensionResource(id = R.dimen.button_width))
        ) {
            Text(text = stringResource(R.string.next))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}