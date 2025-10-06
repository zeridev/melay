package eu.dezeekees.melay.common

object Routes {
    object Ws {
        object Federation {
            const val NAME = "federation"

            object Send {
                const val NAME = "${Federation.NAME}.send"
            }

            object Stream {
                const val NAME = "${Federation.NAME}.stream"
            }
        }
    }
}
