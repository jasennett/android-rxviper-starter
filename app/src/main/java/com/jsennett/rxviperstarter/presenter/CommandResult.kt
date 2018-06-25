package com.jsennett.rxviperstarter.presenter

sealed class CommandResult<out TState>
class NoOp<out TState> : CommandResult<TState>()
class NewState<out TState>(val state: TState) : CommandResult<TState>()