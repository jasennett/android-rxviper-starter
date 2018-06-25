package com.jsennett.rxviperstarter.dagger

/**
 * Class to inject targets using a collection of configured [ITargetInjector] instances.
 */
class Injector private constructor(private val injectors: Map<Class<*>, ITargetInjector<*>>){
    /**
     * Inject the target using the configured [ITargetInjector] that matches the target's class.
     * @param target The object to inject.
     * @param TInjectionTarget The type of the target that will be injected.
     * @return True if the target was successfully injected, false if there was no [ITargetInjector] configured for the object's class.
     */
    fun <TInjectionTarget : Any> inject(target: TInjectionTarget): Boolean {
        @Suppress("UNCHECKED_CAST")
        val injector = injectors[target.javaClass] as? ITargetInjector<TInjectionTarget> ?: return false
        injector.inject(target)
        return true
    }

    /**
     * Builder class for [Injector].
     */
    class Builder {
        private val injectors = mutableMapOf<Class<out Any>, ITargetInjector<out Any>>()

        /**
         * Returns the constructed [Injector] instance.
         * @return A new instance of [Injector].
         */
        fun build(): Injector {
            return Injector(injectors)
        }

        /**
         * Add the [ITargetInjector] instance for the given class.
         * @param clazz The class that will be injected by the given [ITargetInjector].
         * @param injector The instance of [ITargetInjector] that will perform the injection.
         * @param TInjectionTarget The type of the target that will be injected.
         * @return The instance of [Builder] for method chaining.
         */
        fun <TInjectionTarget : Any> addTargetInjector(clazz: Class<TInjectionTarget>, injector: ITargetInjector<TInjectionTarget>): Builder {
            injectors[clazz] = injector
            return this
        }
    }

    companion object {
        /**
         * Creates a new instance of the [Builder].
         * @return Instance of the [Builder].
         */
        fun builder(): Builder {
            return Builder()
        }
    }
}