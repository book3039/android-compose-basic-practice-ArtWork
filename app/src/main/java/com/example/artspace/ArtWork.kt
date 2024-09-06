package com.example.artspace

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ArtWork(
    @StringRes val artistResource: Int,
    @StringRes val titleResource: Int,
    @DrawableRes val paintResource: Int,
    @StringRes val yearOfCreationResource: Int
)
