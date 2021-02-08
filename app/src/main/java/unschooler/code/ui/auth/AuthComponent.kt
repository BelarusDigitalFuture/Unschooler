package unschooler.code.ui.auth

import dagger.Subcomponent
import dagger.android.AndroidInjector
import javax.inject.Scope

@Subcomponent(
    modules = [
    ]
)
@AuthScope
interface AuthComponent : AndroidInjector<AuthActivity> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<AuthActivity>
}

@Scope
@Retention
annotation class AuthScope