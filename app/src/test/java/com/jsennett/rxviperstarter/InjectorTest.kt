package com.jsennett.rxviperstarter

import com.jsennett.rxviperstarter.dagger.ITargetInjector
import com.jsennett.rxviperstarter.dagger.Injector
import org.junit.Test

class InjectorTest {
    class ClassOne {
        var injected: Boolean = false
    }

    class ClassTwo {
        var injected: Boolean = false
    }

    class ClassThree {
        var injected: Boolean = false
    }

    class InjectorOne : ITargetInjector<ClassOne> {
        override fun inject(target: ClassOne) {
            target.injected = true
        }
    }

    class InjectorTwo : ITargetInjector<ClassTwo> {
        override fun inject(target: ClassTwo) {
            target.injected = true
        }
    }

    @Test
    fun testInjector() {
        val injector = Injector.builder()
                .addTargetInjector(ClassOne::class.java, InjectorOne())
                .addTargetInjector(ClassTwo::class.java, InjectorTwo())
                .build()

        val one = ClassOne()
        val two = ClassTwo()
        val three = ClassThree()
        assert(injector.inject(one)) { "Should be able to inject ClassOne" }
        assert(one.injected) { "ClassTwo should be injected" }
        assert(injector.inject(two)) { "Should be able to inject ClassTwo" }
        assert(two.injected) { "ClassTwo should be injected" }
        assert(!injector.inject(three)) { "Should not be able to inject ClassThree" }
        assert(!three.injected) { "ClassThree should not be injected" }
    }
}