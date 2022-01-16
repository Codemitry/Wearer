package com.codemitry.wearer.mvp.usecases

import com.codemitry.wearer.db.ActionCompleteListener

interface DeleteAccountUseCase: BaseUseCase, (ActionCompleteListener) -> Unit {
}