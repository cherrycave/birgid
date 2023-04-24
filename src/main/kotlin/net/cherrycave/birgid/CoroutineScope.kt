package net.cherrycave.birgid

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

internal val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())