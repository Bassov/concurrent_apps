package client_server.wdi.data

import client_server.common.DATA_ROUTE
import client_server.wdi.loader.WDILoader
import java.io.StringWriter

object WDIDAO {
    val data: List<WDI> = WDILoader().load(DATA_ROUTE)

    /**
     * Method that implements a query to the data
     * @param codCountry Cod of the country
     * *
     * @param codIndicator Cod of the indicator
     * *
     * @return The values of the indicator in the country for all the years
     */
    fun query(codCountry: String, codIndicator: String): String {
        var wdi: WDI? = null
        for (i in data.indices) {
            wdi = data[i]
            if (wdi.countryCode.equals(codCountry) && wdi.indicatorCode.equals(codIndicator)) {
                break
            }
        }

        val writer = StringWriter()
        writer.write("$codCountry;$codIndicator;")
        val years = wdi!!.values
        for (i in years.indices) {
            writer.write(years[i].toString())
            if (i < years.size - 1) {
                writer.write(";")
            }
        }
        return writer.toString()
    }

    /**
     * Method that implements a query to the data
     * @param codCountry Cod of the country
     * *
     * @param codIndicator Cod of the indicator
     * *
     * @param year Year of the request
     * *
     * @return The value of the indicator for that country in that year
     * *
     * @throws Exception Exception if something goes wrong
     */
    fun query(codCountry: String, codIndicator: String, year: Int): String {
        println("Query: $codCountry, $codIndicator")
        var wdi: WDI? = null
        for (i in data.indices) {
            wdi = data[i]
            if (wdi.countryCode.equals(codCountry) && wdi.indicatorCode.equals(codIndicator)) {
                break
            }
        }

        return "$codCountry;$codIndicator;$year;${wdi?.getValue(year).toString()}"
    }

    /**
     * Method that makes a report of data
     * @param codIndicator Indicator
     * *
     * @return The medium value of that indicator per country
     */
    fun report(codIndicator: String): String {
        val writer = StringWriter()
        writer.write("$codIndicator;")
        for (i in data.indices) {
            val wdi = data[i]
            if (wdi.indicatorCode.equals(codIndicator)) {
                val years = wdi.values
                var mean = years.indices.sumByDouble { years[it]!! }
                mean /= years.size.toDouble()
                writer.write("${wdi.countryCode};$mean;")
            }
        }

        return writer.toString()
    }

}
