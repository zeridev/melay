package eu.dezeekees.melay.common

object Routes {
    object Api {
        const val NAME = "/api"

        object Auth {
            const val NAME = "${Api.NAME}/auth";
            const val LOGIN = "${NAME}/login";
            const val REGISTER = "${NAME}/register";
        }

        object User {
            const val NAME = "${Api.NAME}/user"
            const val ME = "${Api.NAME}/@me"
            const val COMMUNITIES = "${Api.NAME}/communities"
        }

        object Community {
            const val NAME = "${Api.NAME}/community"
            const val MEMBERS = "${NAME}/memberships"
        }

        object Channel {
            const val NAME = "${Api.NAME}/channel"
        }
    }
    object Socket {
        object Federation {
            const val NAME = "federation"

            object Send {
                const val NAME = "${Federation.NAME}.send"
            }

            object Stream {
                const val NAME = "${Federation.NAME}.stream"
            }
        }

        object MelayClient {
            const val CONNECTION_ROUTE = "/melay-client"
        }
    }
}
