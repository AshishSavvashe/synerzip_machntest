package demo.com.synerzip_ashish_savvashe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import demo.com.synerzip_ashish_savvashe.databinding.HomeitemviewBinding
import demo.com.synerzip_ashish_savvashe.models.weatherresponse4NextFiveDays
import demo.com.synerzip_ashish_savvashe.utils.AppUtil
import java.text.SimpleDateFormat
import java.util.*

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private lateinit var mContext: Context
     var weathrdataList: List<weatherresponse4NextFiveDays.AllData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        var homeItemViewBinding = HomeitemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(homeItemViewBinding)
    }

    override fun getItemCount(): Int {
        return weathrdataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val homeItemViewBinding = holder.homeItemViewBinding
        homeItemViewBinding.homeObj = weathrdataList[position]

        homeItemViewBinding.tvtemp.text ="%.0f°".format(weathrdataList[position].main.temp)

        val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date: Date = fmt.parse(weathrdataList[position].dt_txt)

        val fmtOut = SimpleDateFormat("HH:mm a")
        homeItemViewBinding.tvTime.text = fmtOut.format(date);

        val time: Date = fmt.parse(weathrdataList[position].dt_txt)

        val time1 = SimpleDateFormat("d MMM yyyy")
        homeItemViewBinding.tvDate.text = time1.format(time)

        homeItemViewBinding.animationView.setAnimation(
            AppUtil.getWeatherAnimation(
                weathrdataList[position].weather[0].id
            )
        )
        homeItemViewBinding.animationView.playAnimation()

    }

    fun updateList(updatedList: List<weatherresponse4NextFiveDays.AllData>) {
        this.weathrdataList = updatedList
        notifyDataSetChanged()
    }

    class ViewHolder(val homeItemViewBinding: HomeitemviewBinding) : RecyclerView.ViewHolder(homeItemViewBinding.root)
    val TAG = this::class.java.name
}