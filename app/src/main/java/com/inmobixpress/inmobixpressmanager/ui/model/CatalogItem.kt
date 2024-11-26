package com.inmobixpress.inmobixpressmanager.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CoPresent
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.ui.graphics.vector.ImageVector

data class CatalogItem(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val icon: ImageVector,
    val description: String = "",
    val primary: Boolean
)

val catalogItems = listOf(
    CatalogItem(
        id = 1,
        name = "Propiedades",
        imageUrl = "",
        icon = Icons.Default.HomeWork,
        description = "Listado de propiedades.",
        primary = true
    ),
    CatalogItem(
        id = 2,
        name = "Usuarios",
        imageUrl = "",
        icon = Icons.Default.CoPresent,
        description = "Listado de usuarios.",
        primary = true
    ),
    CatalogItem(
        id = 3,
        name = "Distritos",
        imageUrl = "",
        icon = Icons.Default.LocationCity,
        description = "Listado de distritos.",
        primary = false
    ),
    CatalogItem(
        id = 4,
        name = "Provincias",
        imageUrl = "",
        icon = Icons.Default.Terrain,
        description = "Listado de provincias.",
        primary = false
    ),
    CatalogItem(
        id = 5,
        name = "departamentos",
        imageUrl = "",
        icon = Icons.Default.Terrain,
        description = "Listado de departamentos.",
        primary = true
    ),
    CatalogItem(
        id = 6,
        name = "Países",
        imageUrl = "",
        icon = Icons.Default.Flag,
        description = "Listado de países.",
        primary = true
    )
)