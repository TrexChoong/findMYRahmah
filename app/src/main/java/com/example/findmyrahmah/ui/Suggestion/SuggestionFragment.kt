package com.example.findmyrahmah.ui.Suggestion

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.findmyrahmah.R
import com.example.findmyrahmah.databinding.FragmentSuggestionBinding
import my.edu.tarc.mycontact.WebDB
import org.json.JSONObject
import java.util.*

class SuggestionFragment : Fragment() {

    private var _binding: FragmentSuggestionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val suggestionViewModel =
            ViewModelProvider(this).get(SuggestionViewModel::class.java)

        _binding = FragmentSuggestionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.timePickerButtonStart.setOnClickListener {
            showTimePickerDialog(true)
        }

        binding.timePickerButtonEnd.setOnClickListener {
            showTimePickerDialog(false)
        }
        return binding.root
    }

    private fun suggestShop (suggestion: Suggestion){
        val url = getString (R.string.url_server) + getString (R.string.url_shop_create) + "?name=" + suggestion.name + "&lat=" + "123" + "&longCord=" + "123" + "&date_expired=" + "2024-05-05%2000:00:00"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            {response ->
                try{
                    if (response != null){
                        val strResponse = response.toString()
                        val jsonResponse = JSONObject(strResponse)
                        val success : String = jsonResponse.get("success").toString()

                        if (success.equals("1")){
                            Toast.makeText (requireContext(),"Suggeestion Saved", Toast.LENGTH_SHORT).show ()
                        } else {
                            Toast.makeText (requireContext(),"Suggestion not Saved", Toast.LENGTH_SHORT).show ()
                        }
                    }
                }catch (e:Exception){
                    Log.d("Suggestion Fragment", "Response: %s".format (e.message.toString()))
                }
            },
            {   error->
                Log.d("Suggestion Fragment", "Response: %s".format (error.message.toString()))
            }
        )

        jsonObjectRequest.retryPolicy = DefaultRetryPolicy (DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,0,1F)
        WebDB.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showTimePickerDialog(isStartTime: Boolean) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            activity,
            TimePickerDialog.OnTimeSetListener { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                // Handle the selected time (selectedHour and selectedMinute)
                // You can update a TextView or perform any other action here
                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                if (isStartTime) {
                    binding.timePickerButtonStart.text = selectedTime
                } else {
                    binding.timePickerButtonEnd.text = selectedTime
                }
            },
            hour,
            minute,
            false
        )

        timePickerDialog.show()
    }
}