import Database.*
import domain.*
import Engine.*
import Syotetarkistus.*
import static org.mockito.Mockito.*;

description 'kayttaja voi listata viitteet' 

scenario "kayttaja voi listata viitteita tagin perusteella", {
    given 'ohjelmassa viitteita, joilla tagi', {
        db = mock(Database.class)
        engine = new EngineStub(db)

        viite1 = new Article(new Syotetarkastaja())
        viite1.lisaaCitationKey("avain1")
        viite1.lisaaKentta(Kentta.author, "Kauko Kirjoittaja")
        viite1.lisaaKentta(Kentta.title, "Nimi")
        viite1.lisaaKentta(Kentta.year, "1987")
        viite1.lisaaKentta(Kentta.journal, "Lehti")

        viite2 = new Book(new Syotetarkastaja())
        viite2.lisaaCitationKey("avain2")
        viite2.lisaaKentta(Kentta.editor, "Touko Toimittaja")
        viite2.lisaaKentta(Kentta.title, "Nimeke")
        viite2.lisaaKentta(Kentta.year, "1975")
        viite2.lisaaKentta(Kentta.publisher, "Julkaisija")

        viitteet = new ArrayList<Viite>()
        viitteet.add(viite1)
        viitteet.add(viite2)

        tagit = ["eka", "toka"]

        when(db.getTagit("")).thenReturn tagit
        when(db.listByTag("eka")).thenReturn viitteet
    } 

    when 'kayttaja valitsee yhden tagin', {
        tagivalikko = engine.getTagit()
        lista = engine.listaaByTag(tagivalikko[0])
    }

    then 'tagilla olevat viitteet listautuvat', {
        lista.length.shouldBeEqual 2
        lista.shouldHave viite1.toString()
        lista.shouldHave viite2.toString()
    }
}

scenario "kayttaja voi listata viitteita tagin perusteella, kun viitteilla eri tagit", {
    given 'ohjelmassa viitteita, joilla eri tagit', {
        db = mock(Database.class)
        engine = new EngineStub(db)

        viite1 = new Article(new Syotetarkastaja())
        viite1.lisaaCitationKey("avain1")
        viite1.lisaaKentta(Kentta.author, "Kauko Kirjoittaja")
        viite1.lisaaKentta(Kentta.title, "Nimi")
        viite1.lisaaKentta(Kentta.year, "1987")
        viite1.lisaaKentta(Kentta.journal, "Lehti")

        viitteet1 = new ArrayList<Viite>()
        viitteet1.add(viite1)

        viite2 = new Book(new Syotetarkastaja())
        viite2.lisaaCitationKey("avain2")
        viite2.lisaaKentta(Kentta.editor, "Touko Toimittaja")
        viite2.lisaaKentta(Kentta.title, "Nimeke")
        viite2.lisaaKentta(Kentta.year, "1975")
        viite2.lisaaKentta(Kentta.publisher, "Julkaisija")

        viitteet2 = new ArrayList<Viite>()
        viitteet2.add(viite2)

        when(db.listByTag("tag1")).thenReturn viitteet1
        when(db.listByTag("tag2")).thenReturn viitteet2
    } 

    when 'kayttaja valitsee tagin', {
        lista1 = engine.listaaByTag("tag1")
    }

    then 'tagilla olevat viitteet listautuvat', {
        lista1.length.shouldBeEqual 1
        lista1.shouldHave viite1.toString()
    }
}