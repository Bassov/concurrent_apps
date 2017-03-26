package client_server.serial.client

import client_server.common.SERIAL_PORT
import client_server.wdi.data.WDIDAO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.io.StringWriter
import java.net.Socket
import java.util.*

/**
 * Class that implements a client of the serial server. It implements the Runnable interface.
 * It will be executed as a Thread
 * @author author
 */
class SerialClient : Runnable {

    override fun run() {
        val dao = WDIDAO
        val data = dao.data
        val globalStart: Date = Date()
        val globalEnd: Date
        val randomGenerator = Random()

        for (i in 0..9) {
            for (j in 0..8) {
                try {
                    Socket("localhost", SERIAL_PORT).use { echoSocket ->
                        PrintWriter(echoSocket.getOutputStream(), true).use { out ->
                            BufferedReader(
                                    InputStreamReader(
                                            echoSocket.getInputStream())).use { `in` ->
                                BufferedReader(
                                        InputStreamReader(System.`in`)).use { stdIn ->
                                    val wdi = data[randomGenerator.nextInt(data.size)]
                                    val writer = StringWriter()
                                    writer.write("q")
                                    writer.write(";")
                                    writer.write(wdi.countryCode)
                                    writer.write(";")
                                    writer.write(wdi.indicatorCode)
                                    val command = writer.toString()
                                    out.println(command)
                                    val output = `in`.readLine()
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            try {
                Socket("localhost", SERIAL_PORT).use { echoSocket ->
                    PrintWriter(echoSocket.getOutputStream(), true).use { out ->
                        BufferedReader(
                                InputStreamReader(echoSocket.getInputStream())).use { `in` ->
                            BufferedReader(
                                    InputStreamReader(System.`in`)).use { stdIn ->
                                val wdi = data.get(randomGenerator.nextInt(data.size))

                                val writer = StringWriter()
                                writer.write("r")
                                writer.write(";")
                                writer.write(wdi.indicatorCode)

                                val command = writer.toString()
                                out.println(command)
                                val output = `in`.readLine()

                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        globalEnd = Date()
        println("Total Time: ${(globalEnd.time - globalStart.time) / 1000} seconds.")

    }


}
