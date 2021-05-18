package com.codemitry.wearer.di.delegates

import com.codemitry.wearer.di.components.SignInComponent

interface SignInComponentBuilder {
    fun build(): SignInComponent
}