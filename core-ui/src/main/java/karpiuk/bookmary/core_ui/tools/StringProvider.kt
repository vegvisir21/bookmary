package karpiuk.bookmary.core_ui.tools

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getString(id: Int, vararg args: Any): String {
        return context.getString(id, *args)
    }

}