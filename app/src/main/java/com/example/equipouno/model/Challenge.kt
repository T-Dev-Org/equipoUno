package com.example.equipouno.model

import com.example.equipouno.utils.Constants.challengeTable.COLUMN_CREATION_DATE
import com.example.equipouno.utils.Constants.challengeTable.COLUMN_DESCRIPTION
import com.example.equipouno.utils.Constants.challengeTable.COLUMN_MODIFICATION_DATE
import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class Challenge(
    val id: String = "",

    @get:PropertyName(COLUMN_DESCRIPTION)
    val description: String = "",

    @get:PropertyName(COLUMN_CREATION_DATE)
    val creationDate: Timestamp = Timestamp.now(),

    @get:PropertyName(COLUMN_MODIFICATION_DATE)
    val modificationDate: Timestamp = Timestamp.now()
)