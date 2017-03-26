package client_server.commands

import client_server.wdi.data.WDIDAO


class Query(command: List<String>) : Command(command) {
    override fun execute(): String {
        if (command.size == 3) {
            return WDIDAO.query(command[1], command[2])
        } else if (command.size == 4) {
            try {
                return WDIDAO.query(command[1], command[2], command[3].toInt())
            } catch (e: Exception) { }
        }
        return "ERROR;Bad command"
    }
}