package com.example.sprint22_runtime_permission_fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.sprint22_runtime_permission_fragment.databinding.StartFragmentBinding
import com.markodevcic.peko.PermissionRequester
import com.markodevcic.peko.PermissionResult
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.coroutines.launch

class StartFragment : Fragment() {
    private val rxPermissions by lazy { RxPermissions(this) }
    private var _binding: StartFragmentBinding? = null
    private val binding get() = _binding!!
    // private val requester = PermissionRequester.instance()

//    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted: Boolean ->
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StartFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//       checkPermission()

        binding.permissionRequestFrame.setOnClickListener {
//            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
//                Toast.makeText(requireContext(), "show", Toast.LENGTH_LONG).show()
//            }
           //  checkPermission()
//            lifecycleScope.launch(Dispatchers.Default){
//                delay(1000)
//                withContext(Dispatchers.Main){
//                    Toast.makeText(requireContext(), "show", Toast.LENGTH_LONG).show()
//                }
//            }
            // requestPermissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
            rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe { granted: Boolean ->
                    if (granted) {
                        // Пользователь дал разрешение, можно продолжать работу
                        binding.permissionRequestFrame.visibility = View.GONE
                        binding.permissionGranted.visibility = View.VISIBLE
                    } else {
                        // Пользователь отказал в предоставлении разрешения
                        binding.permissionRequestFrame.visibility = View.VISIBLE
                        binding.permissionGranted.visibility = View.GONE
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun checkPermission(){
//        val permissionProvided = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
//        if (permissionProvided == PackageManager.PERMISSION_GRANTED){
//            binding.permissionRequestFrame.isVisible = false
//            binding.permissionGranted.isVisible = true
//        } else if (permissionProvided == PackageManager.PERMISSION_DENIED){
//            with(binding){
//                permissionGranted.isVisible = false
//                permissionRequestFrame.isVisible = true
//            }
//        }
//    }

//    private fun checkPermission(){
//        lifecycleScope.launch {
//            requester.request(Manifest.permission.CAMERA).collect { result ->
//                when (result) {
//                    // Пользователь дал разрешение, можно продолжать работу
//                    is PermissionResult.Granted -> {
//                        binding.permissionRequestFrame.isVisible = false
//                        binding.permissionGranted.isVisible = true
//                    }
//                    // Пользователь отказал в предоставлении разрешения
//                    is PermissionResult.Denied -> {
//                        binding.permissionRequestFrame.isVisible = true
//                        binding.permissionGranted.isVisible = false
//                    }
//                    // Необходимо показать разрешение
//                    is PermissionResult.Denied.NeedsRationale -> {
//                        Toast.makeText(requireContext(), "show", Toast.LENGTH_LONG).show()
//                        binding.permissionRequestFrame.isVisible = true
//                        binding.permissionGranted.isVisible = false
//                    }
//                    // Запрещено навсегда, перезапрашивать нет смысла, предлагаем пройти в настройки
//                    is PermissionResult.Denied.DeniedPermanently -> {
//                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        intent.data =
//                            Uri.fromParts("package", requireContext().packageName, null)
//                        startActivity(intent)
//                    }
//                    // Запрос на разрешение отменён
//                    is PermissionResult.Cancelled -> {
//                        return@collect
//                    }
//                }
//            }
//        }
//    }

    companion object {
        fun newInstance(): StartFragment = StartFragment().apply { }
    }
}