package client_server.wdi.loader

import client_server.common.DATA_ROUTE
import client_server.wdi.data.WDI
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

fun main(args: Array<String>) {
    val loader = WDILoader()
    loader.load(DATA_ROUTE)
}

class WDILoader {

    fun load(route: String): List<WDI> {
        val file:Path = Paths.get(route).toAbsolutePath()
        val dataSet = mutableListOf<WDI>()

        Files.newBufferedReader(file).use { reader ->
            var line = reader.readLine()
            while (line != null) {
                val dataObject = WDI()
                dataObject.setData(parse(line))
                dataSet.add(dataObject)
                line = reader.readLine()
            }
        }

        return dataSet
    }

    private fun parse(line: String): Array<String?> {
        val ret = arrayOfNulls<String>(59)
        var index = 0
        var buffer = StringBuffer()
        var enComillas = false
        (0..line.length - 1)
                .map { line[it] }
                .forEach {
                    if (it == '"') {
                        enComillas = !enComillas
                    } else if (it == ',' && !enComillas) {
                        ret[index] = buffer.toString()
                        index++
                        buffer = StringBuffer()
                    } else {
                        buffer.append(it)
                    }
                }
        ret[index] = buffer.toString()
        return ret
    }
}