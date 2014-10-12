environments {
    development {
        grails {
            mongo {
                host = "localhost"
                databaseName = "mb_locations"
            }
        }
    }
    test {
        grails {
            mongo {
                host = "localhost"
                databaseName = "mb_locations"
            }
        }
    }
    production {
        grails {
            mongo {

                // replicaSet = []
                host = "localhost"
                username = ""
                password = ""
                databaseName = "mb_locations"
            }
        }
    }
}