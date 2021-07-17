package lobna.parentaps.daily.forecast.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import lobna.parentaps.daily.forecast.R
import lobna.parentaps.daily.forecast.data.ForecastResponse
import lobna.parentaps.daily.forecast.databinding.ItemDayForecastBinding

class DayForecastAdapter(private val items: ArrayList<ForecastResponse.DailyForecast>) :
    RecyclerView.Adapter<DayForecastAdapter.DayForecastViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DayForecastViewHolder {
        val itemDayForecastBinding: ItemDayForecastBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_day_forecast, parent, false
            )
        return DayForecastViewHolder(itemDayForecastBinding)
    }

    override fun onBindViewHolder(holder: DayForecastViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class DayForecastViewHolder(var itemDayForecastBinding: ItemDayForecastBinding) :
        RecyclerView.ViewHolder(itemDayForecastBinding.root) {

        fun bind(item: ForecastResponse.DailyForecast) {
            itemDayForecastBinding.dfivm = DayForecastItemViewModel(item)
        }
    }
}