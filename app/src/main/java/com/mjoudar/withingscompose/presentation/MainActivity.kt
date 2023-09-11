package com.mjoudar.withingscompose.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mjoudar.withingscompose.R
import com.mjoudar.withingscompose.presentation.NavGraph.HomeScreenRoute
import com.mjoudar.withingscompose.presentation.NavGraph.ResultScreenRoute
import com.mjoudar.withingscompose.presentation.ui.screens.home_screen.HomeScreen
import com.mjoudar.withingscompose.presentation.ui.screens.result_screen.ResultScreen
import com.mjoudar.withingscompose.presentation.ui.theme.WithingsComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

@AndroidEntryPoint
class MainActivity : ComponentActivity(), EasyPermissions.PermissionCallbacks {
    private val requestCodePermissions = 111
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestStoragePermissions()

        setContent {
            val navController = rememberNavController()

            val sharedViewModel: SharedViewModel = hiltViewModel()

            WithingsComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = HomeScreenRoute
                    ) {
                        composable(HomeScreenRoute) {
                            HomeScreen(navController, sharedViewModel)
                        }
                        composable(ResultScreenRoute) {
                            ResultScreen(sharedViewModel)
                        }
                    }
                }
            }
        }
    }

    private fun requestStoragePermissions() {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (EasyPermissions.hasPermissions(this, *permissions)) {
            return
        } else {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(
                    this,
                    requestCodePermissions,
                    *permissions
                )
                    .setRationale(getString(R.string.permission_rationale))
                    .setPositiveButtonText(getString(R.string.permission_allow))
                    .setNegativeButtonText(getString(R.string.permission_deny))
                    .build()
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        return
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        AppSettingsDialog.Builder(this).build().show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}