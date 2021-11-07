package org.bundleproject.api.json.assets

typealias ModVersions = Map<String, ModData>

typealias McVersions = Map<String, ModVersions>

typealias ModPlatforms = Map<String, McVersions>

data class ModAsset(val metadata: ModMetadata, val platforms: ModPlatforms)
