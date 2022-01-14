package emsi.mbds

import grails.converters.JSON
import grails.converters.XML
import grails.plugin.springsecurity.annotation.Secured
import org.apache.commons.lang.RandomStringUtils

class ApiController {

    /**
     * GET / PUT / PATCH / DELETE
     * Pour une note max : Gérer la notion de role en plus de l'utilisateur
     */
    @Secured('ROLE_ADMIN')
    def user() {
        switch (request.getMethod()) {

            case "GET":
                    if (!params.id)
                        return response.status = 400

                    def userInstance = User.get(params.id)
                    if (!userInstance)
                        return response.status = 404
                    response.withFormat {
                        xml { render userInstance as XML }
                        json { render userInstance as JSON }
                    }
                break

            case "PUT":
                if (!params.id)
                    return response.status = 400
                def userInstance = User.get(params.id)
                if (!userInstance)
                    return response.status = 404

                def username = request.JSON.username
                userInstance.username=username

                def password = request.JSON.password
                if (password!= userInstance.password)
                    userInstance.password=password

                userInstance.save(flush:true)
                response.withFormat {
                    xml { render userInstance as XML }
                    json { render userInstance as JSON }
                }
                break;

            case "PATCH":
                if (!params.id)
                    return response.status = 400
                def userInstance = User.get(params.id)
                if (!userInstance)
                    return response.status = 404

                if (request.JSON.username) {
                def username = request.JSON.username
                userInstance.username=username
                }

                if (request.JSON.password) {
                    def password = request.JSON.password
                    if (password!= userInstance.password)
                        userInstance.password=password
                }

                userInstance.save(flush:true)
                response.withFormat {
                    xml { render userInstance as XML }
                    json { render userInstance as JSON }
                }
                break;

            case "DELETE":
                if (!params.id)
                    return response.status = 400

                def userInstance = User.get(params.id)
                if (!userInstance)
                    return response.status = 404

                UserRole.removeAll(userInstance)
                userInstance.delete(flush: true)

                return response.status = 200
                break;

            default:
                return response.status = 405
                break;
        }
        return response.status = 406
    }

    /**
     * POST / GET
     */
    @Secured('ROLE_ADMIN')
    def users() {
        switch(request.getMethod()) {

            case "GET":
                def userList = User.getAll()
                if (userList.isEmpty())
                    return response.status = 404
                response.withFormat {
                    xml { render userList as XML }
                    json { render userList as JSON }
                }
                break

            case "POST":
                def userInstance = new User(username :request.JSON.username, password : request.JSON.password)
                if(!userInstance.save(flush:true))
                    return response.status = 400
                userInstance.save(flush:true)

                def role = Role.get(request.JSON.role)
                UserRole.create(userInstance,role,true)
                response.withFormat {
                    xml { render userInstance as XML }
                    json { render userInstance as JSON }
                }
                break

            default:
                return response.status= 405
                break
        }
        return response.status = 406

    }

    /**
     * GET / PUT / PATCH / DELETE
     */
    @Secured('ROLE_ADMIN')
    def annonce() {
        switch(request.getMethod()) {

            case "GET":
                if (!params.id)
                    return response.status = 400
                def annonceInstance = Annonce.get(params.id)
                if (!annonceInstance){
                    return response.status = 404
                }
                response.withFormat {
                    xml { render annonceInstance as XML}
                    json { render annonceInstance as JSON }
                }
                break

            case "PUT":
                if (!params.id)
                    return response.status = 400
                def annonceInstance = Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status = 404

                def title = request.JSON.title
                annonceInstance.title = title

                def description = request.JSON.description
                annonceInstance.description = description

                def price = request.JSON.price
                annonceInstance.price = price

                def ref = request.JSON.ref
                annonceInstance.ref = ref

                annonceInstance.save(flush :true)

                response.withFormat {
                    xml { render annonceInstance as XML }
                    json { render annonceInstance as JSON }
                }
                break

            case "PATCH":
                if (!params.id)
                    return response.status = 400
                def annonceInstance = Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status = 404

                if (request.JSON.title) {
                    def title = request.JSON.title
                    annonceInstance.title = title
                }

                if (request.JSON.description) {
                    def description = request.JSON.description
                    annonceInstance.description = description
                }

                if (request.JSON.price) {
                    def price = request.JSON.price
                    annonceInstance.price = price
                }

                if (request.JSON.ref) {
                    def ref = request.JSON.ref
                    annonceInstance.ref = ref
                }

                annonceInstance.save(flush :true)

                response.withFormat {
                    xml { render annonceInstance as XML }
                    json { render annonceInstance as JSON }
                }
                break

            case "DELETE":
                if (!params.id)
                    return response.status = 400
                def annonceInstance = Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status = 404

                def userInstance = annonceInstance.author
                userInstance.removeFromAnnonces(annonceInstance)

                annonceInstance.delete(flush : true)
                return response.status = 200

            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }

    /**
     * POST / GET
     * Pour une note maximale : gérer la notion d'illustration
     */
    @Secured('ROLE_ADMIN')
    def annonces() {
        switch(request.getMethod()) {

            case "GET":
                def annonceList = Annonce.getAll()
                if (annonceList.isEmpty())
                    return response.status = 404
                response.withFormat {
                    xml { render annonceList as XML }
                    json { render annonceList as JSON }
                }
                break

            case "POST":
                def annonceInstance = new Annonce(
                        title: request.JSON.title,
                        description: request.JSON.description,
                        price: request.JSON.price,
                        ref: request.JSON.ref)

                def userInstance = User.get(request.JSON.id)
                userInstance.addToAnnonces(annonceInstance)

                if (!userInstance.save(flush: true))
                    return response.status = 400

                response.withFormat {
                    xml { render annonceInstance as XML }
                    json { render annonceInstance as JSON }
                }
                return response.status =201
                break

            default:
                return response.status= 405
                break
        }
        return response.status = 406
    }
}

