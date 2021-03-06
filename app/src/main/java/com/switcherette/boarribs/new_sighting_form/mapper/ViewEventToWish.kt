package com.switcherette.boarribs.new_sighting_form.mapper

import com.switcherette.boarribs.new_sighting_form.NewSightingFormView.Event
import com.switcherette.boarribs.new_sighting_form.feature.NewSightingFormFeature.Wish

internal object ViewEventToWish : (Event) -> Wish? {

    override fun invoke(event: Event): Wish? =
        when (event) {
            is Event.SaveSighting -> Wish.SaveSighting(
                event.heading,
                event.adults,
                event.pups,
                event.interaction,
                event.comments
            )
            is Event.CameraRequested -> Wish.RequestCameraStart
        }
}
