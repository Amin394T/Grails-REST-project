package emsi.mbds

class BootStrap {

    def init = { servletContext ->

        def adminRole = new Role(authority: 'ROLE_ADMIN').save()
        def modoRole = new Role(authority: 'ROLE_MODO').save()
        def clientRole = new Role(authority: 'ROLE_CLIENT').save()

        def adminUser = new User(username: 'admin', password: 'admin').save()
        def modoUser = new User(username: 'modo', password: 'modo').save()
        def clientUser = new User(username: 'client', password: 'client').save()

        UserRole.create(adminUser, adminRole, true)
        UserRole.create(modoUser, modoRole, true)
        UserRole.create(clientUser, clientRole, true)

        ["Alice", "Bob", "Charly", "Denis", "Etienne"].each {
            String usernameVar ->
                def userInstance = new User(username: usernameVar, password: 'password').save()
                (1..5).each {
                    Integer annonceIdx ->
                        def annonceInstance = new Annonce(
                                title: "Title $usernameVar $annonceIdx",
                                description: "Description $usernameVar $annonceIdx",
                                price: annonceIdx*100,
                                status: Boolean.TRUE,
                                ref: "#$usernameVar:$annonceIdx"
                        )
                        (1..2).each {
                            annonceInstance.addToIllustrations(new Illustration(filename: "grails.svg"))
                        }
                        userInstance.addToAnnonces(annonceInstance)
                }
                userInstance.save()
        }
    }
    def destroy = {
    }
}
