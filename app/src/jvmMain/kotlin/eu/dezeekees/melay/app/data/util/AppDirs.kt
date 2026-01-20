package eu.dezeekees.melay.app.data.util

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

object AppDirs {

    private const val APP_NAME = "melay"

    private val os: String =
        System.getProperty("os.name").lowercase()

    /* ---------- Base directories ---------- */

    private fun configBaseDir(): Path =
        when {
            os.contains("win") -> {
                (System.getenv("APPDATA")
                    ?: error("APPDATA is not set"))
                    .toPath()
            }

            os.contains("mac") -> {
                listOf(
                    System.getProperty("user.home"),
                    "Library",
                    "Preferences"
                ).joinToString("/").toPath()
            }

            else -> { // Linux / BSD
                (System.getenv("XDG_CONFIG_HOME")
                    ?: listOf(
                        System.getProperty("user.home"),
                        ".config"
                    ).joinToString("/")
                        ).toPath()
            }
        }

    private fun dataBaseDir(): Path =
        when {
            os.contains("win") -> {
                (System.getenv("LOCALAPPDATA")
                    ?: System.getenv("APPDATA")
                    ?: error("Neither LOCALAPPDATA nor APPDATA is set"))
                    .toPath()
            }

            os.contains("mac") -> {
                listOf(
                    System.getProperty("user.home"),
                    "Library",
                    "Application Support"
                ).joinToString("/").toPath()
            }

            else -> { // Linux / BSD
                (System.getenv("XDG_DATA_HOME")
                    ?: listOf(
                        System.getProperty("user.home"),
                        ".local",
                        "share"
                    ).joinToString("/")
                        ).toPath()
            }
        }

    /* ---------- App directories ---------- */

    private val appConfigDir: Path =
        configBaseDir().resolve(APP_NAME)

    private val appDataDir: Path =
        dataBaseDir().resolve(APP_NAME)

    /* ---------- Public API ---------- */

    fun configDir(): Path {
        if (!FileSystem.SYSTEM.exists(appConfigDir)) {
            FileSystem.SYSTEM.createDirectories(appConfigDir)
        }
        return appConfigDir
    }

    fun dataDir(): Path {
        if (!FileSystem.SYSTEM.exists(appDataDir)) {
            FileSystem.SYSTEM.createDirectories(appDataDir)
        }
        return appDataDir
    }

    fun configFile(name: String): Path =
        configDir().resolve(name)

    fun dataFile(name: String): Path =
        dataDir().resolve(name)
}
