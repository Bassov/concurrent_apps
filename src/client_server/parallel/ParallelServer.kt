package client_server.parallel

import client_server.common.CONCURRENT_PORT
import java.net.ServerSocket
import java.util.concurrent.Executors

fun main(args: Array<String>) {
    val cores = Runtime.getRuntime().availableProcessors()

    val pool = Executors.newFixedThreadPool(cores)

    val serverSocket = ServerSocket(CONCURRENT_PORT)
    val serverIsRunning = true

    do {

    } while (serverIsRunning)
}


