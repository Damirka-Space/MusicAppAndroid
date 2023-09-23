package space.damirka.musicappandroid.services

object ServiceLocator {
    fun getPlayer() : PlayerService = PlayerService()
}