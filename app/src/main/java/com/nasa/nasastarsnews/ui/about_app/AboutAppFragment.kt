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


        val textViewAboutApp = root.findViewById<TextView>(R.id.about_app)

        textViewAboutApp.movementMethod = LinkMovementMethod.getInstance()

        val policyAboutApp:Spanned = HtmlCompat.fromHtml(getString(R.string.about_app), HtmlCompat.FROM_HTML_MODE_LEGACY)
        textViewAboutApp.text = policyAboutApp

//        val textView: TextView = root.findViewById(R.id.text_share)
//        shareViewModel.text.observe(this, Observer {
//            textView.text = it
//        })


        return root
    }
}