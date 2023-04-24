package net.cherrycave.birgid.utils

import net.cherrycave.birgid.GertrudClient
import net.cherrycave.birgid.GertrudClientImpl

@OptIn(Internal::class)
internal val GertrudClient.implementation: GertrudClientImpl
    get() = this as GertrudClientImpl