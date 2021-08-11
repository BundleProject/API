package org.bundleproject.json.assets

data class ModAsset(
    val metadata: ModMetadata,
    val platforms:
    // Platform
    Map<
            String,
            // MC Version
            Map<
                    String,
                    // Mod version
                    Map<
                            String,
                            ModVersion
                    >
            >
    >
    // TODO: Find a way to fix this ugliness (probably refactor assets again)
)
