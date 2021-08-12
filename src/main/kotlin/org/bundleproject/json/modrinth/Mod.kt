package org.bundleproject.json.modrinth

data class Mod(
    val body: String,
    val body_url: Any,
    val categories: List<String>,
    val client_side: String,
    val description: String,
    val discord_url: String,
    val donation_urls: List<String>,
    val downloads: Int,
    val followers: Int,
    val icon_url: String,
    val id: String,
    val issues_url: String,
    val license: License,
    val published: String,
    val server_side: String,
    val slug: String,
    val source_url: String,
    val status: String,
    val team: String,
    val title: String,
    val updated: String,
    val versions: List<String>,
    val wiki_url: Any
)