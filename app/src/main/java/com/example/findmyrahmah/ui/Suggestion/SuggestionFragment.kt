package com.example.findmyrahmah.ui.Suggestion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.DefaultRetryPolicy
import com.example.findmyrahmah.R
import com.example.findmyrahmah.databinding.FragmentSuggestionBinding
import org.json.JSONObject

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

        return root
    }

    private fun suggestShop (suggestion: Suggestion){
        val url = getString (R.string.url_server) + getString (R.string.url_insert) + "?name=" + contact.name + "&contact=" + contact.phone
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
        WebDB.getInstance (requireContext()), addToRequestQueue(jsonObjectRequest)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}