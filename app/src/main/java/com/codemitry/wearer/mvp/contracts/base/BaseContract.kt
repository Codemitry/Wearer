package com.codemitry.wearer.mvp.contracts.base

abstract class BaseContract {
    interface BasePresenter<V : BaseView> {
        var view: V?
        fun onViewAttached(view: V) {
            this.view = view
        }

        fun onViewDetached() {
            this.view = null
        }
    }

    interface BaseView

}