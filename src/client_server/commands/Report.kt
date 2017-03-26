package client_server.commands

import client_server.wdi.data.WDIDAO


class Report(command: List<String>) : Command(command) {
    override fun execute(): String {
        return WDIDAO.report(command[1])
    }
}