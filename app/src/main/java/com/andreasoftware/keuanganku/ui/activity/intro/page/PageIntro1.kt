package com.andreasoftware.keuanganku.ui.activity.intro.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andreasoftware.keuanganku.databinding.PageIntroPage1Binding

class PageIntro1 : Fragment() {
    private var _binding: PageIntroPage1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PageIntroPage1Binding.inflate(inflater, container, false)
        return binding.root
    }
}