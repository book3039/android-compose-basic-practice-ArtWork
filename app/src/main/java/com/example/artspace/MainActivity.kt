package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
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
    var artWorkSequence by rememberSaveable { mutableStateOf(ArtWorkSequence.FIRST) }

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

    val isTablet = LocalConfiguration.current.screenWidthDp > 600
    var tabletWidthFraction = LocalConfiguration.current.screenWidthDp * 0.5

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .systemBarsPadding()
    ) {
        if (isTablet) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())

            ) {
                ArtWorkWall(
                    imageResource = artWork.paintResource,
                    modifier = Modifier.width(tabletWidthFraction.dp).padding(bottom = 48.dp)
                )

                ArtWorkTitle(
                    titleResource = artWork.titleResource,
                    artistResource = artWork.artistResource,
                    yearOfCreationResource = artWork.yearOfCreationResource,
                    modifier = Modifier.width(tabletWidthFraction.dp)
                )

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
            }
        } else {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize()
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                ArtWorkWall(
                    imageResource = artWork.paintResource,
                )
            }
            ArtWorkTitle(
                titleResource = artWork.titleResource,
                artistResource = artWork.artistResource,
                yearOfCreationResource = artWork.yearOfCreationResource,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
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
}

@Composable
fun ArtWorkWall(
    @DrawableRes imageResource: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shadowElevation = 10.dp,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = null,
            modifier = Modifier.padding(24.dp)
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
    val isTablet = LocalConfiguration.current.screenWidthDp > 600

    Column(
        modifier = modifier
            .background(Color.LightGray)
    ) {
        Text(
            text = stringResource(titleResource),
            lineHeight = if (isTablet) 50.sp else 40.sp,
            fontSize = if (isTablet) 48.sp else 36.sp,
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
            fontSize = if (isTablet) 24.sp else 18.sp,
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
                color = Color.White
            )
        }

        Button(
            onClick = onNextClick,
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier.width(dimensionResource(id = R.dimen.button_width))
        ) {
            Text(
                text = stringResource(R.string.next),
                color = Color.White
            )
        }
    }
}

@Preview(heightDp = 460, widthDp = 800)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}
