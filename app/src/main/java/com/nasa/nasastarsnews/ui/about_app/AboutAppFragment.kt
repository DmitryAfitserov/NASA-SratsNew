package com.nasa.nasastarsnews.ui.about_app

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.nasa.nasastarsnews.R
import android.content.Intent
import android.net.Uri
import android.text.Spanned
import androidx.core.text.HtmlCompat


class AboutAppFragment : Fragment() {

    private lateinit var shareViewModel: AboutAppViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shareViewModel =
            ViewModelProviders.of(this).get(AboutAppViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_about_app, container, false)

        val textViewAppInMarket = root.findViewById<TextView>(R.id.app_in_market)
        textViewAppInMarket.setOnClickListener {

            val appPackageName = context!!.packageName // getPackageName() from Context or Activity object
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (anfe: android.content.ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }

        }

        val textViewService = root.findViewById<TextView>(R.id.translate_by_service)

        textViewService.movementMethod = LinkMovementMethod.getInstance()

        var policy:Spanned = HtmlCompat.fromHtml(getString(R.string.translate_service), HtmlCompat.FROM_HTML_MODE_LEGACY)
        textViewService.text = policy

//        val textView: TextView = root.findViewById(R.id.text_share)
//        shareViewModel.text.observe(this, Observer {
//            textView.text = it
//        })


        return root
    }
}