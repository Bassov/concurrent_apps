package client_server.wdi.data

class WDI {
    var countryName: String? = null
    var countryCode: String? = null
    var indicatorName: String? = null
    var indicatorCode: String? = null
    var values = arrayOfNulls<Double>(55)

    val FIRST_YEAR: Short = 1960

    fun setData(data: Array<String?>) {
        countryName = getString(data[0])
        countryCode = getString(data[1])
        indicatorName = getString(data[2])
        indicatorCode = getString(data[3])
        for (i in values.indices) values[i] = getValue(data[i + 4])
    }

    private fun getString(string: String?): String {
        if (string!!.startsWith("\"")) {
            return string.substring(1, string.length - 1)
        }
        return string
    }

    private fun getValue(string: String?): Double {
        if (string!!.trim().isEmpty()) return 0.0
        return string.toDouble()
    }

    fun getValue(year: Int): Double? {
        if (year < FIRST_YEAR || year >= FIRST_YEAR + values.size) {
            throw IllegalArgumentException("No data for that year")
        }

        val index = (year - FIRST_YEAR)
        return values[index]
    }
}