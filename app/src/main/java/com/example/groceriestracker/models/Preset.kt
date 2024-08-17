package com.example.groceriestracker.models

import androidx.compose.ui.graphics.Color
import com.example.groceriestracker.FriendlyIcon
import com.example.groceriestracker.FriendlyIconicsIcon
import com.mikepenz.iconics.typeface.library.community.material.CommunityMaterial

class Preset(
    val id: String,
    val name: String,
    val icon: FriendlyIcon,
    val aliases: List<String>,
    // TODO category? tags?
    // unit, unitclass?
) {

    companion object {
        fun getById(id: String): Preset? {
            return Presets.find { preset -> preset.id == id }
        }

        fun searchByAlias(query: String): List<Preset> {
            // TODO couldn't I make this the same for processed items
            //val combinedAliases = getCombinedAliases(context)
            // TODO include filter for hiragana, katakana, kanji, romaji
            val queries = arrayOf(query.lowercase())
            return Presets.filter { preset -> // for each icon
                preset.aliases.any { alias -> // for each alias of that icon
                    queries.any { query -> // for each query
                        alias.contains(query) // if query is a substring of alias
                    }
                }
            }
        }

        // TODO how many times is this constructed? should only be once i think
        val Presets = listOf(
            Preset(
                id = "grape",
                name = "Grapes",
                icon = FriendlyIconicsIcon(
                    CommunityMaterial.Icon2.cmd_fruit_grapes,
                    Color(0xFF6200EE) // dark purple
                ),
                //aliases= stringArrayResource()
                aliases = listOf(
                    "grape", "grapes",
                    "グレープ", "ぶどう", "ブドウ"
                )
            ),
            Preset(
                id = "toothpaste",
                name = "Toothpaste",
                icon = FriendlyIconicsIcon(
                    CommunityMaterial.Icon3.cmd_toothbrush_paste, // TODO find better icon
                    Color(0xFF00ffd0) // minty green
                ),
                aliases = listOf(
                    "toothpaste",
                    //"グレープ", "ぶどう", "ブドウ"
                )
            ),
            Preset(
                id = "bread",
                name = "Bread",
                icon = FriendlyIconicsIcon(
                    CommunityMaterial.Icon.cmd_baguette,
                    Color(0xFFDFBB7E) // light brown (color of bread)
                ),
                aliases = listOf(
                    "bread",
                    //"グレープ", "ぶどう", "ブドウ"
                )
            )
        )

    }
}