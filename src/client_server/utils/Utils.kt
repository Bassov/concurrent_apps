package client_server.utils

import client_server.commands.*

fun parseCommand(command: List<String>): Command {
    return when (command[0]) {
        "q" -> Query(command)
        "r" -> Report(command)
        "z" -> Stop(command)
        else -> ErrorCommand(command)
    }
}
