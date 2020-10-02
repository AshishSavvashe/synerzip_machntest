package demo.com.synerzip_ashish_savvashe.views

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.*
import com.google.gson.Gson
import demo.com.synerzip_ashish_savvashe.R
import demo.com.synerzip_ashish_savvashe.Service.LocationService
import demo.com.synerzip_ashish_savvashe.WorkManager.NotificationWorker
import demo.com.synerzip_ashish_savvashe.adapter.HomeAdapter
import demo.com.synerzip_ashish_savvashe.databinding.FragmentHomeBinding
import demo.com.synerzip_ashish_savvashe.models.weatherresponse
import demo.com.synerzip_ashish_savvashe.models.weatherresponse4NextFiveDays
import demo.com.synerzip_ashish_savvashe.utils.AppUtil
import demo.com.synerzip_ashish_savvashe.viewmodel.ResultDataModel
import java.util.*
import java.util.concurrent.TimeUnit
import demo.com.synerzip_ashish_savvashe.models.firebasesave as firebasesave1


class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var mWorkManager: WorkManager
    var mConstraints: Constraints? = null
    lateinit var viewModel:ResultDataModel
    var locallySavedDate:String? = null

    var isApicall:Boolean = false
    private var mDatabase: DatabaseReference? = null
    var inserObject = firebasesave1()

    var finalDistance1: Double? = 0.0
    private var previousLatLng: LatLng? = null
    private var currentLatLng: LatLng? = null
    val locationA = Location("point A")
    val locationB = Location("point B")

    val CHANNEL_NAME = "Sample Notification"
    val CHANNEL_ID = "ForegroundServiceChannel"

    lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)
        mWorkManager = WorkManager.getInstance(activity!!)
        viewModel = ViewModelProviders.of(this).get(ResultDataModel::class.java)

        //get reference to the "users" node
        mDatabase = FirebaseDatabase.getInstance().reference

        val it = Intent(activity!!, LocationService::class.java)
        ContextCompat.startForegroundService(activity!!,it)

        binding!!.homeRv.layoutManager = LinearLayoutManager(activity)
        homeAdapter = HomeAdapter()
        binding!!.homeRv.adapter = homeAdapter

        initWorkManager()
        initSearchView()
        return binding.root
    }

    private fun initWorkManager() {

        /**
         * Constraints
         * If TRUE task execute only when storage's is not low
         */
        mConstraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED) //checks whether device should have Network Connection
            .build()

        val mRequest: PeriodicWorkRequest =
            PeriodicWorkRequest.Builder(NotificationWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(mConstraints!!) // setting a backoff on case the work needs to retry
                .setInitialDelay(1, TimeUnit.MINUTES)
                .build()
        /**
         * Fetch the particular task status using request ID
         */
        mWorkManager.getWorkInfoByIdLiveData(mRequest.id)
            .observe(activity!!, Observer<WorkInfo> { workInfo ->
                if (workInfo != null) {
                    val calendar = Calendar.getInstance(TimeZone.getDefault())

                    if (System.currentTimeMillis().toString() >= locallySavedDate.toString()) {
                        viewModel.let { it ->
                            it.deleteAllData().let { it1 ->
                            }
                        }
                    }
                }
            })

        /**
         * Enqueue the WorkRequest
         */
        mWorkManager.enqueue(mRequest)
    }

    private fun initSearchView() {

        binding.btnSearch.setOnClickListener {

            if(binding.searchEditText.text.toString().isEmpty()|| binding.searchEditText.text.toString() == " " ){
                showSnack(view!!,getString(R.string.search_msg))
            }else{
                if(isNetworkConnected(activity!!)){
                    isApicall = true
                    init()
                }else{
                    showSnack(view!!,getString(R.string.no_internet))
                }
            }
        }
    }


    private fun init() {
            /*viewModel?.let { it ->
                it.getCityWiseData(binding.cityName.text.toString()).observe(activity!!, Observer {
                    if(it!=null){
                        var data = Gson().fromJson(it.response , weatherresponse::class.java)
                        locallySavedDate = it.currentDate
                        seWeathertData(data )
                    }else{
                        if(isApicall) {
                            callData(binding.searchEditText.text.toString())
                        }
                    }
                })
            }*/

        callData(binding.searchEditText.text.toString())
    }

    /*
    * Call api using observer
    * */
    private fun callData(cityName: String) {
        if(isNetworkConnected(context!!)) {
            viewModel?.let {
                it.getWeatherData(cityName,activity!!).observe(viewLifecycleOwner, Observer<weatherresponse> { apiResponse ->
                    if (apiResponse == null) {
                        showSnack(view!!,getString(R.string.api_error))
                        return@Observer
                    }else{
                        if(apiResponse!= null){
                            callWeatherDataApi4FiveDays()
                            seWeathertData(apiResponse )
                            binding.todayMaterialCard.visibility = View.VISIBLE
                           // binding.CVtemprature.visibility = View.VISIBLE
                            binding.imgNoCity.visibility = View.GONE
                        }else{
                            binding.todayMaterialCard.visibility = View.GONE
                            //binding.CVtemprature.visibility = View.GONE
                            binding.imgNoCity.visibility = View.VISIBLE
                        }
                    }
                })
            }
        }
        else {
            showSnack(view!!,getString(R.string.no_internet))
        }
    }

   /*
    * Set result data
    * */
    private fun seWeathertData(data: weatherresponse) {

        isApicall = false

       val id: String? = mDatabase!!.push().key

       inserObject.id = 0
       inserObject.city_name = binding.searchEditText.text.toString()
       inserObject.response = data
       inserObject.currentDate = System.currentTimeMillis().toString()

       mDatabase!!.child(id!!).setValue(inserObject);

       val currentWeather: weatherresponse = data

        binding.searchEditText.setText(" ")
        binding.cityName.text =  currentWeather.name
        binding.descriptionTextView.text = AppUtil.getWeatherStatus(currentWeather.weather?.get(0)!!.id)
        binding.humidityTextView.text = currentWeather.main!!.humidity.toString()+"%"
        binding.windTextView.text = "%.0f km/hr".format(currentWeather.wind!!.speed)

        binding.tempTextView.text = "%.0f°".format(currentWeather.main!!.temp)
        binding.tvMaxTemp.text = currentWeather.main!!.temp_max.toString()
        binding.tvMinTemp.text = currentWeather.main!!.temp_min.toString()
        binding.tvSeeLevel.text = currentWeather.main!!.sea_level.toString()
        binding.tvGroundLevel.text = currentWeather.main!!.grnd_level.toString()

        binding.animationView.setAnimation(
            AppUtil.getWeatherAnimation(
                currentWeather.weather?.get(0)!!.id
            )
        )
        binding.animationView.playAnimation()
    }

    override fun onStart() {
        super.onStart()

        mDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val alldata: String = postSnapshot.child("response").value.toString()

                   // showSnack(view!!,alldata)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if(intent.action == "action_send_message") {
                val latitude = java.lang.Double.valueOf(intent.getStringExtra("latitude"))
                val longitude = java.lang.Double.valueOf(intent.getStringExtra("longitude"))

                currentLatLng = LatLng(latitude,longitude)

                if(previousLatLng == null)
                {
                    previousLatLng = currentLatLng
                }

                    locationA.latitude = previousLatLng!!.latitude
                    locationA.longitude = previousLatLng!!.longitude

                    locationB.latitude = currentLatLng!!.latitude
                    locationB.longitude = currentLatLng!!.longitude

                   finalDistance1 = locationA.distanceTo(locationB).toDouble()


                    if(finalDistance1!! > 50){
                        finalDistance1 = 0.0
                        callApi(currentLatLng!!.latitude,currentLatLng!!.longitude)
                    }
                }
            }
        }

     fun callApi(latitude: Double, longitude: Double) {

         if(isNetworkConnected(context!!)) {
             viewModel?.let {
                 it.getWeatherDatafromlatlong(latitude,longitude!!,activity!!).observe(viewLifecycleOwner, Observer<weatherresponse> { apiResponse ->
                     if (apiResponse == null) {
                         showSnack(view!!,getString(R.string.api_error))
                         return@Observer
                     }else{
                         if(apiResponse!= null){
                             //seWeathertData(apiResponse )
                             createNotification(apiResponse)
                         }else{
                             showSnack(view!!,getString(R.string.no_data))
                         }
                     }
                 })
             }
         }
         else {
             showSnack(view!!,getString(R.string.no_internet))
         }
    }

    fun callWeatherDataApi4FiveDays() {

        if(isNetworkConnected(context!!)) {
            viewModel?.let {
                it.getWeatherData5FiveDays(binding.searchEditText.text.toString(),activity!!).observe(viewLifecycleOwner, Observer<weatherresponse4NextFiveDays> { apiResponse ->
                    if (apiResponse == null) {
                        showSnack(view!!,getString(R.string.api_error))
                        return@Observer
                    }else{
                        if(apiResponse!= null){
                            homeAdapter.updateList(apiResponse.list)
                        }else{
                            showSnack(view!!,getString(R.string.no_data))
                        }
                    }
                })
            }
        }
        else {
            showSnack(view!!,getString(R.string.no_internet))
        }
    }

    override fun onResume() {
        super.onResume()
        context!!.registerReceiver(broadcastReceiver, IntentFilter("action_send_message"))
    }

    override fun onDetach() {
        super.onDetach()
        context!!.unregisterReceiver(broadcastReceiver)
    }

    fun createNotification(apiResponse: weatherresponse) {

        val notificationIntent = Intent(requireContext(), MainActivity::class.java)
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val pendingIntent = PendingIntent.getActivity(
            requireContext(),
            0, notificationIntent, 0
        )

        val notificationBuilder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setContentTitle("Current Temperature is" + "%.0f°".format(apiResponse.main!!.temp))

            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = (Context.NOTIFICATION_SERVICE) as NotificationManager

        // Notification Channel is required for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH
            )
            channel.setDescription("channel description")
            channel.setShowBadge(true)
            channel.canShowBadge()
            channel.enableLights(true)
            channel.setLightColor(Color.RED)
            channel.enableVibration(true)
            channel.setVibrationPattern(longArrayOf(100, 200, 300, 400, 500))
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
