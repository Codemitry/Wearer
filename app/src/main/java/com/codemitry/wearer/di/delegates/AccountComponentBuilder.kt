package com.codemitry.wearer.di.delegates

import com.codemitry.wearer.di.components.AccountComponent

interface AccountComponentBuilder {
    fun build(): AccountComponent
}