package com.jsennett.rxviperstarter.presenter

abstract class Command<TState> {
    abstract fun handle(previousState: TState): CommandResult<TState>
}