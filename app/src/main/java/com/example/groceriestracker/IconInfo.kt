package com.example.groceriestracker

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WineBar
import androidx.compose.ui.res.stringArrayResource
import com.mikepenz.iconics.typeface.library.community.material.CommunityMaterial


class IconInfo(
    val id: String,
    val icon: FriendlyIcon,
    private val aliases: Array<String>
) {
    companion object {

        /**
         * Gets an icon from INFO by id
         */
        fun getIconById(id: String): IconInfo? {
            return INFO.find { iconInfo -> // for each icon
                iconInfo.id == id
            }
        }

        /**
         * Finds all icons that match the search query
         */
        fun searchIconsByAlias(alias: String): List<IconInfo> {
            //val combinedAliases = getCombinedAliases(context)
            // TODO include filter for hiragana, katakana, kanji, romaji
            val queries = arrayOf(alias.lowercase())
            return INFO.filter { iconInfo -> // for each icon
                iconInfo.aliases.any { alias -> // for each alias of that icon
                    queries.any { query -> // for each query
                        alias.contains(query) // if query is a substring of alias
                    }
                }
            }
        }

        val INFO = listOf(
            IconInfo(
                id = "grape",
                icon = FriendlyIconicsIcon(
                    CommunityMaterial.Icon2.cmd_fruit_grapes,
                    Color(0xFF6200EE) // dark purple
                ),
                //aliases= stringArrayResource()
                aliases = arrayOf(
                    "grape", "grapes",
                    "グレープ", "ぶどう", "ブドウ"
                )
            ),

            IconInfo(
                id = "toothpaste",
                icon = FriendlyIconicsIcon(
                    CommunityMaterial.Icon3.cmd_toothbrush_paste, // TODO find better icon
                    Color(0xFF00ffd0) // minty green
                ),
                aliases = arrayOf(
                    "toothpaste",
                    //"グレープ", "ぶどう", "ブドウ"
                )
            ),

            IconInfo(
                id = "bread",
                icon = FriendlyIconicsIcon(
                    CommunityMaterial.Icon.cmd_baguette,
                    Color(0xFFDFBB7E) // light brown (color of bread)
                ),
                aliases = arrayOf(
                    "bread",
                    //"グレープ", "ぶどう", "ブドウ"
                )
            )
        )
    }
}