package com.tianjun.tls_tkb.presentation.main.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        binding.settingsFBtnEditHostname.setOnClickListener { openChangeHostnameDialog() }
        binding.settingsFBtnEditPort.setOnClickListener { openChangePortDialog() }
        binding.settingsFBtnDownloadData.setOnClickListener { openDownloadDataDialog() }

        observeSetup()

        return binding.root
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
                }
                is Result.Nothing -> {

                }

            }
        }
    }

    /**
     * Function to open a dialog for changing the server's port number.
     * The dialog allows the user to enter a custom port number or set it to the default value, auto-detect, or exit without changes.
     * The user input is validated to ensure it's not empty.
     * If the input is valid, the ViewModel's corresponding method is called to update the port number.
     * The dialog contains four buttons: "Exit", "Auto-detect", "Set to Default", and "Save".
     * - "Exit" button: Closes the dialog without saving any changes.
     * - "Auto-detect" button: Detects and sets the port number automatically and dismisses the dialog.
     * - "Set to Default" button: Sets the port number to the default value and dismisses the dialog.
     * - "Save" button: Saves the entered port number if it's valid and dismisses the dialog.
     *
     * The dialog is created using MaterialAlertDialogBuilder from the current fragment's context.
     * The layout for the dialog is inflated using DialogChangeHostPortBinding.
     * The MaterialAlertDialogBuilder is used to set the title, view (the inflated layout), and create the dialog.
     *
     * @see DialogChangeHostPortBinding: The layout binding for the change port dialog.
     * @see showToastIfEditTextIsEmpty(): A utility function to check if an EditText is empty and show a toast message if it is.
     * @see settingsViewModel: An instance of the ViewModel responsible for managing the app's settings.
     */
    private fun openChangePortDialog() {
        // Inflate the layout for the change port dialog
        val dialogBinding = DialogChangeHostPortBinding.inflate(layoutInflater)

        // Create the MaterialAlertDialogBuilder with the title and the inflated layout as the view
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Chỉnh sửa cổng")
            .setView(dialogBinding.root)
            .create()

        // Set click listeners for the buttons in the dialog
        dialogBinding.changeHostPortDBtnExit.setOnClickListener {
            // Dismiss the dialog without saving any changes
            dialog.dismiss()
        }

        dialogBinding.changeHostPortDBtnAutoDetect.setOnClickListener {
            // Auto-detect and set the port number and dismiss the dialog
            dialog.dismiss()
            settingsViewModel.detectPort()
        }

        dialogBinding.changeHostPortDBtnSetDefault.setOnClickListener {
            // Set the port number to the default value and dismiss the dialog
            settingsViewModel.setDefaultHostPort()
            dialog.dismiss()
        }

        dialogBinding.changeHostPortDBtnSave.setOnClickListener {
            // Check if the entered port number is empty and show a toast message if it is
            if (showToastIfEditTextIsEmpty(dialogBinding.changeHostPortDEtHostPort)) {
                return@setOnClickListener
            }

            // Get the trimmed port number from the EditText
            val port = dialogBinding.changeHostPortDEtHostPort.text.toString().trim()

            // Update the ViewModel with the new port number and dismiss the dialog
            settingsViewModel.setHostPort(port)
            dialog.dismiss()
        }

        // Show the dialog
        dialog.show()
    }


    /**
     * Function to open a dialog for changing the hostname of the server.
     * The dialog allows the user to enter a custom hostname or set it to the default value.
     * The user input is validated to ensure it's not empty and is a valid URL.
     * If the input is valid, the ViewModel's corresponding method is called to update the hostname.
     * The dialog contains three buttons: "Save", "Set to Default", and "Exit".
     * - "Save" button: Saves the entered hostname if it's valid and dismisses the dialog.
     * - "Set to Default" button: Sets the hostname to the default value and dismisses the dialog.
     * - "Exit" button: Closes the dialog without saving any changes.
     *
     * The dialog is created using MaterialAlertDialogBuilder from the current fragment's context.
     * The layout for the dialog is inflated using DialogChangeHostNameBinding.
     * The MaterialAlertDialogBuilder is used to set the title, view (the inflated layout), and create the dialog.
     *
     * @see DialogChangeHostNameBinding: The layout binding for the change hostname dialog.
     * @see NetworkHelper.isValidURL(): A utility function to check if the entered hostname is a valid URL.
     * @see settingsViewModel: An instance of the ViewModel responsible for managing the app's settings.
     */
    private fun openChangeHostnameDialog() {
        // Inflate the layout for the change hostname dialog
        val dialogBinding = DialogChangeHostNameBinding.inflate(layoutInflater)

        // Create the MaterialAlertDialogBuilder with the title and the inflated layout as the view
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Chỉnh sửa địa chỉ máy chủ")
            .setView(dialogBinding.root)
            .create()

        // Set click listeners for the buttons in the dialog
        dialogBinding.changeHostNameDBtnSave.setOnClickListener {
            // Check if the entered hostname is empty and show a toast message if it is
            if (showToastIfEditTextIsEmpty(dialogBinding.changeHostNameDEtHostName)) {
                return@setOnClickListener
            }

            // Get the trimmed hostname from the EditText
            val hostname = dialogBinding.changeHostNameDEtHostName.text.toString().trim()

            // Validate the entered hostname
            if (!NetworkHelper.isValidURL(hostname)) {
                // Show a toast message if the entered hostname is not a valid URL
                Toast.makeText(
                    requireContext(),
                    "Vui lòng nhập địa chỉ hợp lệ!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Update the ViewModel with the new hostname and dismiss the dialog
            settingsViewModel.setHostName(hostname)
            dialog.dismiss()
        }

        dialogBinding.changeHostNameDBtnSetToDefault.setOnClickListener {
            // Set the hostname to the default value and dismiss the dialog
            settingsViewModel.setDefaultHostName()
            dialog.dismiss()
        }

        dialogBinding.changeHostNameDBtnExit.setOnClickListener {
            // Dismiss the dialog without saving any changes
            dialog.dismiss()
        }

        // Show the dialog
        dialog.show()
    }

}