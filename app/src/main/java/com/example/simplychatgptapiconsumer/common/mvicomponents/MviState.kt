package com.example.simplychatgptapiconsumer.common.mvicomponents

import com.example.simplychatgptapiconsumer.main.mvimodel.ResponseType

abstract class MviState {
    abstract val type: ResponseType
}
