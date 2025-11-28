package eu.dezeekees.melay.app.data.util

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

object ConfigDir {

    private val appConfigDir: Path =
        listOf(System.getProperty("user.home"), ".config", "melay")
            .joinToString(separator = "/")
            .toPath()

    fun getConfigDir(): Path {
        // Create the directory if it doesn't exist
        if (!FileSystem.SYSTEM.exists(appConfigDir)) {
            FileSystem.SYSTEM.createDirectories(appConfigDir)
        }
        return appConfigDir
    }

    fun getFile(fileName: String): Path {
        return getConfigDir().resolve(fileName)
    }
}