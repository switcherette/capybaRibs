package com.switcherette.boarribs.camera

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.badoo.binder.using
import com.badoo.mvicore.android.lifecycle.createDestroy
import com.badoo.mvicore.android.lifecycle.startStop
import com.badoo.ribs.clienthelper.interactor.Interactor
import com.badoo.ribs.core.modality.BuildParams
import com.switcherette.boarribs.camera.feature.CameraFeature
import com.switcherette.boarribs.camera.mapper.InputToWish
import com.switcherette.boarribs.camera.mapper.NewsToOutput
import com.switcherette.boarribs.camera.mapper.StateToViewModel
import com.switcherette.boarribs.camera.mapper.ViewEventToWish

internal class CameraInteractor(
    buildParams: BuildParams<*>,
    private val feature: CameraFeature,
) : Interactor<Camera, CameraView>(
    buildParams = buildParams
) {

    override fun onCreate(nodeLifecycle: Lifecycle) {
        nodeLifecycle.createDestroy {
            bind(feature.news to rib.output using NewsToOutput)
            bind(rib.input to feature using InputToWish)
        }
    }

    override fun onViewCreated(view: CameraView, viewLifecycle: Lifecycle) {
        viewLifecycle.startStop {
            bind(feature to view using StateToViewModel)
            bind(view to feature using ViewEventToWish)
        }

        viewLifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                feature.accept(CameraFeature.Wish.StartCameraIfReady)
            }
        })

    }

}
