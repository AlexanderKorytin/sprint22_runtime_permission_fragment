package com.example.sprint22_runtime_permission_fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.sprint22_runtime_permission_fragment.databinding.StartFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartFragment: Fragment() {
    private var _binding: StartFragmentBinding? = null
    private val binding get() = _binding!!

//    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted: Boolean ->
//        if (isGranted) {
//            binding.permissionRequestFrame.isVisible = false
//            binding.permissionGranted.isVisible = true
//        } else {
//            with(binding){
//                permissionGranted.isVisible = false
//                permissionRequestFrame.isVisible = true
//            }
//        }
//
//    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { resultMap ->
        val cameraPermissionGranted = resultMap[Manifest.permission.CAMERA]
        if (cameraPermissionGranted != null && cameraPermissionGranted) {
            // Пользователь дал разрешение, можно продолжать работу
            binding.permissionRequestFrame.visibility = View.GONE
            binding.permissionGranted.visibility = View.VISIBLE
        } else {
            // Пользователь отказал в предоставлении разрешения
            binding.permissionRequestFrame.visibility = View.VISIBLE
            binding.permissionGranted.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = StartFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission()

        binding.permissionRequestFrame.setOnClickListener {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                Toast.makeText(requireContext(), "show", Toast.LENGTH_LONG).show()
            }
//            lifecycleScope.launch(Dispatchers.Default){
//                delay(1000)
//                withContext(Dispatchers.Main){
//                    Toast.makeText(requireContext(), "show", Toast.LENGTH_LONG).show()
//                }
//            }
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkPermission(){
        val permissionProvided = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        if (permissionProvided == PackageManager.PERMISSION_GRANTED){
            binding.permissionRequestFrame.isVisible = false
            binding.permissionGranted.isVisible = true
        } else if (permissionProvided == PackageManager.PERMISSION_DENIED){
            with(binding){
                permissionGranted.isVisible = false
                permissionRequestFrame.isVisible = true
            }
        }
    }

companion object{
    fun newInstance(): StartFragment = StartFragment().apply {  }
}
}