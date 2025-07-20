package karpiuk.bookmary.core_domain.enums

enum class PlaybackSpeed(val multiplier: Float) {
    Normal(1.0f),
    Fast(1.25f),
    SuperFast(1.5f),
    Max(2.0f);

    fun getNextSpeed(): PlaybackSpeed {
        val all = entries
        val nextIndex = (all.indexOf(this) + 1) % all.size
        return all[nextIndex]
    }
}
