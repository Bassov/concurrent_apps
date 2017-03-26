package client_server.commands


class ErrorCommand(command: List<String>) : Command(command) {
    override fun execute(): String {
        return "Unknown command: ${command[0]}"
    }
}