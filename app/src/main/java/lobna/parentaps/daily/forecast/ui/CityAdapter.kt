package lobna.parentaps.daily.forecast.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import lobna.parentaps.daily.forecast.R
import lobna.parentaps.daily.forecast.data.CityModel

class CityAdapter(private val items: List<CityModel>, val cityInterface: CityInterface) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityViewHolder {
        return CityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class CityViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: CityModel) {
            view.findViewById<TextView>(R.id.city_name).run {
                text = item.name
                setOnClickListener { cityInterface.onCityClick(item) }
            }
        }
    }
}