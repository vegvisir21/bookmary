package karpiuk.bookmary.core_domain.extensions

fun Float.toTrimmedString(): String {
    return if (this % 1f == 0f) {
        this.toInt().toString()
    } else {
        this.toString()
    }
}