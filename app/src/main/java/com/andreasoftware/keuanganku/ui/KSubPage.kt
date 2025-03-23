package com.andreasoftware.keuanganku.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.andreasoftware.keuanganku.R

abstract class KSubPage<VB : ViewBinding, VM : ViewModel> : Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected abstract val viewModel: VM

    open val title: String = ""
    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    open fun setupObserver() {} // Opsional
    open fun setupListener() {} // Opsional
    open fun setupAdapter() {} // Opsional
    open fun setupComponent() {}

    open fun init() {
        setupObserver()
        setupListener()
        setupAdapter()
        setupComponent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.appBarTitle)?.text = title
        view.findViewById<ImageView>(R.id.appBarBackButton).setOnClickListener {
            findNavController().navigateUp()
        }
        init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Mencegah memory leak
    }
}
