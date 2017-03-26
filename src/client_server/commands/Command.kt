package client_server.commands

abstract class Command(protected val command: List<String>) : Runnable {
    abstract fun execute(): String
    override fun run() {
        execute()
    }
}
