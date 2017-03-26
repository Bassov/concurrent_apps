package client_server.serial

import client_server.commands.Stop
import client_server.common.SERIAL_PORT
import client_server.utils.parseCommand
import client_server.wdi.data.WDIDAO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

fun main(args: Array<String>) {
    WDIDAO.data
    var serverRunning = true
    println("Initialization complete")

    val serverSocket = ServerSocket(SERIAL_PORT)
    do {
        val clientSocket = serverSocket.accept()
        val out = PrintWriter(clientSocket.getOutputStream(), true)
        val input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))

        val commandData = input.readLine().split(";")
        println("Command: ${commandData!![0]}")

        val command = parseCommand(commandData)
        if (command is Stop) serverRunning = false
        val response = command.execute()

        println("Response: $response")
        out.println(response)
    } while (serverRunning)
}
