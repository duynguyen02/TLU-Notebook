package com.tianjun.tls_tkb.presentation.main.settings

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tianjun.tls_tkb.BuildConfig
import com.tianjun.tls_tkb.databinding.DialogChangeHostNameBinding
import com.tianjun.tls_tkb.databinding.DialogChangeHostPortBinding
import com.tianjun.tls_tkb.databinding.DialogDownloadDataBinding
import com.tianjun.tls_tkb.databinding.FragmentSettingsBinding
import com.tianjun.tls_tkb.databinding.LayoutLoadingBinding
import com.tianjun.tls_tkb.domain.model.Login
import com.tianjun.tls_tkb.util.extension.showMessageDialog
import com.tianjun.tls_tkb.util.extension.showToastIfEditTextIsEmpty
import com.tianjun.tls_tkb.util.network.NetworkHelper
import com.tianjun.tls_tkb.util.view.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var loadingBinding: LayoutLoadingBinding
    private val settingsViewModel: SettingsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        loadingBinding = LayoutLoadingBinding.bind(binding.settingsFIncLoading.root)

        binding.settingsFTvVersion.text = BuildConfig.VERSION_NAME
        binding.settingsFBtnEditHostname.setOnClickListener { openChangeHostnameDialog() }
        binding.settingsFBtnEditPort.setOnClickListener { openChangePortDialog() }
        binding.settingsFBtnDownloadData.setOnClickListener { openDownloadDataDialog() }
        binding.settingsFBtnDeleteAllData.setOnClickListener { openConfirmDeleteAllDataDiaLog() }

        observeSetup()

        return binding.root
    }

    private fun openConfirmDeleteAllDataDiaLog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Cảnh báo")
            .setMessage("Bạn có muốn xóa toàn bộ dữ liệu (bao gồm lịch học, tài khoản đăng nhập, các cấu hình,...)?")
            .setPositiveButton("Có"
            ) { _, _ -> settingsViewModel.deleteAllData() }
            .setNegativeButton("Không", null)
            .create()
            .show()
    }

    private fun openDownloadDataDialog() {

        val dialogBinding = DialogDownloadDataBinding.inflate(layoutInflater)
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Tải dữ liệu")
            .setView(dialogBinding.root)
            .create()
        dialogBinding.downloadDataDBtnGet.setOnClickListener {
            if (
                showToastIfEditTextIsEmpty(
                    dialogBinding.downloadDataDEtStudentId,
                    dialogBinding.downloadDataDEtStudentPassword
                )
            ) {
                return@setOnClickListener
            }

            dialog.dismiss()
            settingsViewModel.downloadData(
                Login(
                    dialogBinding.downloadDataDEtStudentId.text.toString().trim(),
                    dialogBinding.downloadDataDEtStudentPassword.text.toString().trim()
                )
            )

        }
        dialogBinding.downloadDataDBtnExit.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun observeSetup() {

        settingsViewModel.getHostName().observe(viewLifecycleOwner) {
            binding.settingsFTvHostnameValue.text = it
        }

        settingsViewModel.getHostPort().observe(viewLifecycleOwner) {
            binding.settingsFTvHostPortValue.text = it
        }

        settingsViewModel.uiState.observe(viewLifecycleOwner){
            when(it){
                is Result.Success<*> -> {
                    binding.settingsFFlLoading.visibility = View.GONE
                    showMessageDialog(it.t.toString())
                    settingsViewModel.setNothingState()
                }
                is Result.Loading -> {
                    binding.settingsFFlLoading.visibility = View.VISIBLE
                    loadingBinding.loadingLTvNotification.text = it.progress.toString()
                }
                is Result.Error -> {
                    binding.settingsFFlLoading.visibility = View.GONE
                    showMessageDialog("Lỗi: ${it.error}")
                    settingsViewModel.setNothingState()
                }
                is Result.Nothing -> {

                }

            }
        }
    }

    private fun openChangePortDialog() {

        val dialogBinding = DialogChangeHostPortBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Chỉnh sửa cổng")
            .setView(dialogBinding.root)
            .create()

        dialogBinding.changeHostPortDBtnExit.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.changeHostPortDBtnAutoDetect.setOnClickListener {
            dialog.dismiss()
            settingsViewModel.detectPort()
        }

        dialogBinding.changeHostPortDBtnSetDefault.setOnClickListener {
            settingsViewModel.setDefaultHostPort()
            dialog.dismiss()
        }

        dialogBinding.changeHostPortDBtnSave.setOnClickListener {

            if (showToastIfEditTextIsEmpty(dialogBinding.changeHostPortDEtHostPort)) {
                return@setOnClickListener
            }

            val port = dialogBinding.changeHostPortDEtHostPort.text.toString().trim()

            settingsViewModel.setHostPort(port)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun openChangeHostnameDialog() {

        val dialogBinding = DialogChangeHostNameBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Chỉnh sửa địa chỉ máy chủ")
            .setView(dialogBinding.root)
            .create()

        dialogBinding.changeHostNameDBtnSave.setOnClickListener {
            if (showToastIfEditTextIsEmpty(dialogBinding.changeHostNameDEtHostName)) {
                return@setOnClickListener
            }

            val hostname = dialogBinding.changeHostNameDEtHostName.text.toString().trim()

            if (!NetworkHelper.isValidURL(hostname)) {
                Toast.makeText(
                    requireContext(),
                    "Vui lòng nhập địa chỉ hợp lệ!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            settingsViewModel.setHostName(hostname)
            dialog.dismiss()
        }

        dialogBinding.changeHostNameDBtnSetToDefault.setOnClickListener {
            settingsViewModel.setDefaultHostName()
            dialog.dismiss()
        }

        dialogBinding.changeHostNameDBtnExit.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}