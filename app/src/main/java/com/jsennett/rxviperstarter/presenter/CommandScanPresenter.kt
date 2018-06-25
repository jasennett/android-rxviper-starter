package com.jsennett.rxviperstarter.presenter

import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class CommandScanPresenter<TState, TCommand : Command<TState>> : PresenterBase() {
    protected val savedState: BehaviorSubject<TState> = BehaviorSubject.create<TState>()
    protected val commands: PublishSubject<TCommand> = PublishSubject.create<TCommand>()
    protected lateinit var state: Observable<TState>

    abstract fun getInitialState(arguments: Bundle?, savedInstanceState: Bundle?): TState

    open fun subscribeCreate() {}

    open fun getResumeCommands(): List<Observable<TCommand>> = listOf()

    open fun getCreateCommands(): List<Observable<TCommand>> = listOf()

    open fun getCreateCommands(initialState: TState): List<Observable<TCommand>> = getCreateCommands()

    override fun onCreate(arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onCreate(arguments, savedInstanceState)

        val initialState = getInitialState(arguments, savedInstanceState)

        val publishedState = commands
                .observeOn(Schedulers.computation())
                .scan(AccumulatorNewState(initialState)) { previousState: AccumulatorResult<TState>, command: TCommand ->
                    val commandResult = command.handle(previousState.state)
                    when (commandResult) {
                        is NoOp -> AccumulatorNoOp(previousState.state)
                        is NewState -> AccumulatorNewState(commandResult.state)
                    }
                }
                .filter { it is AccumulatorNewState<TState> }
                .map { (it as AccumulatorNewState<TState>).state }
                .publish()

        state = publishedState
        state.subscribe { savedState.onNext(it) }
                .addTo(destroyDisposables)

        subscribeCreate()

        publishedState.connect()
                .addTo(destroyDisposables)

        val createCommands = getCreateCommands(initialState)
        if (createCommands.isNotEmpty()) {
            Observable.merge<TCommand>(createCommands)
                    .subscribe { commands.onNext(it) }
                    .addTo(destroyDisposables)
        }
    }

    override fun onResume() {
        super.onResume()

        val resumeCommands = getResumeCommands()
        if (resumeCommands.isNotEmpty()) {
            Observable.merge<TCommand>(resumeCommands)
                    .subscribe { commands.onNext(it) }
                    .addTo(pauseDisposables)
        }
    }
}

sealed class AccumulatorResult<out TState>(val state: TState)
class AccumulatorNoOp<out TState>(previousState: TState): AccumulatorResult<TState>(previousState)
class AccumulatorNewState<out TState>(newState: TState): AccumulatorResult<TState>(newState)