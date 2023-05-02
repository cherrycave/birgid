package net.cherrycave.birgid.utils

import net.cherrycave.birgid.GertrudClient
import net.cherrycave.birgid.GertrudClientImpl

@Internal
public val GertrudClient.implementation: GertrudClientImpl
    get() = this as GertrudClientImpl