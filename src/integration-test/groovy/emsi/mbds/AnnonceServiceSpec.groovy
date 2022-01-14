package emsi.mbds

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class AnnonceServiceSpec extends Specification {

    AnnonceService annonceService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Annonce(...).save(flush: true, failOnError: true)
        //new Annonce(...).save(flush: true, failOnError: true)
        //Annonce annonce = new Annonce(...).save(flush: true, failOnError: true)
        //new Annonce(...).save(flush: true, failOnError: true)
        //new Annonce(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //annonce.id
    }

    void "test get"() {
        setupData()

        expect:
        annonceService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Annonce> annonceList = annonceService.list(max: 2, offset: 2)

        then:
        annonceList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        annonceService.count() == 5
    }

    void "test delete"() {
        Long annonceId = setupData()

        expect:
        annonceService.count() == 5

        when:
        annonceService.delete(annonceId)
        sessionFactory.currentSession.flush()

        then:
        annonceService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Annonce annonce = new Annonce()
        annonceService.save(annonce)

        then:
        annonce.id != null
    }
}
