package eu.dezeekees.melay.common

object Routes {
    object Api {
        const val NAME = "/api"

        object Auth {
            const val NAME = "${Api.NAME}/auth";
            const val LOGIN = "${NAME}/login";
        }

        object User {
            const val NAME = "${Api.NAME}/user"
        }
    }
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
