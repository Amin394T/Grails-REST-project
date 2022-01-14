package emsi.mbds

class Illustration {

    String filename

    static belongsTo = [annonce: Annonce]

    static constraints = {
        filename nullable: false, blank: false
    }
}
