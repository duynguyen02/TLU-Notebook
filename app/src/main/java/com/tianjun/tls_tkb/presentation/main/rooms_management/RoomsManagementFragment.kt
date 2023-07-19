package com.tianjun.tls_tkb.presentation.main.rooms_management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tianjun.tls_tkb.databinding.FragmentRoomsManagementBinding

class RoomsManagementFragment : Fragment() {
    private lateinit var binding: FragmentRoomsManagementBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomsManagementBinding.inflate(inflater, container, false)


        return binding.root
    }
}