package emsi.mbds

class Annonce {

    String title
    String description
    Float price
    String ref
    Boolean status = Boolean.FALSE

    Date dateCreated
    Date lastUpdated

    List illustrations

    static hasMany = [illustrations: Illustration]

    static belongsTo = [author: User]

    static constraints = {
        title nullable: false, blank: false, size: 3..55
        description nullable: false, blank: false
        price nullable: true, scale: 2, min: 0F
        ref nullable: false, blank: false, unique: true
        status nullable: false
    }

    static mapping = {
        description type: 'text'
    }
}
