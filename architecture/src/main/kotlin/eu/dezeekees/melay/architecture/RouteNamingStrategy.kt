package eu.dezeekees.melay.architecture

import com.structurizr.component.Type
import com.structurizr.component.naming.DefaultNamingStrategy
import com.structurizr.component.naming.NamingStrategy
import java.rmi.Naming

class RouteNamingStrategy: NamingStrategy {
    override fun nameOf(type: Type?): String? {
        val defaultNamingStrategy = DefaultNamingStrategy()
        val defaultName = defaultNamingStrategy.nameOf(type)

        // remove Kt from end of name
        return defaultName
            ?.removeSuffix("Kt")
            ?.trim()
    }
}