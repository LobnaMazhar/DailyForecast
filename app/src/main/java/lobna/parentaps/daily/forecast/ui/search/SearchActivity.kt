package lobna.parentaps.daily.forecast.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import lobna.parentaps.daily.forecast.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activitySearchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(activitySearchBinding.root)

        activitySearchBinding.svm = searchViewModel

        searchViewModel.onBackEvent.observe(this, { onBackPressed() })
        searchViewModel.selectCity.observe(this, {
            val data = Intent().apply {
                putExtra("data", bundleOf(Pair("name", it.name)))
            }
            setResult(RESULT_OK, data)
            finish()
        })
    }
}