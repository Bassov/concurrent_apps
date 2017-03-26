package client_server.commands


class Stop(command: List<String>) : Command(command) {
    override fun execute(): String {
        return "Server stopped"
    }
}